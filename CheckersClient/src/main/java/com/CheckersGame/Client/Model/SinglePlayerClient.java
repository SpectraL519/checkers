package com.CheckersGame.Client.Model;

import com.CheckersGame.Client.GameController;





public class SinglePlayerClient extends GameClient {
    public SinglePlayerClient (GameController controller) {
        super(controller);
        this.port = 4444;
    }
}
