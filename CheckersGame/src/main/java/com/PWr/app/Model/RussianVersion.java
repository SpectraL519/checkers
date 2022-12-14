package com.PWr.app.Model;





public class RussianVersion extends GameVersion {
    public RussianVersion () {
        this.boardSize = 8;
        this.pawnLines = 3;
        this.board = new int[this.boardSize][this.boardSize];
    }



    // Czy curr i mov sa na planszy
    // Czy na curr jest pion
    // Czy na mov nie ma piona
    // Czy mov jest na ukos
    // std::pion - Jesli skok = 2 => Czy na mov - 1 jest pion przeciwnika
    // std::pion - Czy rusza sie do tyluy bez bicia
    @Override
    public int checkMove (int rCurr, int cCurr, int rMov, int cMov) {
        System.out.println("Checking the given move...");
        System.out.printf("Curr (r: %d, c: %d): %d\n", rCurr, cCurr, this.board[rCurr][cCurr]);
        System.out.printf("Mov (r: %d, c: %d): %d\n", rMov, cMov, this.board[rMov][cMov]);

        if (!this.isOnBoard(rCurr, cCurr)) {
            System.out.println("Curr not on board");
            return -1;
        }
        System.out.println("Curr on board");

        if (!this.isOnBoard(rMov, cMov)) {
            System.out.println("Mov not on board");
            return -1;
        }
        System.out.println("Mov on board");

        // No pawn on curr or pawn on mov 
        if (this.board[rCurr][cCurr] == 0 || this.board[rMov][cMov] > 0) {
            System.out.println("No pawn on curr or pawn on mov");
            return -1;
        }
        System.out.println("Pawn on curr and no pawn on mov");

        boolean queen = this.isQueen(rCurr, cCurr);

        int[] step = this.correctStep(rCurr, cCurr, rMov, cMov, queen);
        if (step[0] * step[1] == 0) {
            System.out.println("Incorrect step");
            return -1;
        }
        System.out.println("Correct step");

        if (queen) {
            // if (longestMove(rMov, cMov) > 0) {}
        }

        if (!this.checkPawnTake(rCurr, rCurr, step[0], step[1])) {
            System.out.println("Incorrect take");
            return -1;
        }
        System.out.println("Correct take");

        if (longestPawnMove(rMov, cMov) > 0) {
            System.out.println("OK: more moves possible");
            return 2;
        }

        System.out.println("OK: no more moves possible");
        return 1;
    }
}
