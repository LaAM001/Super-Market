package gui;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import market.Notification;
import market.Product;
import market.Receipt;
import market.ReceiptInfo;
import market.Supplier;
import market.User;

public class Service {
	protected static JFrame frame;
	protected static LoginPanel login;
	protected static ManagerAddPanel managerAddPanel;
	protected static ProductsPanel productsPanel;
	protected static ReceiptPanel receiptPanel;
	protected static OrderPanel orderPanel;
	protected static NotificationPanel notification;
	protected static ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
	protected static ArrayList<User> users = new ArrayList<User>();
	protected static ArrayList<Product> products = new ArrayList<Product>();
	protected static ArrayList<Notification> notifications = new ArrayList<Notification>();
	protected static ArrayList<Receipt> receipts = new ArrayList<Receipt>();
	protected static ArrayList<ReceiptInfo> receiptsinfo = new ArrayList<ReceiptInfo>();
	
	protected static String userPermission, cashierName;
	protected static Dimension screen;
	protected static JMenuBar menuBar;
	protected static JMenuItem managerAddIcon, receiptIcon, productsIcon, supplierIcon, notificationIcon, suppliersIcon,
			orderIcon;
	protected static JLabel userIcon, background;
	protected static JButton logoutButton;
	protected static boolean viewSFlag = false;
	protected static ViewSuppliers vs = null;

	// הוספת שורה חדשה בטבלה והכנסת הנתונים שהתקבלו
	public static String[][] insertRow(String[][] arr, String[] newData) {
		String[][] out = new String[arr.length + 1][];
		if(arr.length == 0)
			out[0] = newData;
		else{
			for (int i=0; i<arr.length; i++)
				out[i] = arr[i];
			out[out.length-1] = newData;
		}
		return out;
	}
	// עיצוב לטבלה שהתקבלה
	public static void tableDesign(JTable table) {
		table.setFocusable(false);
		table.setShowVerticalLines(false);
		table.setSelectionBackground(new Color(232, 57, 95));
		table.setRowMargin(0);
		table.setRowHeight(40);
		table.setShowHorizontalLines(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setBackground(new Color(255, 255, 255));
		table.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC, 16));
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(new Color(32,136,203));
		table.getTableHeader().setForeground(new Color(255,255,255));
	}

	//  יצירת רקע לממשק שהתקבל
	public static void backGround(JPanel p) {
		if(background != null)
			p.remove(background);
		background = new JLabel(new ImageIcon(Service.class.getResource("/images/wallpaper.jpeg")));
		background.setBounds(0, 0, 1920, 950);
		p.add(background);
	}
	// רענון הממשק במידע החדש שהתקבל
	public static void refresh() {
		if(managerAddPanel != null) managerAddPanel.getTabels();
		if(productsPanel != null) productsPanel.productsTable();
		if(receiptPanel != null) receiptPanel.receiptsTable();
		if(notification != null) notification.genNotify();
	}
