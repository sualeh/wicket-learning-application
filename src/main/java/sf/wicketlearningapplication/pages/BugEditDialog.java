package sf.wicketlearningapplication.pages;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.IModel;

public class BugEditDialog
  extends ModalWindow
{

  private static final long serialVersionUID = 5522553988105296877L;

  public BugEditDialog(final String id, final IModel model)
  {
    super(id);
    setContent(new BugPanel(getContentId(), model));
    setTitle("Edit Bug");
    setInitialHeight(250);
    setInitialWidth(350);

    setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
    {
      private static final long serialVersionUID = 2578038324045130551L;

      @SuppressWarnings("unused")
      public void onClose(final AjaxRequestTarget target)
      {
        setResponsePage(getPage());
      }
    });
  }

}
