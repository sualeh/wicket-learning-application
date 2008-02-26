package sf.wicketlearningapplication.pages;


import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import sf.wicketlearningapplication.domain.User;

public class EventsTable
  extends DefaultDataTable
{

  private static final long serialVersionUID = 8016043970738990340L;

  private static IColumn[] getEventsColumns()
  {
    return new IColumn[] {
        new PropertyColumn(new Model("Id"), "id", "id"),
        new PropertyColumn(new Model("Event"), "name", "name"),
        new PropertyColumn(new Model("Start Date"), "startDate", "startDate"),
        new PropertyColumn(new Model("Duration"),
                           "duration.duration",
                           "duration.duration"),
        new PropertyColumn(new Model("Duration Type"),
                           "duration.durationType",
                           "duration.durationType"),
        new PropertyColumn(new Model("Owner"), "owner.name", "owner.name")
        {
          private static final long serialVersionUID = 7459737880589990417L;

          @Override
          public void populateItem(final Item cellItem,
                                   final String componentId,
                                   final IModel rowModel)
          {
            cellItem.add(new OwnerDetailsPanel(componentId, rowModel));
          }
        },
    };
  }

  EventsTable(final String id, final int itemsPerPage, final User user)
  {
    super(id, getEventsColumns(), new EventsDataProvider(user), itemsPerPage);
  }

}
