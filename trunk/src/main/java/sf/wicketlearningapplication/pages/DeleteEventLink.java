package sf.wicketlearningapplication.pages;


import javax.persistence.EntityManager;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import sf.wicketlearningapplication.domain.Event;
import sf.wicketlearningapplication.persistence.EventDataAccessOperator;
import sf.wicketlearningapplication.persistence.Persistence;

final class DeleteEventLink
  extends Link
{

  private static final long serialVersionUID = -1627754859597257917L;

  DeleteEventLink(final String id, final IModel object)
  {
    super(id, object);
    final Event event = (Event) getModelObject();
    final String call = String
      .format("return getConfirmation('Are you you want to permanently delete \"%s\"?')",
              new Object[] {
                event.getName()
              });
    add(new AttributeModifier("onClick", true, new Model(call)));
  }

  @Override
  public void onClick()
  {
    deleteEvent((Event) getModelObject());
    setResponsePage(EventsPage.class);
  }

  private void deleteEvent(final Event event)
  {
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final EventDataAccessOperator eventDao = new EventDataAccessOperator(em);

    eventDao.beginTransaction();
    eventDao.delete(event);
    eventDao.commitTransaction();

    em.clear();
    em.close();
  }

}
