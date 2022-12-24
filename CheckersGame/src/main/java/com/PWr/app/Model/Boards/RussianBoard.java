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



    // throws CloneNotSupportedException 
    @Override
    protected Object clone () {
        Board boardClone = new RussianBoard();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                boardClone.fields[i][j] = this.fields[i][j];
            }
        }

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
    public void mockQueenTake () {
        this.fields = new int[this.size][this.size];

        this.fields[6][1] = 10;
        this.fields[4][3] = 2;
        this.fields[1][2] = 2;

        this.blackPawns = 2;
        this.whitePawns = 1;

        this.state = GameState.WHITE.getStateBahaviour();
    }



    // Czy curr i mov sa na planszy
    // Czy na curr jest pion
    // Czy na mov nie ma piona
    // Czy mov jest na ukos
    // std::pion - Jesli skok = 2 => Czy na mov - 1 jest pion przeciwnika
    // std::pion - Czy rusza sie do tyluy bez bicia
    // Czy odpowiedni kolor piona sie rusza
    /* (non-Javadoc)
     * @see com.PWr.app.Model.Boards.Board#checkMove(int, int, int, int)
     */
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
            System.out.println("Moving a queen...");
            int[] step = this.checkQueenStep(rCurr, cCurr, rMov, cMov);
            if (step == null) {
                return -6;
            }

            if (this.checkQueenTake(rCurr, cCurr, step[0], step[1], this)) {
                // Take pawn
                System.out.println("Take!");

                if (this.state.getState() == GameState.WHITE) {
                    int rDir = (int)Math.signum(rMov - rCurr);
                    int cDir = (int)Math.signum(cMov - cCurr);
                    int rTmp = rCurr + rDir;
                    int cTmp = cCurr + cDir;

                    while (rTmp != rMov && cTmp != cMov) {
                        if (this.isBlack(rTmp, cTmp)) {
                            this.fields[rTmp][cTmp] = 0;
                            this.blackPawns--;
                        }

                        rTmp += rDir;
                        cTmp += cDir;
                    }

                    // White wins
                    if (this.blackPawns == 0) {
                        return 10;
                    }
                }
                else if (this.state.getState() == GameState.BLACK) {
                    int rDir = (int)Math.signum(rMov - rCurr);
                    int cDir = (int)Math.signum(cMov - cCurr);
                    int rTmp = rCurr + rDir;
                    int cTmp = cCurr + cDir;

                    while (rTmp != rMov && cTmp != cMov) {
                        if (this.isWhite(rTmp, cTmp)) {
                            this.fields[rTmp][cTmp] = 0;
                            this.whitePawns--;
                        }

                        rTmp += rDir;
                        cTmp += cDir;
                    }

                    // Black wins
                    if (this.whitePawns == 0) {
                        return 20;
                    }
                }

                // Check for further movement possibilities
                try {
                    int lqm = this.longestQueenMove(rMov, cMov, this);
                    System.out.println("LQM(mov): " + lqm);
                    if (lqm > 0) {
                        return 2;
                    }
                    return 1;
                }
                catch (CloneNotSupportedException e) {
                    System.out.println("Clone error!");
                    return -8;
                }
            }

            // Check if there is any take possible on the board
            try {
                for (int i = 0; i < this.size; i++) {
                    for (int j = 0; j < this.size; j++) {
                        if (this.checkPlayer(i, j) && this.longestPawnMove(i, j, this) > 0) {
                            return -7; // Not taking an enemy pawn when it's possible
                        }
                    }
                }
            }
            catch (CloneNotSupportedException e) {
                System.out.println("Clone error!");
                return -8;
            }

            return 1;
        }


        int[] step = this.checkPawnStep(rCurr, cCurr, rMov, cMov);
        if (step == null) {
            return -6;
        }

        if (Math.abs(step[0]) == 2) {
            System.out.println("Take!");
            // Take pawn
            if (this.state.getState() == GameState.WHITE) {
                this.blackPawns--;
                this.fields[rCurr + (step[0] / 2)][cCurr + (step[1] / 2)] = 0;

                // White wins
                if (this.blackPawns == 0) {
                    return 10;
                }
            }
            else if (this.state.getState() == GameState.BLACK) {
                this.whitePawns--;
                this.fields[rCurr + (step[0] / 2)][cCurr + (step[1] / 2)] = 0;

                // Black wins
                if (this.whitePawns == 0) {
                    return 20;
                }
            }

            // Check for further movement possibilities
            try {
                if (this.longestPawnMove(rMov, cMov, this) > 0) {
                    return 2;
                }
                return 1;
            }
            catch (CloneNotSupportedException e) {
                System.out.println("Clone error!");
                return -8;
            }
        }

        // Check if there is any take possible on the board
        try {
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    if (this.checkPlayer(i, j) && this.longestPawnMove(i, j, this) > 0) {
                        return -7; // Not taking an enemy pawn when it's possible
                    }
                }
            }
        }
        catch (CloneNotSupportedException e) {
            System.out.println("Clone error!");
            return -8;
        }

        return 1;
    }
}
