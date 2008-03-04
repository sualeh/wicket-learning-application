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


import org.apache.wicket.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.WebPage;

public class LoginPage
  extends WebPage
{

  private static final long serialVersionUID = 5785408843233190968L;

  public LoginPage()
  {
    add(new SignInPanel("signInPanel"));
  }

}
