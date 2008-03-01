/**
 * 
 */
package sf.wicketlearningapplication.pages;


import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.NumberValidator;
import org.apache.wicket.validation.validator.StringValidator;

import sf.wicketlearningapplication.WicketLearningApplicationSession;
import sf.wicketlearningapplication.domain.DurationType;
import sf.wicketlearningapplication.domain.Event;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.EventDataAccessOperator;
import sf.wicketlearningapplication.persistence.Persistence;

final class EventAddForm
  extends Form
{

  private static final long serialVersionUID = 2682300618749680498L;

  private Event event;

  EventAddForm(final String id)
  {
    super(id);

    event = new Event();
    setModel(new CompoundPropertyModel(event));

    final TextField eventName = new TextField("name");
    eventName.setRequired(true);
    eventName.add(StringValidator.maximumLength(256));
    add(eventName);

    final TextField eventStartDate = new TextField("startDate", Date.class);
    eventStartDate.setRequired(true);
    eventStartDate.add(new DatePicker());
    add(eventStartDate);

    final TextField eventDuration = new TextField("duration.duration",
                                                  Integer.class);
    eventDuration.setRequired(true);
    eventDuration.add(NumberValidator.POSITIVE);
    add(eventDuration);

    final DropDownChoice eventDurationType = new DropDownChoice("duration.durationType",
                                                                DurationType
                                                                  .list());
    eventDurationType.setRequired(true);
    add(eventDurationType);
  }

  @Override
  protected void onSubmit()
  {
    final User user = ((WicketLearningApplicationSession) getSession())
      .getLoggedInUser();
    event.setOwner(user);
    saveEvent(event);
    event = null;

    setResponsePage(EventsPage.class);
  }

  private void saveEvent(final Event event)
  {
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final EventDataAccessOperator eventDao = new EventDataAccessOperator(em);

    eventDao.beginTransaction();
    eventDao.save(event);
    eventDao.commitTransaction();

    em.clear();
    em.close();
  }

}
