package views;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import dataAccessLayer.PlayerDataAccess;

@ManagedBean(name="fileUploadBean")
@SessionScoped
public class FileUploadBean implements Serializable{
/**
     *
     */
    private static final long serialVersionUID = 1L;

    private UploadedFile resume;
 
    public UploadedFile getResume() {
        return resume;
    }
 
    public void setResume(UploadedFile resume) {
        this.resume = resume;
    }
 

    public void uploadPhoto(FileUploadEvent e) throws IOException{
 
        UploadedFile uploadedPhoto=e.getFile();
		
        String filePath="D:/Code/TFL/TFL/WebContent/resources/img/";
        byte[] bytes=null;
 
            if (null!=uploadedPhoto) {
                bytes = uploadedPhoto.getContents();
                String filename = FilenameUtils.getName(uploadedPhoto.getFileName());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath+filename)));
                stream.write(bytes);
                stream.close();
                
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
				LoginView firstBean = (LoginView) elContext.getELResolver().getValue(elContext, null, "loginView");
				firstBean.setCurrentPlayer(PlayerDataAccess.updateProfilePicture(firstBean.getCurrentPlayer().getId(),filename));
				System.out.println(firstBean.getCurrentPlayer().getPicture());
            }
 
        FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage(FacesMessage.SEVERITY_INFO,"Your Photo (File Name "+ uploadedPhoto.getFileName()+ " with size "+ uploadedPhoto.getSize()+ ")  Uploaded Successfully", ""));
    }
 
}