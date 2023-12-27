import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;

public class StartServer implements ActionListener {
	private JButton startButton, stopButton;
	protected static JLabel connectedCnt;

	/*
	בנאי שמגדיר ממשק של צד שרת.
	מוגדר בו כל הכפתורים והאלמנטים שבממשק
	 */
	public StartServer() {
		// החזרת ערך של גודל המסך בפיקסלים
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		// הגדרת המסגרת של ממשק השרת
		JFrame frame = new JFrame();
		frame.setBounds(screen.width / 2 - 200, screen.height / 2 - 150, 350, 130);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		// הגדרת הפנל שמכיל את כל הכפתורים והאלמנטים
		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, 350, 130);
		// כפתור להפעלת שרת
		startButton = new JButton("Start");
		startButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		startButton.setBackground(new Color(76, 175, 80));
		startButton.setForeground(Color.white);
		startButton.setBounds(10,10,150,50);
		panel.add(startButton);
		// כפתור לכיבוי שרת
		stopButton = new JButton("Stop");
		stopButton.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		stopButton.setForeground(Color.white);
		stopButton.setBackground(new Color(244, 67, 54));
		stopButton.setBounds(170,10,150,50);
		stopButton.setEnabled(false);
		panel.add(stopButton);
		// מציג את מספר המשתמשים שמחוברים לשרת כרגע
		connectedCnt = new JLabel("Users Connected: 0");
		connectedCnt.setBounds(10, 60, 350, 30);
		panel.add(connectedCnt);
		frame.add(panel);
		frame.setVisible(true);
		// הגדרת מאזינים לכפתורים
		startButton.addActionListener(this);
		stopButton.addActionListener(this);
	}

	//הפעלת הבנאי
	public static void main(String[] args) {
		new StartServer();
	}

	// פונקציה פועלת בעת לחיצה על כפתור בממשק
	@Override
	public void actionPerformed(ActionEvent e) {
		// הפעלת השרת
		if (e.getSource() == startButton) {
			new Server(7000);
			startButton.setEnabled(false);
			stopButton.setEnabled(true);

		}
		// כיבוי השרת
		else if(e.getSource() == stopButton) {
			try {
				if(Server.client != null)
					Server.client.close();
				if(Server.server != null)
					Server.server.close();
				if(ReceiveSendMessage.conn != null)
					ReceiveSendMessage.conn.close();
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				connectedCnt.setText("Users Connected: 0");
			} catch (IOException | SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

}
