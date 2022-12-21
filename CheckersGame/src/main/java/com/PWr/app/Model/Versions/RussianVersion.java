package com.PWr.app.Model.Versions;

import com.PWr.app.Model.Boards.*;





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
