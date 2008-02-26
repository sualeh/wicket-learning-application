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


import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import sf.wicketlearningapplication.AuthenticatedWebPage;

public class EventAddPage
  extends AuthenticatedWebPage
{

  private static final long serialVersionUID = 8546164546033425516L;

  public EventAddPage()
  {

    final Form form = new EventAddForm("eventAddForm");
    add(form);

    final FeedbackPanel errorMessages = new FeedbackPanel("errorMessages");
    add(errorMessages);
  }

}
