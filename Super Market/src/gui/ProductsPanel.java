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
import market.Product;


public class ProductsPanel extends JPanel implements MouseListener, DocumentListener {

	private JPanel panelTemp, productsPanel;
	private JTextPane productInfo;
	private JScrollPane spTable;
	private JLabel searchLabel, background;
	private JTextField searchBar;
	private ArrayList<JPanel> panelArray;
	private ArrayList<Product> products;
   // בנאי להגדרת ממשק עבור המוצרים וצפייה בפרטיהם
	public ProductsPanel() {
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

		productsTable();
	}

  // פונקציה להצגת המוצרים בצורה מסודרת
	public void productsTable() {
		// הסר תהטבלה שהיתה להגדרת אחת חדשה
		if (spTable != null) {
			remove(spTable);
			spTable.setVisible(false);
		}
		panelArray = new ArrayList<JPanel>();
		productsPanel = new JPanel(new AbsoluteLayout());
		productsPanel.setBackground(Color.white);
		int productX = 0, productY = 0, j = 0;
		products = new ArrayList<Product>();
		// מיון המוצרים במערך לפי -א,ב-
		Collections.sort(Service.products, new Comparator<Product>() {
			public int compare(Product p1, Product p2) {
				if (p1.getName().compareTo(p2.getName()) > 0)
					return 1;
				else
					return -1;
			}
		});
		// הכנסת והצגת המוצרים בטבלה בכרטסיות -פנלים- בצורה מסודרת
		for (int i = 0; i < Service.products.size(); i++) {
			String searchName = Service.products.get(i).getName().toLowerCase();
			String searchBarcode = Service.products.get(i).getBarcode()+"";
			if (searchName.startsWith(searchBar.getText().toLowerCase()) || searchBarcode.startsWith(searchBar.getText())) {
				panelTemp = new JPanel(new GridLayout(4, 1));
				panelTemp.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
				panelTemp.setBackground(Color.decode("#4596c5"));
				int barcode = Service.products.get(i).getBarcode();
				String panelName = "p" + barcode;
				panelTemp.setName(panelName);
				JLabel productBarcode = new JLabel("Barcode: " + barcode);
				productBarcode.setFont(new Font("Times New Roman", Font.ITALIC, 20));
				productBarcode.setHorizontalAlignment(SwingConstants.CENTER);
				productBarcode.setForeground(Color.white);
				JLabel productName = new JLabel("Name: " + Service.products.get(i).getName());
				productName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
				productName.setHorizontalAlignment(SwingConstants.CENTER);
				productName.setForeground(Color.white);
				JLabel shelfNum = new JLabel("Shelf: " + Service.products.get(i).getShelf());
				shelfNum.setFont(new Font("Times New Roman", Font.PLAIN, 20));
				shelfNum.setHorizontalAlignment(SwingConstants.CENTER);
				shelfNum.setForeground(Color.white);
				JLabel price = new JLabel("Price: " + Service.products.get(i).getPrice()+"\u20AA");
				price.setFont(new Font("Times New Roman", Font.PLAIN, 20));
				price.setHorizontalAlignment(SwingConstants.CENTER);
				price.setForeground(Color.white);
				panelTemp.add(productBarcode);
				panelTemp.add(productName);
				panelTemp.add(shelfNum);
				panelTemp.add(price);
				panelArray.add(panelTemp);
				products.add(Service.products.get(i));
				panelArray.get(j++).addMouseListener(this);
				productsPanel.add(panelTemp, new AbsoluteConstraints(productX, productY, 200, 200));
				productX += 210;
				if (j % 6 == 0) {
					productY += 210;
					productX = 0;
				}
			}
		}

		spTable = new JScrollPane(productsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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
		if (productInfo != null) {
			remove(productInfo);
		}
		productInfo = new JTextPane();
		productInfo.setBounds(1430, 150, 350, 620);
		productInfo.setEditable(false);
		productInfo.setFocusable(false);
		productInfo.setContentType("text/html");
		String html = new String();
		html = "<html><head> </head><body>";
		for (int i = 0; i < panelArray.size(); i++) {
			if (e.getSource() == panelArray.get(i)) {
				html += "<br /><center style='font-size: 20px; color:#008cba; '><b>" + products.get(i).getName()
						+ "</b></center><br />";
				html += "<center style='font-size: 16px'>Barcode => " + products.get(i).getBarcode()
						+ "</center><br />";
				html += "<center style='font-size: 16px'>Price => " + products.get(i).getPrice() +"\u20AA"
						+ "</center><br />";
				html += "<center style='font-size: 16px'>Shelf Number => " + products.get(i).getShelf()
						+ "</center><br />";
				html += "<center style='font-size: 16px'>Quantity On Shelf => "
						+ products.get(i).getQuantityShelf() + "</center><br />";
				html += "<center style='font-size: 16px'>Quantity In Storage => "
						+ products.get(i).getQuantityStorage() + "</center><br />";
				html += "<center style='font-size: 16px'>Min On Shelf => " + products.get(i).getMinLimitShelf()
						+ "</center><br />";
				html += "<center style='font-size: 16px'>Max On Shelf => " + products.get(i).getMaxLimitShelf()
						+ "</center><br />";
				html += "<center style='font-size: 16px'>Min In Storage => "
						+ products.get(i).getMinLimitStorage() + "</center><br />";
				html += "<center style='font-size: 16px'>Max In Storage => "
						+ products.get(i).getMaxLimitStorage() + "</center><br />";
				html += "<center style='font-size: 16px'>Supplier Name => "
						+ products.get(i).getSupplier().getName() + "</center><br />";

				html += "<center style='font-size: 16px;'>Supplier Phone Number => "
						+ products.get(i).getSupplier().getPhoneNum() + "</center><br />";

				break;
			}
		}
		html += "</body></html>";

		productInfo.setText(html);

		productInfo.setVisible(true);
		add(productInfo);
		SwingUtilities.updateComponentTreeUI(productInfo);
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
	// פונקציה מופעלת כשיש שינוי בשדה טקסט לצורך מיון תוצאות
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		productsTable();
	}
	// פונקציה מופעלת כשיש שינוי בשדה טקסט לצורך מיון תוצאות
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		productsTable();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}

}
