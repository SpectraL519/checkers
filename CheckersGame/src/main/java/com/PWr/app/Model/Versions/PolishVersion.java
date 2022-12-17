package com.PWr.app.Model.Versions;

import com.PWr.app.Model.Boards.*;





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
