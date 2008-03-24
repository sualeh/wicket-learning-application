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

  private static final class BugDeleteLink
    extends Link
  {

    private static final long serialVersionUID = 8375528747622018389L;

    private BugDeleteLink(final String id, final IModel model)
    {
      super(id, model);

      Long bugNumber = ((Bug) getModelObject()).getId();
      final String callConfirmJs = String.format("return confirmDelete(%d)",
                                                 bugNumber);
      add(new AttributeModifier("onClick", true, new Model(callConfirmJs)));
    }

    @Override
    public void onClick()
    {
      BugDao.deleteBug((Bug) getModelObject());
      setResponsePage(getPage());
    }
  }

  private static final long serialVersionUID = 2753920209773575465L;

  BugEditDeletePanel(final String id, final IModel model)
  {
    super(id, model);

    add(new BugDeleteLink("delete", model));

    final BugEditDialog bugEditDialog = new BugEditDialog("bugEditDialog",
                                                          model);
    add(bugEditDialog);

    add(new AjaxLink("edit")
    {
      private static final long serialVersionUID = -7501809051827115404L;

      @Override
      public void onClick(final AjaxRequestTarget target)
      {
        bugEditDialog.show(target);
      }
    });

  }
}
