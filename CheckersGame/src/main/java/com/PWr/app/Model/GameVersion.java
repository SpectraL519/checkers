package com.PWr.app.Model;





// This is a factory class
public abstract class GameVersion {
    protected int boardSize;
    protected int pawnLines;
    protected int[][] board;



    public void initPawns() {
        // Pawns:
        // 0 - no pawn
        // 1 - white
        // 10 - white queen
        // 2 - black
        // 20 - black queen

        // Init white pawns
        int limit = this.boardSize - this.pawnLines - 1;
        for (int r = this.boardSize - 1; r > limit; r--) {
            int startPos = (r + 1) % 2;
            for (int c = startPos; c < this.boardSize; c += 2) {
                this.board[r][c] = 1;
            }
        }
        
        // Init black pawns
        for (int r = 0; r < this.pawnLines; r++) {
            int startPos = (r + 1) % 2;
            for (int c = startPos; c < this.boardSize; c += 2) {
                this.board[r][c] = 2;
            }
        }
    }



    public void displayPawns() {
        for (int r = 0; r < this.boardSize; r++) {
            System.out.printf("%d: ", r);
            for (int c = 0; c < this.boardSize; c++) {
                System.out.printf("%d ", this.board[r][c]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n  ");
        for (int c = 0; c < this.boardSize; c++) {
            System.out.printf(" %d", c);
        }
        System.out.printf("\n");
    }



    public int movePawn (int rCurr, int cCurr, int rMov, int cMov) {
        // -1: move NOT ok - genereal errrors
        // 0: move NOT ok - not optimal take
        // 1: move ok but no further move is allowed
        // 2: move ok and furhter moves allowed

        int check = this.checkMove(rCurr, cCurr, rMov, cMov);
        if (check > 0) {
            if (pawnToQueen(rCurr, cCurr, rMov, cMov)) {
                this.board[rMov][cMov] = this.board[rCurr][cCurr] * 10;
            }
            else {
                this.board[rMov][cMov] = this.board[rCurr][cCurr];
            }

            this.board[rCurr][cCurr] = 0;
        }

        return check;
    }
    
    
    
    public abstract int checkMove (int rCurr, int cCurr, int rMov, int cMov);



    private boolean pawnToQueen (int rCurr, int cCurr, int rMov, int cMov) {
        if (isQueen(rCurr, cCurr)) {
            return false;
        }

        if (longestPawnMove(rMov, cMov) > 0) {
            return false;
        }

        if (this.board[cCurr][rCurr] == 1 && cMov == this.boardSize - 1) {
            return true;
        }

        if (this.board[cCurr][rCurr] == 2 && cMov == 0) {
            return true;
        }

        return false;
    }



    protected boolean isOnBoard (int x, int y) {
        if (x < 0 || x >= this.boardSize) {
            return false;
        }

        if (y < 0 || y >= this.boardSize) {
            return false;
        }

        return true;
    }



    protected boolean isQueen (int rCurr, int cCurr) {
        if ((this.board[rCurr][cCurr] / 10) == 0) {
            return false;
        }

        return true;
    }



    // Separate this method to correctPawnStep and correctQueenStep
    protected int[] correctStep (int rCurr, int cCurr, int rMov, int cMov, boolean queen) {
        int stepX = rMov - rCurr;
        int stepY = cMov - cCurr;
        int[] step = {stepX, stepY};
        
        stepX = Math.abs(stepX);
        stepY = Math.abs(stepY);

        if (stepX == stepY) {
            if (queen) {
                return step;
            }

            if (stepX > 2) {
                step[0] = 0;
                step[1] = 0;
                return step;
            }

            return step;
        }

        step[0] = 0;
        step[1] = 0;
        return step;
    }



    protected boolean checkPawnTake (int rCurr, int cCurr, int rStep, int cStep) {
        if (!isOnBoard(rCurr + rStep, cCurr + cStep)) {
            return false;
        }

        if (Math.abs(rStep) == 2 && this.board[rCurr + (rStep / 2)][cCurr + (cStep / 2)] == 0) {
            return false;
        }

        return true;
    } 



    protected boolean checkQueenTake (int rCurr, int cCurr, int rStep, int cStep) {
        int xDir = (int)Math.signum(rStep);
        int yDir = (int)Math.signum(cStep);
        int xLim = rCurr + rStep;
        int yLim = cCurr + cStep;

        if (xDir > 0 && yDir > 0) {
            while ((rCurr + 2 * xDir) <= xLim && (cCurr + 2 * yDir) <= yLim) {
                if (this.checkPawnTake(rCurr, cCurr, rStep, cStep)) {
                    return true;
                }
                rCurr += xDir;
                cCurr += yDir;
            }
        }

        if (xDir > 0 && yDir < 0) {
            while ((rCurr + 2 * xDir) <= xLim && (cCurr + 2 * yDir) >= yLim) {
                if (this.checkPawnTake(rCurr, cCurr, rStep, cStep)) {
                    return true;
                }
                rCurr += xDir;
                cCurr += yDir;
            }
        }

        if (xDir < 0 && yDir > 0) {
            while ((rCurr + 2 * xDir) >= xLim && (cCurr + 2 * yDir) <= yLim) {
                if (this.checkPawnTake(rCurr, cCurr, rStep, cStep)) {
                    return true;
                }
                rCurr += xDir;
                cCurr += yDir;
            }
        }

        if (xDir < 0 && yDir < 0) {
            while ((rCurr + 2 * xDir) >= xLim && (cCurr + 2 * yDir) >= yLim) {
                if (this.checkPawnTake(rCurr, cCurr, rStep, cStep)) {
                    return true;
                }
                rCurr += xDir;
                cCurr += yDir;
            }
        }

        return false;
    }



    protected int longestPawnMove (int r, int c) {
        int length = 0;
        int[] moveLengths = new int[4];

        if (this.isOnBoard(r, c) && this.checkPawnTake(r, c, 2, 2)) {
            moveLengths[0] = longestPawnMove(r + 2, c + 2);
        }
        if (this.isOnBoard(r, c) && this.checkPawnTake(r, c, 2, -2)) {
            moveLengths[0] = longestPawnMove(r + 2, c - 2);
        }
        if (this.isOnBoard(r, c) && this.checkPawnTake(r, c, -2, 2)) {
            moveLengths[0] = longestPawnMove(r - 2, c + 2);
        }
        if (this.isOnBoard(r, c) && this.checkPawnTake(r, c, -2, -2)) {
            moveLengths[0] = longestPawnMove(r - 2, c - 2);
        }

        int maxLength = moveLengths[0];
        for (int i = 1; i < 4; i++) {
            maxLength = Math.max(maxLength, moveLengths[i]);
        }

        return length + maxLength;
    }



    protected int longestQueenMove (int r, int c) {
        int length = 0;

        // TODO

        return length;
    }
}
