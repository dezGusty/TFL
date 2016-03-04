package views;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dataAccessLayer.PlayerDataAccess;
import model.Player;

@ManagedBean(name = "loginView")
@SessionScoped
public class LoginView implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

    public String getUsername() {
	return this.username;

    }

    public void setUsername(String value) {
	this.username = value;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String value) {
	this.password = value;
    }

    public String login() {
    	
	PlayerDataAccess pda = new PlayerDataAccess();
	if ((this.username != null) && (this.password != null)) {
		Player player=new Player();
	    player = pda.loginUser(this.username, this.password);
	    if (player!=null) {
	    	if(player.getType()==null)
	    	{
	    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "This username does not have rights!"));
	    		return "/index";
	    	}
	    	else
	    	{
	    		if(player.getType()==1)
	    		{
	    			return "/resources/user";
	    		}
	    		else
	    		{
	    			return "/resources/adminuser";
	    		}
	    	}
	    }
	}
	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Incorrect username or password!"));
	return "/index";
    }
}
