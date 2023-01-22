package com.CheckersGame.Client;





public class SinglePlayerClient extends GameClient{
    public SinglePlayerClient (GameController controller) {
        super(controller);
        this.port = 4444;
    }
}
