package market;

import java.util.ArrayList;

public class Product {
	private int barcode, quantityShelf, quantityStorage, minLimitShelf, maxLimitShelf, minLimitStorage, maxLimitStorage, supplierID;
	private double price;
	private String name, shelf;
	private Supplier supplier;
	private Notification notification;
	private ArrayList<ReceiptInfo> receiptInfo;

	//בנאי שיוצר מוצר לפי הנתונים שקיבל
	public Product(int barcode, String name, double price, String shelf, int quantityShelf, int quantityStorage, int minLimitShelf, int maxLimitShelf, int minLimitStorage, int maxLimitStorage, int supplierID) {
		this.setBarcode(barcode);
		this.setName(name);
		this.setPrice(price);
		this.setShelf(shelf);
		this.setQuantityShelf(quantityShelf);
		this.setQuantityStorage(quantityStorage);
		this.setMinLimitShelf(minLimitShelf);
		this.setMaxLimitShelf(maxLimitShelf);
		this.setMinLimitStorage(minLimitStorage);
		this.setMaxLimitStorage(maxLimitStorage);
		this.setSupplierID(supplierID);
		receiptInfo = new ArrayList<ReceiptInfo>();
	}
	public int getBarcode() {
		return barcode;
	}
	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}
	public int getQuantityShelf() {
		return quantityShelf;
	}
	public void setQuantityShelf(int quantityShelf) {
		this.quantityShelf = quantityShelf;
	}
	public int getQuantityStorage() {
		return quantityStorage;
	}
	public void setQuantityStorage(int quantityStorage) {
		this.quantityStorage = quantityStorage;
	}
	public int getMinLimitShelf() {
		return minLimitShelf;
	}
	public void setMinLimitShelf(int minLimitShelf) {
		this.minLimitShelf = minLimitShelf;
	}
	public int getMaxLimitShelf() {
		return maxLimitShelf;
	}
	public void setMaxLimitShelf(int maxLimitShelf) {
		this.maxLimitShelf = maxLimitShelf;
	}
	public int getMinLimitStorage() {
		return minLimitStorage;
	}
	public void setMinLimitStorage(int minLimitStorage) {
		this.minLimitStorage = minLimitStorage;
	}
	public int getMaxLimitStorage() {
		return maxLimitStorage;
	}
	public void setMaxLimitStorage(int maxLimitStorage) {
		this.maxLimitStorage = maxLimitStorage;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getShelf() {
		return shelf;
	}
	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSupplierID() {
		return supplierID;
	}
	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

	public Supplier getSupplier() {
		return supplier;
	}
	// הגדרת קשר בין הספק והמוצר
	public void setSupplier(Supplier supplier) {
		if(this.supplier!=supplier) {
			if(this.supplier!= null)
				this.supplier.removeProduct(this);
			this.supplier = supplier;
			if(this.supplier != null)
				this.supplier.addProduct(this);
		}
	}

	public Notification getNotification() {
		return this.notification;
	}
	// הגדרת קשר בין ההתראה והמוצר
	public void setNotification(Notification notification) {
		if(this.notification!=notification) {
			this.notification = notification;
			if(this.notification!=null)
				this.notification.setProduct(this);
		}
	}

	// הוספת מידע קבלה למערך בעצם
	public void addReceiptInfo(ReceiptInfo ri) {
		int index = findReceiptInfo(ri);
		if (index == -1) {
			receiptInfo.add(ri);
			ri.setProduct(this);
		}

	}
	//  מחיקת מידע קבלה במערך בעצם ומחיקת הקשר בניהם
	public void removeReceiptInfo(ReceiptInfo ri) {
		int index = findReceiptInfo(ri);
		if (index != -1) {
			receiptInfo.set(index, receiptInfo.get(receiptInfo.size() - 1));
			receiptInfo.remove(receiptInfo.size() - 1);
			ri.setProduct(null);
		}
	}
	// מציאת מידע קבלה בתוך המערך
	public int findReceiptInfo(ReceiptInfo ri) {
		for (int i = 0; i < receiptInfo.size(); i++) {
			if (receiptInfo.get(i).equals(ri))
				return i;
		}
		return -1;
	}
	
	
	@Override
	public String toString() {
		return "Product#" + barcode + "#" + name + "#"+ price + "#" + shelf + "#" + quantityShelf + "#"
				+ quantityStorage + "#" + minLimitShelf + "#" + maxLimitShelf + "#"+ minLimitStorage + "#"+ maxLimitStorage + "#"+ supplierID;
	}
	
}
