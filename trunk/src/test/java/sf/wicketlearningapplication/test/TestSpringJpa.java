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
package sf.wicketlearningapplication.test;


import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.EntityManagerFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;
import sf.wicketlearningapplication.persistence.UserDao;
import sf.wicketlearningapplication.server.DatabaseServer;
import sf.wicketlearningapplication.server.TestUtility;

public class TestSpringJpa
{

  private static final String BUG_COUNT_SQL = "SELECT COUNT(*) FROM Bug";
  private static final String USER_COUNT_SQL = "SELECT COUNT(*) FROM User";

  private static ClassPathXmlApplicationContext context;
  private static DatabaseServer databaseServer;

  @BeforeClass
  public static void start()
  {
    databaseServer = new DatabaseServer();
    databaseServer.start();

    context = new ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");
  }

  @AfterClass
  public static void tearDown()
  {
    context.close();
    context = null;

    databaseServer.stop();
    databaseServer = null;
  }

  @Test
  public void bugs()
  {

    final EntityManagerFactory entityManagerFactory = (EntityManagerFactory) context
      .getBean("entityManagerFactory", EntityManagerFactory.class);
    final JdbcTemplate jdbcTemplate = (JdbcTemplate) context
      .getBean("jdbcTemplate", JdbcTemplate.class);

    int count;
    Bug bug;

    final BugDao dao = new BugDao(entityManagerFactory);
    bug = TestUtility.createNewBugInstance();
    dao.save(bug);

    count = jdbcTemplate.queryForInt(BUG_COUNT_SQL);
    assertEquals("Save test: Number of bugs do not match", 1, count);

    final Collection<Bug> bugs = dao.findAll(bug.getOwner(), null, true, 0, 10);
    bug = null;
    for (final Iterator<Bug> iter = bugs.iterator(); iter.hasNext();)
    {
      bug = iter.next();
      if (bug == null)
      {
        throw new RuntimeException("Got null entity");
      }
      dao.delete(bug);
      bug = null;
    }

    count = jdbcTemplate.queryForInt(BUG_COUNT_SQL);
    assertEquals("Delete test: Number of bugs do not match", 0, count);

  }

  @Test
  public void users()
  {

    final EntityManagerFactory entityManagerFactory = (EntityManagerFactory) context
      .getBean("entityManagerFactory", EntityManagerFactory.class);
    final JdbcTemplate jdbcTemplate = (JdbcTemplate) context
      .getBean("jdbcTemplate", JdbcTemplate.class);

    int count;
    User user;

    final UserDao dao = new UserDao(entityManagerFactory);
    user = TestUtility.createNewUserInstance(1);
    dao.save(user);

    user = null;

    count = jdbcTemplate.queryForInt(USER_COUNT_SQL);
    assertEquals("Save test: Number of users do not match", 1, count);

    Collection<User> users;
    users = dao.findAll();
    for (final Iterator<User> iter = users.iterator(); iter.hasNext();)
    {
      user = iter.next();
      if (user == null)
      {
        throw new RuntimeException("Got null entity");
      }
      dao.delete(user);
      user = null;
    }

    count = jdbcTemplate.queryForInt(USER_COUNT_SQL);
    assertEquals("Delete test: Number of users do not match", 0, count);

  }

}
