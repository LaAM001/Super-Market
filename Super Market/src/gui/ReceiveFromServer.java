package gui;

import market.*;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ReceiveFromServer extends Thread {
	protected static Socket socket;
	protected ClientHandler clientH;
	protected static int cInd = 0;

	// בנאי באמצעותו מתחברים לשרת
	public ReceiveFromServer(String ip, int port) {
		try {
			// התחברות לשרת
			this.socket = new Socket(ip, port);
			// הגדרת קלט\פלט למשתמש
			this.clientH = new ClientHandler(this.socket);
			// פתיחת Thread חדש לשליחת מידע לשרת
			new SendToServer(this.clientH);
			// הפעלת ה run
			this.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Check IP/Port", "Server", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// פונקציה מקבלת מידע מהשרת ומטפלת בו לפי סוגו
	public void run() {
		try {

			String buffer = null;
			Product product;
			Notification notification;
			ReceiptInfo receiptInfo;

			while (true) {
				//  ממתין עד קבלת מידע מהשרת
				buffer = this.clientH.in.readLine();
				System.out.println(buffer);
				// המרת המידע שהתקבל מהשרת למערך
				String[] receiveArr = (String[]) buffer.split("#");

				// מספר ייחודי לכל משתמש
				if (receiveArr[0].equals("clientIndex")) {
					cInd = Integer.parseInt(receiveArr[1]);
				}
				// הוסםת ספק חדש
				if (receiveArr[0].equals("Supplier")) {
					Service.suppliers.add(new Supplier(Integer.parseInt(receiveArr[1]), receiveArr[2], receiveArr[3], receiveArr[4]));

				}
				// הוספת מוצר חדש
				else if (receiveArr[0].equals("Product")) {
					product = new Product(Integer.parseInt(receiveArr[1]), receiveArr[2],
							Double.parseDouble(receiveArr[3]), receiveArr[4], Integer.parseInt(receiveArr[5]),
							Integer.parseInt(receiveArr[6]), Integer.parseInt(receiveArr[7]),
							Integer.parseInt(receiveArr[8]), Integer.parseInt(receiveArr[9]),
							Integer.parseInt(receiveArr[10]), Integer.parseInt(receiveArr[11]));

					int suppNum = Integer.parseInt(receiveArr[11]);
					// קשר בין המוצר לספק
					for (int i = 0; i < Service.suppliers.size(); i++)
						if (suppNum == Service.suppliers.get(i).getID()) {
							Service.suppliers.get(i).addProduct(product);
							break;
						}

					Service.products.add(product);

				}
				// יצירת התראה חדשה והגדרת הקשר עם המוצר
				else if (receiveArr[0].equals("Notification")) {
					boolean flag = false;
					for (int i = 0; i < Service.notifications.size(); i++) {
						if (Service.notifications.get(i).getType().equals(receiveArr[1]) && Service.notifications.get(i)
								.getProduct().getBarcode() == Integer.parseInt(receiveArr[2])) {
							flag = true;
							break;
						}
					}
					if (flag == false) {
						notification = new Notification(receiveArr[1]);
						System.out.println(receiveArr[1]);
						for (int i = 0; i < Service.products.size(); i++) {
							if (Integer.parseInt(receiveArr[2]) == Service.products.get(i).getBarcode()) {
								notification.setProduct(Service.products.get(i));
								break;
							}
						}

						Service.notifications.add(notification);
					}
				}

				// העברת ההתראה לסוף המערך
				else if (receiveArr[0].equals("Later") || receiveArr[0].equals("OrderPlaced")) {
					//שינוי סוג ההתראה ל OrderAccepted
					if (receiveArr[2].equals("OrderAccepted")) {
						for (int i = 0; i < Service.notifications.size(); i++) {
							if (Integer.parseInt(receiveArr[3]) == Service.notifications.get(i).getProduct()
									.getBarcode() && Service.notifications.get(i).getType().equals("Storage")) {
								Service.notifications.get(i).setType("OrderAccepted");
								break;
							}
						}
					}

					for (int i = 0; i < Service.notifications.size(); i++) {
						if (Service.notifications.get(i).getType().equals(receiveArr[2]) && Service.notifications.get(i).getProduct().getBarcode() == Integer.parseInt(receiveArr[3])) {
							Service.notifications.remove(i);
							break;
						}
					}

					notification = new Notification(receiveArr[2]);
					for (int i = 0; i < Service.products.size(); i++)
						if (Integer.parseInt(receiveArr[3]) == Service.products.get(i).getBarcode()) {
							notification.setProduct(Service.products.get(i));
							break;
						}

					Service.notifications.add(notification);

				}
				// מחיקת ההתראה
				else if ((receiveArr[0].equals("Delete") || receiveArr[0].equals("InMyWay")
						|| receiveArr[0].equals("OrderAccepted")) && receiveArr[1].equals("Notification")) {
					for (int i = 0; i < Service.notifications.size(); i++) {
						System.out.println(Service.notifications.get(i).getType() + " " + receiveArr[2] + " "
								+ Service.notifications.get(i).getProduct().getBarcode() + " " + receiveArr[3]);
						if (Service.notifications.get(i).getType().equals(receiveArr[2]) && Service.notifications.get(i).getProduct().getBarcode() == Integer.parseInt(receiveArr[3])) {
							Service.notifications.remove(i);
							break;
						}
					}

				}
				// הוספת משתמש חדש
				else if (receiveArr[0].equals("User")) {
					Service.users.add(new User(Integer.parseInt(receiveArr[1]), receiveArr[2], receiveArr[3],
							receiveArr[4], receiveArr[5], receiveArr[6]));
				}
				// הוספת קבלה חדשה
				else if (receiveArr[0].equals("Receipt")) {
					Service.receipts.add(new Receipt(Integer.parseInt(receiveArr[1]), Double.parseDouble(receiveArr[2]),
							Double.parseDouble(receiveArr[3]), receiveArr[4], receiveArr[5]));
				}
				// הוספת מידע קבלה חדש והגדרת הקשר עם הקבלה והמוצר
				else if (receiveArr[0].equals("ReceiptInfo")) {
					receiptInfo = new ReceiptInfo(Integer.parseInt(receiveArr[1]), Integer.parseInt(receiveArr[2]),
							Integer.parseInt(receiveArr[5]));

					for (int i = 0; i < Service.receipts.size(); i++) {
						if (Integer.parseInt(receiveArr[1]) == Service.receipts.get(i).getReceiptNum()) {
							receiptInfo.setReceipt(Service.receipts.get(i));
							break;
						}
					}
					
					for(int i=0; i<Service.products.size(); i++) {
						if(Service.products.get(i).getBarcode() == Integer.parseInt(receiveArr[2])) {
							receiptInfo.setProduct(Service.products.get(i));
							break;
						}
					}
					
					Service.receiptsinfo.add(receiptInfo);

				}
				// עדכון כמות המוצר על המדף
				else if (receiveArr[0].equals("UpdateQuantityShelf")) {
					for (int i = 0; i < Service.products.size(); i++) {
						if (Service.products.get(i).getBarcode() == Integer.parseInt(receiveArr[1])) {
							Service.products.get(i).setQuantityShelf(Integer.parseInt(receiveArr[2]));
							break;
						}
					}
				}
				// עדכון פרטי משתמש
				else if (receiveArr[0].equals("Update") && receiveArr[1].equals("User")) {
					for (int i = 0; i < Service.users.size(); i++) {
						if (Integer.parseInt(receiveArr[2]) == Service.users.get(i).getID()) {
							Service.users.get(i).setName(receiveArr[3]);
							Service.users.get(i).setEmail(receiveArr[4]);
							Service.users.get(i).setPhoneNum(receiveArr[5]);
							Service.users.get(i).setPassword(receiveArr[6]);
							Service.users.get(i).setPermission(receiveArr[7]);
							break;
						}
					}
				}
				// עדכון פרטי ספק
				else if (receiveArr[0].equals("Update") && receiveArr[1].equals("Supplier")) {
					for (int i = 0; i < Service.suppliers.size(); i++) {
						if (Integer.parseInt(receiveArr[2]) == Service.suppliers.get(i).getID()) {
							Service.suppliers.get(i).setName(receiveArr[3]);
							Service.suppliers.get(i).setPhoneNum(receiveArr[4]);
							Service.suppliers.get(i).setAddress(receiveArr[5]);
							break;
						}
					}
				}
				// עדכון פרטי מוצר
				else if (receiveArr[0].equals("Update") && receiveArr[1].equals("Product")) {
					for (int i = 0; i < Service.products.size(); i++) {
						if (Service.products.get(i).getBarcode() == Integer.parseInt(receiveArr[2])) {
							Service.products.get(i).setName(receiveArr[3]);
							Service.products.get(i).setPrice(Double.parseDouble(receiveArr[4]));
							Service.products.get(i).setShelf(receiveArr[5]);
							Service.products.get(i).setQuantityShelf(Integer.parseInt(receiveArr[6]));
							Service.products.get(i).setQuantityStorage(Integer.parseInt(receiveArr[7]));
							Service.products.get(i).setMinLimitShelf(Integer.parseInt(receiveArr[8]));
							Service.products.get(i).setMaxLimitShelf((Integer.parseInt(receiveArr[9])));
							Service.products.get(i).setMinLimitStorage(Integer.parseInt(receiveArr[10]));
							Service.products.get(i).setMaxLimitStorage(Integer.parseInt(receiveArr[11]));
							Service.products.get(i).setSupplierID(Integer.parseInt(receiveArr[12]));

							for(int j = 0; j< Service.suppliers.size(); j++){
								if(Service.products.get(i).getSupplierID() == Service.suppliers.get(j).getID()) {
									Service.products.get(i).setSupplier(Service.suppliers.get(j));
									break;
								}
							}

							break;
						}
					}
				}
				// מחיקת משתמש
				else if (receiveArr[0].equals("Delete")
						&& (receiveArr[1].equals("Stockkeeper") || receiveArr[1].equals("Cashier"))) {
					for (int i = 0; i < Service.users.size(); i++) {
						if (Service.users.get(i).getID() == Integer.parseInt(receiveArr[2])) {
							Service.users.remove(i);
							break;
						}
					}
				}
				// מחיקת ספק
				else if (receiveArr[0].equals("Delete") && receiveArr[1].equals("Supplier")) {
					for (int i = 0; i < Service.suppliers.size(); i++) {
						if (Service.suppliers.get(i).getID() == Integer.parseInt(receiveArr[2])) {
							Service.suppliers.remove(i);
							break;
						}
					}
				}
				// מחיקת מוצר
				else if (receiveArr[0].equals("Delete") && receiveArr[1].equals("Product")) {
					for (int i = 0; i < Service.products.size(); i++) {
						if (Service.products.get(i).getBarcode() == Integer.parseInt(receiveArr[2])) {
							for (int j = 0; j < Service.notifications.size();) {
								if (Service.notifications.get(j).getProduct().getBarcode() == Service.products.get(i)
										.getBarcode()) {
									System.out.println(Service.notifications.get(j).getProduct().getBarcode());
									System.out.println(Service.notifications);
									Service.notifications.remove(j);
								}

								else
									j++;
							}
							Service.products.remove(i);
							break;
						}
					}
				}
				// רענון כל טבלאות
				Service.refresh();
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Check IP", "Server", JOptionPane.INFORMATION_MESSAGE);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Server Closed", "Server", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Socket Closed", "Server", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}