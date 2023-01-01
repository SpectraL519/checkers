package com.CheckersGame.Server.Versions;

import com.CheckersGame.Server.Boards.*;





public class RussianVersion extends GameVersion {
    public RussianVersion () {
        this.board = new RussianBoard();
    }



    @Override
    public void reset () {
        this.board = new RussianBoard();
        this.board.init();
    }
}
