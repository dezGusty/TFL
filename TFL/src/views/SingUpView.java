package views;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import dataAccessLayer.PlayerDataAccess;

@ManagedBean(name="singUpView")
public class SingUpView {
	
	private String name;
    
    private String pass;
    
    private String cpass;
    
    
    public void setCpass(String value)
    {
    	this.cpass=value;
    }
    
    public String getCpass()
    {
    	return this.cpass;
    }
    
    public String getName() {
        return this.name;
    }
 
    public void setName(String name) {
        this.name =name;
    }
 
    public String getPass() {
        return this.pass;
    }
 
    public void setPass(String value) {
        this.pass = value;
    }
     
    public void save() {
    	PlayerDataAccess.createUser(this.name, this.pass);
    	FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO!", "Account created successfully!"));
    }
    
    public void newPlayer(ActionEvent event)  {
	    PlayerDataAccess.createUser(this.name, this.pass);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO!", "Player "+this.name+" successfullt added!"));
	}
}
