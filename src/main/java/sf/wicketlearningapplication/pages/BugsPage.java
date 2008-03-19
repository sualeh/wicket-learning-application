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
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;

import sf.wicketlearningapplication.WicketLearningApplicationSession;
import sf.wicketlearningapplication.domain.User;

@AuthorizeInstantiation(Roles.USER)
public class BugsPage
  extends BaseWebPage
{

  private static final long serialVersionUID = -4454721164415868831L;

  public BugsPage()
  {
    final User user = ((WicketLearningApplicationSession) getSession())
      .getSignedInUser();
    add(new BugsTable("bugsTable", 10, user));

    final BugPanel bugPanel = new BugPanel("bugAdd", null);
    bugPanel.setVisible(false);
    bugPanel.setOutputMarkupPlaceholderTag(true);
    add(bugPanel);

    final AjaxLink addBugLink = new AjaxLink("add")
    {
      private static final long serialVersionUID = -846141758899328311L;

      @Override
      public void onClick(final AjaxRequestTarget target)
      {
        bugPanel.setVisible(!bugPanel.isVisible());
        target.addComponent(bugPanel);
      }
    };
    add(addBugLink);
  }

}
