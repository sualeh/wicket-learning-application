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


import javax.persistence.EntityManagerFactory;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sf.wicketlearningapplication.WicketLearningApplicationSession;
import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;

final class BugEditDeletePanel
  extends Panel
{

  private final class BugDeleteLink
    extends Link<Bug>
  {

    private static final long serialVersionUID = 8375528747622018389L;

    private BugDeleteLink(final String id, final IModel<Bug> model)
    {
      super(id, model);

      add(new SimpleAttributeModifier("onClick", String
        .format("return confirmDelete(%d)", getModelObject().getId())));
    }

    @Override
    public void onClick()
    {
      final BugDao bugDao = new BugDao(entityManagerFactory);
      bugDao.delete(getModelObject());
    }

  }

  private static final long serialVersionUID = 2753920209773575465L;

  @SpringBean
  private EntityManagerFactory entityManagerFactory;

  BugEditDeletePanel(final String id, final IModel<Bug> model)
  {
    super(id, model);

    final User user = ((WicketLearningApplicationSession) getSession())
      .getSignedInUser();

    final BugDeleteLink bugDeleteLink = new BugDeleteLink("delete", model);
    if (!user.isAdmin())
    {
      bugDeleteLink.setVisible(false);
    }
    add(bugDeleteLink);

    final BugEditDialog bugEditDialog = new BugEditDialog("bugEditDialog",
                                                          model);
    add(bugEditDialog);

    final AjaxLink<Void> bugEditLink = new AjaxLink<Void>("edit")
    {
      private static final long serialVersionUID = -7501809051827115404L;

      @Override
      public void onClick(final AjaxRequestTarget target)
      {
        bugEditDialog.show(target);
      }
    };
    add(bugEditLink);

  }
}
