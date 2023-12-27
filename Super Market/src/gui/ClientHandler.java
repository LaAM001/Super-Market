package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {
    protected Socket client;
    protected PrintWriter out;
    protected BufferedReader in;
    // בנאי שמגדיר את הקלט והפלט לכל משתמש
    public ClientHandler(Socket client) {
        this.client = client;
        try {
            // הגדרת הפלט
            this.out = new PrintWriter(client.getOutputStream());
            // הגדרת הקלט
            this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}