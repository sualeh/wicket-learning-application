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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.persistence.BugDao;
import sf.wicketlearningapplication.server.DatabaseServer;
import sf.wicketlearningapplication.server.ServersMain;

public class TestSpringJpa
{

  private static final String BUG_COUNT_SQL = "SELECT COUNT(*) FROM Bug";

  private ClassPathXmlApplicationContext context;
  private DatabaseServer databaseServer;

  @Test
  public void daoHelper()
  {

    final EntityManagerFactory entityManagerFactory = (EntityManagerFactory) context
      .getBean("entityManagerFactory", EntityManagerFactory.class);
    final JdbcTemplate jdbcTemplate = (JdbcTemplate) context
      .getBean("jdbcTemplate", JdbcTemplate.class);

    int bugCount;
    Bug bug;

    final EntityManager em = entityManagerFactory.createEntityManager();
    final BugDao bugDao = new BugDao(em);
    EntityTransaction transaction;

    transaction = em.getTransaction();
    transaction.begin();

    bug = ServersMain.createNewBugInstance();
    bugDao.create(bug);
    transaction.commit();

    bug = null;

    bugCount = jdbcTemplate.queryForInt(BUG_COUNT_SQL);
    assertEquals("Save test: Number of bugs do not match", 1, bugCount);

    Collection<Bug> bugs;
    transaction = em.getTransaction();
    transaction.begin();
    bugs = bugDao.findAll(Bug.class);
    for (final Iterator<Bug> iter = bugs.iterator(); iter.hasNext();)
    {
      bug = iter.next();
      if (bug == null)
      {
        throw new RuntimeException("Got null entity");
      }
      bugDao.delete(bug);
      bug = null;
    }
    transaction.commit();

    bug = null;

    bugCount = jdbcTemplate.queryForInt(BUG_COUNT_SQL);
    assertEquals("Delete test: Number of bugs do not match", 0, bugCount);

  }

  @Before
  public void setUp()
  {
    databaseServer = new DatabaseServer();
    databaseServer.start();

    context = new ClassPathXmlApplicationContext("context.xml");
  }

  @After
  public void tearDown()
  {
    context.close();
    context = null;

    databaseServer.stop();
    databaseServer = null;
  }

}
