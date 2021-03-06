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

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;

public final class BugDao
  extends Dao<Bug>
{

  public BugDao(final EntityManagerFactory entityManagerFactory)
  {
    super(entityManagerFactory);
  }

  public int count(final User owner)
  {
    em.getTransaction().begin();
    final Query query = em.createNamedQuery("count");
    query.setParameter("owner", owner);
    final int count = ((Number) query.getSingleResult()).intValue();
    em.getTransaction().commit();

    return count;
  }

  @SuppressWarnings("unchecked")
  public List<Bug> findAll(final User owner,
                           final String orderBy,
                           final boolean isAscending,
                           final int startPosition,
                           final int maxResult)
  {
    final boolean filterByOwner = owner != null && !owner.isAdmin();
    em.getTransaction().begin();
    List<Bug> bugs = null;
    String hql = "from Bug b";
    if (filterByOwner)
    {
      hql = hql + " where b.owner = :owner";
    }
    if (!StringUtils.isBlank(orderBy))
    {
      hql = hql + " order by " + orderBy + " " + (isAscending? "asc": "desc");
    }
    final Query query = em.createQuery(hql);
    if (filterByOwner)
    {
      query.setParameter("owner", owner);
    }
    query.setFirstResult(startPosition);
    query.setMaxResults(maxResult);
    bugs = query.getResultList();
    em.getTransaction().commit();

    return bugs;
  }

}
