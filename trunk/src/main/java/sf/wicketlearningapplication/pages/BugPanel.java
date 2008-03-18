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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import sf.wicketlearningapplication.domain.Bug;

public class BugPanel
  extends Panel
{

  private static final long serialVersionUID = 8546164546033425516L;

  public BugPanel(final String id)
  {
    super(id);
    addForm(new CompoundPropertyModel(new Bug()), false);
  }

  public BugPanel(final String id, final IModel model)
  {
    super(id, model);
    addForm(model, true);
  }

  private void addForm(final IModel model, final boolean isInEditMode)
  {
    add(new Label("header", (isInEditMode? "Edit Bug": "Add New Bug")));

    add(new BugForm("bugAddForm", model, isInEditMode));
  }

}
