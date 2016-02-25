
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "productDetailView")
public class ProductDetailView {
    private String productId = "Hello";

    public String getProductId() {
	return productId;
    }

    public void setProductId(String productId) {
	this.productId = productId;
    }
}
