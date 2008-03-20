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


import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import sf.wicketlearningapplication.domain.User;

public class UserDao
  extends Dao<User>
{

  public static User findUser(final String username, final String password)
  {
    try
    {
      final EntityManager em = Persistence.getEntityManagerFactory()
        .createEntityManager();
      final UserDao userDao = new UserDao(em);
      return userDao.find(username, password);
    }
    catch (final NoResultException e)
    {
      return null;
    }
  }

  public static Collection<User> findAllUsers()
  {
    try
    {
      final EntityManager em = Persistence.getEntityManagerFactory()
        .createEntityManager();
      final UserDao userDao = new UserDao(em);
      return userDao.findAll(User.class);
    }
    catch (final NoResultException e)
    {
      return null;
    }
  }

  public static boolean isAdmin(final User user)
  {
    return user != null && user.getId() == 1;
  }

  public UserDao(final EntityManager em)
  {
    super(em);
  }

  public User find(final String username, final String password)
  {
    beginTransaction();
    User user = null;
    final String query = "from User u where u.username = :username and u.password = :password";
    user = (User) createQuery(query).setParameter("username", username)
      .setParameter("password", password).getSingleResult();
    commitTransaction();
    return user;
  }

}
