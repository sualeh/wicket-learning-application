package sf.wicketlearningapplication.pages;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;

public class BugsTable
  extends DefaultDataTable
{

  private static final class BugsDataProvider
    extends SortableDataProvider
  {

    private static final long serialVersionUID = -7664388454797401713L;

    private final User user;

    BugsDataProvider(final User user)
    {
      this.user = user;
      setSort(new SortParam("severity", false));
    }

    public Iterator<Bug> iterator(final int first, final int count)
    {
      final SortParam sortParam = getSort();
      final List<Bug> bugsList = BugDao.listBugsByOwner(user, sortParam
        .getProperty(), sortParam.isAscending());
      return bugsList.listIterator(first);
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

  private static final long serialVersionUID = 8016043970738990340L;

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

  private static IColumn[] getColumns(final User user)
  {
    final List<IColumn> columns = new ArrayList<IColumn>();
    columns.add(new AbstractColumn(new Model("Edit/ Delete"))
    {
      private static final long serialVersionUID = 6566804574749277918L;

      public void populateItem(final Item cellItem,
                               final String componentId,
                               final IModel rowModel)
      {
        cellItem.add(new BugEditDeletePanel(componentId, rowModel));
      }
    });
    columns.add(new PropertyColumn(new Model("Id"), "id", "id"));
    columns.add(new PropertyColumn(new Model("Summary"), "summary", "summary"));
    columns.add(new PropertyColumn(new Model("Severity"),
                                   "severity",
                                   "severity"));
    columns.add(new PropertyColumn(new Model("Due By Date"),
      "dueByDate",
      "dueByDate")
    {
      private static final long serialVersionUID = 6019283751055902836L;

      @Override
      public void populateItem(final Item cellItem,
                               final String componentId,
                               final IModel rowModel)
      {
        final Bug bug = (Bug) rowModel.getObject();
        final Model model = new Model(DATE_FORMAT.format(bug.getDueByDate()));
        cellItem.add(new Label(componentId, model));
      }
    });
    columns.add(new PropertyColumn(new Model("Estimated Hours"),
                                   "estimatedHours",
                                   "estimatedHours"));
    if (user == null || user.getId() <= 1)
    {
      columns.add(new PropertyColumn(new Model("Owner"),
                                     "owner.name",
                                     "owner.name"));
    }
    return columns.toArray(new IColumn[columns.size()]);
  }

  BugsTable(final String id, final int itemsPerPage, final User user)
  {
    super(id, getColumns(user), new BugsDataProvider(user), itemsPerPage);
  }

}
