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
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;

final class BugsTable
  extends DefaultDataTable<Bug>
{

  private static class ModelPropertyColumn<T>
    extends AbstractColumn<T>
  {
    private static final long serialVersionUID = 5407215338461041724L;

    private ModelPropertyColumn(final IModel<String> displayModel,
                                final String sortProperty)
    {
      super(displayModel, sortProperty);
    }

    public void populateItem(final Item<ICellPopulator<T>> cellItem,
                             final String componentId,
                             final IModel<T> rowModel)
    {
      cellItem
        .add(new Label(componentId, new PropertyModel<T>(rowModel,
                                                         getSortProperty())));
    }
  }

  private static final long serialVersionUID = 8016043970738990340L;

  private static IColumn<Bug>[] getColumns(final EntityManagerFactory entityManagerFactory,
                                           final User user)
  {
    final List<IColumn<Bug>> columns = new ArrayList<IColumn<Bug>>();
    columns.add(new AbstractColumn<Bug>(new Model<String>(""))
    {
      private static final long serialVersionUID = 6566804574749277918L;

      public void populateItem(final Item<ICellPopulator<Bug>> cellItem,
                               final String componentId,
                               final IModel<Bug> rowModel)
      {
        cellItem.add(new BugEditDeletePanel(componentId, rowModel));
      }
    });
    columns
      .add(new ModelPropertyColumn<Bug>(new ResourceModel("bugForm.number"),
                                        "id"));
    columns
      .add(new ModelPropertyColumn<Bug>(new ResourceModel("bugForm.summary"),
                                        "summary"));
    columns
      .add(new ModelPropertyColumn<Bug>(new ResourceModel("bugForm.severity"),
                                        "severity"));
    columns
      .add(new ModelPropertyColumn<Bug>(new ResourceModel("bugForm.dueByDate"),
        "dueByDate")
      {
        private static final long serialVersionUID = 6019283751055902836L;

        @Override
        public void populateItem(final Item<ICellPopulator<Bug>> cellItem,
                                 final String componentId,
                                 final IModel<Bug> rowModel)
        {
          cellItem.add(DateLabel.forDateStyle(componentId,
                                              new AbstractReadOnlyModel<Date>()
                                              {

                                                private static final long serialVersionUID = 1L;

                                                @Override
                                                public Date getObject()
                                                {
                                                  return rowModel.getObject()
                                                    .getDueByDate();
                                                }
                                              },
                                              "L-"));
        }
      });
    columns
      .add(new ModelPropertyColumn<Bug>(new ResourceModel("bugForm.estimatedHours"),
                                        "estimatedHours"));
    if (user.isAdmin())
    {
      columns
        .add(new ModelPropertyColumn<Bug>(new ResourceModel("bugForm.owner"),
                                          "owner.name"));
    }

    return columns.toArray(new IColumn[columns.size()]);
  }

  BugsTable(final String id,
            final EntityManagerFactory entityManagerFactory,
            final User user)
  {
    super(id,
          getColumns(entityManagerFactory, user),
          new BugsDataProvider(entityManagerFactory, user),
          10);
  }

}
