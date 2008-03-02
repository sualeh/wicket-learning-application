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
import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import sf.wicketlearningapplication.domain.Duration;
import sf.wicketlearningapplication.domain.DurationType;
import sf.wicketlearningapplication.domain.Event;
import sf.wicketlearningapplication.persistence.EventDataAccessOperator;
import sf.wicketlearningapplication.server.DatabaseServer;

public class TestSpringJpa
{

  private static final String EVENT_COUNT_SQL = "SELECT COUNT(*) FROM Event";

  private ClassPathXmlApplicationContext context;
  private DatabaseServer databaseServer;

  @Test
  public void daoHelper()
  {

    final EntityManagerFactory entityManagerFactory = (EntityManagerFactory) context
      .getBean("entityManagerFactory", EntityManagerFactory.class);
    final JdbcTemplate jdbcTemplate = (JdbcTemplate) context
      .getBean("jdbcTemplate", JdbcTemplate.class);

    int eventCount;
    Event event;

    final EntityManager em = entityManagerFactory.createEntityManager();
    final EventDataAccessOperator eventDao = new EventDataAccessOperator(em);
    EntityTransaction transaction;

    transaction = em.getTransaction();
    transaction.begin();

    event = createNewEvent();
    eventDao.create(event);
    transaction.commit();

    event = null;

    eventCount = jdbcTemplate.queryForInt(EVENT_COUNT_SQL);
    assertEquals("Save test: Number of events do not match", 1, eventCount);

    Collection<Event> events;
    transaction = em.getTransaction();
    transaction.begin();
    events = eventDao.findAll(Event.class);
    for (final Iterator<Event> iter = events.iterator(); iter.hasNext();)
    {
      event = iter.next();
      if (event == null)
      {
        throw new RuntimeException("Got null entity");
      }
      eventDao.delete(event);
      event = null;
    }
    transaction.commit();

    event = null;

    eventCount = jdbcTemplate.queryForInt(EVENT_COUNT_SQL);
    assertEquals("Delete test: Number of events do not match", 0, eventCount);

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

  private Event createNewEvent()
  {
    final Event event = new Event();
    event.setName("Event");
    event.setDuration(new Duration((int) (Math.random() * 100),
                                   DurationType.days));
    event.setStartDate(new Date());
    return event;
  }

}
