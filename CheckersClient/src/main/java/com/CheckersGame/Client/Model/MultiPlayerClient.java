package com.CheckersGame.Client.Model;

import com.CheckersGame.Client.GameController;





public class MultiPlayerClient extends GameClient {
    public MultiPlayerClient (GameController controller) {
        super(controller);
        this.port = 8888;
    }
}
