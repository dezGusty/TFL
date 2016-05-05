package views;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.Player;

public class RedirectView {
	public static void Redirect(Player player,String pageForNormalUser, String pageForAdmin)
	{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		if (player != null) {
			try
			{
				if (player.getType() == 1) {
					context.redirect(context.getRequestContextPath() + pageForNormalUser);
				} else {
					context.redirect(context.getRequestContextPath() + pageForAdmin);
				}
				
			}
			catch(Exception ex)
			{
				System.out.println("Redirection error!"+ex.getMessage());
			}
		}
	}
}