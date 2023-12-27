package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel implements ActionListener, KeyListener {
	protected JButton login;
	public static JTextField emailTF;
	public static JPasswordField passwordTF;
	protected JLabel invalidUser;
	// הגדרת הפנל עם כל האילימנטים שבתוכו
	public LoginPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		setBounds(0, 0, 713, 437);

		login = new JButton("Login");
		login.setForeground(Color.white);
		login.setBackground(new Color(241, 57, 83));
		login.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		login.setBounds(392, 276, 283, 36);
		add(login);

		emailTF = new JTextField();
		emailTF.setBounds(392, 114, 283, 36);
		emailTF.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(emailTF);

		JLabel username = new JLabel("USERNAME");
		username.setBounds(392, 82, 114, 14);
		username.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		add(username);

		JLabel password = new JLabel("PASSWORD");
		password.setBounds(392, 171, 96, 14);
		password.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		add(password);

		passwordTF = new JPasswordField();
		passwordTF.setBounds(392, 196, 283, 36);
		passwordTF.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(passwordTF);

		invalidUser = new JLabel("check username/password");
		invalidUser.setBounds(392, 331, 283, 43);

		invalidUser.setHorizontalAlignment(SwingConstants.CENTER);
		invalidUser.setForeground(Color.RED);
		invalidUser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		invalidUser.setVisible(false);
		add(invalidUser);

		JPanel loginPanel2 = new JPanel(null);
		loginPanel2.setBounds(0, 0, 367, 437);
		add(loginPanel2);

		JLabel image = new JLabel();
		image.setBounds(0, 0, 367, 437);
		image.setIcon(new ImageIcon(LoginPanel.class.getResource("../images/login.jpg")));
		loginPanel2.add(image);

		login.addActionListener(this);
		emailTF.addKeyListener(this);
		passwordTF.addKeyListener(this);
		emailTF.requestFocus();
	}
	// פונקציה שבודקת האם פרטי ההתחברות נכונים ומנווטת המשתמש לפי הרשאתו
	public void login() {
		try {
			for (int i = 0; i < Service.users.size(); i++) {
				if (emailTF.getText().equals(Service.users.get(i).getEmail())
						&& passwordTF.getText().equals(Service.users.get(i).getPassword())) {
					setVisible(false);
					if (Service.users.get(i).getPermission().equals("Manager")) {
						Service.userPermission="Manager";
					} else if (Service.users.get(i).getPermission().equals("Stockkeeper")) {
						Service.userPermission="Stockkeeper";
					} else {
						Service.userPermission="Cashier";
						Service.cashierName=Service.users.get(i).getName();
					}
					UserPanel user = new UserPanel();
					Service.frame.add(user);
					
					Service.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					return;
				}

			}
			invalidUser.setVisible(true);
			passwordTF.setText("");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// בעת לחיצה על כפתור מופעלת הפונקציה ()login
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == login) {
			login();
		}
	}

	// אחרי הזנת הנתונים בלחיצה במקלדת על Enter מפעילים פונקציה ()login
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			if (emailTF.getText().length() != 0) {
				// העברת פוקוס הקלט לשדה של הסיסמה
				passwordTF.requestFocus();
			}
			if (passwordTF.getText().length() != 0) {
				login();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
