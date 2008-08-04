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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableChoiceLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;
import sf.wicketlearningapplication.persistence.UserDao;

final class BugsTable
  extends DefaultDataTable<Bug>
{

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
    columns.add(new PropertyColumn<Bug>(new ResourceModel("bugForm.number"),
                                        "id"));
    columns.add(new PropertyColumn<Bug>(new ResourceModel("bugForm.summary"),
                                        "summary"));
    columns.add(new PropertyColumn<Bug>(new ResourceModel("bugForm.severity"),
                                        "severity"));
    columns.add(new PropertyColumn<Bug>(new ResourceModel("bugForm.dueByDate"),
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
      .add(new PropertyColumn<Bug>(new ResourceModel("bugForm.estimatedHours"),
                                   "estimatedHours"));
    if (user.isAdmin())
    {
      columns.add(new PropertyColumn<Bug>(new ResourceModel("bugForm.owner"),
        "owner")
      {
        private static final long serialVersionUID = 3017864173690322164L;

        @Override
        public void populateItem(final Item<ICellPopulator<Bug>> cellItem,
                                 final String componentId,
                                 final IModel<Bug> rowModel)
        {
          class EditableOwnerChoice
            extends AjaxEditableChoiceLabel<User>
          {
            private static final long serialVersionUID = -6892026330177948403L;

            private final Bug bug;

            private EditableOwnerChoice(final String id,
                                        final IModel<Bug> model,
                                        final List<User> users,
                                        final IChoiceRenderer<User> renderer)
            {
              super(id,
                    new PropertyModel<User>(model.getObject(), "owner"),
                    users,
                    renderer);
              bug = model.getObject();
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target)
            {
              super.onSubmit(target);
              final BugDao bugDao = new BugDao(entityManagerFactory);
              bugDao.save(bug);
            }
          }
          cellItem
            .add(new EditableOwnerChoice(componentId,
                                         rowModel,
                                         new UserDao(entityManagerFactory)
                                           .findAll(),
                                         new ChoiceRenderer<User>("name", "id")));
        }
      });
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
