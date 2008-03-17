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

import javax.persistence.EntityManager;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.Severity;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;
import sf.wicketlearningapplication.persistence.Persistence;
import sf.wicketlearningapplication.persistence.UserDao;

public class ServersMain
{

  public static void main(final String[] args)
  {
    try
    {
      final DatabaseServer databaseServer = new DatabaseServer();
      databaseServer.start();
      createData();

      final String webApplicationPath = args[0];
      final int port = Integer.parseInt(args[1]);
      final WebApplicationServer webApplicationServer = new WebApplicationServer(webApplicationPath,
                                                                                 port);
      webApplicationServer.start();

      while (System.in.available() == 0)
      {
        Thread.sleep(5000);
      }
      webApplicationServer.stop();
      databaseServer.stop();
    }
    catch (final Exception e)
    {
      e.printStackTrace();
      System.exit(100);
    }
  }

  private static void createData()
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
      bug.setOwner(users.get((int) (Math.random() * 100 % USER_COUNT)));
      // 
      bugDao.create(bug);
    }
    bugDao.commitTransaction();

    em.clear();
    em.close();
  }

  public static User createNewUserInstance(int userNumber)
  {
    final User user = new User();
    user.setName("User #" + userNumber);
    user.setUsername("user" + userNumber);
    user.setPassword("user" + userNumber);
    return user;
  }

  public static Bug createNewBugInstance()
  {
    final int estimatedHours = (int) (Math.random() * 8);
    final Severity severity = Severity.values()[(int) (Math.random() * Severity
      .values().length)];
    final Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, (int) (Math.random() * 30));
    //
    final Bug bug = new Bug();
    bug.setSummary("This is a " + severity + " severity bug");
    bug.setSeverity(severity);
    bug.setEstimatedHours(estimatedHours);
    bug.setDueByDate(calendar.getTime());
    return bug;
  }

}
