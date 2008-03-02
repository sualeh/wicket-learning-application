/**
 * 
 */
package sf.wicketlearningapplication.pages;


import javax.persistence.EntityManager;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
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

final class EventForm
  extends Form
{

  private static final long serialVersionUID = 2682300618749680498L;

  private final boolean isInEditMode;

  EventForm(final String id, final Event event)
  {
    super(id);

    final CompoundPropertyModel model;
    if (event == null)
    {
      isInEditMode = false;
      model = new CompoundPropertyModel(new Event());
    }
    else
    {
      isInEditMode = true;
      model = new CompoundPropertyModel(event);
    }
    setModel(model);

    final TextField eventName = new RequiredTextField("name");
    eventName.add(StringValidator.maximumLength(256));
    add(eventName);

    final TextField eventStartDate = new DateTextField("startDate");
    eventStartDate.add(new DatePicker());
    add(eventStartDate);

    final TextField eventDuration = new RequiredTextField("duration.duration",
                                                          Integer.class);
    eventDuration.setRequired(true);
    eventDuration.add(NumberValidator.POSITIVE);
    add(eventDuration);

    final DropDownChoice eventDurationType = new DropDownChoice("duration.durationType",
                                                                DurationType
                                                                  .list());
    eventDurationType.setRequired(true);
    add(eventDurationType);

    final Button cancelButton = new Button("cancel")
    {
      /**
       * 
       */
      private static final long serialVersionUID = 8251200359384967045L;

      @Override
      public void onSubmit()
      {
        setResponsePage(EventsPage.class);
      }
    };
    cancelButton.setDefaultFormProcessing(false);
    add(cancelButton);
  }

  @Override
  protected void onSubmit()
  {
    final Event event = (Event) getModelObject();
    final User user = ((WicketLearningApplicationSession) getSession())
      .getLoggedInUser();
    event.setOwner(user);
    saveEvent(event);

    setResponsePage(EventsPage.class);
  }

  private void saveEvent(final Event event)
  {
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final EventDataAccessOperator eventDao = new EventDataAccessOperator(em);

    eventDao.beginTransaction();
    if (isInEditMode)
    {
      eventDao.save(event);
    }
    else
    {
      eventDao.create(event);
    }
    eventDao.commitTransaction();

    em.clear();
    em.close();
  }

}
