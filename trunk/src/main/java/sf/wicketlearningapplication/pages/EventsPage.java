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


import org.apache.wicket.markup.html.link.PageLink;

import sf.wicketlearningapplication.AuthenticatedWebPage;
import sf.wicketlearningapplication.domain.User;

public class EventsPage
  extends AuthenticatedWebPage
{

  private static final long serialVersionUID = -4454721164415868831L;

  public EventsPage()
  {
    final User user = getSession().getLoggedInUser();
    final EventsTable eventsView = new EventsTable("eventsTable", 5, user);
    add(eventsView);

    add(new PageLink("home", HomePage.class));
    add(new PageLink("eventAdd", EventAddPage.class));
    add(new LogoutLink("logout"));
  }

}
