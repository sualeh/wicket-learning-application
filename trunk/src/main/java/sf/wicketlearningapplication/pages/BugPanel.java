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
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import sf.wicketlearningapplication.domain.Bug;

@AuthorizeInstantiation("USER")
public class BugPanel
  extends Panel
{

  private static final long serialVersionUID = 8546164546033425516L;

  public BugPanel(final String id)
  {
    this(id, null);
  }

  public BugPanel(final String id, final Bug bug)
  {
    super(id);

    final String header;
    if (bug == null)
    {
      header = "Add New Bug";
    }
    else
    {
      header = "Edit Bug";
    }
    add(new Label("header", header));

    add(new BugForm("bugAddForm", bug));
  }

}
