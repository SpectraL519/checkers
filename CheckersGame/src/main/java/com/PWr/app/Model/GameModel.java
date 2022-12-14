package com.PWr.app.Model;

import com.PWr.app.Model.Boards.Board;





public class GameModel {
    private GameVersion version;

    public GameModel () {}



    public void setVersion (String v) {
        switch (v) {
            case "Polish": {
                System.out.println("Polish version has been selected");
                this.version = new PolishVersion();
                break;
            }

            case "Russian": {
                System.out.println("Russian version has been selected");
                this.version = new RussianVersion();
                break;
            }

            case "Canadian": {
                System.out.println("Canadian version has been selected");
                this.version = new CanadianVersion();
                break;
            }

            default: {
                System.out.println("Invalid game version specified!");
                break;
            }
        }
    }



    public GameVersion getVersion () {
        return this.version;
    }



    public void initBoard () {
        this.version.initBoard();
    }



    public void displayBoard () {
        this.version.displayBoard();
    }



    public Board getBoard () {
        return this.version.getBoard();
    }



    public int movePawn (int rCurr, int cCurr, int rMov, int cMov) {
        return this.version.movePawn(rCurr, cCurr, rMov, cMov);
    }
}
