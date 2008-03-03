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
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.border.BoxBorder;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

import sf.wicketlearningapplication.BaseWebPage;
import sf.wicketlearningapplication.WicketLearningApplicationSession;
import sf.wicketlearningapplication.domain.User;

@AuthorizeInstantiation(Roles.USER)
public class BugsPage
  extends BaseWebPage
{

  private static final long serialVersionUID = -4454721164415868831L;

  public BugsPage()
  {
    add(new FeedbackPanel("errorMessages"));

    final User user = ((WicketLearningApplicationSession) getSession())
      .getSignedInUser();
    add(new BugsTable("bugsTable", 5, user));

    final Panel bugPanel = new BugPanel("bugAdd");
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

    final Border border = new BoxBorder("border");
    border.add(new PageLink("home", HomePage.class));
    border.add(new LogoutLink("logout"));
    add(border);
  }

}
