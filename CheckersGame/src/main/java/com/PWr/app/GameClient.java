package com.PWr.app;

import java.io.*;
import java.net.*;
import java.util.Scanner;




// MVC::Model class
public class GameClient {
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
                String line = this.input.readLine();
                System.out.print(line);
                
                if (line.startsWith("cmd:")) {
                    this.output.println(this.stdInScanner.nextLine()); // send command (args)
                    System.out.println(this.input.readLine()); // print message
                }
                else {
                    System.out.println();
                }
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
