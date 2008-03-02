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


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class EventDurationPanel
  extends Panel
{

  private static final long serialVersionUID = 8909451694138184290L;

  public EventDurationPanel(final String id, final IModel entryModel)
  {
    super(id, entryModel);

    add(new Label("duration.duration"));
    add(new Label("duration.durationType"));
  }

}
