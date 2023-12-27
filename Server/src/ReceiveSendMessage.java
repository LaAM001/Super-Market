
/*
 receive from client (GUI),
 add/update/delete database,
 resend to all the clients.  
 */
import java.io.IOException;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.Arrays;

public class ReceiveSendMessage extends Thread {

	protected ClientHandler clientH;
	protected  static Connection conn;
	// בנאי מפעיל thread בעת התחברות חדשה של משתמש לשליחת וקבלת מידע דרך השרת
	public ReceiveSendMessage(ClientHandler clientH) throws IOException {
		this.clientH = clientH;
		this.start();
	}
	// פונקציית קבלת ושליחת מידע דרך השרת ולעשות הנחה ב5% למוצר הנמכר ביותר בחודש הקודם בכל תחילת חודש
	public void run() {
		try {
			// שליחת המספר היחודי למשתמש שהתחבר
			clientH.out.println("clientIndex#" + (Server.cInd++));
			clientH.out.flush();
			// שליחת כל מידע הספקים השמור במערכים
			for (int i = 0; i < Server.suppliers.size(); i++) {
				clientH.out.println(Server.suppliers.get(i));
				clientH.out.flush();

			}
			// שליחת כל מידע המוצרים השמור במערכים
			for (int i = 0; i < Server.products.size(); i++) {
				clientH.out.println(Server.products.get(i));
				clientH.out.flush();
			}
			// שליחת כל מידע ההתראות השמור במערכים
			for (int i = 0; i < Server.notifications.size(); i++) {
				clientH.out.println(Server.notifications.get(i));
				clientH.out.flush();
			}
			// שליחת כל מידע המוצרים השמור במערכים
			for (int i = 0; i < Server.users.size(); i++) {
				clientH.out.println(Server.users.get(i));
				clientH.out.flush();
			}
			// שליחת כל מידע הקבלות השמור במערכים
			for (int i = 0; i < Server.receipts.size(); i++) {
				clientH.out.println(Server.receipts.get(i));
				clientH.out.flush();
			}
			// שליחת כל מידע מידע הקבלות השמור במערכים
			for (int i = 0; i < Server.receiptInfo.size(); i++) {
				Server.clients.get(Server.clients.size() - 1).out.println(Server.receiptInfo.get(i));
				clientH.out.flush();
			}

			String buffer = null;
			// התחברות לבסיס הנתונים
			conn = connClass.getConn();
			PreparedStatement preparedStmt;
			String query;
			// הגדרת היודש הנוכחי
			int month = LocalDate.now().getMonth().getValue();

			// בדיקת אם יש מידע במערך סטטיסטיקת הקניות
			if (Server.saleStatistic.size() > 1) {
				boolean thereIsMax = false, newYear = false;
				String[] firstMonth = null;
				for (int i = 0; i < Server.saleStatistic.size(); i++) {
					firstMonth = Server.saleStatistic.get(i).split("#");
					if (!firstMonth[4].equals("0.0")) {
						if(firstMonth[3].equals("12") && month == 1)
							// נכנסנו לשנה חדשה
							newYear = true;
						// מצאנו המוצר בעל הקניות הכי הרבה בחודש הקודם
						thereIsMax = true;
						break;
					}
				}

				String[] saleLastMonth = Server.saleStatistic.get(Server.saleStatistic.size() - 1).split("#");
				// בדיקת כניסה לחודש חדש
				if (month > Integer.parseInt(saleLastMonth[3]) || newYear == true) {
					// חודש ראשון (אין עוד מוצר שנקנה הכי הרבה לחודש הקודם)
					if(thereIsMax==false){
						double tempCnt = 0;
						String[] maxSale = null;
						int maxSaleInd = 0;
						for (int i = 0; i < Server.saleStatistic.size(); i++) {
							maxSale = Server.saleStatistic.get(i).split("#");
							//בדיקת המוצר הנמכר ביותר
							if (Double.parseDouble(maxSale[2]) > tempCnt) {
								tempCnt = Double.parseDouble(maxSale[2]);
								// שמירת index המוצר הנמכר ביותר
								maxSaleInd = i;
							}
						}
						maxSale = Server.saleStatistic.get(maxSaleInd).split("#");
						// מחיקת כל המידע בבסיס הנתומים מתוך טבלת salestatistic חוץ מהמוצר הנמכר ביותר לחודש הקודם
						query = ("delete from salestatistic where proBarcode NOT IN (?)");
						preparedStmt = conn.prepareStatement(query);
						preparedStmt.setInt(1, Integer.parseInt(maxSale[1]));
						preparedStmt.executeUpdate();
						// מחיקת כל המידע ממערך salestatistic חוץ מהמוצר הנמכר ביותר לחודש הקודם
						for (int i = 0; i < Server.saleStatistic.size(); ) {
							String[] saleTemp = Server.saleStatistic.get(i).split("#");
							if (!saleTemp[1].equals(maxSale[1]))
								Server.saleStatistic.remove(i);
							else i++;
						}

						for (int i = 0; i < Server.products.size(); i++) {
							String[] pro = Server.products.get(i).split("#");
							maxSale = Server.saleStatistic.get(0).split("#");
							if (pro[1].equals(maxSale[1])) {
								//  עדכון בטבלת salestatistic ושמירת המחיר המקורי ואיפוס הכמות הנמכרת למוצר הנמכר ביותר
								query = ("UPDATE salestatistic SET proCnt = ?, originalPrice = ? where proBarcode = ?");
								preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, 0);
								preparedStmt.setDouble(2, Double.parseDouble(pro[3]));
								preparedStmt.setInt(3, Integer.parseInt(maxSale[1]));
								preparedStmt.executeUpdate();

								//  עדכון במערך salestatistic ושמירת המחיר המקורי ואיפוס הכמות הנמכרת למוצר הנמכר ביותר
								maxSale[4] = pro[3] + "";
								maxSale[2] = "0";
								Server.saleStatistic.set(0, String.join("#", maxSale));
								// הנחה 5% למוצר הנמכר ביותר בחודש הקודם
								pro[3] = String.format("%.2f", (Double.parseDouble(pro[3]) * 0.95));

								//  עדכון בטבלת salestatistic את מחיר המוצר הנמכר ביותר למחיר אחרי ההנחה
								query = ("UPDATE product SET  price = ? where barcode = ?");
								preparedStmt = conn.prepareStatement(query);
								preparedStmt.setDouble(1, Double.parseDouble(pro[3]));
								preparedStmt.setInt(2, Integer.parseInt(pro[1]));
								preparedStmt.executeUpdate();

								//עדכון כל המשתמשים החדשים במחיר החדש למוצר
								for (ClientHandler c : Server.clients) {
									c.out.println("Update#" + String.join("#", pro));
									c.out.flush();
								}

								break;
							}
						}
					}
					// יש מוצר שנמכר הכי הרבה
					if(thereIsMax == true) {
						for(int i=0; i< Server.saleStatistic.size(); i++){
							saleLastMonth = Server.saleStatistic.get(i).split("#");
							// מציאת המוצר הנמכר ביותר לחודש הקודם
							if(!saleLastMonth[4].equals("0.0"))
								break;
						}
						double tempCnt = 0;
						String[] maxSale;
						int maxSaleInd = 0;
						for (int i = 1; i < Server.saleStatistic.size(); i++) {
							maxSale = Server.saleStatistic.get(i).split("#");
							//בדיקת המוצר הנמכר ביותר
							if (Double.parseDouble(maxSale[2]) > tempCnt) {
								tempCnt = Double.parseDouble(maxSale[2]);
								// שמירת index המוצר הנמכר ביותר
								maxSaleInd = i;
							}
						}
						maxSale = Server.saleStatistic.get(maxSaleInd).split("#");
						// בדיקת אם המוצר הנמכר ביותר של החודש הקודם שונה מזה של חודשיים קודם
						if (!saleLastMonth[1].equals(maxSale[1])) {
							for (int i = 0; i < Server.products.size(); i++) {
								String[] pro = Server.products.get(i).split("#");

								if (pro[1].equals(saleLastMonth[1])) {
									//החזרת המוצר שעלו הנחה בחודש האחרון למחיר המקורי שלו
									pro[3] = saleLastMonth[4];
									Server.products.set(i, String.join("#", pro));
									//עדכון כל המשתמשים החדשים במחיר החדש למוצר
									for (ClientHandler c : Server.clients) {
										c.out.println("Update#" + Server.products.get(i));
										c.out.flush();
									}
									//החזרת המוצר שעלו הנחה בחודש האחרון למחיר המקורי שלו בבסיס הנתונים
									query = ("UPDATE product SET  price = ? where barcode = ?");
									preparedStmt = conn.prepareStatement(query);
									preparedStmt.setDouble(1, Double.parseDouble(pro[3]));
									preparedStmt.setInt(2, Integer.parseInt(pro[1]));
									preparedStmt.executeUpdate();

									break;
								}
							}
							// מחיקת כל המידע בבסיס הנתומים מתוך טבלת salestatistic חוץ מהמוצר הנמכר ביותר לחודש הקודם
							query = ("delete from salestatistic where proBarcode NOT IN (?)");
							preparedStmt = conn.prepareStatement(query);
							preparedStmt.setInt(1, Integer.parseInt(maxSale[1]));
							preparedStmt.executeUpdate();

							// מחיקת כל המידע בבסיס הנתומים מתוך טבלת salestatistic חוץ מהמוצר הנמכר ביותר לחודש הקודם
							for (int i = 0; i < Server.saleStatistic.size(); ) {
								String[] saleTemp = Server.saleStatistic.get(i).split("#");
								if (!saleTemp[1].equals(maxSale[1]))
									Server.saleStatistic.remove(i);
								else i++;
							}
							double originalPrice = 0;
							for (int i = 0; i < Server.products.size(); i++) {
								String[] pro = Server.products.get(i).split("#");
								if (pro[1].equals(maxSale[1])) {
									originalPrice = Double.parseDouble(pro[3]);
									// הנחה 5% למוצר הנמכר ביותר בחודש הקודם
									pro[3] = String.format("%.2f", (Double.parseDouble(pro[3]) * 0.95));
									maxSale[4] = pro[3];
									maxSale[2] = "0";
									Server.saleStatistic.set(0, String.join("#", maxSale));
									Server.products.set(i, String.join("#", pro));
									//עדכון כל המשתמשים החדשים במחיר החדש למוצר
									for (ClientHandler c : Server.clients) {
										c.out.println("Update#" + String.join("#", pro));
										c.out.flush();
									}

									//  עדכון בטבלת product את מחיר המוצר הנמכר ביותר למחיר אחרי ההנחה
									query = ("UPDATE product SET  price = ? where barcode = ?");
									preparedStmt = conn.prepareStatement(query);
									preparedStmt.setDouble(1, Double.parseDouble(pro[3]));
									preparedStmt.setInt(2, Integer.parseInt(pro[1]));
									preparedStmt.executeUpdate();

									//  עדכון בטבלת salestatistic את מחיר המוצר הנמכר ביותר למחיר אחרי ההנחה
									query = ("UPDATE salestatistic SET  originalPrice = ? , proCnt = ? where proBarcode = ?");
									preparedStmt = conn.prepareStatement(query);
									preparedStmt.setDouble(1, originalPrice);
									preparedStmt.setInt(2, 0);
									preparedStmt.setInt(3, Integer.parseInt(pro[1]));
									preparedStmt.executeUpdate();


									break;
								}
							}
						}
					}
				}
			}

			while (true) {

				// שמירת הקלט שהתקב ללשרת ממשתמש
				buffer = clientH.in.readLine();
				System.out.println(buffer);
				// הפיכת הקלט למערך
				String[] receiveArr = (String[]) buffer.split("#");
				// טיבול ביציאת משתמש מהמערכת
				if (receiveArr[0].equals("Exit")) {
					for(int i=0; i<Server.clients.size(); i++) {
						if(Integer.parseInt(receiveArr[1]) ==  Server.clientsInd.get(i)) {
							Server.clientsInd.remove(i);
							Server.clients.remove(i);
							StartServer.connectedCnt.setText("Users Connected: "+Server.clients.size());
						}
					}

				}
				// יצירת ספק חדש בבסיס הנתונים ועדכון המערך והמשתמשים המחוברים לשרת
				else if (receiveArr[0].equals("Supplier")) {
					query = "insert into supplier values(?,?,?,?)";
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, Integer.parseInt(receiveArr[1]));
					preparedStmt.setString(2, receiveArr[2]);
					preparedStmt.setString(3, receiveArr[3]);
					preparedStmt.setString(4, receiveArr[4]);
					preparedStmt.execute();

					Server.suppliers.add(buffer);

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}
				}
				// יצירת מוצר חדש בבסיס הנתונים ועדכון המערך והמשתמשים המחוברים לשרת
				else if (receiveArr[0].equals("Product")) {

					query = "insert into product values(?,?,?,?,?,?,?,?,?,?,?)";
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, Integer.parseInt(receiveArr[1]));
					preparedStmt.setString(2, receiveArr[2]);
					preparedStmt.setDouble(3, Double.parseDouble(receiveArr[3]));
					preparedStmt.setString(4, receiveArr[4]);
					preparedStmt.setInt(5, Integer.parseInt(receiveArr[5]));
					preparedStmt.setInt(6, Integer.parseInt(receiveArr[6]));
					preparedStmt.setInt(7, Integer.parseInt(receiveArr[7]));
					preparedStmt.setInt(8, Integer.parseInt(receiveArr[8]));
					preparedStmt.setInt(9, Integer.parseInt(receiveArr[9]));
					preparedStmt.setInt(10, Integer.parseInt(receiveArr[10]));
					preparedStmt.setInt(11, Integer.parseInt(receiveArr[11]));
					preparedStmt.execute();

