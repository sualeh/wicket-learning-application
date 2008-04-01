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
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;

public class BugDao
  extends Dao<Bug>
{

  public static int countBugsByOwner(final User owner)
  {
    int count = 0;
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final BugDao bugDao = new BugDao(em);
    if (UserDao.isAdmin(owner))
    {
      count = bugDao.countAll();
    }
    else
    {
      count = bugDao.countAllForOwner(owner);
    }
    return count;
  }

  public static void createBug(final Bug bug)
  {
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final BugDao bugDao = new BugDao(em);

    bugDao.beginTransaction();
    bugDao.create(bug);
    bugDao.commitTransaction();

    em.clear();
    em.close();
  }

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

  public static List<Bug> listBugsByOwner(final User owner,
                                          final String orderBy,
                                          final boolean isAscending,
                                          final int startPosition,
                                          final int maxResult)
  {
    final Collection<Bug> bugs;

    User findByOwner = null;
    if (!UserDao.isAdmin(owner))
    {
      findByOwner = owner;
    }

    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final BugDao bugDao = new BugDao(em);
    bugs = bugDao.findAll(findByOwner,
                          orderBy,
                          isAscending,
                          startPosition,
                          maxResult);
    return new ArrayList<Bug>(bugs);
  }

  public static void saveBug(final Bug bug)
  {
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final BugDao bugDao = new BugDao(em);

    bugDao.beginTransaction();
    bugDao.save(bug);
    bugDao.commitTransaction();

    em.clear();
    em.close();
  }

  public BugDao(final EntityManager em)
  {
    super(em);
  }

  public int countAll()
  {
    beginTransaction();
    final String hql = "select count(*) from Bug b";
    final int count = ((Number) createQuery(hql).getSingleResult()).intValue();
    commitTransaction();

    return count;
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

  @SuppressWarnings("unchecked")
  public List<Bug> findAll(final User owner,
                           final String orderBy,
                           final boolean isAscending,
                           final int startPosition,
                           final int maxResult)
  {
    beginTransaction();
    List<Bug> bugs = null;
    String hql = "from Bug b";
    if (owner != null)
    {
      hql = hql + " where b.owner = :owner";
    }
    if (!StringUtils.isBlank(orderBy))
    {
      hql = hql + " order by " + orderBy + " " + (isAscending? "asc": "desc");
    }
    final Query query = createQuery(hql);
    if (owner != null)
    {
      query.setParameter("owner", owner);
    }
    query.setFirstResult(startPosition);
    query.setMaxResults(maxResult);
    bugs = query.getResultList();
    commitTransaction();

    return bugs;
  }

}
