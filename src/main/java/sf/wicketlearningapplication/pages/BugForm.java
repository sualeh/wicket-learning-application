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
import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.Severity;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;
import sf.wicketlearningapplication.persistence.Persistence;

final class BugForm
  extends Form
{

  private static final long serialVersionUID = 2682300618749680498L;

  private final boolean isInEditMode;

  BugForm(final String id, final Bug bug)
  {
    super(id);

    final CompoundPropertyModel model;
    if (bug == null)
    {
      isInEditMode = false;
      model = new CompoundPropertyModel(new Bug());
    }
    else
    {
      isInEditMode = true;
      model = new CompoundPropertyModel(bug);
    }
    setModel(model);

    final TextField summary = new RequiredTextField("summary");
    summary.add(StringValidator.maximumLength(256));
    add(summary);

    final DropDownChoice severity = new DropDownChoice("severity", Severity
      .list());
    severity.setRequired(true);
    add(severity);

    final TextField dueByDate = new DateTextField("dueByDate");
    dueByDate.add(new DatePicker());
    add(dueByDate);

    final TextField estimatedHours = new RequiredTextField("estimatedHours",
                                                           Integer.class);
    estimatedHours.setRequired(true);
    estimatedHours.add(NumberValidator.POSITIVE);
    add(estimatedHours);

    final Button cancelButton = new Button("cancel")
    {
      private static final long serialVersionUID = 8251200359384967045L;

      @Override
      public void onSubmit()
      {
        setResponsePage(BugsPage.class);
      }
    };
    cancelButton.setDefaultFormProcessing(false);
    add(cancelButton);
  }

  @Override
  protected void onSubmit()
  {
    final Bug event = (Bug) getModelObject();
    final User user = ((WicketLearningApplicationSession) getSession())
      .getLoggedInUser();
    event.setOwner(user);
    saveEvent(event);

    setResponsePage(BugsPage.class);
  }

  private void saveEvent(final Bug event)
  {
    final EntityManager em = Persistence.getEntityManagerFactory()
      .createEntityManager();
    final BugDao eventDao = new BugDao(em);

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
