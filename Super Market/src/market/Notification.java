package market;
public class Notification {
	private String type;
	private Product product;

	// בנאי שמקבל סוג התראה ויוצר התראה מסוג זה
	public Notification(String type) {
		this.setType(type);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Product getProduct() {
		return this.product;
	}
	// הגדרת קשר בין מוצר להתראה
	public void setProduct(Product product) {
		if(this.product!=product) {
			if(this.product != null)
				this.product.setNotification(null);
			this.product = product;
			this.product.setNotification(this);
		}
	}
	@Override
	public String toString() {
		return "Notification#"+type+"#"+product.getBarcode();
	}
	
	
}