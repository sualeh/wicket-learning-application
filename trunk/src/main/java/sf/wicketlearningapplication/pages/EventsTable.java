package sf.wicketlearningapplication.pages;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import sf.wicketlearningapplication.domain.Event;
import sf.wicketlearningapplication.domain.User;

public class EventsTable
  extends DefaultDataTable
{

  private static final long serialVersionUID = 8016043970738990340L;

  private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

  private static IColumn[] getEventsColumns()
  {
    return new IColumn[] {
        new PropertyColumn(new Model("Id"), "id", "id"),
        new PropertyColumn(new Model("Event"), "name", "name"),
        new PropertyColumn(new Model("Start Date"), "startDate", "startDate")
        {
          private static final long serialVersionUID = 6019283751055902836L;

          @Override
          public void populateItem(final Item cellItem,
                                   final String componentId,
                                   final IModel rowModel)
          {
            final Event event = (Event) rowModel.getObject();
            cellItem.add(new Label(componentId, new Model(dateFormat
              .format(event.getStartDate()))));
          }
        },
        new PropertyColumn(new Model("Duration"), "duration", "duration")
        {
          private static final long serialVersionUID = 7863677007871016499L;

          @Override
          public void populateItem(final Item cellItem,
                                   final String componentId,
                                   final IModel rowModel)
          {
            cellItem.add(new EventDurationPanel(componentId, rowModel));
          }
        },
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
        new AbstractColumn(new Model("Edit/ Delete"))
        {
          private static final long serialVersionUID = 6566804574749277918L;

          public void populateItem(final Item cellItem,
                                   final String componentId,
                                   final IModel rowModel)
          {
            cellItem.add(new EventEditDeletePanel(componentId, rowModel));
          }
        },
    };
  }

  EventsTable(final String id, final int itemsPerPage, final User user)
  {
    super(id, getEventsColumns(), new EventsDataProvider(user), itemsPerPage);
  }

}
