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
package sf.wicketlearningapplication;


import javax.persistence.EntityManagerFactory;

import org.apache.wicket.Application;
import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.springframework.context.ApplicationContext;

import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.UserDao;

public class WicketLearningApplicationSession
  extends AuthenticatedWebSession
{

  private static final long serialVersionUID = 4142570295721648419L;

  private User signedInUser;

  public WicketLearningApplicationSession(final Request request)
  {
    super(request);
  }

  @Override
  public boolean authenticate(final String username, final String password)
  {
    final ApplicationContext applicationContext = ((WicketLearningApplication) Application
      .get()).getApplicationContext();
    signedInUser = new UserDao((EntityManagerFactory) applicationContext
      .getBean("entityManagerFactory")).find(username, password);
    return signedInUser != null;
  }

  @Override
  public Roles getRoles()
  {
    final Roles roles = new Roles();
    if (isSignedIn())
    {
      roles.add(Roles.USER);
      if (signedInUser.isAdmin())
      {
        roles.add(Roles.ADMIN);
      }
    }
    return roles;
  }

  public User getSignedInUser()
  {
    return signedInUser;
  }

}
