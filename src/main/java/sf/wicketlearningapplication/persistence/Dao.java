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


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

abstract class Dao<T>
{

  @PersistenceContext
  protected final EntityManager em;

  public Dao(final EntityManagerFactory entityManagerFactory)
  {
    if (entityManagerFactory == null)
    {
      throw new IllegalArgumentException("No EntityManagerFactory provided");
    }
    this.em = entityManagerFactory.createEntityManager();
  }

  public void delete(final T object)
  {
    em.getTransaction().begin();
    em.remove(em.merge(object));
    em.flush();
    em.getTransaction().commit();
  }

  public void save(final T object)
  {
    em.getTransaction().begin();
    T mergedEntity;
    try
    {
      mergedEntity = em.merge(object);
    }
    catch (final RuntimeException e)
    {
      mergedEntity = object;
    }
    em.persist(mergedEntity);
    em.flush();
    em.getTransaction().commit();
  }

}
