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


import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authentication.pages.SignInPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.lang.PackageName;

import sf.wicketlearningapplication.pages.HomePage;

public class WicketLearningApplication
  extends AuthenticatedWebApplication
{

  @Override
  protected void init()
  {
    super.init();
    mount("/wicketlearningapplication", PackageName.forClass(HomePage.class));
    mountBookmarkablePage("/wicketlearningapplication/SignInPage",
                          SignInPage.class);
  }

  @Override
  public Class<? extends WebPage> getHomePage()
  {
    return HomePage.class;
  }

  @Override
  protected Class<? extends WebPage> getSignInPageClass()
  {
    return SignInPage.class;
  }

  @Override
  protected Class<? extends AuthenticatedWebSession> getWebSessionClass()
  {
    return WicketLearningApplicationSession.class;
  }

}
