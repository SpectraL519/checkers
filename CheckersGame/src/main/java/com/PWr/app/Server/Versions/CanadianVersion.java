package com.PWr.app.Server.Versions;

import com.PWr.app.Server.Boards.*;





public class CanadianVersion extends GameVersion {
    public CanadianVersion () {
        this.board = new CanadianBoard();
    }



    @Override
    public void reset () {
        this.board = new CanadianBoard();
        this.board.init();
    }
}