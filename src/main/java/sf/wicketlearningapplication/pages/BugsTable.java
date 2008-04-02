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
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableChoiceLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;
import sf.wicketlearningapplication.persistence.UserDao;

final class BugsTable
  extends DefaultDataTable
{

  private static final long serialVersionUID = 8016043970738990340L;

  private static IColumn[] getColumns(final User user)
  {
    final List<IColumn> columns = new ArrayList<IColumn>();
    columns.add(new AbstractColumn(new Model(""))
    {
      private static final long serialVersionUID = 6566804574749277918L;

      public void populateItem(final Item cellItem,
                               final String componentId,
                               final IModel rowModel)
      {
        cellItem.add(new BugEditDeletePanel(componentId, rowModel));
      }
    });
    columns.add(new PropertyColumn(new Model("Number"), "id", "id"));
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
        cellItem.add(DateLabel.forDateStyle(componentId,
                                            new PropertyModel(rowModel
                                              .getObject(), "dueByDate"),
                                            "L-"));
      }
    });
    columns.add(new PropertyColumn(new Model("Estimated Hours"),
                                   "estimatedHours",
                                   "estimatedHours"));
    if (user.isAdmin())
    {
      columns.add(new PropertyColumn(new Model("Owner"),
        "owner.name",
        "owner.name")
      {
        private static final long serialVersionUID = 3017864173690322164L;

        @Override
        public void populateItem(final Item cellItem,
                                 final String componentId,
                                 final IModel rowModel)
        {
          class EditableOwnerChoice
            extends AjaxEditableChoiceLabel
          {
            private static final long serialVersionUID = -6892026330177948403L;

            private final Bug bug;

            private EditableOwnerChoice(String id,
                                        IModel model,
                                        List<User> users,
                                        IChoiceRenderer renderer)
            {
              super(id,
                    new PropertyModel(model.getObject(), "owner"),
                    users,
                    renderer);
              bug = (Bug) model.getObject();
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target)
            {
              super.onSubmit(target);
              bug.setOwner((User) getModelObject());
              BugDao.saveBug(bug);
            }
          }
          cellItem
            .add(new EditableOwnerChoice(componentId,
                                         rowModel,
                                         new ArrayList<User>(UserDao
                                           .findAllUsers()),
                                         new ChoiceRenderer("name", "id")));
        }
      });
    }
    return columns.toArray(new IColumn[columns.size()]);
  }

  BugsTable(final String id, final User user)
  {
    super(id, getColumns(user), new BugsDataProvider(user), 10);
  }

}
