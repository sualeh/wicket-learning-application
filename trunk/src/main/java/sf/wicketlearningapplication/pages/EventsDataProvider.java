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


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import sf.wicketlearningapplication.domain.Event;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.EventDataAccessOperator;
import sf.wicketlearningapplication.persistence.Persistence;

final class EventsDataProvider
  extends SortableDataProvider
{

  private static final long serialVersionUID = -7664388454797401713L;

  private final User user;

  EventsDataProvider(final User user)
  {
    this.user = user;
  }

  @Override
  public SortParam getSort()
  {
    SortParam sortParam = super.getSort();
    if (sortParam == null)
    {
      sortParam = new SortParam("name", true);
    }
    return sortParam;
  }

  public Iterator<Event> iterator(final int first, final int count)
  {
    final int eventsCount = size();
    int toIndex = first + count;
    if (toIndex > eventsCount)
    {
      toIndex = getEventsCount(user);
    }
    final List<Event> eventsList = getEvents();
    return eventsList.subList(first, toIndex).listIterator();
  }

  public IModel model(final Object object)
  {
    return new CompoundPropertyModel(object);
  }

  public int size()
  {
    return getEventsCount(user);
  }

  private List<Event> getEvents()
  {
    final Collection<Event> events;
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final EventDataAccessOperator eventDao = new EventDataAccessOperator(em);
    if (user != null)
    {
      final SortParam sort = getSort();
      events = eventDao.findAllForOwner(user, sort.getProperty(), sort
        .isAscending());
    }
    else
    {
      events = eventDao.findAll(Event.class);
    }
    return new ArrayList<Event>(events);
  }

  private int getEventsCount(final User user)
  {
    int count = 0;
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final EventDataAccessOperator eventDao = new EventDataAccessOperator(em);
    if (user != null)
    {
      count = eventDao.countAllForOwner(user);
    }
    return count;
  }

}
