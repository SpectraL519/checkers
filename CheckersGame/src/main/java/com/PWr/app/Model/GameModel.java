package com.PWr.app.Model;





// public class GameModel extends Observable ???
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



    public void initPawns () {
        this.version.initPawns();
    }



    public void displayPawns () {
        this.version.displayPawns();
    }
}
