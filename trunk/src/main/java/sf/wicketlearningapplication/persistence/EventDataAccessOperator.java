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
package sf.wicketlearningapplication.persistence;


import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import sf.wicketlearningapplication.domain.Event;
import sf.wicketlearningapplication.domain.User;

public class EventDataAccessOperator
  extends DataAccessOperator<Event>
{

  public EventDataAccessOperator(final EntityManager em)
  {
    super(em);
  }

  public int countAllForOwner(final User owner)
  {
    beginTransaction();
    String query = "select count(*) from Event e";
    if (owner != null)
    {
      query = query + " where e.owner = :owner";
    }
    final int count = ((Number) createQuery(query).setParameter("owner", owner)
      .getSingleResult()).intValue();
    commitTransaction();
    return count;
  }

  @SuppressWarnings("unchecked")
  public List<Event> findAllForOwner(final User owner,
                                     final String orderBy,
                                     final boolean isAscending)
  {
    beginTransaction();
    List<Event> events = null;
    String query = "from Event e";
    if (owner != null)
    {
      query = query + " where e.owner = :owner";
    }
    if (!StringUtils.isBlank(orderBy))
    {
      query = query + " order by " + orderBy + " "
              + (isAscending? "asc": "desc");
    }
    events = createQuery(query).setParameter("owner", owner).getResultList();
    commitTransaction();
    return events;
  }

}