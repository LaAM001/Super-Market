package market;
import java.util.ArrayList;

public class Supplier {

	private int ID;
	private String name, address, phoneNum;
	private ArrayList<Product> products;

	//בנאי שיוצר ספק לפי הנתונים שקיבל
	public Supplier(int ID, String name, String phoneNum, String address) {
		this.setID(ID);
		this.setName(name);
		this.setPhoneNum(phoneNum);
		this.setAddress(address);
		products = new ArrayList<Product>();
	}
	
	public ArrayList<Product> getProducts(){
		return this.products;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// הוספת מוצר למערך בעצם
	public void addProduct(Product p) {

		int index = findProduct(p);
		if (index == -1) {
			products.add(p);
			p.setSupplier(this);
		}

	}
	//  מחיקת מוצר למערך בעצם ומחיקת הקשר בניהם
	public void removeProduct(Product p) {
		int index = findProduct(p);
		if (index != -1) {
			products.set(index, products.get(products.size() - 1));
			products.remove(products.size() - 1);
			p.setSupplier(null);
		}
	}
	// מציאת מוצר בתוך המערך
	public int findProduct(Product p) {
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).equals(p))
				return i;
		}
		return -1;
	}

	@Override
	public String toString() {
		return "Supplier#" + ID + "#" + name + "#"+ phoneNum + "#" + address;
	}
	

}
