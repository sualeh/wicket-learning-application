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


import javax.persistence.EntityManager;

import sf.wicketlearningapplication.domain.User;

public class UserDataAccessOperator
  extends DataAccessOperator<User>
{

  public UserDataAccessOperator(final EntityManager em)
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
