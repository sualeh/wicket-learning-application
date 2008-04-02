/*
 * Copyright 2007-2008, Sualeh Fatehi <sualeh@hotmail.com>
 * 
 * This work is licensed under the Creative Commons Attribution 3.0 License. 
 * To view a copy of this license, visit 
 * http://creativecommons.org/licenses/by/3.0/ 
 * or send a letter to 
 * Creative Commons
 * 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package sf.wicketlearningapplication.server;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.Severity;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;
import sf.wicketlearningapplication.persistence.Persistence;
import sf.wicketlearningapplication.persistence.UserDao;

public class TestUtility
{
  private static final Random random = new Random();

  public static Bug createNewBugInstance()
  {
    final int estimatedHours = random.nextInt(8) + 1;
    final Severity severity = Severity.values()[(int) (Math.random() * Severity
      .values().length)];
    final Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, random.nextInt(30));
    //
    final Bug bug = new Bug();
    bug.setSummary(text());
    bug.setSeverity(severity);
    bug.setEstimatedHours(estimatedHours);
    bug.setDueByDate(calendar.getTime());
    return bug;
  }

  public static User createNewUserInstance(final int userNumber)
  {
    final User user = new User();
    user.setName("User #" + userNumber);
    user.setUsername("user" + userNumber);
    user.setPassword("user" + userNumber);
    return user;
  }

  static void createData()
  {
    final int USER_COUNT = 3;
    final int BUG_COUNT = 45;

    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();

    final List<User> users = new ArrayList<User>();
    final UserDao userDao = new UserDao(em);
    userDao.beginTransaction();
    for (int i = 0; i < USER_COUNT; i++)
    {
      final User user = createNewUserInstance(i);
      if (i == 0)
      {
        user.setAdmin(true);
      }
      // 
      userDao.create(user);
      users.add(user);
    }
    userDao.commitTransaction();

    final BugDao bugDao = new BugDao(em);
    bugDao.beginTransaction();
    for (int i = 0; i < BUG_COUNT; i++)
    {
      final Bug bug = createNewBugInstance();
      bug.setOwner(users.get(random.nextInt(USER_COUNT - 1) + 1));
      // 
      bugDao.create(bug);
    }
    bugDao.commitTransaction();

    em.clear();
    em.close();
  }

  private static String text()
  {
    final StringBuffer buffer = new StringBuffer();
    final int words = random.nextInt(3) + 3;
    for (int i = 0; i < words; i++)
    {
      buffer.append(RandomStringUtils.randomAlphabetic(random.nextInt(5) + 1))
        .append(" ");
    }
    return StringUtils.capitalize(buffer.toString().toLowerCase());
  }

  private TestUtility()
  {
  }

}
