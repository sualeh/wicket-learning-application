package sf.wicketlearningapplication.pages;


import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.persistence.BugDao;

final class BugDeleteLink
  extends Link
{

  private static final long serialVersionUID = -1627754859597257917L;

  BugDeleteLink(final String id, final IModel object)
  {
    super(id, object);
    final Bug bug = (Bug) getModelObject();
    final String callConfirmJs = String
      .format("return getConfirmation('Are you you want to permanently delete \"%s\"?')",
              bug.getSummary());
    add(new AttributeModifier("onClick", true, new Model(callConfirmJs)));
  }

  @Override
  public void onClick()
  {
    BugDao.deleteBug((Bug) getModelObject());
    setResponsePage(BugsPage.class);
  }

}
