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
package sf.wicketlearningapplication.persistence;


import javax.persistence.EntityManagerFactory;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Persistence
{

  private static ClassPathXmlApplicationContext context;

  public synchronized static EntityManagerFactory getEntityManagerFactory()
  {
    if (context == null)
    {
      context = new ClassPathXmlApplicationContext("context.xml");
    }
    return (EntityManagerFactory) context.getBean("entityManagerFactory",
                                                  EntityManagerFactory.class);
  }

  private Persistence()
  {
    // Prevent instantiation
  }

}
