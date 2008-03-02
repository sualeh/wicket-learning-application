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
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;

public class BugDao
  extends DataAccessOperator<Bug>
{

  public BugDao(final EntityManager em)
  {
    super(em);
  }

  public int countAllForOwner(final User owner)
  {
    beginTransaction();
    String hql = "select count(*) from Bug b";
    if (owner != null && owner.getId() > 1)
    {
      hql = hql + " where b.owner = :owner";
    }
    final Query query = createQuery(hql);
    if (owner != null && owner.getId() > 1)
    {
      query.setParameter("owner", owner);
    }
    final int count = ((Number) query.getSingleResult()).intValue();
    commitTransaction();
    return count;
  }

  @SuppressWarnings("unchecked")
  public List<Bug> findAllForOwner(final User owner,
                                   final String orderBy,
                                   final boolean isAscending)
  {
    beginTransaction();
    List<Bug> bugs = null;
    String hql = "from Bug b";
    if (owner != null && owner.getId() > 1)
    {
      hql = hql + " where b.owner = :owner";
    }
    if (!StringUtils.isBlank(orderBy))
    {
      hql = hql + " order by " + orderBy + " " + (isAscending? "asc": "desc");
    }
    final Query query = createQuery(hql);
    if (owner != null && owner.getId() > 1)
    {
      query.setParameter("owner", owner);
    }
    bugs = query.getResultList();
    commitTransaction();
    return bugs;
  }

}
