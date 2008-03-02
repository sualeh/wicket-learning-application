package sf.wicketlearningapplication.pages;


import javax.persistence.EntityManager;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.persistence.BugDao;
import sf.wicketlearningapplication.persistence.Persistence;

final class DeleteBugLink
  extends Link
{

  private static final long serialVersionUID = -1627754859597257917L;

  DeleteBugLink(final String id, final IModel object)
  {
    super(id, object);
    final Bug bug = (Bug) getModelObject();
    final String call = String
      .format("return getConfirmation('Are you you want to permanently delete \"%s\"?')",
              new Object[] {
                bug.getSummary()
              });
    add(new AttributeModifier("onClick", true, new Model(call)));
  }

  @Override
  public void onClick()
  {
    delete((Bug) getModelObject());
    setResponsePage(BugsPage.class);
  }

  private void delete(final Bug bug)
  {
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final BugDao bugDao = new BugDao(em);

    bugDao.beginTransaction();
    bugDao.delete(bug);
    bugDao.commitTransaction();

    em.clear();
    em.close();
  }

}
