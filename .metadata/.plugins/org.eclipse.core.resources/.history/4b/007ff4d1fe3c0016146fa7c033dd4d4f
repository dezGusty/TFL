package views;

import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
public class FileDownloadView {
     
    private StreamedContent file;

    
    @PostConstruct
	public void init() {
    	InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("../images/513993278.jpg");
        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.jpg");
	}
 
    public StreamedContent getFile() {
        return file;
    }
}
