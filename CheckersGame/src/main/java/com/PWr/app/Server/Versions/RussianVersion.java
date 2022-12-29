package com.PWr.app.Server.Versions;

import com.PWr.app.Server.Boards.*;





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
