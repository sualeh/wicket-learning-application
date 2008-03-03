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
package sf.wicketlearningapplication.pages;


import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.border.BoxBorder;
import org.apache.wicket.markup.html.link.PageLink;

import sf.wicketlearningapplication.BaseWebPage;

@AuthorizeInstantiation("USER")
public class HomePage
  extends BaseWebPage
{

  private static final long serialVersionUID = 8957237889070269644L;

  public HomePage()
  {
    final Border border = new BoxBorder("border");
    border.add(new PageLink("bugs", BugsPage.class));
    border.add(new LogoutLink("logout"));
    add(border);
  }

}
