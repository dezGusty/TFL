package views;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import dataAccessLayer.PlayerDataAccess;

@ManagedBean(name = "loginView")
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
	    boolean result = pda.loginUser(this.username, this.password);
	    if (result) {
		return "/resources/home";
	    }
	}
	return "/index";

    }
}
