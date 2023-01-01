package com.CheckersGame.Server.Versions;

import com.CheckersGame.Server.Boards.*;





public class PolishVersion extends GameVersion {
    public PolishVersion () {
        this.board = new PolishBoard();
    }



    @Override
    public void reset () {
        this.board = new PolishBoard();
        this.board.init();
    }
}
