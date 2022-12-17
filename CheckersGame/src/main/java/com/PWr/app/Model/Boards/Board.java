package com.PWr.app.Model.Boards;

import com.PWr.app.Model.States.*;





// This is a factory class
public abstract class Board {
    protected int size;
    protected int pawnLines;
    protected int[][] fields;
    protected GameStateBahaviour state;

    

    public void init () {
        // Pawns:
        // 0 - no pawn
        // 1 - white
        // 10 - white queen
        // 2 - black
        // 20 - black queen

        // Init white pawns
        int limit = this.size - this.pawnLines - 1;
        for (int r = this.size - 1; r > limit; r--) {
            int startPos = (r + 1) % 2;
            for (int c = startPos; c < this.size; c += 2) {
                this.fields[r][c] = 1;
            }
        }
        
        // Init black pawns
        for (int r = 0; r < this.pawnLines; r++) {
            int startPos = (r + 1) % 2;
            for (int c = startPos; c < this.size; c += 2) {
                this.fields[r][c] = 2;
            }
        }

        this.state = this.state.startGame();
    }



    public GameState getState () {
        return this.state.getState();
    }



    public void display () {
        for (int r = 0; r < this.size; r++) {
            System.out.printf("%d: ", r);
            for (int c = 0; c < this.size; c++) {
                System.out.printf("%d ", this.fields[r][c]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n  ");
        for (int c = 0; c < this.size; c++) {
            System.out.printf(" %d", c);
        }
        System.out.printf("\n");
    }




    public int movePawn (int rCurr, int cCurr, int rMov, int cMov) {
        // < 0: move NOT ok - genereal errrors
        // 0: move NOT ok - not optimal take
        // 1: move ok but no further move is allowed
        // 2: move ok and furhter moves allowed

        int check = this.checkMove(rCurr, cCurr, rMov, cMov);
        if (check > 0) {
            if (this.pawnToQueen(rCurr, cCurr, rMov, cMov)) {
                System.out.println("Pawn changed to queen");
                this.fields[rMov][cMov] = this.fields[rCurr][cCurr] * 10;
            }
            else {
                this.fields[rMov][cMov] = this.fields[rCurr][cCurr];
            }

            this.fields[rCurr][cCurr] = 0;
            System.out.printf("Pawn moved: (%d,%d) -> (%d,%d)\n", rCurr, cCurr, rMov, cMov);

            if (check == 1) {
                this.state = this.state.switchPlayer();
            }
        }

        return check;
    }



    protected abstract int checkMove (int rCurr, int cCurr, int rMov, int cMov);



    protected boolean checkPlayer (int rCurr, int cCurr) {
        if (this.fields[rCurr][cCurr] == 1) {
            if (this.state.getState() == GameState.WHITE) {
                return true;
            }

            return false;
        }

        if (this.fields[rCurr][cCurr] == 2) {
            if (this.state.getState() == GameState.BLACK) {
                return true;
            }

            return false;
        }

        return false;
    }



    protected boolean isOnBoard (int r, int c) {
        if (r < 0 || r >= this.size) {
            return false;
        }

        if (c < 0 || c >= this.size) {
            return false;
        }

        return true;
    }



    protected int[] checkPawnStep (int rCurr, int cCurr, int rMov, int cMov) {
        int rStep = rMov - rCurr;
        int cStep = cMov - cCurr;
        int[] step = {rStep, cStep};

        // Check for backward movement
        if (this.fields[rCurr][cCurr] == 1 && rStep > 0) {
            return null;
        } 
        if (this.fields[rCurr][cCurr] == 2 && rStep < 0) {
            return null;
        } 

        rStep = Math.abs(rStep);
        cStep = Math.abs(cStep);

        // Check for stepping on a white field
        if (rStep != cStep) {
            return null;
        }

        // Checking for a standard move
        if (rStep == 1 && this.fields[rCurr + step[0]][cCurr + step[1]] == 0) {
            return step;
        }

        // Check for taking an enemy pawn
        if (rStep == 2 && this.checkPawnTake(rCurr, cCurr, step[0], step[1], this)) {
            return step;
        }

        return null;
    }



    protected boolean checkPawnTake (int rCurr, int cCurr, int rStep, int cStep, Board b) {
        if (!b.isOnBoard(rCurr + rStep, cCurr + cStep)) {
            return false;
        }

        if (!(Math.abs(rStep) == 2)) {
            return false;
        }

        if (b.fields[rCurr + rStep][cCurr + cStep] != 0) {
            return false;
        }

        if (b.fields[rCurr + (rStep / 2)][cCurr + (cStep / 2)] == 0) {
            return false;
        }

        if (b.fields[rCurr + (rStep / 2)][cCurr + (cStep / 2)] == b.fields[rCurr][cCurr]) {
            return false;
        }

        return true;
    } 



    protected int longestPawnMove (int r, int c, Board b) throws CloneNotSupportedException {
        int[] moveLengths = new int[4];

        if (!this.isOnBoard(r, c)) {
            return 0;
        }

        if (this.checkPawnTake(r, c, 2, 2, b)) {
            Board bc = (Board)b.clone();

            bc.fields[r + 2][c + 2] = bc.fields[r][c];
            bc.fields[r + 1][c + 1] = 0;
            bc.fields[r][c] = 0;

            moveLengths[0] = 1 + this.longestPawnMove(r + 2, c + 2, bc);
        }

        if (this.checkPawnTake(r, c, 2, -2, b)) {
            Board bc = (Board)b.clone();

            bc.fields[r + 2][c - 2] = bc.fields[r][c];
            bc.fields[r + 1][c - 1] = 0;
            bc.fields[r][c] = 0;

            moveLengths[0] = 1 + this.longestPawnMove(r + 2, c - 2, bc);
        }

        if (this.checkPawnTake(r, c, -2, 2, b)) {
            Board bc = (Board)b.clone();

            bc.fields[r - 2][c + 2] = bc.fields[r][c];
            bc.fields[r - 1][c + 1] = 0;
            bc.fields[r][c] = 0;

            moveLengths[0] = 1 + this.longestPawnMove(r - 2, c + 2, bc);
        }

        if (this.checkPawnTake(r, c, -2, -2, b)) {
            Board bc = (Board)b.clone();

            bc.fields[r - 2][c - 2] = bc.fields[r][c];
            bc.fields[r - 1][c - 1] = 0;
            bc.fields[r][c] = 0;

            moveLengths[0] = 1 + this.longestPawnMove(r - 2, c - 2, bc);
        }

        int maxLength = 0;
        for (int i = 0; i < 4; i++) {
            maxLength = Math.max(maxLength, moveLengths[i]);
        }

        return maxLength;
    }



    protected boolean pawnToQueen (int rCurr, int cCurr, int rMov, int cMov) {
        if (isQueen(rCurr, cCurr)) {
            return false;
        }

        // if (this.longestPawnMove(rMov, cMov) > 0) {
        //     return false;
        // }

        if (this.fields[rCurr][cCurr] == 1 && rMov == this.size - 1) {
            return true;
        }

        if (this.fields[rCurr][cCurr] == 2 && rMov == 0) {
            return true;
        }

        return false;
    }



    protected boolean isQueen (int rCurr, int cCurr) {
        if ((this.fields[rCurr][cCurr] / 10) == 0) {
            return false;
        }

        return true;
    }



    // TODO
    protected int[] correctQueenStep (int rCurr, int cCurr, int rMov, int cMov) {
        int rStep = rMov - rCurr;
        int cStep = cMov - cCurr;
        int[] step = {rStep, cStep};

        rStep = Math.abs(rStep);
        cStep = Math.abs(cStep);
        if (rStep == cStep) {
            return step;
        }

        return null;
    }



    // TODO
    protected boolean checkQueenTake (int rCurr, int cCurr, int rStep, int cStep, Board b) {
        int xDir = (int)Math.signum(rStep);
        int yDir = (int)Math.signum(cStep);
        int xLim = rCurr + rStep;
        int yLim = cCurr + cStep;

        if (xDir > 0 && yDir > 0) {
            while ((rCurr + 2 * xDir) <= xLim && (cCurr + 2 * yDir) <= yLim) {
                if (this.checkPawnTake(rCurr, cCurr, rStep, cStep, b)) {
                    return true;
                }
                rCurr += xDir;
                cCurr += yDir;
            }
        }

        if (xDir > 0 && yDir < 0) {
            while ((rCurr + 2 * xDir) <= xLim && (cCurr + 2 * yDir) >= yLim) {
                if (this.checkPawnTake(rCurr, cCurr, rStep, cStep, b)) {
                    return true;
                }
                rCurr += xDir;
                cCurr += yDir;
            }
        }

        if (xDir < 0 && yDir > 0) {
            while ((rCurr + 2 * xDir) >= xLim && (cCurr + 2 * yDir) <= yLim) {
                if (this.checkPawnTake(rCurr, cCurr, rStep, cStep, b)) {
                    return true;
                }
                rCurr += xDir;
                cCurr += yDir;
            }
        }

        if (xDir < 0 && yDir < 0) {
            while ((rCurr + 2 * xDir) >= xLim && (cCurr + 2 * yDir) >= yLim) {
                if (this.checkPawnTake(rCurr, cCurr, rStep, cStep, b)) {
                    return true;
                }
                rCurr += xDir;
                cCurr += yDir;
            }
        }

        return false;
    }



    // TODO
    protected int longestQueenMove (int r, int c) {
        return 0;
    }
}
