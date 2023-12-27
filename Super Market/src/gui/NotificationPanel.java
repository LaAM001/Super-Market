package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class NotificationPanel extends JPanel implements ActionListener, MouseListener {
	private ArrayList<JPanel> aln = new ArrayList<JPanel>();
	private ArrayList<JButton> all = new ArrayList<JButton>();
	private ArrayList<JButton> ali = new ArrayList<JButton>();
	private JPanel notificationPanel, whitePanel;
	private JButton laterBtn, updateQntBtn;
	private JLabel notifyL, noNotifications;
	private JTextPane productInfo;
	private String displayNotification;

	// הגדרת הפנל עם האלימנטים הראשיים
	public NotificationPanel() {
		setLayout(null);
		setBounds(0, 85, 1920, 950);

		whitePanel = new JPanel();
		whitePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		whitePanel.setBounds(50, 100, 1200, 800);
		add(whitePanel);
		whitePanel.setLayout(null);
		whitePanel.setBackground(Color.white);

		// הצגת כל ההתראות
		genNotify();
	}
	// פונקציה מציגה כל ההתראות שיש בחנות עבור המוצרים
	public void genNotify() {
		// מחיקת כל ההתראות הקיימות בממשק
		for(int i=0; i<aln.size(); ){
			aln.get(i).setVisible(false);
			aln.remove(i);
		}

		if(productInfo != null)
			remove(productInfo);

		aln = new ArrayList<JPanel>();
		all = new ArrayList<JButton>();
		ali = new ArrayList<JButton>();

		// הצגת כל ההתראות
		int yLocation = 50;
		for (int i = 0; i < Service.notifications.size(); i++) {
			if(noNotifications != null && noNotifications.isVisible() == true) {
				System.out.println(1234555555);
				noNotifications.setVisible(false);
				whitePanel.remove(noNotifications);
				SwingUtilities.updateComponentTreeUI(noNotifications);
			}
			// פנל שמכיל כל המידע
			notificationPanel = new JPanel();
			notificationPanel.setBounds(10, yLocation, 1150, 50);
			notificationPanel.setLayout(null);
			aln.add(notificationPanel);
			yLocation += 100;

			if(!Service.notifications.get(i).getType().equals("OrderAccepted"))
				displayNotification = Service.notifications.get(i).getType() + " Notification ==> "+ Service.notifications.get(i).getProduct().getName();
			else displayNotification =  "Supplier Order Notification ==> "+ Service.notifications.get(i).getProduct().getName();

			notifyL = new JLabel(displayNotification, SwingConstants.CENTER);
			notifyL.setBorder(new LineBorder(Color.black));
			notifyL.setForeground(Color.black);
			notifyL.setFont(new Font("Times New Roman", Font.PLAIN, 22));
			notifyL.setBounds(80, 0, 600, 50);
			notifyL.setName("notifyL" + i);
			notifyL.addMouseListener(this);
			notificationPanel.add(notifyL);

			laterBtn = new JButton("Later");
			laterBtn.setFont(new Font("Times New Roman", Font.PLAIN, 17));
			laterBtn.setBounds(830, 0, 100, 50);
			laterBtn.setName("laterBtn" + i);
			laterBtn.setBackground(new Color(76, 175, 80));
			laterBtn.setForeground(Color.white);
			notificationPanel.add(laterBtn);
			all.add(laterBtn);
			all.get(i).addActionListener(this);

			// שינוי שם הכפתור לפי סוג ההתראה
			if (Service.notifications.get(i).getType().equals("Shelf")) {
				updateQntBtn = new JButton("In My Way");
			} else if (Service.notifications.get(i).getType().equals("Storage")) {
				updateQntBtn = new JButton("Order Placed");
			} else if(Service.notifications.get(i).getType().equals("OrderAccepted")) {
				updateQntBtn = new JButton("Order Accepted");
			}
			updateQntBtn.setFont(new Font("Times New Roman", Font.PLAIN, 17));
			updateQntBtn.setBounds(980, 0, 150, 50);
			updateQntBtn.setName("updateQntBtn" + i);
			updateQntBtn.setForeground(Color.white);
			updateQntBtn.setBackground(new Color(0, 140, 186));
			notificationPanel.add(updateQntBtn);
			ali.add(updateQntBtn);
			ali.get(i).addActionListener(this);

			notificationPanel.setBackground(Color.white);

			whitePanel.add(notificationPanel);
			// רענון
			SwingUtilities.updateComponentTreeUI(notificationPanel);


		}

		// אם אין התראות בחנות
		if(Service.notifications.size() == 0) {
			noNotifications = new JLabel("There is no notifications now, Thanks for your great work :)", SwingConstants.CENTER);
			noNotifications.setForeground(Color.gray);
			noNotifications.setFont(new Font("Times New Roman", Font.ITALIC, 30));
			noNotifications.setBounds(200, 350, 800, 50);
			whitePanel.add(noNotifications);
			noNotifications.setVisible(true);
			SwingUtilities.updateComponentTreeUI(noNotifications);
		}

		// הוספת רקע לפנל
		Service.backGround(this);

	}

	// פונקציה מופעלת בעת לחיצה על אחד מהכפתורים
	@Override
	public void actionPerformed(ActionEvent e) {
		if(productInfo!=null) {
			productInfo.setVisible(false);
			remove(productInfo);
		}
		// זיהוי איזה כפתור נלחץ
		JButton button = (JButton) e.getSource();
		// מבצע את הפעולות לפי הטקסט שרשום בתוך הכפתור
		switch (button.getText()) {
			case "Later":
				for (int i = 0; i < all.size(); i++) {
					if (button.getName().equals(all.get(i).getName())) {
						// שליחה לפונקציה את פרטי ההתראה להמשך טיפול
						SendToServer.notification("Later", Service.notifications.get(i).toString());
						break;
					}
				}
				break;
			case "In My Way":
				for (int i = 0; i < ali.size(); i++) {
					if (button.getName().equals(ali.get(i).getName())) {
						// שליחה לפונקציה את פרטי ההתראה להמשך טיפול
						SendToServer.notification("InMyWay", Service.notifications.get(i).toString());
						// מציג בהודעה כמה המחסנאי צריך למלות את המדף ממוצר מסויים
						if(Service.notifications.get(i).getProduct().getMaxLimitShelf() <= Service.notifications.get(i).getProduct().getQuantityStorage())
							JOptionPane.showMessageDialog(null, "Quantity to fill: "+ (Service.notifications.get(i).getProduct().getMaxLimitShelf() - Service.notifications.get(i).getProduct().getQuantityShelf()), "Invalid", JOptionPane.INFORMATION_MESSAGE);
						else JOptionPane.showMessageDialog(null, "Quantity to fill: "+ Service.notifications.get(i).getProduct().getQuantityStorage(), "Invalid", JOptionPane.INFORMATION_MESSAGE);
						break;
					}
				}
				break;
			case "Order Placed":
				for (int i = 0; i < ali.size(); i++) {
					if (button.getName().equals(ali.get(i).getName())) {
						// שליחה לפונקציה את פרטי ההתראה להמשך טיפול
						SendToServer.notification("OrderPlaced", Service.notifications.get(i).toString());
						break;
					}
				}
				break;
			case "Order Accepted":
				for (int i = 0; i < ali.size(); i++) {
					if (button.getName().equals(ali.get(i).getName())) {
						// שליחה לפונקציה את פרטי ההתראה להמשך טיפול
						String res = JOptionPane.showInputDialog("How many items received?");
						if(res != null) {
							int resNum = Integer.parseInt(res);
							resNum = Service.notifications.get(i).getProduct().getQuantityStorage() + resNum;
							SendToServer.notification("OrderAccepted", Service.notifications.get(i).toString() + "#" + resNum);
							break;
						}
					}
				}
				break;
		}
	}

	// פונקציה מופעלת בעת לחיצה על אחד מה panels
	@Override
	public void mouseClicked(MouseEvent e) {
		if (productInfo != null) {
			remove(productInfo);
		}
		productInfo = new JTextPane();
		productInfo.setBounds(1430, 150, 350, 620);
		productInfo.setEditable(false);
		productInfo.setFocusable(false);
		// שינוי סוג הכתיבה ל HTML
		productInfo.setContentType("text/html");
		String html = new String();
		html = "<html><head> </head><body>";

		// הצגת פרטי המוצר
		for (int i = 0; i < Service.products.size(); i++) {
			if (Service.products.get(i).getNotification() != null) {

				if(!Service.products.get(i).getNotification().getType().equals("OrderAccepted"))
					displayNotification = Service.products.get(i).getNotification().getType() + " Notification ==> "
							+ Service.products.get(i).getName();
				else displayNotification = "Supplier Order Notification ==> "+ Service.products.get(i).getName();
				// זיהוי על איזה label נלחץ והצגת כל הפרטים של המוצר
				if (((JLabel) e.getSource()).getText().equals(displayNotification)) {

					html += "<br /><center style='font-size: 20px; color:#008cba; '><b>"
							+ Service.products.get(i).getName() + "</b></center><br />";
					html += "<center style='font-size: 16px'>Barcode => "
							+ Service.products.get(i).getBarcode() + "</center><br />";
					html += "<center style='font-size: 16px'>Price => " + Service.products.get(i).getPrice()
							+ "</center><br />";
					html += "<center style='font-size: 16px'>Shelf Number => "
							+ Service.products.get(i).getShelf() + "</center><br />";
					html += "<center style='font-size: 16px'>Quantity On Shelf => "
							+ Service.products.get(i).getQuantityShelf() + "</center><br />";
					html += "<center style='font-size: 16px'>Quantity In Storage => "
							+ Service.products.get(i).getQuantityStorage() + "</center><br />";
					html += "<center style='font-size: 16px'>Min On Shelf => "
							+ Service.products.get(i).getMinLimitShelf() + "</center><br />";
					html += "<center style='font-size: 16px'>Max On Shelf => "
							+ Service.products.get(i).getMaxLimitShelf() + "</center><br />";
					html += "<center style='font-size: 16px'>Min In Storage => "
							+ Service.products.get(i).getMinLimitStorage() + "</center><br />";
					html += "<center style='font-size: 16px'>Max In Storage => "
							+ Service.products.get(i).getMaxLimitStorage() + "</center><br />";
					html += "<center style='font-size: 16px'>Supplier Name => "
							+ Service.products.get(i).getSupplier().getName() + "</center><br />";
					String[] receiveArr = (String[]) displayNotification.split(" ");
					if (receiveArr[0].equals("Shelf")) {
						if(Service.products.get(i).getMaxLimitShelf() <= Service.products.get(i).getQuantityStorage())
							html += "<center style='font-size: 16px ; color:red;'>Quantity To Fill => "
									+ (Service.products.get(i).getMaxLimitShelf() - Service.products.get(i).getQuantityShelf())
									+ "</center><br />";
						else html += "<center style='font-size: 16px ; color:red;'>Quantity To Fill => "
								+ Service.products.get(i).getQuantityStorage()
								+ "</center><br />";
					}

					else
						html += "<center style='font-size: 16px ; color:red;'>Supplier Phone Number => "
								+ Service.products.get(i).getSupplier().getPhoneNum() + "</center><br />";

					html += "</body></html>";

					productInfo.setText(html);

					productInfo.setVisible(true);
					add(productInfo);
					// רענון
					SwingUtilities.updateComponentTreeUI(productInfo);

					break;
				}
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
