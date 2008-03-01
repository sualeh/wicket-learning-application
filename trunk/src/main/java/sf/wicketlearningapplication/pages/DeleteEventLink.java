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


import javax.persistence.EntityManager;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import sf.wicketlearningapplication.domain.Event;
import sf.wicketlearningapplication.persistence.EventDataAccessOperator;
import sf.wicketlearningapplication.persistence.Persistence;

final class DeleteEventLink
  extends Link
{

  private static final long serialVersionUID = 2753920209773575465L;

  DeleteEventLink(final String id, IModel model)
  {
    super(id, model);
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
