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


import java.util.Arrays;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.DateValidator;
import org.apache.wicket.validation.validator.NumberValidator;
import org.apache.wicket.validation.validator.StringValidator;

import sf.wicketlearningapplication.WicketLearningApplicationSession;
import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.Severity;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;

final class BugForm
  extends Form
{

  private static final long serialVersionUID = 2682300618749680498L;

  private final boolean isInEditMode;

  BugForm(final String id, IModel model, final boolean isInEditMode)
  {
    super(id, model);

    this.isInEditMode = isInEditMode;

    final TextField summary = new RequiredTextField("summary");
    summary.add(StringValidator.maximumLength(256));
    add(summary);

    final DropDownChoice severity = new DropDownChoice("severity", Arrays
      .asList(Severity.values()));
    severity.setRequired(true);
    add(severity);

    final TextField dueByDate = new DateTextField("dueByDate");
    dueByDate.add(new DatePicker());
    dueByDate.add(DateValidator.minimum(new Date()));
    add(dueByDate);

    final TextField estimatedHours = new RequiredTextField("estimatedHours",
                                                           Integer.class);
    estimatedHours.setRequired(true);
    estimatedHours.add(NumberValidator.POSITIVE);
    add(estimatedHours);

    final Button saveButton = new IndicatingAjaxButton("save", this)
    {
      private static final long serialVersionUID = 7949306415616423528L;

      @Override
      protected void onSubmit(AjaxRequestTarget target, Form form)
      {
        ModalWindow.closeCurrent(target);

        final Bug bug = (Bug) getForm().getModelObject();
        final User user = ((WicketLearningApplicationSession) getSession())
          .getSignedInUser();
        bug.setOwner(user);
        BugDao.saveBug(bug, !isInEditMode);
      }

    };
    add(saveButton);
  }

}
