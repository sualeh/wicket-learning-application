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


import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;

final class BugsDataProvider
  extends SortableDataProvider
{

  private static final long serialVersionUID = -7664388454797401713L;

  private final User user;

  BugsDataProvider(final User user)
  {
    this.user = user;
    setSort(new SortParam("severity", true));
  }

  public Iterator<Bug> iterator(final int first, final int count)
  {
    final SortParam sortParam = getSort();
    final List<Bug> bugsList = BugDao.listBugsByOwner(user, sortParam
      .getProperty(), sortParam.isAscending(), first, count);
    return bugsList.listIterator();
  }

  public IModel model(final Object object)
  {
    return new CompoundPropertyModel(object);
  }

  public int size()
  {
    return BugDao.countBugsByOwner(user);
  }

}
