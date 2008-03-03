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


import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.persistence.BugDao;

final class BugEditDeletePanel
  extends Panel
{

  private static final long serialVersionUID = 2753920209773575465L;

  BugEditDeletePanel(final String id, final IModel model)
  {
    super(id, model);

    final Bug bug = (Bug) model.getObject();
    final Panel bugPanel = new BugPanel("bugEdit", bug);
    bugPanel.setVisible(false);
    bugPanel.setOutputMarkupPlaceholderTag(true);
    add(bugPanel);

    final Link deleteLink = new Link("delete", model)
    {
      private static final long serialVersionUID = 8375528747622018389L;

      @Override
      public void onClick()
      {
        BugDao.deleteBug((Bug) getModelObject());
        setResponsePage(BugsPage.class);
      }
    };
    final String callConfirmJs = String
      .format("return getConfirmation('Are you you want to permanently delete \"%s\"?')",
              bug.getSummary());
    deleteLink.add(new AttributeModifier("onClick",
                                         true,
                                         new Model(callConfirmJs)));
    add(deleteLink);

    final AjaxLink editLink = new AjaxLink("edit")
    {
      private static final long serialVersionUID = 7695320796784956116L;

      @Override
      public void onClick(AjaxRequestTarget target)
      {
        bugPanel.setVisible(!bugPanel.isVisible());
        target.addComponent(bugPanel);
      }
    };
    add(editLink);

  }

}
