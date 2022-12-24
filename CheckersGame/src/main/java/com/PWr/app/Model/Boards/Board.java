package com.PWr.app.Model.Boards;

import com.PWr.app.Model.States.*;





// This is a factory class
public abstract class Board {
    protected int size;
    protected int pawnLines;
    protected int[][] fields;
    protected int whitePawns;
    protected int blackPawns;
    protected GameStateBahaviour state;



    protected abstract int checkMove (int rCurr, int cCurr, int rMov, int cMov);
    public abstract void mockEndgame (String player);
    public abstract void mockQueenTake ();

    

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

        this.whitePawns = this.pawnLines * this.size / 2;
        this.blackPawns = this.whitePawns;

        this.state = this.state.startGame();
    }



    public GameState getState () {
        return this.state.getState();
    }



    public void display () {
        System.out.printf("White pawns: %d\n", this.whitePawns);
        System.out.printf("Black pawns: %d\n", this.blackPawns);
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

            switch (check) {
                // TODO:
                // Printing messages via getMessage(check) method
                case 1: {
                    this.state = this.state.switchPlayer();
                    break;
                }

                case 2: {
                    break;
                }

                case 10: {
                    System.out.println("White wins!");
                    this.display();
                    this.state = this.state.endGame();
                    break;
                }

                case 20: {
                    System.out.println("Black wins!");
                    this.display();
                    System.out.println();
                    this.state = this.state.endGame();
                    break;
                }

                default: {
                    System.out.println("Error: unknown flag - game ended!");
                    this.state = this.state.endGame();
                    break;
                }
            }
        }
        else {
            switch (check) {
                case 0: {
                    System.out.println("Error: Not optimal take!");
                    break;
                }

                case -1: {
                    System.out.println("Error: The game has not started yet!");
                    break;
                }

                case -2: {
                    System.out.printf("Error: The selected Current position (%d,%d) is not on the board!\n", rCurr, cCurr);
                    break;
                }

                case -3: {
                    System.out.printf("Error: The selected Movement position (%d,%d) is not on the board!\n", rMov, cMov);
                    break;
                }

                case -4: {
                    System.out.println("Error: Invalid position selected for the current player: " + this.state.getState() + "!");
                    break;
                }

                case -5: {
                    System.out.printf("Error: There is a pawn on the `Movement` position (%d,%d)\n", rMov, cMov);
                    break;
                }

                case -6: {
                    System.out.printf("Error: Invalid step (%d,%d) -> (%d,%d)\n", rCurr, cCurr, rMov, cMov);
                    break;
                }

                case -7: {
                    System.out.println("Error: Not taking an enemy pawn when it is possible!");
                    break;
                }

                default: {
                    System.out.println("Error: Unknown");
                    break;
                }
            }
        }

        return check;
    }



    protected boolean isWhite (int r, int c) {
        return (this.fields[r][c] == 1 || this.fields[r][c] == 10);
    }



    protected boolean isBlack (int r, int c) {
        return (this.fields[r][c] == 2 || this.fields[r][c] == 20);
    }



    protected boolean checkPlayer (int r, int c) {
        if (this.isWhite(r, c)) {
            if (this.state.getState() == GameState.WHITE) {
                return true;
            }

            return false;
        }

        if (this.isBlack(r, c)) {
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
        if (this.isWhite(rCurr, cCurr) && rStep > 0) {
            return null;
        } 
        if (this.isBlack(rCurr, cCurr) && rStep < 0) {
            return null;
        } 

        rStep = Math.abs(rStep);
        cStep = Math.abs(cStep);

        // Check for stepping on a white field
        if (rStep != cStep) {
            return null;
        }

        // Check a standard move
        if (rStep == 1 && this.fields[rCurr + step[0]][cCurr + step[1]] == 0) {
            return step;
        }

        // Check taking an enemy pawn
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
        if (!this.isOnBoard(r, c)) {
            return 0;
        }
        
        int[] moveLengths = new int[4];

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

        try {
            if (this.longestPawnMove(rMov, cMov, this) > 0) {
                return false;
            }
        }
        catch (CloneNotSupportedException e) {
            System.out.println("Clone error!");
            return false;
        }

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



    protected int[] checkQueenStep (int rCurr, int cCurr, int rMov, int cMov) {
        int rStep = rMov - rCurr;
        int cStep = cMov - cCurr;
        int[] step = {rStep, cStep};
    
        if (Math.abs(rStep) == Math.abs(cStep)) {
            return step;
        }

        return null;
    }



    protected boolean checkQueenTake (int rCurr, int cCurr, int rStep, int cStep, Board b) {
        if (!b.isOnBoard(rCurr + rStep, cCurr + cStep)) {
            return false;
        }

        if (b.fields[rCurr + rStep][cCurr + cStep] != 0) {
            return false;
        }

        if (Math.abs(rStep) < 2) {
            return false;
        }

        int rDir = (int)Math.signum(rStep);
        int cDir = (int)Math.signum(cStep);

        int rMov = rCurr + rStep;
        int cMov = cCurr + cStep;
        
        int enemyCount = 0;
        int rTmp = rCurr + rDir;
        int cTmp = cCurr + cDir;

        if (this.getState() == GameState.WHITE) {
            while (rTmp != rMov && cTmp != cMov) {
                if (b.isBlack(rTmp, cTmp)) {
                    if (b.isBlack(rTmp + rDir, cTmp + cDir)) {
                        return false;
                    }

                    enemyCount++;
                }

                rTmp += rDir;
                cTmp += cDir;
            }
        }
        else if (this.getState() == GameState.BLACK) {
            while (rTmp != rMov && cTmp != cMov) {
                if (b.isWhite(rTmp, cTmp)) {
                    if (b.isWhite(rTmp + rDir, cTmp + cDir)) {
                        return false;
                    }

                    enemyCount++;
                }

                rTmp += rDir;
                cTmp += cDir;
            }
        }

        if (enemyCount > 0) {
            return true;
        }

        return false;
    }



    // TODO
    protected int longestQueenMove (int r, int c, Board b) throws CloneNotSupportedException {
        if (!this.isOnBoard(r, c)) {
            return 0;
        }
        
        int[] moveLengths = new int[4];
        int rDir, cDir;
        int rStep, cStep;
        int m, length;


        rDir = 1;
        cDir = 1;
        rStep = 2 * rDir;
        cStep = 2 * cDir;
        m = 0;
        while (this.isOnBoard(r + rStep, c + cStep)) {
            if (this.checkQueenTake(r, c, rStep, cStep, b)) {
                Board bc = (Board)b.clone();

                bc.fields[r + 2][c + 2] = bc.fields[r][c];
                bc.fields[r + 1][c + 1] = 0;
                bc.fields[r][c] = 0;

                length = 1 + this.longestQueenMove(r + rStep, c + cStep, bc);
                if (length > m) {
                    m = length;
                }
            }
            rStep += rDir;
            cStep += cDir;
        }
        moveLengths[0] = m;


        rDir = 1;
        cDir = -1;
        rStep = 2 * rDir;
        cStep = 2 * cDir;
        m = 0;
        while (this.isOnBoard(r + rStep, c + cStep)) {
            if (this.checkQueenTake(r, c, rStep, cStep, b)) {
                Board bc = (Board)b.clone();

                bc.fields[r + 2][c + 2] = bc.fields[r][c];
                bc.fields[r + 1][c + 1] = 0;
                bc.fields[r][c] = 0;

                length = 1 + this.longestQueenMove(r + rStep, c + cStep, bc);
                if (length > m) {
                    m = length;
                }
            }
            rStep += rDir;
            cStep += cDir;
        }
        moveLengths[0] = m;


        rDir = -1;
        cDir = 1;
        rStep = 2 * rDir;
        cStep = 2 * cDir;
        m = 0;
        while (this.isOnBoard(r + rStep, c + cStep)) {
            if (this.checkQueenTake(r, c, rStep, cStep, b)) {
                Board bc = (Board)b.clone();

                bc.fields[r + 2][c + 2] = bc.fields[r][c];
                bc.fields[r + 1][c + 1] = 0;
                bc.fields[r][c] = 0;

                length = 1 + this.longestQueenMove(r + rStep, c + cStep, bc);
                if (length > m) {
                    m = length;
                }
            }
            rStep += rDir;
            cStep += cDir;
        }
        moveLengths[0] = m;


        rDir = -1;
        cDir = -1;
        rStep = 2 * rDir;
        cStep = 2 * cDir;
        m = 0;
        while (this.isOnBoard(r + rStep, c + cStep)) {
            if (this.checkQueenTake(r, c, rStep, cStep, b)) {
                Board bc = (Board)b.clone();

                bc.fields[r + 2][c + 2] = bc.fields[r][c];
                bc.fields[r + 1][c + 1] = 0;
                bc.fields[r][c] = 0;

                length = 1 + this.longestQueenMove(r + rStep, c + cStep, bc);
                if (length > m) {
                    m = length;
                }
            }
            rStep += rDir;
            cStep += cDir;
        }
        moveLengths[0] = m;



        int maxLength = 0;
        for (int i = 0; i < 4; i++) {
            maxLength = Math.max(maxLength, moveLengths[i]);
        }

        return maxLength;
    }
}
