
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class getFromDB {
	// פונקציה שמתחברת לבסיס הנתונים ושולפת את כל המידע מהטבלאות
	public static void getValues() throws ClassNotFoundException {

		Statement stmt;
		ResultSet rs;
		try {
			// התחברות לבסיס הנתונים
			Connection conn = connClass.getConn();
			stmt = conn.createStatement();
			//שליפת כל המידע מטבלת הספקים
			String query = "SELECT * from supplier";
			rs = stmt.executeQuery(query);
			// הוספת כל המידע של הספקים לתוך מערך מחרוזות
			while (rs.next()) {
				Server.suppliers.add("Supplier#" + rs.getInt("ID") + "#" + rs.getString("name") + "#"
						+ rs.getString("phonenum") + "#" + rs.getString("address"));
			}

			//שליפת כל המידע מטבלת המשתמשים
			query = "SELECT * from user";
			rs = stmt.executeQuery(query);
			// הוספת כל המידע של המשתמשים לתוך מערך מחרוזות
			while (rs.next()) {
				Server.users.add("User#" + rs.getInt("ID") + "#" + rs.getString("name") + "#" + rs.getString("email")
						+ "#" + rs.getString("phone") + "#" + rs.getString("password") + "#" + rs.getString("permission"));
			}

			//שליפת כל המידע מטבלת המוצרים
			query = "SELECT * from product";
			rs = stmt.executeQuery(query);
			// הוספת כל המידע של המוצרים לתוך מערך מחרוזות
			while (rs.next()) {
				Server.products.add("Product#" + rs.getInt("barcode") + "#" + rs.getString("name") + "#"
						+ rs.getDouble("price") + "#" + rs.getString("shelfID") + "#" + rs.getInt("quantityShelf") + "#"
						+ rs.getInt("quantityStorage") + "#" + rs.getInt("minLimitShelf") + "#" + rs.getInt("maxLimitShelf") + "#"+ rs.getInt("minLimitStorage") + "#"+ rs.getInt("maxLimitStorage") + "#" + rs.getInt("supID"));
			}

			//שליפת כל המידע מטבלת ההתראות
			query = "SELECT * from notification";
			rs = stmt.executeQuery(query);
			// הוספת כל המידע של ההתראות לתוך מערך מחרוזות
			while (rs.next()) {
				Server.notifications.add("Notification#" + rs.getString("type") + "#" + rs.getInt("productBarcode"));
			}

			//שליפת כל המידע מטבלת הקבלות
			query = "SELECT * from receipt";
			rs = stmt.executeQuery(query);
			// הוספת כל המידע של הקבלות לתוך מערך מחרוזות
			while (rs.next()) {
				Server.receipts.add("Receipt#" + rs.getInt("receiptNum") + "#" + rs.getInt("totalPrice") + "#"
						+ rs.getInt("pay") + "#" + rs.getString("cashierName") + "#" + rs.getString("date"));
			}

			//שליפת כל המידע מטבלת מידע של הקבלות
			query = "SELECT * from receiptsinfo";
			rs = stmt.executeQuery(query);
			// הוספת כל המידע של המידע של הקבלות לתוך מערך מחרוזות
			while (rs.next()) {
				Server.receiptInfo.add("ReceiptInfo#" + rs.getInt("receiptNum") + "#" + rs.getInt("Barcode") + "#"
						+ rs.getString("name") + "#" + rs.getDouble("price") + "#" + rs.getInt("count"));
			}

			//שליפת כל המידע מטבלת סטטיסטיקת הקניות
			query = "SELECT * from salestatistic";
			rs = stmt.executeQuery(query);
			// הוספת כל המידע של סטטיסטיקת הקניות לתוך מערך מחרוזות
			while (rs.next()) {
				Server.saleStatistic.add("SaleStatistic#" + rs.getInt("proBarcode") + "#" + rs.getInt("proCnt") + "#"+ rs.getInt("month")+ "#" + rs.getDouble("originalPrice"));
			}

			// סגירת ההתחברות לבסיס הנתונים
			conn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
