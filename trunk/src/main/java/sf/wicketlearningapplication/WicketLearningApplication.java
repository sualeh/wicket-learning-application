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
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;

import sf.wicketlearningapplication.pages.HomePage;
import sf.wicketlearningapplication.pages.LoginPage;

public class WicketLearningApplication
  extends WebApplication
{

  @Override
  public Class getHomePage()
  {
    return HomePage.class;
  }

  @Override
  public Session newSession(final Request request, final Response response)
  {
    return new WicketLearningApplicationSession(request);
  }

  @Override
  protected void init()
  {
    getSecuritySettings()
      .setAuthorizationStrategy(new WicketLearningApplicationAuthorizationStrategy(AuthenticatedWebPage.class,
                                                                                   LoginPage.class));
  }

}