// עיצוב הפנל העליון והצגת האילימטים לפי הרשאות המשתמש שהתחבר
	public static void viewGUI(JPanel p) throws ClassNotFoundException {

		p.setLayout(null);

		if (userPermission.equals("Manager"))
			userIcon = new JLabel("Manager");

		else if (userPermission.equals("Stockkeeper"))
			userIcon = new JLabel("Stockkeeper");

		else
			userIcon = new JLabel("Cashier");

		userIcon.setForeground(new Color(153, 50, 204));
		userIcon.setFont(new Font("Algerian", Font.PLAIN, 27));
		userIcon.setBounds(10, 27, 200, 37);
		p.add(userIcon);

		logoutButton = new JButton("Logout");
		logoutButton.setForeground(Color.white);
		logoutButton.setBackground(new Color(244, 67, 54));
		logoutButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		logoutButton.setOpaque(true);
		logoutButton.setBounds(1800, 15, 100, 40);
		p.add(logoutButton);

		menuBar = new JMenuBar();
		menuBar.setLayout(new FlowLayout());
		menuBar.setBounds(0, 0, 1920, 85);
		menuBar.setBackground(Color.white);
		menuBar.setOpaque(true);
		p.add(menuBar);

		productsIcon = new JMenuItem();
		productsIcon.setIcon(new ImageIcon(UserPanel.class.getResource("/images/products.png")));
		menuBar.add(productsIcon);

		productsPanel = new ProductsPanel();

		if (userPermission.equals("Manager")) {

			receiptIcon = new JMenuItem();
			receiptIcon.setIcon(new ImageIcon(UserPanel.class.getResource("/images/receiptT.jpg")));
			menuBar.add(receiptIcon);

			suppliersIcon = new JMenuItem();
			suppliersIcon.setIcon(new ImageIcon(UserPanel.class.getResource("/images/supplier.png")));
			menuBar.add(suppliersIcon);

			managerAddIcon = new JMenuItem();
			managerAddIcon.setIcon(new ImageIcon(UserPanel.class.getResource("/images/employee.png")));
			menuBar.add(managerAddIcon);

			managerAddPanel = new ManagerAddPanel();
			receiptPanel = new ReceiptPanel();

			p.add(managerAddPanel);
		}

		else if (userPermission.equals("Stockkeeper")) {

			notificationIcon = new JMenuItem();
			notificationIcon.setIcon(new ImageIcon(UserPanel.class.getResource("/images/notification.png")));
			menuBar.add(notificationIcon);

			suppliersIcon = new JMenuItem();
			suppliersIcon.setIcon(new ImageIcon(UserPanel.class.getResource("/images/supplier.png")));
			menuBar.add(suppliersIcon);

			notification = new NotificationPanel();
			p.add(notification);
		}

		else if (userPermission.equals("Cashier")) {

			receiptIcon = new JMenuItem();
			receiptIcon.setIcon(new ImageIcon(UserPanel.class.getResource("/images/receiptT.jpg")));
			menuBar.add(receiptIcon);

			orderIcon = new JMenuItem();
			orderIcon.setIcon(new ImageIcon(UserPanel.class.getResource("/images/shekel.jpeg")));
			menuBar.add(orderIcon);

			orderPanel = new OrderPanel(cashierName);
			receiptPanel = new ReceiptPanel();

			p.add(orderPanel);
		}

		p.add(menuBar);

		// בלחיצה על כפתור היציאה חוזרים לדף ההתחברות
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.setVisible(false);
				screen = Toolkit.getDefaultToolkit().getScreenSize();
				frame.setBounds(screen.width / 2 - 300, screen.height / 2 - 250, 729, 476);
				login.setVisible(true);
				login.emailTF.setText("");
				login.passwordTF.setText("");
				if (vs != null) {
					vs.setVisible(false);
					p.remove(vs);
				}
			}

		});
		if (userPermission.equals("Manager"))
			managerAddIcon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					receiptPanel.setVisible(false);
					productsPanel.setVisible(false);
					if (!p.isAncestorOf(managerAddPanel))
						p.add(managerAddPanel);
					managerAddPanel.setVisible(true);
					productsIcon.addActionListener(this);
					receiptIcon.addActionListener(this);
				}
			});

		// כפתור עמוד הקבלות
		if (userPermission.equals("Cashier") || userPermission.equals("Manager"))
			receiptIcon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (userPermission.equals("Manager"))
						managerAddPanel.setVisible(false);
					if (userPermission.equals("Cashier"))
						orderPanel.setVisible(false);
					if(productsPanel != null)
						productsPanel.setVisible(false);
					if (!p.isAncestorOf(receiptPanel))
						p.add(receiptPanel);
					receiptPanel.setVisible(true);
				}

			});
		// כפתור עמוד הספקים
		if (userPermission.equals("Stockkeeper") || userPermission.equals("Manager"))
			suppliersIcon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (viewSFlag == false) {
						try {
							vs = new ViewSuppliers();
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
						viewSFlag = true;
					}
					vs.show();
				}

			});
		// כפתור עמוד ממשק הקופה
		if (userPermission.equals("Cashier"))
			orderIcon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					receiptPanel.setVisible(false);
					productsPanel.setVisible(false);
					if (!p.isAncestorOf(receiptPanel))
						p.add(orderPanel);
					orderPanel.setVisible(true);
				}

			});

		// לחיצה על כפתור המוצרים וצפייה בממשק
		productsIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userPermission.equals("Manager"))
					managerAddPanel.setVisible(false);
				if (userPermission.equals("Cashier") || userPermission.equals("Manager"))
					receiptPanel.setVisible(false);
				if (userPermission.equals("Stockkeeper"))
					notification.setVisible(false);
				if (userPermission.equals("Cashier"))
					orderPanel.setVisible(false);
				if (!p.isAncestorOf(productsPanel))
					p.add(productsPanel);
				productsPanel.setVisible(true);
			}

		});

		// כפתור עמוד ממשק ההתראת
		if (userPermission.equals("Stockkeeper"))
			notificationIcon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					productsPanel.setVisible(false);
					if (!p.isAncestorOf(notification))
						p.add(notification);
					notification.setVisible(true);
				}

			});

	}

}
