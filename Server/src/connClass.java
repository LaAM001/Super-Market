

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connClass {
	
	public static Connection conn;
	// פונקציה שבאמצעותה מתחברים לבסיס הנתונים בעזרת קבצי JAR
	public static Connection getConn() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// הגדרת הניתוב של בסיס הנתונים הנדרש
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafinalproject?autoReconnect=true&useSSL=false", "root", "");
			return conn;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}
}