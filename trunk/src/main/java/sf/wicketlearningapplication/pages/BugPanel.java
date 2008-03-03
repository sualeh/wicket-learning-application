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


import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.NumberValidator;
import org.apache.wicket.validation.validator.StringValidator;

import sf.wicketlearningapplication.WicketLearningApplicationSession;
import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.Severity;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;

@AuthorizeInstantiation("USER")
public class BugPanel
  extends Panel
{

  private class BugForm
    extends Form
  {

    private final class CancelButton
      extends Button
    {
      private static final long serialVersionUID = 8251200359384967045L;

      private CancelButton(final String id)
      {
        super(id);
        setDefaultFormProcessing(false);
      }

      @Override
      public void onSubmit()
      {
        setResponsePage(BugsPage.class);
      }
    }

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

      add(new CancelButton("cancel"));
    }

    @Override
    protected void onSubmit()
    {
      final Bug bug = (Bug) getModelObject();
      final User user = ((WicketLearningApplicationSession) getSession())
        .getLoggedInUser();
      bug.setOwner(user);
      BugDao.saveBug(bug, !isInEditMode);

      setResponsePage(BugsPage.class);
    }

  }

  private static final long serialVersionUID = 8546164546033425516L;

  public BugPanel(final String id)
  {
    this(id, null);
  }

  public BugPanel(final String id, final Bug bug)
  {
    super(id);

    final String header;
    if (bug == null)
    {
      header = "Add New Bug";
    }
    else
    {
      header = "Edit Bug";
    }
    add(new Label("header", header));

    add(new BugForm("bugAddForm", bug));
  }

}
