package views;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

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
    	PlayerDataAccess pda=new PlayerDataAccess();
    	pda.createUser(this.name, this.pass, 1, true, 0.0);
    	
    	System.out.println(this.name+" "+this.pass);
    	System.out.println("Data saved!");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Data Saved"));
    }

	
}
