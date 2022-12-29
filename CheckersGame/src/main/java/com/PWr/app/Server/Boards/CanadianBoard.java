package com.PWr.app.Server.Boards;

import com.PWr.app.Server.States.GameState;





public class CanadianBoard extends Board implements Cloneable {
    public CanadianBoard () {
        this.size = 12;
        this.pawnLines = 5;
        this.fields = new int[this.size][this.size];

        this.whitePawns = 0;
        this.blackPawns = 0;

        this.rPrevTake = - 1;
        this.cPrevTake = - 1;

        this.state = GameState.RESTING.getStateBahaviour();
    }



    @Override
    protected Board clone () {
        Board boardClone = new CanadianBoard();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                boardClone.fields[i][j] = this.fields[i][j];
            }
        }

        boardClone.whitePawns = this.whitePawns;
        boardClone.blackPawns = this.blackPawns;

        boardClone.state = this.state;

        return boardClone;
    }



    @Override
    public int checkMove (int rCurr, int cCurr, int rMov, int cMov) {
        // Check if the game has started
        if (this.state.getState() == GameState.RESTING) {
            return -1;
        }
        // Check if Curr is on the board
        if (!this.isOnBoard(rCurr, cCurr)) {
            return -2;
        }
        
        // Check if Mov is on the board
        if (!this.isOnBoard(rMov, cMov)) {
            return -3;
        }

        // Check player
        if (!this.checkPlayer(rCurr, cCurr)) {
            return -4;
        }

        // Check if there is a pawn on Mov
        if (this.fields[rMov][cMov] > 0) {
            System.out.println("Pawn on mov");
            return -5;
        }


        boolean queen = this.isQueen(rCurr, cCurr);
        int lt = longestTake();

        if (queen) {
            int[] queenStep = this.checkQueenStep(rCurr, cCurr, rMov, cMov);
            if (queenStep == null) {
                return -6;
            }

            if (this.checkQueenTake(rCurr, cCurr, queenStep[0], queenStep[1])) {
                try {
                    // Check for the optimal take
                    if (this.longestQueenTake(rCurr, cCurr) < lt) {
                        return 0;
                    }

                    // Check for further movement possibilities
                    Board bc = this.clone();
                    bc.queenTake(rCurr, cCurr, rMov, cMov);
                    if (bc.longestQueenTake(rMov, cMov) > 0) {
                        return 3;
                    }

                    return 2;
                }
                catch (CloneNotSupportedException e) {
                    return -10;
                }
            }

            // Check if there is any take possible on the board
            if (lt > 0) {
                return -7; // Not taking an enemy pawn when it's possible
            }
            if (lt < 0) {
                return -8; // Clone error
            }
            

            return 1;
        }


        int[] pawnStep = this.checkPawnStep(rCurr, cCurr, rMov, cMov);
        if (pawnStep == null) {
            return -6;
        }

        if (this.checkPawnTake(rCurr, cCurr, pawnStep[0], pawnStep[1])) {
            try {
                // Check for the optimal take
                if (this.longestPawnTake(rCurr, cCurr) < lt) {
                    return 0;
                }
                
                // Check for further movement possibilities
                Board bc = this.clone();
                bc.pawnTake(rCurr, cCurr, rMov, cMov);
                if (bc.longestPawnTake(rMov, cMov) > 0) {
                    return 3;
                }

                return 2;
            }
            catch (CloneNotSupportedException e) {
                return -10;
            }
        }

        return 1;
    }
}
