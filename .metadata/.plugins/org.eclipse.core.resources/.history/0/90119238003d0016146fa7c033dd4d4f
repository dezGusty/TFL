package views;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import dataAccessLayer.PlayerDataAccess;
 
@ManagedBean
@ApplicationScoped
public class FileUploadManagedBean {
	 
	private UploadedFile file;
	 
	public UploadedFile getFile() {
	    return file;
	}
	 
	public void setFile(UploadedFile file) {
	   this.file = file;
	}
	     
	public void upload() {
	    	System.out.println("upload");
	        if(file != null) {
	            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
	            try {
					System.out.println(file.getInputstream());
					
					ELContext elContext = FacesContext.getCurrentInstance().getELContext();
					LoginView firstBean = (LoginView) elContext.getELResolver().getValue(elContext, null, "loginView");
					System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
					
					String newName=firstBean.getCurrentPlayer().getId()+file.getFileName();
					
					copyFile(newName, file.getInputstream());
//					byte[] bytes = new byte[1024];
//					file.getInputstream().read(bytes);
//					firstBean.getCurrentPlayer().setImage(bytes);
//					System.out.println(firstBean.getCurrentPlayer().getImage());
					firstBean.setCurrentPlayer(PlayerDataAccess.updateProfilePicture(firstBean.getCurrentPlayer().getId(),"../images/"+ newName));
//					System.out.println("Current player image"+firstBean.getCurrentPlayer().getImage());
					
				} catch (IOException e) {
					e.printStackTrace();
				}
	            FacesContext.getCurrentInstance().addMessage(null, message);
	        }
	    }

	    public void copyFile(String fileName, InputStream in) {
	           try {
	        	    String destination="C://Users//luchi//Desktop//TFL//TFL//WebContent//images//";
	        	    ExternalContext extContext = 
	                          FacesContext.getCurrentInstance().getExternalContext();
	            File result = new File(extContext.getRealPath
	                         ("//WEB-INF//files//" + fileName));
	          //  System.out.println(extContext.getResource(path));
	            System.out.println(extContext.getRealPath
	                         ("//WEB-INF//files//" + fileName));
	            
	                OutputStream out = new FileOutputStream(new File(destination + fileName));
	              
	                int read = 0;
	                byte[] bytes = new byte[1024];
	              
	                while ((read = in.read(bytes)) != -1) {
	                    out.write(bytes, 0, read);
	                    System.out.println(bytes);
	                }
	               
	                in.close();
	                out.flush();
	                out.close();
	              
	                System.out.println("New file created!");
	                } catch (IOException e) {
	                System.out.println(e.getMessage());
	                }
	    }
	    
	    public void incarcaImagine(FileUploadEvent event)
	    {
	    	System.out.println("Incarca imagine");
	    }
}