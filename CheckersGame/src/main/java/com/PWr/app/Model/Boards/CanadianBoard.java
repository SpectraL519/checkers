package com.PWr.app.Model.Boards;





public class CanadianBoard extends Board {
    public CanadianBoard () {
        this.size = 12;
        this.pawnLines = 5;
        this.fields = new int[this.size][this.size];
    }



    @Override
    public int checkMove (int rCurr, int cCurr, int rMov, int cMov) {
        return -1;
    }
}