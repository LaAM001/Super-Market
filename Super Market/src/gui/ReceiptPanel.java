package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import market.Receipt;
import market.ReceiptInfo;

public class ReceiptPanel extends JPanel implements MouseListener, DocumentListener {

	private JPanel panelTemp;
	private JTextPane receiptInfo;
	private JScrollPane spTable, spReceipt;
	private JTextField searchBar;
	private JLabel searchLabel, background;
	private JPanel receiptsPanel;
	private ArrayList<JPanel> panelArray;
	private ArrayList<Receipt> receipts;

	// בנאי להגדרת ממשק עבור הקבלות וצפייה בפרטיהם
	public ReceiptPanel() throws ClassNotFoundException {
		setLayout(null);
		setBounds(0, 85, 1920, 950);
		
		searchLabel = new JLabel("Search");
		searchLabel.setBounds(40, 50, 260, 40);
		searchLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		searchLabel.setForeground(Color.white);
		add(searchLabel);
		
		searchBar = new JTextField();
		searchBar.setBounds(130, 55, 160, 30);
		add(searchBar);
		searchBar.getDocument().addDocumentListener(this);

		receiptsTable();

		}
	// פונקציה להצגת הקבלות בצורה מסודרת דרך פנל
	public void receiptsTable() {
		if (spTable != null) {
			remove(spTable);
			spTable.setVisible(false);
		}
		panelArray = new ArrayList<JPanel>();
		receiptsPanel = new JPanel(new AbsoluteLayout());
		receiptsPanel.setBackground(Color.white);
		// מיון הקבלות במערך לפי -א,ב-
		Collections.sort(Service.receipts, new Comparator<Receipt>() {
			public int compare(Receipt r1, Receipt r2) {
				return Integer.valueOf(r1.getReceiptNum()).compareTo(r2.getReceiptNum());
			}
		});
		int receiptX = 0, receiptY = 0, j = 0;
		receipts = new ArrayList<Receipt>();
		// הכנסת והצגת הקבלות בטבלה בכרטסיות -פנלים- בצורה מסודרת
		for (int i = 0; i < Service.receipts.size(); i++) {
			String searchTemp = Service.receipts.get(i).getReceiptNum() + "";
			if (searchTemp.startsWith(searchBar.getText())) {
				panelTemp = new JPanel(new GridLayout(4, 1));
				panelTemp.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
				panelTemp.setBackground(Color.decode("#4596c5"));
				int num = Service.receipts.get(i).getReceiptNum();
				String panelName = "r" + num;
				panelTemp.setName(panelName);
				JLabel receiptNum = new JLabel("Receipt Number: " + num);
				receiptNum.setFont(new Font("Times New Roman", Font.ITALIC, 20));
				receiptNum.setHorizontalAlignment(SwingConstants.CENTER);
				receiptNum.setForeground(Color.white);
				JLabel cashierName = new JLabel("Cashier: " + Service.receipts.get(i).getCashierName());
				cashierName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
				cashierName.setHorizontalAlignment(SwingConstants.CENTER);
				cashierName.setForeground(Color.white);
				JLabel date = new JLabel("Date: " + Service.receipts.get(i).getDate());
				date.setFont(new Font("Times New Roman", Font.ITALIC, 20));
				date.setHorizontalAlignment(SwingConstants.CENTER);
				date.setForeground(Color.white);
				JLabel price = new JLabel("Price: " + Service.receipts.get(i).getTotalPrice());
				price.setFont(new Font("Times New Roman", Font.ITALIC, 20));
				price.setHorizontalAlignment(SwingConstants.CENTER);
				price.setForeground(Color.white);
				panelTemp.add(receiptNum);
				panelTemp.add(cashierName);
				panelTemp.add(date);
				panelTemp.add(price);
				panelArray.add(panelTemp);
				receipts.add(Service.receipts.get(i));
				panelArray.get(j++).addMouseListener(this);
				receiptsPanel.add(panelTemp, new AbsoluteConstraints(receiptX, receiptY, 200, 200));
				
				receiptX += 210;
				if (j % 6 == 0) {
					receiptY += 210;
					receiptX = 0;
				}
			}
		}
		spTable = new JScrollPane(receiptsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spTable.setBorder(BorderFactory.createLineBorder(Color.decode("#2bb5bb"), 6));
		spTable.setBounds(20, 100, 1280, 825);
		spTable.setVisible(true);
		add(spTable);

		SwingUtilities.updateComponentTreeUI(spTable);

		backGround();
	}

	// פונקציה שמוסיפה רקע לפנל
	public void backGround() {
		if (background != null)
			remove(background);
		background = new JLabel(new ImageIcon(ManagerAddPanel.class.getResource("/images/wallpaper.jpeg")));
		background.setBounds(0, 0, 1920, 950);
		add(background);
	}

	@Override
	// בעת לחיצה על איזה פנל בטבלה יוצג מידע מפורט על הפריט שנלחץ עליו
	public void mouseClicked(MouseEvent e) {
		if (spReceipt != null) {
			remove(spReceipt);
		}
		receiptInfo = new JTextPane();
		receiptInfo.setEditable(false);
		receiptInfo.setFocusable(false);
		receiptInfo.setContentType("text/html");
		String html = new String();
		html = "<html><head> </head><body>";
		html += "<center style='font-size: 18px'>SuperMarket</center>";
		html += "<center style='font-size: 15px'>Snunit St 51, Karmiel, 2161002<br />";
		double totalPrice = 0, pay = 0, charge = 0;
		for (int i = 0; i < panelArray.size(); i++) {
			if (e.getSource() == panelArray.get(i)) {
				html += "Receipt: " + receipts.get(i).getReceiptNum() + "<br />";
				html += "Date: " + receipts.get(i).getDate() + "<br />";
				html += "Cashier: " + receipts.get(i).getCashierName() + "<br />";
				html += "****************************************** </center><br />";
				html += "<table style='width: 100%; border='1 px'>";
				ArrayList<ReceiptInfo> info = receipts.get(i).getReceiptInfo();
				for (int j = 0; j < info.size(); j++) {
					String receiptNum = "r" + info.get(j).getReceiptNum();
					if (receiptNum.equals(panelArray.get(i).getName())) {
						html += "<tr>" + "<td style='text-align: left; font-size: 15px'>" + info.get(j).getProduct().getName()
								+ "</td>" + "<td width='80px' style='text-align: right; font-size: 13px'>"
								+ info.get(j).getProduct().getPrice() + "\u20AA</td>"
								+ "<td width='80px' style='text-align: right; font-size: 13px'>x"
								+ info.get(j).getProductCount() + "</td>"
								+ "<td width='80px' style='text-align: right; font-size: 13px'>"
								+ info.get(j).getProduct().getPrice() * info.get(j).getProductCount() + "\u20AA </td>"
								+ "</tr>";

						totalPrice += info.get(j).getProduct().getPrice() * info.get(j).getProductCount();
						pay = receipts.get(i).getPay();
						charge = receipts.get(i).getCharge();
					}
				}
				break;
			}
		}

		html += "</table><br />";
		html += "<center style='font-size: 15px'>****************************************** </center><br />";
		html += "<table align='left'><tr><td style='text-align: left; font-size: 13px'>Total Price: " + totalPrice
				+ "\u20AA</td></tr>";
		html += "<tr><td style='text-align: left; font-size: 13px'>Pay: " + pay
				+ "\u20AA</td></tr>";
		html += "<tr><td style='text-align: left; font-size: 13px'>Charge: " + charge
				+ "\u20AA</td></tr></table>";
		html += "<center style='font-size: 20px'>Thank You!!</center><br />";
		html += "</body></html>";

		receiptInfo.setText(html);
		spReceipt = new JScrollPane(receiptInfo);
		spReceipt.setBounds(1350, 150, 470, 606);
		spReceipt.setVisible(true);
		add(spReceipt);

		SwingUtilities.updateComponentTreeUI(spReceipt);

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	// פונקציה מופעלת כשיש שינוי בשדה טקסט לצורך מיון תוצאות
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		receiptsTable();
	}
	// פונקציה מופעלת כשיש שינוי בשדה טקסט לצורך מיון תוצאות
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		receiptsTable();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
	}

}
