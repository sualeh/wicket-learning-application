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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import sf.wicketlearningapplication.domain.User;

public final class UserDao
  extends Dao<User>
{

  public UserDao(final EntityManagerFactory entityManagerFactory)
  {
    super(entityManagerFactory);
  }

  public User find(final String username, final String password)
  {
    em.getTransaction().begin();
    final Query query = em.createNamedQuery("authentication");
    query.setParameter("username", username).setParameter("password", password);
    final User user = (User) query.getSingleResult();
    em.getTransaction().commit();
    return user;
  }

  public List<User> findAll()
  {
    try
    {
      em.getTransaction().begin();
      final List<User> allUsers = em
        .createQuery("from " + User.class.getName()).getResultList();
      em.getTransaction().commit();
      Collections.sort(allUsers);
      return allUsers;
    }
    catch (final NoResultException e)
    {
      return new ArrayList<User>();
    }
  }
}
