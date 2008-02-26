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
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import sf.wicketlearningapplication.domain.Duration;
import sf.wicketlearningapplication.domain.DurationType;
import sf.wicketlearningapplication.domain.Event;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.DataAccessOperator;
import sf.wicketlearningapplication.persistence.Persistence;

public class ServersMain
{
  private static final int USER_COUNT = 3;
  private static final int EVENT_COUNT = 45;

  public static synchronized void createData()
  {
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();

    final List<User> users = new ArrayList<User>();
    final DataAccessOperator<User> userDao = new DataAccessOperator<User>(em);
    userDao.beginTransaction();
    for (int i = 0; i < USER_COUNT; i++)
    {
      final User user = new User();
      user.setName("User #" + i);
      user.setUsername("user" + i);
      user.setPassword("user" + i);
      // 
      userDao.save(user);
      users.add(user);
    }
    userDao.commitTransaction();

    final DataAccessOperator<Event> eventDao = new DataAccessOperator<Event>(em);
    eventDao.beginTransaction();
    for (int i = 0; i < EVENT_COUNT; i++)
    {
      final Event event = new Event();
      event.setName("Event #" + i);
      event.setDuration(new Duration((int) (Math.random() * 100),
                                     DurationType.days));
      event.setStartDate(new Date());
      event.setOwner(users.get((int) (Math.random() * 100 % USER_COUNT)));
      // 
      eventDao.save(event);
    }
    eventDao.commitTransaction();

    em.clear();
    em.close();
  }

  public static void main(final String[] args)
  {
    try
    {
      // Start the database server
      final DatabaseServer databaseServer = new DatabaseServer();
      databaseServer.start();

      // Create dummy data
      createData();

      // Start web container
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

}
