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


import org.apache.wicket.Session;
import org.apache.wicket.authorization.strategies.page.SimplePageAuthorizationStrategy;

class WicketLearningApplicationAuthorizationStrategy
  extends SimplePageAuthorizationStrategy
{
  WicketLearningApplicationAuthorizationStrategy(final Class securePageSuperType,
                                                 final Class signInPageClass)
  {
    super(securePageSuperType, signInPageClass);
  }

  @Override
  protected boolean isAuthorized()
  {
    return ((WicketLearningApplicationSession) Session.get()).getLoggedInUser() != null;
  }
}
