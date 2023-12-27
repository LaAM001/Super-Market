package gui;

import java.awt.*;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;

public class ViewSuppliers extends JFrame implements DocumentListener {
	private JPanel panel;
	private JLabel searchLabel;
	private JTextField searchBar;
	private String data[][], col[] = {"Supplier ID"," Name"," Phone","Address"};
	private JScrollPane sp;
	private JTable table;
	// הגדרת הממשק עם כל האילימטים
	public ViewSuppliers() throws InterruptedException {
		// זיהוי גדלי המסך
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		super.setBounds(screen.width/2-350, screen.height/2-400, 700, 800);

		panel = new JPanel(null);
		panel.setBounds(0,0, super.getWidth(), super.getHeight());

		searchLabel = new JLabel("Search");
		searchLabel.setBounds(50, 25, 120, 25);
		searchLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(searchLabel);
		searchBar = new JTextField();
		searchBar.setBounds(120,20,170,35);
		searchBar.getDocument().addDocumentListener(this);
		panel.add(searchBar);

		// הצגת כל הספקים
		genSuppliers();

		super.add(panel);
		setResizable(false);
	}
	// עדכון הטבלה בכל הספקים הקיימים
	public void genSuppliers(){
		if(sp != null)
			panel.remove(sp);
		data = new String[0][4];
		for(int i=0; i<Service.suppliers.size(); i++) {
			String searchName = Service.suppliers.get(i).getName().toLowerCase();
			String searchID = Service.suppliers.get(i).getID()+"";
			if (searchName.startsWith(searchBar.getText().toLowerCase()) || searchID.startsWith(searchBar.getText())) {
				data = Service.insertRow(data, new String[]{Service.suppliers.get(i).getID() + "", Service.suppliers.get(i).getName(),
						Service.suppliers.get(i).getPhoneNum() + "", Service.suppliers.get(i).getAddress()});
			}

		}
		table = new JTable(data, col){
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
		Service.tableDesign(table);
		sp = new JScrollPane(table);
		sp.setBounds(0, 70,super.getWidth(), 725);
		panel.add(sp);
	}
	// פונקציה מופעלת כשיש שינוי בשדה טקסט לצורך מיון תוצאות
	@Override
	public void insertUpdate(DocumentEvent e) {
		genSuppliers();
	}
	// פונקציה מופעלת כשיש שינוי בשדה טקסט לצורך מיון תוצאות
	@Override
	public void removeUpdate(DocumentEvent e) {
		genSuppliers();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}
}
