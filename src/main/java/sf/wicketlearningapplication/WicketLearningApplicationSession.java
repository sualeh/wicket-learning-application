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


import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;

import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.UserDao;

public class WicketLearningApplicationSession
  extends AuthenticatedWebSession
{

  private static final long serialVersionUID = 4142570295721648419L;

  private User loggedInUser;

  public WicketLearningApplicationSession(final AuthenticatedWebApplication application,
                                          final Request request)
  {
    super(application, request);
  }

  @Override
  public boolean authenticate(final String username, final String password)
  {
    loggedInUser = UserDao.findUser(username, password);
    return loggedInUser != null;
  }

  public User getLoggedInUser()
  {
    return loggedInUser;
  }

  @Override
  public Roles getRoles()
  {
    final Roles roles = new Roles();
    if (isSignedIn())
    {
      roles.add("USER");
    }
    return roles;
  }

}
