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


import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

abstract class Dao<T>
{

  @PersistenceContext
  private final EntityManager em;

  public Dao(final EntityManagerFactory entityManagerFactory)
  {
    if (entityManagerFactory == null)
    {
      throw new IllegalArgumentException("No EntityManagerFactory provided");
    }
    this.em = entityManagerFactory.createEntityManager();
  }

  protected void beginTransaction()
  {
    em.getTransaction().begin();
  }

  protected void commitTransaction()
  {
    em.getTransaction().commit();
  }

  protected void create(final T entity)
  {
    em.persist(entity);
  }

  protected Query createNamedQuery(final String namedQuery)
  {
    return em.createNamedQuery(namedQuery);
  }

  protected Query createQuery(final String query)
  {
    return em.createQuery(query);
  }

  protected void delete(final T entity)
  {
    em.remove(em.merge(entity));
  }

  protected T find(final Class<T> clazz, final long id)
  {
    return em.find(clazz, id);
  }

  @SuppressWarnings("unchecked")
  protected Collection<T> findAll(final Class<T> clazz)
  {
    final Query query = em.createQuery("from " + clazz.getName());
    return query.getResultList();
  }

  protected void flush()
  {
    em.flush();
  }

  protected void rollbackTransaction()
  {
    em.getTransaction().rollback();
  }

  protected void save(final T entity)
  {
    T mergedEntity;
    try
    {
      mergedEntity = em.merge(entity);
    }
    catch (final RuntimeException e)
    {
      mergedEntity = entity;
    }
    em.persist(mergedEntity);
  }

  protected void update(final T entity)
  {
    em.merge(entity);
  }

}
