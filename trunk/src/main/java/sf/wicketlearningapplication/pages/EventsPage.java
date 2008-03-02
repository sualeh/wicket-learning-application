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


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

import sf.wicketlearningapplication.BaseWebPage;
import sf.wicketlearningapplication.WicketLearningApplicationSession;
import sf.wicketlearningapplication.domain.User;

@AuthorizeInstantiation("USER")
public class EventsPage
  extends BaseWebPage
{

  private static final long serialVersionUID = -4454721164415868831L;

  public EventsPage()
  {
    add(new FeedbackPanel("errorMessages"));

    final User user = ((WicketLearningApplicationSession) getSession())
      .getLoggedInUser();
    final EventsTable eventsView = new EventsTable("eventsTable", 5, user);
    add(eventsView);

    final Panel eventPanel = new EventPanel("eventAdd");
    eventPanel.setVisible(false);
    eventPanel.setOutputMarkupPlaceholderTag(true);
    add(eventPanel);

    final AjaxLink addEventLink = new AjaxLink("add")
    {
      private static final long serialVersionUID = -846141758899328311L;

      @Override
      public void onClick(AjaxRequestTarget target)
      {
        eventPanel.setVisible(!eventPanel.isVisible());
        target.addComponent(eventPanel);
      }
    };
    add(addEventLink);

    add(new PageLink("home", HomePage.class));
    add(new LogoutLink("logout"));
  }

}
