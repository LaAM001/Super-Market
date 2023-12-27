package market;

import java.util.ArrayList;

public class Receipt {
	private int receiptNum;
	private double totalPrice,pay, charge;
	private String cashierName, date;
	private ArrayList<ReceiptInfo> receiptInfo;

	//בנאי שיוצר קבלה לפי הנתונים שקיבל
	public Receipt(int receiptNum, double totalPrice, double pay, String cashierName, String date) {
		this.setReceiptNum(receiptNum);
		this.setTotalPrice(totalPrice);
		this.setPay(pay);
		this.setCashierName(cashierName);
		this.setDate(date);
		this.setCharge();
		receiptInfo = new ArrayList<ReceiptInfo>();
	}
	
	public void setCharge() {
		charge = pay - totalPrice;
	}
	
	public double getCharge() {
		return charge;
	}
	

	public int getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(int receiptNum) {
		this.receiptNum = receiptNum;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getPay() {
		return pay;
	}

	public void setPay(double pay) {
		this.pay = pay;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public ArrayList<ReceiptInfo> getReceiptInfo(){
		return this.receiptInfo;
	}

	// הוספת "מידע קבלה" למערך שנמצא בעצם
	public void addReceiptInfo(ReceiptInfo info) {
		int index = findReceiptInfo(info);
		if (index == -1) {
			receiptInfo.add(info);
			info.setReceipt(this);
		}
	}
	//  מחיקת "מידע קבלה" במערך שנמצא בעצם ומחיקת הקשר בניהם
	public void removeReceiptInfo(ReceiptInfo info) {
		int index = findReceiptInfo(info);
		if (index != -1) {
			receiptInfo.set(index, receiptInfo.get(receiptInfo.size() - 1));
			receiptInfo.remove(receiptInfo.size() - 1);
			info.setReceipt(null);
		}
	}
	// מציאת "מידע קבלה" בתוך המערך
	public int findReceiptInfo(ReceiptInfo info) {
		for (int i = 0; i < receiptInfo.size(); i++) {
			if (receiptInfo.get(i).equals(info))
				return i;
		}
		return -1;
	}

	@Override
	public String toString() {
		return "Receipt#" + receiptNum + "#" + totalPrice + "#"
				+ pay + "#" + cashierName + "#" + date;
		
		
	}
	
	

}
