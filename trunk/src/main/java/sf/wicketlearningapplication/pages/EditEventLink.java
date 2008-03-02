package sf.wicketlearningapplication.pages;


import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import sf.wicketlearningapplication.domain.Event;

final class EditEventLink
  extends Link
{

  private static final long serialVersionUID = 4440835428920186875L;

  EditEventLink(final String id, final IModel object)
  {
    super(id, object);
  }

  @Override
  public void onClick()
  {
    setResponsePage(new EventPage((Event) getModelObject()));
  }

}
