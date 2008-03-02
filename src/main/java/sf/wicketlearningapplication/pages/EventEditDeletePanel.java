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
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import sf.wicketlearningapplication.domain.Event;

final class EventEditDeletePanel
  extends Panel
{

  private static final long serialVersionUID = 2753920209773575465L;

  EventEditDeletePanel(final String id, final IModel model)
  {
    super(id, model);

    Event event = (Event) model.getObject();
    final Panel eventPanel = new EventPanel("eventEdit", event);
    eventPanel.setVisible(false);
    eventPanel.setOutputMarkupPlaceholderTag(true);
    add(eventPanel);

    add(new DeleteEventLink("delete", model));

    final AjaxLink editEventLink = new AjaxLink("edit")
    {
      private static final long serialVersionUID = 7695320796784956116L;

      @Override
      public void onClick(AjaxRequestTarget target)
      {
        eventPanel.setVisible(!eventPanel.isVisible());
        target.addComponent(eventPanel);
      }
    };
    add(editEventLink);

  }
}
