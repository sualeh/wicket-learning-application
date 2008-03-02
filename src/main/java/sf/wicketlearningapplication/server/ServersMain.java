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

import sf.wicketlearningapplication.domain.Duration;
import sf.wicketlearningapplication.domain.DurationType;
import sf.wicketlearningapplication.domain.Event;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.EventDataAccessOperator;
import sf.wicketlearningapplication.persistence.Persistence;
import sf.wicketlearningapplication.persistence.UserDataAccessOperator;

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
    final int EVENT_COUNT = 45;

    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();

    final List<User> users = new ArrayList<User>();
    final UserDataAccessOperator userDao = new UserDataAccessOperator(em);
    userDao.beginTransaction();
    for (int i = 0; i < USER_COUNT; i++)
    {
      final User user = new User();
      user.setName("User #" + i);
      user.setUsername("user" + i);
      user.setPassword("user" + i);
      // 
      userDao.create(user);
      users.add(user);
    }
    userDao.commitTransaction();

    final EventDataAccessOperator eventDao = new EventDataAccessOperator(em);
    eventDao.beginTransaction();
    for (int i = 0; i < EVENT_COUNT; i++)
    {
      final int duration = (int) (Math.random() * 100);
      final DurationType durationType = DurationType.values()[(int) (Math
        .random() * DurationType.values().length)];
      final Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_MONTH, (int) (Math.random() * 30));
      //
      final Event event = new Event();
      event.setName("Event #" + (i + 1));
      event.setDuration(new Duration(duration, durationType));
      event.setStartDate(calendar.getTime());
      event.setOwner(users.get((int) (Math.random() * 100 % USER_COUNT)));
      // 
      eventDao.create(event);
    }
    eventDao.commitTransaction();

    em.clear();
    em.close();
  }
}
