/**
 * 
 */
package sf.wicketlearningapplication.pages;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import sf.wicketlearningapplication.WicketLearningApplicationSession;
import sf.wicketlearningapplication.domain.User;
import sf.wicketlearningapplication.persistence.Persistence;
import sf.wicketlearningapplication.persistence.UserDataAccessOperator;

final class LoginForm
  extends Form
{

  private static final long serialVersionUID = 4251400310261345930L;

  private User user;

  LoginForm(final String id)
  {
    super(id);

    user = new User();
    setModel(new CompoundPropertyModel(user));

    add(new TextField("username"));
    add(new PasswordTextField("password"));
  }

  @Override
  protected void onSubmit()
  {
    if (isAuthenticatedUser())
    {
      ((WicketLearningApplicationSession) getSession()).setLoggedInUser(user);
      setResponsePage(HomePage.class);
    }
    else
    {
      error("Login failed. Try again.");
    }
  }

  private boolean isAuthenticatedUser()
  {
    try
    {
      final EntityManager em = Persistence.getEntityManagerFactory()
        .createEntityManager();
      final UserDataAccessOperator userDao = new UserDataAccessOperator(em);
      user = userDao.find(user.getUsername(), user.getPassword());
      return true;
    }
    catch (final NoResultException e)
    {
      return false;
    }
  }

}
