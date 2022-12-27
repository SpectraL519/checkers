package com.PWr.app.Model.Boards;

import com.PWr.app.Model.States.GameState;





public class RussianBoard extends Board implements Cloneable {
    public RussianBoard () {
        this.size = 8;
        this.pawnLines = 3;
        this.fields = new int[this.size][this.size];

        this.whitePawns = 0;
        this.blackPawns = 0;

        this.state = GameState.RESTING.getStateBahaviour();
    }



    @Override
    protected Board clone () {
        Board boardClone = new RussianBoard();

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
    public void mockEndgame (String player) {
        this.fields = new int[this.size][this.size];

        this.fields[4][3] = 1;
        this.fields[3][4] = 2;

        this.whitePawns = 1;
        this.blackPawns = 1;

        switch (player) {
            case "White": {
                this.state = GameState.WHITE.getStateBahaviour();
                break;
            }

            case "Black": {
                this.state = GameState.BLACK.getStateBahaviour();
                break;
            }

            default: {
                System.out.println("Invalid player");
                break;
            }
        }
    }



    @Override
    public void mockQueenEndgame () {
        this.fields = new int[this.size][this.size];

        this.fields[4][5] = 10;
        this.fields[5][4] = 2;
        this.fields[2][3] = 2;
        this.fields[3][6] = 2;
        this.fields[1][6] = 2;

        this.whitePawns = 1;
        this.blackPawns = 4;

        this.state = GameState.WHITE.getStateBahaviour();
    }



    @Override
    public void mockPawnToQueen () {
        this.fields = new int[this.size][this.size];

        this.fields[1][6] = 1;
        this.fields[1][4] = 2;

        this.whitePawns = 1;
        this.blackPawns = 1;

        this.state = GameState.WHITE.getStateBahaviour();
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

        if (queen) {
            int[] queenStep = this.checkQueenStep(rCurr, cCurr, rMov, cMov);
            if (queenStep == null) {
                return -6;
            }

            if (this.checkQueenTake(rCurr, cCurr, queenStep[0], queenStep[1])) {
                // Check for further movement possibilities
                try {
                    Board bc = this.clone();
                    bc.queenTake(rCurr, cCurr, rMov, cMov);
                    if (bc.longestQueenTake(rMov, cMov) > 0) {
                        return 3;
                    }

                    return 2;
                }
                catch (CloneNotSupportedException e) {
                    System.out.println("Clone error!");
                    return -8;
                }
            }

            // Check if there is any take possible on the board
            int lt = this.longestTake();
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
            // Check for further movement possibilities
            try {
                Board bc = this.clone();
                bc.pawnTake(rCurr, cCurr, rMov, cMov);
                if (bc.longestPawnTake(rMov, cMov) > 0) {
                    return 3;
                }

                return 2;
            }
            catch (CloneNotSupportedException e) {
                System.out.println("Clone error!");
                return -8;
            }
        }

        // Check if there is any take possible on the board
        int lt = this.longestTake();
        if (lt > 0) {
            return -7; // Not taking an enemy pawn when it's possible
        }
        if (lt < 0) {
            return -8; // Clone error
        }

        return 1;
    }
}
