package com.PWr.app.Model.Versions;

import com.PWr.app.Model.Boards.*;





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
