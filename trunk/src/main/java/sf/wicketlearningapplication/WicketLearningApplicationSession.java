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
import org.apache.wicket.protocol.http.WebSession;

import sf.wicketlearningapplication.domain.User;

public class WicketLearningApplicationSession
  extends WebSession
{

  private static final long serialVersionUID = 4142570295721648419L;

  private User loggedInUser;

  public WicketLearningApplicationSession(final Request request)
  {
    super(request);
  }

  public User getLoggedInUser()
  {
    return loggedInUser;
  }

  public void setLoggedInUser(final User loggedInUser)
  {
    this.loggedInUser = loggedInUser;
  }

}
