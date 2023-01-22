package com.CheckersGame.Client;





public class MultiPlayerClient extends GameClient{
    public MultiPlayerClient (GameController controller) {
        super(controller);
        this.port = 8888;
    }
}
