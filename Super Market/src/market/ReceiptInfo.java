package market;

public class ReceiptInfo {
	private int receiptNum, productBarcode, productCount;
	private Receipt receipt;
	private Product product;

	//בנאי שיוצר עצם מידע-קבלה לפי הנתונים שקיבל
	public ReceiptInfo(int receiptNum, int productBarcode, int productCount) {
		this.setReceiptNum(receiptNum);
		this.setProductBarcode(productBarcode);
		this.setProductCount(productCount);
	}

	public int getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(int receiptNum) {
		this.receiptNum = receiptNum;
	}

	public int getProductBarcode() {
		return productBarcode;
	}

	public void setProductBarcode(int productBarcode) {
		this.productBarcode = productBarcode;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public Receipt getReceipt() {
		return this.receipt;
	}
	// הגדרת הקשר בין הקבלה ובין פרטיה
	public void setReceipt(Receipt receipt) {
		if (this.receipt != receipt) {
			if (this.receipt != null)
				this.receipt.removeReceiptInfo(this);
			this.receipt = receipt;
			if (this.receipt != null)
				this.receipt.addReceiptInfo(this);
		}
	}

	public Product getProduct() {
		return product;
	}
	// הגדרת הקשר בין המוצר למידע של הקבלה
	public void setProduct(Product product) {
		if (this.product != product) {
			if (this.product != null)
				this.product.removeReceiptInfo(this);
			this.product = product;
			if (this.product != null)
				this.product.addReceiptInfo(this);
		}
	}

	@Override
	public String toString() {
		return "ReceiptInfo#" + receiptNum + "#"+ productBarcode + "#"+ product.getName() + "#"+ product.getPrice() + "#"+ productCount;
	}

}
