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


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;

public class BugDao
  extends DataAccessOperator<Bug>
{

  public static void deleteBug(final Bug bug)
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

  public static int countBugsByOwner(final User owner)
  {
    final boolean hasOwner = owner != null && owner.getId() > 1;
    int count = 0;
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final BugDao bugDao = new BugDao(em);
    if (hasOwner)
    {
      count = bugDao.countAllForOwner(owner);
    }
    else
    {
      count = bugDao.countAll();
    }
    return count;
  }

  public static List<Bug> listBugsByOwner(final User owner,
                                          final String orderBy,
                                          final boolean isAscending)
  {
    final boolean hasOwner = owner != null && owner.getId() > 1;
    final Collection<Bug> bugs;
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final BugDao bugDao = new BugDao(em);
    if (hasOwner)
    {
      bugs = bugDao.findAllForOwner(owner, orderBy, isAscending);
    }
    else
    {
      bugs = bugDao.findAll(Bug.class);
    }
    return new ArrayList<Bug>(bugs);
  }

  public static void saveBug(final Bug bug, final boolean create)
  {
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final BugDao bugDao = new BugDao(em);

    bugDao.beginTransaction();
    if (create)
    {
      bugDao.create(bug);
    }
    else
    {
      bugDao.save(bug);
    }
    bugDao.commitTransaction();

    em.clear();
    em.close();
  }

  public BugDao(final EntityManager em)
  {
    super(em);
  }

  public int countAllForOwner(final User owner)
  {
    if (owner == null)
    {
      throw new IllegalArgumentException("No owner provided");
    }

    beginTransaction();
    final String hql = "select count(*) from Bug b where b.owner = :owner";
    final int count = ((Number) createQuery(hql).setParameter("owner", owner)
      .getSingleResult()).intValue();
    commitTransaction();

    return count;
  }

  public int countAll()
  {
    beginTransaction();
    final String hql = "select count(*) from Bug b";
    final int count = ((Number) createQuery(hql).getSingleResult()).intValue();
    commitTransaction();

    return count;
  }

  @SuppressWarnings("unchecked")
  public List<Bug> findAllForOwner(final User owner,
                                   final String orderBy,
                                   final boolean isAscending)
  {
    if (owner == null)
    {
      throw new IllegalArgumentException("No owner provided");
    }

    beginTransaction();
    List<Bug> bugs = null;
    String hql = "from Bug b where b.owner = :owner";
    if (!StringUtils.isBlank(orderBy))
    {
      hql = hql + " order by " + orderBy + " " + (isAscending? "asc": "desc");
    }
    bugs = createQuery(hql).setParameter("owner", owner).getResultList();
    commitTransaction();

    return bugs;
  }

}
