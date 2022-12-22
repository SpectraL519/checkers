package com.PWr.app;

import com.PWr.app.Model.GameModel;





public class ConsoleApp {
    public static void main(String args[]) {
        GameModel model = new GameModel();
        CommandLine cmd = new CommandLine(model);

        while (true) {
            try {
                cmd.getCommand();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

// java -cp target/CheckersGame-1.0-SNAPSHOT.jar com.PWr.app.ConsoleApp
