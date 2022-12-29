package com.PWr.app.Model;

import com.PWr.app.Server.Boards.Board;
import com.PWr.app.Server.Versions.GameVersion;
import com.PWr.app.Server.States.GameState;

import java.io.*;
import java.net.*;
import java.util.Scanner;




public class GameClient {
    // TODO: Change project structure
    // Separate maven projects for the game server and for the game client

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private Scanner stdInScanner;



    public GameClient () {
        this.stdInScanner = new Scanner(System.in);
    }



    public void start () {
        this.listen();
        this.getInit();

        while (true) {
            try{
                System.out.print(this.input.readLine());
                this.output.println(this.stdInScanner.nextLine());
                System.out.println(this.input.readLine());
            }
            catch (IOException e) {
                System.err.println("IOError: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }



    private void listen () {
        try {
            this.socket = new Socket("localhost", 4444);
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream(), true);
        } 
        catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } 
        catch (IOException e) {
            System.out.println("I/O error");
            System.exit(1);
        }
    }



    private void getInit () {
        try {
            System.out.println(this.input.readLine());
            System.out.println(this.input.readLine());
        }
        catch (IOException e) {
            System.out.println("I/O error");
            System.exit(1);
        }
    }
}
