import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

public class ClientHandler {
    protected Socket socket;
    protected PrintWriter out;
    protected BufferedReader in;
    // בנאי שמגדיר את הקלט והפלט לכל משתמש
    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            // הגדרת הפלט
            this.out = new PrintWriter(this.socket.getOutputStream());
            // הגדרת הקלט
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}