					Server.products.add(buffer);

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}
				}
				// יצירת משתמש חדש בבסיס הנתונים ועדכון המערך והמשתמשים המחוברים לשרת
				else if (receiveArr[0].equals("User")) {
					query = "insert into user values(?,?,?,?,?,?)";
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, Integer.parseInt(receiveArr[1]));
					preparedStmt.setString(2, receiveArr[2]);
					preparedStmt.setString(3, receiveArr[3]);
					preparedStmt.setString(4, receiveArr[4]);
					preparedStmt.setString(5, receiveArr[5]);
					preparedStmt.setString(6, receiveArr[6]);
					preparedStmt.execute();

					Server.users.add(buffer);

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}
					// יצירת קבלה חדש בבסיס הנתונים ועדכון המערך והמשתמשים המחוברים לשרת
				} else if (receiveArr[0].equals("Receipt")) {

					query = "insert into receipt values(?,?,?,?,?)";
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, Integer.parseInt(receiveArr[1]));
					preparedStmt.setDouble(2, Double.parseDouble(receiveArr[2]));
					preparedStmt.setDouble(3, Double.parseDouble(receiveArr[3]));
					preparedStmt.setString(4, receiveArr[4]);
					preparedStmt.setString(5, receiveArr[5]);
					preparedStmt.execute();

					Server.receipts.add(buffer);

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}

					// יצירת מידע קבלה חדש בבסיס הנתונים ועדכון המערך והמשתמשים המחוברים לשרת
				} else if (receiveArr[0].equals("ReceiptInfo")) {

					query = "insert into receiptsinfo values(?,?,?,?,?,?)";
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, 0);
					preparedStmt.setInt(2, Integer.parseInt(receiveArr[1]));
					preparedStmt.setInt(3, Integer.parseInt(receiveArr[2]));
					preparedStmt.setString(4, receiveArr[3]);
					preparedStmt.setDouble(5, Double.parseDouble(receiveArr[4]));
					preparedStmt.setInt(6, Integer.parseInt(receiveArr[5]));
					preparedStmt.execute();

					Server.receiptInfo.add(buffer);

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}

				} else if (receiveArr[0].equals("UpdateQuantityShelf")) {
					String notify=null;
					// עדכון כמות המוצר שנקנה לכמות החדשה
					query = "UPDATE product SET quantityShelf = ? where barcode = ?";
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, Integer.parseInt(receiveArr[2]));
					preparedStmt.setInt(2, Integer.parseInt(receiveArr[1]));
					preparedStmt.execute();

					boolean isInStatistics = false;
					for (int i = 0; i < Server.products.size(); i++) {
						String[] tempProduct = (String[]) Server.products.get(i).split("#");
						if (tempProduct[1].equals(receiveArr[1])) {
							// הכמות שנקנתה ברכישה
							int proCnt = Integer.parseInt(tempProduct[5]) - Integer.parseInt(receiveArr[2]);
							tempProduct[5] = receiveArr[2];
							// אם הכמות במדף חצתה הגבול שנקבע שולח למשתמשים ליצירת התראה למחסנאי גם יצירת התראה בבסיס הנתונים והמערך
							if (Integer.parseInt(tempProduct[5]) < Integer.parseInt(tempProduct[7])) {
								notify = "Notification#Shelf#" + tempProduct[1];
								Server.notifications.add("Notification#Shelf#"+tempProduct[1]);

								query = "insert into notification values(?,?,?)";
								preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, 0);
								preparedStmt.setString(2, "Shelf");
								preparedStmt.setInt(3, Integer.parseInt(receiveArr[1]));
								preparedStmt.execute();

							}

							Server.products.set(i, String.join("#", tempProduct));
							for (int j = 0; j < Server.saleStatistic.size(); j++) {
								String[] tempSale = (String[]) Server.saleStatistic.get(j).split("#");
								if (tempSale[1].equals(tempProduct[1])) {
									isInStatistics = true;
									tempSale[2] = (Integer.parseInt(tempSale[2]) + proCnt) + "";
									// עדכון הכמות ב salestatistic למוצר שנקנה
									query = ("UPDATE salestatistic SET  proCnt = ? , month = ? where proBarcode = ?");
									preparedStmt = conn.prepareStatement(query);
									preparedStmt.setInt(1, Integer.parseInt(tempSale[2]));
									preparedStmt.setInt(2, month);
									preparedStmt.setInt(3, Integer.parseInt(tempSale[1]));
									preparedStmt.executeUpdate();
								}
							}
							//  המוצר נקנה לפעם הראשונה בחודש זה, מוסיפים אותו לבסיס הנתונים ולמערך בשרת
							if (isInStatistics == false) {
								query = ("INSERT into salestatistic values(?,?,?,?)");
								preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, Integer.parseInt(tempProduct[1]));
								preparedStmt.setInt(2, proCnt);
								preparedStmt.setInt(3, month);
								preparedStmt.setNull(4, Types.DOUBLE);
								preparedStmt.executeUpdate();

								Server.saleStatistic.add("SaleStatistic#"+tempProduct[1]+"#"+proCnt+"#"+month+"#0.0");
							}
							break;
						}
					}
					// עדכון המשתמשים בכל המידע הרלונטי
					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
						if(notify!=null) {
							c.out.println(notify);
							c.out.flush();
						}
					}
				}
				// עדכון משתמש בבסיס הנתונים, המערך בשרת ועדכון המשתמשים האחרים בשינויים
				else if (receiveArr[0].equals("Update") && receiveArr[1].equals("User")) {
					query = ("UPDATE user SET name = ? , email = ? , phone = ? , password= ? , permission = ? where ID = ?");
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, receiveArr[3]);
					preparedStmt.setString(2, receiveArr[4]);
					preparedStmt.setString(3, receiveArr[5]);
					preparedStmt.setString(4, receiveArr[6]);
					preparedStmt.setString(5, receiveArr[7]);
					preparedStmt.setInt(6, Integer.parseInt(receiveArr[2]));
					preparedStmt.executeUpdate();

					for(int i=0;i<Server.users.size();i++) {
						String[] tempIn = (String[]) Server.users.get(i).split("#");

						if(tempIn[1].equals(receiveArr[2])) {
							String userTemp = String.join("#",Arrays.copyOfRange(receiveArr, 1, receiveArr.length));
							Server.users.set(i, userTemp);
							break;
						}

					}

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}
					// עדכון ספק בבסיס הנתונים, המערך בשרת ועדכון המשתמשים האחרים בשינויים
				} else if (receiveArr[0].equals("Update") && receiveArr[1].equals("Supplier")) {
					query = ("UPDATE supplier SET name = ? , phonenum = ? , address = ? where ID = ?");
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, receiveArr[3]);
					preparedStmt.setString(2, receiveArr[4]);
					preparedStmt.setString(3, receiveArr[5]);
					preparedStmt.setInt(4, Integer.parseInt(receiveArr[2]));
					preparedStmt.executeUpdate();

					for(int i=0;i<Server.suppliers.size();i++) {
						String[] tempIn = (String[]) Server.suppliers.get(i).split("#");

						if(tempIn[1].equals(receiveArr[2])) {
							String suppTemp = String.join("#",Arrays.copyOfRange(receiveArr, 1, receiveArr.length));
							Server.suppliers.set(i, suppTemp);
							break;
						}
					}

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}
					// עדכון מוצר בבסיס הנתונים, המערך בשרת ועדכון המשתמשים האחרים בשינויים
				} else if (receiveArr[0].equals("Update") && receiveArr[1].equals("Product")) {
					query = ("UPDATE product SET name = ? , price = ? , shelfID = ?, quantityShelf = ?, quantityStorage = ?, minLimitShelf = ?, maxLimitShelf = ?,minLimitStorage = ?,maxLimitStorage = ?, supID = ? where barcode = ?");
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, receiveArr[3]);
					preparedStmt.setDouble(2, Double.parseDouble(receiveArr[4]));
					preparedStmt.setString(3, receiveArr[5]);
					preparedStmt.setInt(4, Integer.parseInt(receiveArr[6]));
					preparedStmt.setInt(5, Integer.parseInt(receiveArr[7]));
					preparedStmt.setInt(6, Integer.parseInt(receiveArr[8]));
					preparedStmt.setInt(7, Integer.parseInt(receiveArr[9]));
					preparedStmt.setInt(8, Integer.parseInt(receiveArr[10]));
					preparedStmt.setInt(9, Integer.parseInt(receiveArr[11]));
					preparedStmt.setInt(10, Integer.parseInt(receiveArr[12]));
					preparedStmt.setInt(11, Integer.parseInt(receiveArr[2]));
					preparedStmt.executeUpdate();

					for(int i=0;i<Server.products.size();i++) {
						String[] tempIn = (String[]) Server.products.get(i).split("#");

						if(tempIn[1].equals(receiveArr[2])) {
							String proTemp = String.join("#",Arrays.copyOfRange(receiveArr, 1, receiveArr.length));
							Server.products.set(i, proTemp);
							break;
						}
					}

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}
					// מחיקת משתמש בבסיס הנתונים, המערך בשרת ועדכון המשתמשים האחרים בשינויים
				} else if (receiveArr[0].equals("Delete")
						&& (receiveArr[1].equals("Stockkeeper") || receiveArr[1].equals("Cashier"))) {
					query = "DELETE FROM user WHERE id = ?";
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, Integer.parseInt(receiveArr[2]));
					preparedStmt.executeUpdate();

					for(int i=0;i<Server.users.size();i++) {
						String[] tempIn = (String[]) Server.users.get(i).split("#");

						if(tempIn[1].equals(receiveArr[2])) {
							Server.users.remove(i);
							break;
						}
					}

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}
					// מחיקת ספק בבסיס הנתונים, המערך בשרת ועדכון המשתמשים האחרים בשינויים
				} else if (receiveArr[0].equals("Delete") && receiveArr[1].equals("Supplier")) {
					query = "DELETE FROM supplier WHERE id = ?";
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, Integer.parseInt(receiveArr[2]));
					preparedStmt.executeUpdate();

					for(int i=0;i<Server.suppliers.size();i++) {
						String[] tempIn = (String[]) Server.suppliers.get(i).split("#");

						if(tempIn[1].equals(receiveArr[2])) {
							Server.suppliers.remove(i);
							break;
						}
					}
					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}
					// מחיקת מוצר בבסיס הנתונים, המערך בשרת ועדכון המשתמשים האחרים בשינויים
				} else if (receiveArr[0].equals("Delete") && receiveArr[1].equals("Product")) {
					query = "DELETE FROM product WHERE barcode = ?";
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, Integer.parseInt(receiveArr[2]));
					preparedStmt.executeUpdate();

					for(int i=0; i< Server.products.size(); i++){
						String[] tempIn = (String[]) Server.products.get(i).split("#");
						if(tempIn[1].equals(receiveArr[2])){
							Server.products.remove(i);
							break;
						}
					}

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}
					// מחיקת התראה בבסיס הנתונים, המערך בשרת ועדכון המשתמשים האחרים בשינויים
				} else if (receiveArr[0].equals("Delete") && receiveArr[1].equals("Notification")) {
					query = "DELETE FROM notification WHERE productBarcode = ?";
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, Integer.parseInt(receiveArr[3]));
					preparedStmt.executeUpdate();

					for(int i=0; i<Server.notifications.size(); i++){
						String[] tempIn = (String[]) Server.notifications.get(i).split("#");
						if(tempIn[1].equals(receiveArr[2]) && tempIn[2].equals(receiveArr[3])){
							Server.notifications.remove(i);
							break;
						}
					}

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}
					// העברת התראה לסוף המערך ועדכון המשתמשים בזה
				} else if (receiveArr[0].equals("Later") && receiveArr[1].equals("Notification")) {
					for (int i = 0; i < Server.notifications.size(); i++) {
						String[] tempIn = (String[]) Server.notifications.get(i).split("#");
						if (tempIn[1].equals(receiveArr[2]) && tempIn[2].equals(receiveArr[3])) {
							Server.notifications.add(Server.notifications.get(i));
							Server.notifications.remove(i);
							break;
						}
					}
					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
					}
				}
				//  עדכון הכמות המוצר על המדף לגבול שלו ומחיקת התראה
				else if (receiveArr[0].equals("InMyWay") && receiveArr[1].equals("Notification")) {
					String proTemp = "", orderPlaced=null;
					for (int i = 0; i < Server.products.size(); i++) {
						String[] tempIn = (String[]) Server.products.get(i).split("#");
						if (tempIn[1].equals(receiveArr[3])) {
							if ((Integer.parseInt(tempIn[6])) >= (Integer.parseInt(tempIn[8]) - Integer.parseInt(tempIn[5]))) {
								tempIn[6] = "" + (Integer.parseInt(tempIn[6]) - (Integer.parseInt(tempIn[8]) - Integer.parseInt(tempIn[5])));
								tempIn[5] = tempIn[8];
							} else {
								tempIn[5] = (Integer.parseInt(tempIn[6]) + Integer.parseInt(tempIn[5]))+"";
								tempIn[6] = "0";
							}
							// אם המוצר במחסן חצה גבול המנימום שלו יוצר התראה ומעדכן הגורמים הרלונטיים
							if (Integer.parseInt(tempIn[6]) < Integer.parseInt(tempIn[9])) {
								orderPlaced = "OrderPlaced#Notification#Storage#" + tempIn[1];

								for(int j=0; j<Server.notifications.size(); j++){
									String[] tempNotification = (String[]) Server.notifications.get(j).split("#");
									if(tempNotification[2].equals(receiveArr[3])){
										tempNotification[1] = "Storage";
										Server.notifications.set(j, String.join("#", tempNotification));

										// עדכון סוג התראה בבסיס הנתונים
										query = "UPDATE notification SET type = ? WHERE productBarcode = ? AND type = ?";
										preparedStmt = conn.prepareStatement(query);
										preparedStmt.setString(1, "Storage");
										preparedStmt.setInt(2, Integer.parseInt(receiveArr[3]));
										preparedStmt.setString(3, "Shelf");
										preparedStmt.executeUpdate();
										break;
									}
								}

							}
							// מחיקת התראה מבסיס הנתונים
							else {
								query = ("DELETE FROM notification WHERE productBarcode = ? AND type = ?");
								preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, Integer.parseInt(receiveArr[3]));
								preparedStmt.setString(2, receiveArr[2]);
								preparedStmt.executeUpdate();

								for(int j=0; j<Server.notifications.size(); j++){
									String[] tempNotification = (String[]) Server.notifications.get(j).split("#");
									if(tempNotification[2].equals(receiveArr[3]) && tempNotification[1].equals( receiveArr[2])){
										Server.notifications.remove(j);
										break;
									}
								}
							}

							proTemp = String.join("#", tempIn);
							Server.products.set(i, proTemp);
							// עדכון הכמות של המוצר בבסיס הנתונים על המדף ובמחסן לכמות החדשה
							query = ("UPDATE product SET quantityShelf = ?, quantityStorage = ? where barcode = ?");
							preparedStmt = conn.prepareStatement(query);
							preparedStmt.setInt(1, Integer.parseInt(tempIn[5]));
							preparedStmt.setInt(2, Integer.parseInt(tempIn[6]));
							preparedStmt.setInt(3, Integer.parseInt(tempIn[1]));
							preparedStmt.executeUpdate();


							break;

						}
					}

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
						c.out.println("Update#"+proTemp);
						c.out.flush();
						if(orderPlaced!=null){
							c.out.println(orderPlaced);
							c.out.flush();
						}

					}

					// עדכון הסוג של ההתארה בבסיס הנתונים והמערך ועדכון המשתמשים בזה
				} else if (receiveArr[0].equals("OrderPlaced") && receiveArr[1].equals("Notification")) {
					String updateOrderTemp = "";
					for (int i = 0; i < Server.notifications.size(); i++) {
						String[] tempIn = (String[]) Server.notifications.get(i).split("#");
						if (tempIn[2].equals(receiveArr[3]) && tempIn[1].equals(receiveArr[2])) {
							tempIn[1]= "OrderAccepted";
							updateOrderTemp = String.join("#",tempIn);
							Server.notifications.set(i, updateOrderTemp);

							query = ("UPDATE notification SET type = ? WHERE productBarcode = ? AND type = ?");
							preparedStmt = conn.prepareStatement(query);
							preparedStmt.setString(1, "OrderAccepted");
							preparedStmt.setInt(2, Integer.parseInt(receiveArr[3]));
							preparedStmt.setString(3, receiveArr[2]);
							preparedStmt.executeUpdate();

							break;
						}
					}
					for (ClientHandler c : Server.clients) {
						c.out.println("OrderPlaced#"+updateOrderTemp);
						c.out.flush();
					}

					// עדכון כמות המלאי למוצר מחיקת התראה ועדכון משתמשים
				} else if (receiveArr[0].equals("OrderAccepted") && receiveArr[1].equals("Notification")){
					String acceptedTemp = "", newStorageNotification = null;
					for(int i=0; i<Server.products.size(); i++) {
						String[] tempIn = (String[]) Server.products.get(i).split("#");
						if (tempIn[1].equals(receiveArr[3])) {
							tempIn[6] = receiveArr[4];

							query = ("UPDATE product SET name = ? , price = ? , shelfID = ?, quantityShelf = ?, quantityStorage = ?, minLimitShelf = ?, maxLimitShelf = ?,minLimitStorage = ?,maxLimitStorage = ?, supID = ? where barcode = ?");
							preparedStmt = conn.prepareStatement(query);
							preparedStmt.setString(1, tempIn[2]);
							preparedStmt.setDouble(2, Double.parseDouble(tempIn[3]));
							preparedStmt.setString(3, tempIn[4]);
							preparedStmt.setInt(4, Integer.parseInt(tempIn[5]));
							preparedStmt.setInt(5, Integer.parseInt(tempIn[6]));
							preparedStmt.setInt(6, Integer.parseInt(tempIn[7]));
							preparedStmt.setInt(7, Integer.parseInt(tempIn[8]));
							preparedStmt.setInt(8, Integer.parseInt(tempIn[9]));
							preparedStmt.setInt(9, Integer.parseInt(tempIn[10]));
							preparedStmt.setInt(10, Integer.parseInt(tempIn[11]));
							preparedStmt.setInt(11, Integer.parseInt(tempIn[1]));
							preparedStmt.executeUpdate();

							acceptedTemp = String.join("#", tempIn);
							Server.products.set(i, acceptedTemp);


							if(Integer.parseInt(tempIn[6]) < Integer.parseInt(tempIn[9])){
								query = "insert into notification values(?,?,?)";
								preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, 0);
								preparedStmt.setString(2, "Storage");
								preparedStmt.setInt(3, Integer.parseInt(receiveArr[3]));
								preparedStmt.execute();
								newStorageNotification = "Notification#Storage#"+receiveArr[3];
								Server.notifications.add(newStorageNotification);
							}
							break;
						}
					}

					for (int i = 0; i < Server.notifications.size(); i++) {
						String[] tempIn = (String[]) Server.notifications.get(i).split("#");
						if (tempIn[2].equals(receiveArr[3]) && tempIn[1].equals(receiveArr[2])) {
							Server.notifications.remove(i);

							query = ("DELETE FROM notification WHERE productBarcode = ? AND type = ?");
							preparedStmt = conn.prepareStatement(query);
							preparedStmt.setInt(1, Integer.parseInt(receiveArr[3]));
							preparedStmt.setString(2, receiveArr[2]);
							preparedStmt.executeUpdate();

							break;

						}
					}

					for (ClientHandler c : Server.clients) {
						c.out.println(buffer);
						c.out.flush();
						c.out.println("Update#"+acceptedTemp);
						c.out.flush();
						if(newStorageNotification != null){
							c.out.println(newStorageNotification);
							c.out.flush();
						}

					}

				}

			}
		} catch (SocketException e) {

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}