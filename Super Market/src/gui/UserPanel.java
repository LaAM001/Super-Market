package gui;

import javax.swing.JPanel;

public class UserPanel extends JPanel {

	// בנאי ראשי, כל הפנלים יופיעו בתוכו
	public UserPanel() throws ClassNotFoundException {
		Service.viewGUI(this);
	}

}
