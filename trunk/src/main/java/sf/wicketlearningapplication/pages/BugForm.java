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
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.DateValidator;
import org.apache.wicket.validation.validator.NumberValidator;
import org.apache.wicket.validation.validator.StringValidator;

import sf.wicketlearningapplication.domain.Bug;
import sf.wicketlearningapplication.domain.Severity;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.BugDao;
import sf.wicketlearningapplication.persistence.UserDao;

final class BugForm
  extends Form<Bug>
{

  private static final long serialVersionUID = 2682300618749680498L;

  @SpringBean
  private EntityManagerFactory entityManagerFactory;

  BugForm(final String id, final IModel<Bug> model)
  {
    super(id, model);

    final boolean isInEditMode = model != null;
    if (!isInEditMode)
    {
      setModel(new CompoundPropertyModel<Bug>(new Bug()));
    }

    final TextField<String> summary = new RequiredTextField<String>("summary");
    summary.add(StringValidator.maximumLength(256));
    add(summary);

    final List<Severity> severities = Arrays.asList(Severity.values());
    final DropDownChoice<Severity> severity = new DropDownChoice<Severity>("severity",
                                                                           severities);
    severity.setRequired(true);
    add(severity);

    final TextField<Date> dueByDate = new TextField<Date>("dueByDate");
    dueByDate.add(new DatePicker());
    dueByDate.add(DateValidator.minimum(new Date()));
    add(dueByDate);

    final TextField<Integer> estimatedHours = new TextField<Integer>("estimatedHours",
                                                                     Integer.class);
    estimatedHours.add(NumberValidator.POSITIVE);
    add(estimatedHours);

    final List<User> users = new UserDao(entityManagerFactory).findAll();
    final DropDownChoice<User> owner = new DropDownChoice<User>("owner",
                                                                users,
                                                                new ChoiceRenderer<User>("name",
                                                                                         "id"));
    add(owner);

    if (isInEditMode)
    {
      AjaxFormValidatingBehavior.addToAllFormComponents(this, "onblur");

      final IndicatingAjaxButton saveButton = new IndicatingAjaxButton("save",
        BugForm.this)
      {

        private static final long serialVersionUID = 7949306415616423528L;

        @Override
        protected void onSubmit(final AjaxRequestTarget target,
                                final Form<?> form)
        {
          ModalWindow.closeCurrent(target);
          final BugDao bugDao = new BugDao(entityManagerFactory);
          bugDao.save((Bug) form.getModelObject());
        }

      };
      add(saveButton);
    }
    else
    {
      final Button createButton = new Button("save",
        new Model<String>("Create"))
      {

        private static final long serialVersionUID = 7949306415616423528L;

        @Override
        public void onSubmit()
        {
          final Bug bug = (Bug) getForm().getModelObject();
          final BugDao bugDao = new BugDao(entityManagerFactory);
          bugDao.save(bug);
          BugForm.this.setModel(new CompoundPropertyModel<Bug>(new Bug()));
        }

      };
      add(createButton);
    }

  }

}
