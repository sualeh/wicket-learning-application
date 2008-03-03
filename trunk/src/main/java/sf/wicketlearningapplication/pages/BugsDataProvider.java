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

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;
import sf.wicketlearningapplication.persistence.Persistence;

final class BugsDataProvider
  extends SortableDataProvider
{

  private static final long serialVersionUID = -7664388454797401713L;

  private final User user;

  BugsDataProvider(final User user)
  {
    this.user = user;
  }

  @Override
  public SortParam getSort()
  {
    SortParam sortParam = super.getSort();
    if (sortParam == null)
    {
      sortParam = new SortParam("summary", true);
    }
    return sortParam;
  }

  public Iterator<Bug> iterator(final int first, final int count)
  {
    final int bugsCount = size();
    int toIndex = first + count;
    if (toIndex > bugsCount)
    {
      toIndex = getBugCount(user);
    }
    final List<Bug> bugsList = getBugs();
    return bugsList.subList(first, toIndex).listIterator();
  }

  public IModel model(final Object object)
  {
    return new CompoundPropertyModel(object);
  }

  public int size()
  {
    return getBugCount(user);
  }

  private int getBugCount(final User user)
  {
    int count = 0;
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final BugDao bugDao = new BugDao(em);
    if (user != null)
    {
      count = bugDao.countAllForOwner(user);
    }
    return count;
  }

  private List<Bug> getBugs()
  {
    final boolean hasOwner = user != null && user.getId() > 1;
    final Collection<Bug> bugs;
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final BugDao bugDao = new BugDao(em);
    if (hasOwner)
    {
      final SortParam sort = getSort();
      bugs = bugDao.findAllForOwner(user, sort.getProperty(), sort
        .isAscending());
    }
    else
    {
      bugs = bugDao.findAll(Bug.class);
    }
    return new ArrayList<Bug>(bugs);
  }

}
