import javax.faces.bean.ManagedBean;

@ManagedBean(name = "editor")
public class EditorBean {

    public String showHello() {
	return "Hello Paula!";
    }

    private String value = "This editor is provided by PrimeFaces";

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }
}
