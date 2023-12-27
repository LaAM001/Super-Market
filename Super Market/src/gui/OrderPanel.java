package gui;

import market.Receipt;
import market.ReceiptInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellRenderer;

public class OrderPanel extends JPanel implements ActionListener, MouseListener {

	private JLabel totalPriceL, barcodeL, quantityL, payL, orderPriceL, sheklSymbol;
	private JTextField payTF, barcodeTF;
	private JSpinner spinner;
	private JTable productsTable;
	private JButton AddB, purchaseB;
	private DefaultTableModel dtmProduct;
	private JScrollPane sp, scrollPane;
	private String[][] dataArr = new String[0][5];
	private double livePrice = 0;
	private int quantityCnt = 0, lastRNum = 1;
	private String cashierName;
	private JTextPane receiptPane;
	private Dimension screen;

	// הגדרת הפנל עם כל האילימנטים שלו
	public OrderPanel(String cashierName) {
		this.cashierName = cashierName;
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLayout(null);
		setBounds(0, 85, 1920, 950);

		barcodeL = new JLabel("Enter Barcode:");
		barcodeL.setFont(new Font("Times New Roman", Font.ITALIC, 24));
		barcodeL.setBounds(80, 60, 169, 48);
		barcodeL.setForeground(Color.white);
		add(barcodeL);

		barcodeTF = new JTextField();
		barcodeTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		barcodeTF.setBounds(244, 62, 217, 45);
		add(barcodeTF);
		barcodeTF.setColumns(10);

		quantityL = new JLabel("Enter Quantity:");
		quantityL.setFont(new Font("Times New Roman", Font.ITALIC, 24));
		quantityL.setBounds(497, 60, 169, 48);
		quantityL.setForeground(Color.white);
		add(quantityL);

		spinner = new JSpinner();
		spinner.setFont(new Font("Times New Roman", Font.ITALIC, 24));
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setBounds(664, 66, 65, 42);
		add(spinner);

		AddB = new JButton("+Add");
		AddB.setFont(new Font("Times New Roman", Font.ITALIC, 24));
		AddB.setBounds(778, 65, 95, 42);
		AddB.setBackground(Color.decode("#008297"));
		AddB.setForeground(Color.white);
		add(AddB);

		String[] columnNames = { "Barcode", "Name", "Quantity", "Price Per Unit", "Total Price" };

		dtmProduct = new DefaultTableModel(dataArr, columnNames);

		productsTable = new JTable() {
			// לא מאפשר עריכה של הטבלה
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
				return false;
			}
			// מזיז כל הטקסט לאמצע
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);
				return comp;
			}
		};
		productsTable.setModel(dtmProduct);
		// עיצוב הטבלה
		Service.tableDesign(productsTable);
		productsTable.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC, 24));
		productsTable.addMouseListener(this);

		scrollPane = new JScrollPane(productsTable);
		scrollPane.setBounds(60, 120, 1220, 711);
		scrollPane.setViewportView(productsTable);
		scrollPane.getViewport().setBackground(new Color(255, 255, 255));
		add(scrollPane);

		payL = new JLabel("Pay");
		payL.setHorizontalAlignment(SwingConstants.CENTER);
		payL.setFont(new Font("Times New Roman", Font.ITALIC, 30));
		payL.setForeground(Color.white);
		payL.setBounds(1475, 650, 77, 69);
		add(payL);

		payTF = new JTextField();
		payTF.setFont(new Font("Times New Roman", Font.ITALIC, 30));
		payTF.setBounds(1555, 665, 135, 40);
		add(payTF);

		sheklSymbol = new JLabel("\u20AA");
		sheklSymbol.setHorizontalAlignment(SwingConstants.CENTER);
		sheklSymbol.setFont(new Font("Times New Roman", Font.ITALIC, 30));
		sheklSymbol.setBounds(1695, 650, 36, 69);
		sheklSymbol.setForeground(Color.white);
		add(sheklSymbol);

		orderPriceL = new JLabel("Order Price");
		orderPriceL.setHorizontalAlignment(SwingConstants.CENTER);
		orderPriceL.setFont(new Font("Times New Roman", Font.ITALIC, 30));
		orderPriceL.setForeground(Color.white);
		orderPriceL.setBounds(1450, 710, 205, 53);
		add(orderPriceL);

		totalPriceL = new JLabel(livePrice + " \u20AA");
		totalPriceL.setHorizontalAlignment(SwingConstants.LEFT);
		totalPriceL.setFont(new Font("Times New Roman", Font.ITALIC, 30));
		totalPriceL.setForeground(Color.white);
		totalPriceL.setBounds(1650, 710, 300, 53);
		add(totalPriceL);

		purchaseB = new JButton("Purchase");
		purchaseB.setBounds(1525, 773, 169, 42);
		purchaseB.setBackground(Color.decode("#008297"));
		purchaseB.setForeground(Color.white);
		add(purchaseB);
		purchaseB.setFont(new Font("Times New Roman", Font.ITALIC, 24));

		// הוספת מאזין לכפתורים
		AddB.addActionListener(this);
		purchaseB.addActionListener(this);

		// הוספת רקע לפנל
		Service.backGround(this);

	}
	// פונקציה מופעלת בעת לחיצה על אחד מהכפתורים
	@Override
	public void actionPerformed(ActionEvent e) {
		// הוספת מוצר לטבלה
		if (e.getSource() == AddB) {
			if(sp != null && sp.isVisible())
				sp.setVisible(false);
			
			int i;
			boolean correctBarcode = false, correctQuantity = false, foundInTable = false;
			// בדיקה האם הברקוד והכמות תקינים
			for (i = 0; i < Service.products.size(); i++) {
				if (barcodeTF.getText().equals(Service.products.get(i).getBarcode() + "")) {
					correctBarcode = true;
					// בדיקה האם יש מוצרים ברשימה
					if(productsTable.getRowCount() >= 1) {
						for (int row = 0; row < productsTable.getRowCount(); row++) {
							if (barcodeTF.getText().equals((String) productsTable.getValueAt(row, 0))) {
								foundInTable = true;
								// בדיקה האם הכמות שהזין הקופאי תקינה
								if ((int) spinner.getValue() <= Service.products.get(i).getQuantityShelf() - Integer.parseInt((String) productsTable.getValueAt(row, 2))) {
									correctQuantity = true;
									break;
								}
							}
						}
						// בדיקה האם הכמות שהזין הקופאי תקינה
						if(foundInTable == false && (int) spinner.getValue() <= Service.products.get(i).getQuantityShelf())
							correctQuantity = true;
					//	הזנת המוצר הראשון
					} else if ((int) spinner.getValue() <= Service.products.get(i).getQuantityShelf())
						correctQuantity = true;

					break;
				}
			}
			// אם הברקוד והכמות תקינים
			if (correctBarcode && correctQuantity) {
				if (productsTable.getRowCount() >= 1) {
					// בדיקה האם המוצר כבר קיים ברשימת הקניות
					for (int row = 0; row < productsTable.getRowCount(); row++) {
						// אם כן, מעדכנים את הכמות
						if (productsTable.getValueAt(row, 0).equals(barcodeTF.getText())) {
							quantityCnt = Integer.parseInt((String) productsTable.getValueAt(row, 2));
							dtmProduct.removeRow(row);
							break;
						}
					}
				}
				// מוסיף את המוצר לרשימת הקניות
				dtmProduct.addRow(new String[] { barcodeTF.getText(), Service.products.get(i).getName(),
						((int) spinner.getValue() + quantityCnt) + "", Service.products.get(i).getPrice() + "",
						String.format("%.2f",Service.products.get(i).getPrice() * ((int) spinner.getValue() + quantityCnt))});
				quantityCnt = 0;
				// עדכון של המחיר הכולל
				livePrice += Service.products.get(i).getPrice() * (int) spinner.getValue();
				totalPriceL.setText(String.format("%.2f",livePrice) + " \u20AA");
				
				spinner.setValue(1);
				
			}
			// הזנת ברקוד לא תקין
			else if(!correctBarcode) JOptionPane.showMessageDialog(null, "Invalid Barcode", "Invalid", JOptionPane.INFORMATION_MESSAGE);
			// הזנת כמות לא תקינה
			else JOptionPane.showMessageDialog(null, "Wrong Quantity", "Invalid", JOptionPane.INFORMATION_MESSAGE);

		}
		// סיום הזמנה
		else if (e.getSource() == purchaseB) {
			// בדיקה האם הקשימה ריקה
			if (productsTable.getRowCount() == 0)
				JOptionPane.showMessageDialog(null, "No Products", "Invalid", JOptionPane.INFORMATION_MESSAGE);
			// בדיקה האם הקופאי הזין סכום תקין
			else if (payTF.getText().length() == 0 || !payTF.getText().matches("^[0-9]*$"))
				JOptionPane.showMessageDialog(null, "Enter Price", "Invalid", JOptionPane.INFORMATION_MESSAGE);
			// אם הכל תקין
			else if (Double.parseDouble(payTF.getText()) >= Double.parseDouble(totalPriceL.getText().substring(0, totalPriceL.getText().length() - 2))) {

				if (receiptPane != null) {
					receiptPane.setVisible(false);
					sp.setVisible(false);
				}
				receiptPane = new JTextPane();
				receiptPane.setEditable(false);
				receiptPane.setFocusable(false);
				// שינוי סוג הכתיבה ל HTML
				receiptPane.setContentType("text/html");
				// פונקציה שלוקחת התאריך הנוכחי במחשב
				LocalDate date = LocalDate.now();

				//מציאת מספר הקבלה
				if (Service.receipts.size() != 0)
					lastRNum = Service.receipts.get(Service.receipts.size() - 1).getReceiptNum() + 1;

				Receipt receipt = new Receipt(lastRNum, Double.parseDouble(totalPriceL.getText().substring(0, totalPriceL.getText().length() - 2)), Double.parseDouble(payTF.getText()), cashierName, date + "");

				// הצגת כל פרטי הקבלה
				String html = new String();
				html = "<html><head>" + "</head><body>";
				html += "<center style='font-size: 18px'>SuperMarket</center>";
				html += "<center style='font-size: 15px'>Snunit St 51, Karmiel, 2161002<br />";

				html += "Receipt: " + lastRNum + "<br />";
				html += "Date: " + date + "<br />";

				html += "Cashier: " + cashierName + "<br />";

				html += "****************************************** </center><br />";
				html += "<table style='width: 100%;'>";
				for (int row = 0; row < productsTable.getRowCount(); row++) {
					html += "<tr>" + "<td style='text-align: left; font-size: 15px'>" + productsTable.getValueAt(row, 1)
							+ "</td>" + "<td width='80px' style='text-align: right; font-size: 13px'>"
							+ productsTable.getValueAt(row, 3) + "\u20AA</td>"
							+ "<td width='80px' style='text-align: right; font-size: 13px'>x"
							+ productsTable.getValueAt(row, 2) + "</td>"
							+ "<td width='80px' style='text-align: right; font-size: 13px'>"
							+ productsTable.getValueAt(row, 4) + "\u20AA </td>" + "</tr>";

					ReceiptInfo info = new ReceiptInfo(lastRNum, Integer.parseInt((String) productsTable.getValueAt(row, 0)), Integer.parseInt((String) productsTable.getValueAt(row, 2)));

					// הוספת קשר בין המוצר והקבלה
					for(int i=0; i<Service.products.size(); i++) {
						if(info.getProductBarcode() == Service.products.get(i).getBarcode()) {
							info.setProduct(Service.products.get(i));
							break;
						}
					}
					// הוספת קשר בין הקבלה לתוכן שלה
					receipt.addReceiptInfo(info);

				}

				html += "</table><br />";
				html += "<center style='font-size: 15px'>****************************************** </center><br />";
				html += "<table align='left'><tr><td style='text-align: left; font-size: 13px'>Total Price: "
						+ String.format("%.2f",Double.parseDouble(totalPriceL.getText().substring(0, totalPriceL.getText().length() - 2))) + "\u20AA</td></tr>";
				html += "<tr><td style='text-align: left; font-size: 13px'>Pay: "
						+ payTF.getText()+"\u20AA</td></tr>";
				html += "<tr><td style='text-align: left; font-size: 13px'>Charge: "
						+ String.format("%.2f", (Double.parseDouble(payTF.getText()) - livePrice))+"\u20AA</td></tr></table>";
				html += "<center style='font-size: 20px'>Thank You!!</center><br />";
				html += "</body></html>";
				receiptPane.setText(html);
				receiptPane.setVisible(true);
				sp = new JScrollPane(receiptPane);
				sp.setVisible(true);
				sp.setBounds(screen.width - 550, 150, 430, 500);
				add(sp);
				// רענון
				SwingUtilities.updateComponentTreeUI(sp);
				// שליחת רשימת הקניות והקבלה לפונקציה להמשך טיפול
				SendToServer.updatePay(productsTable, receipt);
				// חלון שמציג כמה צריך להחזיר ללקוח
				JOptionPane.showMessageDialog(null, "Change: " + String.format("%.2f", (Double.parseDouble(payTF.getText()) - livePrice)), "", JOptionPane.INFORMATION_MESSAGE);
				// לרוקן את הטבלה
				while(dtmProduct.getRowCount() != 0) 
					dtmProduct.removeRow(0);

				// החזרת ערכים למצבם בְּרִירַת מֶחדָל
				barcodeTF.setText("");
				livePrice = 0;
				totalPriceL.setText(livePrice + " \u20AA");
				payTF.setText("");
				spinner.setValue(1);
				
			} else {
				// אם התשלום פחות מסכום ההזמנה
				JOptionPane.showMessageDialog(null, "Incorrect Pay", "Invalid", JOptionPane.INFORMATION_MESSAGE);
			}

		}

	}


	// פונקציה למחיקה ועדכון המוצר בטבלה
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) { // to detect double click events
			// שומר מספר השורה
			int row = productsTable.getSelectedRow();
			barcodeTF.setText((String)dtmProduct.getValueAt(row, 0));
			spinner.setValue(Integer.parseInt((String)dtmProduct.getValueAt(row, 2)));
			livePrice -= Double.parseDouble((String)dtmProduct.getValueAt(row, 4));
			totalPriceL.setText(String.format("%.2f",livePrice) + " \u20AA");
			dtmProduct.removeRow(row);
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
