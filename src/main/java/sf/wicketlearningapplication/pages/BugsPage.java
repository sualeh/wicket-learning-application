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


import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.Link;

import sf.wicketlearningapplication.WicketLearningApplicationSession;
import sf.wicketlearningapplication.domain.User;

@AuthorizeInstantiation(Roles.USER)
public final class BugsPage
  extends BaseWebPage
{

  private static final long serialVersionUID = -4454721164415868831L;

  public BugsPage()
  {
    final User user = ((WicketLearningApplicationSession) getSession())
      .getSignedInUser();
    add(new BugsTable("bugsTable", user));

    final BugFormPanel bugFormPanel = new BugFormPanel("bugAdd", null);
    bugFormPanel.setVisible(false);
    add(bugFormPanel);

    final Link addBugLink = new Link("add")
    {
      private static final long serialVersionUID = -846141758899328311L;

      @Override
      public void onClick()
      {
        bugFormPanel.setVisible(!bugFormPanel.isVisible());
      }
    };
    add(addBugLink);
  }

}
