package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class Main{
	// פונקציה שמפעילה את ממשק המשתמש ומתחברת לשרת
	public static void main(String[] args) {
		// הגדרת המסגרת של הממשק
		Service.frame = new JFrame();
		// אתחול דף ההתחברות
		Service.login = new LoginPanel();
		// החזרת ערך של גודל המסך בפיקסלים
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Service.frame.setBounds(screen.width / 2 - 300, screen.height / 2 - 250, 729, 476);
		Service.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Service.frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent ev) {
		    	  try {
		    	  	// בעת יציאה מהתוכנה מופעלת הפונקציה
					SendToServer.exit();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }
		    });
		Service.frame.add(Service.login);
		Service.login.setVisible(true);
		Service.frame.setVisible(true);
		// התחברות לשרת וקבלת מידע מהשרת
		new ReceiveFromServer("localhost", 7000);
	}
}