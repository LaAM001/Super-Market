import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Server extends Thread {
	protected static ServerSocket server;
	protected static Socket client;
	private ClientHandler clientHandler;
	protected static List<ClientHandler> clients;
	protected static ArrayList<String> suppliers;
	protected static ArrayList<String> users;
	protected static ArrayList<String> products;
	protected static ArrayList<String> notifications;
	protected static ArrayList<String> receipts;
	protected static ArrayList<String> receiptInfo;
	protected static ArrayList<String> saleStatistic;
	protected static ArrayList<Integer> clientsInd;
	protected static int cInd;
	/*
	בנאי שמאתחל את כל המערכים, מפעיל את השרת, שולף את המידע מבסיס הנתונים
	 */
	public Server(int port) {
		try {
			//הפעלת השרת על פורט שהתקבל כפרמטר
			server = new ServerSocket(port);
			// רשימה של כל המשתמשים שמחוברים לשרת
			clients = Collections.synchronizedList(new ArrayList<ClientHandler>());
			// מערך שמכיל את כל המידע שנשלף מבסיס הנתונים על הספקים
			suppliers = new ArrayList<String>();
			// מערך שמכיל את כל המידע שנשלף מבסיס הנתונים על משתמשים
			users = new ArrayList<String>();
			// מערך שמכיל את כל המידע שנשלף מבסיס הנתונים על המוצרים
			products = new ArrayList<String>();
			// מערך שמכיל את כל המידע שנשלף מבסיס הנתונים על ההתראות
			notifications = new ArrayList<String>();
			// מערך שמכיל את כל המידע שנשלף מבסיס הנתונים על הקבלות
			receipts = new ArrayList<String>();
			// מערך שמכיל את כל המידע שנשלף מבסיס הנתונים על מידע הקבלות
			receiptInfo = new ArrayList<String>();
			// מערך שמכיל את כל המידע שנשלף מבסיס הנתונים על סטטיסטיקות של הקניות
			saleStatistic = new ArrayList<String>();
			// כל משתמש שהתחבר לשרת מקבל מספר ייחודי משלו ושומר בתוך המערך
			clientsInd = new ArrayList<Integer>();
			cInd = 0;
			//פונקציה ששולפת כל המידע מבסיס הנתונים
			getFromDB.getValues();
			// הפעלת פונקציה run
			this.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// פונקציה שמכילה לולאה אינסופית להגדרת משתמשים חדשים שהתחברו לשרת
	public void run() {
		try {
			while (true) {
				// המתנה עד התחברות של משתמש חדש והגדרתו
				client = server.accept();
				// הגדרת קלט ופלט לכל משתמש במתחבר
				clientHandler = new ClientHandler(client);
				//הוספת המשתמש לרשימה
				clients.add(clientHandler);
				//הוספת מספר הייחודי של המשתמש שהתחבר למערך
				clientsInd.add(cInd);
				// פתיחת Thread חדש לקבלה ושליחה של נתונים לכל משתמש חדש
				new ReceiveSendMessage(clientHandler);
				// עדכון מספר המשתמשים שמחוברים כרגע
				StartServer.connectedCnt.setText("Users Connected: "+clients.size());
			}
			} catch (Exception e) {
			Thread.currentThread().stop();
			}

		
	}
}