/*
 send strings from client (GUI) to the server
 add,update,delete
 */

package gui;

import market.Receipt;

import java.net.UnknownHostException;
import javax.swing.JTable;

public class SendToServer extends Thread {
	protected static ClientHandler clientH;
	// בנאי לשליחת מידע לשרת
	public SendToServer(ClientHandler clientH) {
		this.clientH = clientH;
	}
	// פונקציה להוספת מידע לבסיס הנתונים והמערכים
	public static void add(String add) {
		try {
			clientH.out.println(add);
			clientH.out.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//פונקציה לעדכון מידע
	public static void update(String updt) {

		clientH.out.println(updt);
		clientH.out.flush();

	}

	//פונקציה למחיקת מידע
	public static void delete(String job, int ID) {
		clientH.out.println("Delete#" + job + "#" + ID);
		clientH.out.flush();
	}
	// פונקציה לשליחת קבלה והמידע שלה לשרת כדי לטבל בה
	public static void updatePay(JTable table, Receipt receipt) {

		int quantity = 0, i = 0;
		for (int row = 0; row < table.getRowCount(); row++) {

			int QuantityCnt = Integer.parseInt((String) table.getValueAt(row, 2));

			for (i = 0; i < Service.products.size(); i++)
				if (table.getValueAt(row, 0).equals(Service.products.get(i).getBarcode() + ""))
					break;
			// הכמות שנדרש להוריד מהמדף למוצר הנוכחי
			quantity = Service.products.get(i).getQuantityShelf() - QuantityCnt;
			// עדכן השרת בשנויים כדי שיטבל בהם
			clientH.out.println("UpdateQuantityShelf#" + Service.products.get(i).getBarcode() + "#" + quantity);
			clientH.out.flush();

		}
		clientH.out.println(receipt.toString());
		clientH.out.flush();

		for (i = 0; i < receipt.getReceiptInfo().size(); i++) {
			clientH.out.println(receipt.getReceiptInfo().get(i).toString());
			clientH.out.flush();

		}

	}
	// שליחת מידע לשרת ליצור התראה חדשה
	public static void notification(String title, String sub) {

		clientH.out.println(title + "#" + sub);
		clientH.out.flush();

	}
	// עדכון השרת ביציאה של משתמש
	public static void exit() throws UnknownHostException {

		try {
			clientH.out.println("Exit#" + ReceiveFromServer.cInd);
			clientH.out.flush();

		} catch (NullPointerException e) {

		}

	}
}
