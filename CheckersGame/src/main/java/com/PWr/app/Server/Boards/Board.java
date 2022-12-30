package com.PWr.app.Server.Boards;

import com.PWr.app.Server.States.*;





// This is a factory class
public abstract class Board {
    protected int size;
    protected int pawnLines;

    protected int[][] fields;
    protected int whitePawns;
    protected int blackPawns;

    protected int rPrevTake;
    protected int cPrevTake;

    protected GameStateBahaviour state;
    
    // TODO: add error codes (here and in the Game.java class)
    // In the Game.java class add method: getErrorMessage(int errorNo)
    protected int GAME_NOT_STARED = -1;
    // ...



    // The following method is of functionality verification purposes only
    public void mockEndgame (String player) {
        this.fields = new int[this.size][this.size];
        
        int half = this.size / 2;
        
        this.fields[half][half - 1] = 1;
        this.fields[half - 1][half] = 2;
        
        this.whitePawns = 1;
        this.blackPawns = 1;

        switch (player) {
            case "white": {
                this.state = GameState.WHITE.getStateBahaviour();
                break;
            }
            
            case "black": {
                this.state = GameState.BLACK.getStateBahaviour();
                break;
            }
            
            default: {
                System.out.println("Invalid player");
                break;
            }
        }
    }
    
    // The following method is of functionality verification purposes only
    public void mockQueenEndgame (String player) {
        this.fields = new int[this.size][this.size];

        switch (player) {
            case "white": {
                this.fields[4][5] = 10;
                this.fields[6][3] = 2;
                this.fields[1][4] = 2;
                this.fields[3][6] = 2;
                this.fields[1][6] = 2;

                this.whitePawns = 1;
                this.blackPawns = 4;

                this.state = GameState.WHITE.getStateBahaviour();
                break;
            }

            case "black": {
                this.fields[4][1] = 1;
                this.fields[1][4] = 1;
                this.fields[6][1] = 1;
                this.fields[5][4] = 1;
                this.fields[3][2] = 20;

                this.whitePawns = 4;
                this.blackPawns = 1;

                this.state = GameState.BLACK.getStateBahaviour();
                break;
            }

            default: {
                System.out.println("Invalid player");
                break;
            }
        }
    }
    
    // The following method is of functionality verification purposes only
    public void mockPawnToQueen (String player) {
        this.fields = new int[this.size][this.size];
        
        switch (player) {
            case "white": {
                this.fields[1][this.size - 2] = 1;
                this.fields[1][this.size - 4] = 2;
                
                this.whitePawns = 1;
                this.blackPawns = 1;
                
                this.state = GameState.WHITE.getStateBahaviour();
                break;
            }
            
            case "black": {
                this.fields[this.size - 2][3] = 1;
                this.fields[this.size - 2][1] = 2;
                
                this.whitePawns = 1;
                this.blackPawns = 1;
                
                this.state = GameState.BLACK.getStateBahaviour();
                break;
            }
            
            default: {
                System.out.println("Invalid player");
                break;
            }
        }
    }



    protected abstract int checkMove (int rCurr, int cCurr, int rMov, int cMov);
    
    
    
    public void init () {
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



    // TODO: Printing messages via getMessage(check) method
    public int movePawn (int rCurr, int cCurr, int rMov, int cMov) {
        // < 0: move NOT ok - genereal errrors
        // 0: move NOT ok - not optimal take
        // 1: move ok
        // 2: move ok + enemy pawn taken (furhter moves NOT allowed)
        // 3: move ok + enemy pawn taken (furhter moves allowed)

        int check = this.checkMove(rCurr, cCurr, rMov, cMov);
        if (check > 0) {
            System.out.printf("Pawn moved: (%d,%d) -> (%d,%d)\n", rCurr, cCurr, rMov, cMov);

            switch (check) {
                case 1: {
                    // Moving the pawn
                    this.fields[rMov][cMov] = this.fields[rCurr][cCurr];
                    this.fields[rCurr][cCurr] = 0;

                    if (this.pawnToQueen(rCurr, cCurr, rMov, cMov)) {
                        System.out.println("Pawn changed to queen");
                        this.fields[rMov][cMov] *= 10;
                    }

                    this.state = this.state.switchPlayer();
                    break;
                }

                case 2: {
                    if (!this.checkTakingPlayer(rCurr, cCurr)) {
                        System.out.println("Error: Sequential enemy pawn taking performed by invalid pawn");
                        return -8;
                    }
                    
                    // Moving the pawn and taking an enemy pawn
                    if (this.isQueen(rCurr, cCurr)) {
                        this.queenTake(rCurr, cCurr, rMov, cMov);
                    }
                    else {
                        this.pawnTake(rCurr, cCurr, rMov, cMov);
                    }

                    try {
                        if (this.pawnToQueen(rCurr, cCurr, rMov, cMov) && this.longestPawnTake(rMov, cMov) == 0) {
                            System.out.println("Pawn changed to queen");
                            this.fields[rMov][cMov] *= 10;
                        }
                    }
                    catch (CloneNotSupportedException e) {
                        System.out.println("Clone error!");
                    }

                    if (this.getState() == GameState.WHITE && this.blackPawns == 0) {
                        this.display();
                        System.out.println("White wins!\n");
                        this.state = this.state.endGame();
                        return 10;
                    }

                    if (this.getState() == GameState.BLACK && this.whitePawns == 0) {
                        this.display();
                        System.out.println("Black wins!\n");
                        this.state = this.state.endGame();
                        return 20;
                    }

                    this.rPrevTake = -1;
                    this.cPrevTake = -1;
                    this.state = this.state.switchPlayer();
                    break;
                }

                case 3: {
                    if (!this.checkTakingPlayer(rCurr, cCurr)) {
                        System.out.println("Error: Sequential enemy pawn taking performed by invalid pawn");
                        return -8;
                    }

                    // Moving the pawn and taking an enemy pawn
                    if (this.isQueen(rCurr, cCurr)) {
                        this.queenTake(rCurr, cCurr, rMov, cMov);
                    }
                    else {
                        this.pawnTake(rCurr, cCurr, rMov, cMov);
                    }

                    if (this.getState() == GameState.WHITE && this.blackPawns == 0) {
                        this.display();
                        System.out.println("White wins!\n");
                        this.state = this.state.endGame();
                        return 10;
                    }

                    if (this.getState() == GameState.BLACK && this.whitePawns == 0) {
                        this.display();
                        System.out.println("Black wins!\n");
                        this.state = this.state.endGame();
                        return 20;
                    }

                    this.rPrevTake = rMov;
                    this.cPrevTake = cMov;

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
                    System.out.printf("Error: There is a pawn on the `Movement` position (%d,%d)!\n", rMov, cMov);
                    break;
                }

                case -6: {
                    System.out.printf("Error: Invalid step (%d,%d) -> (%d,%d)!\n", rCurr, cCurr, rMov, cMov);
                    break;
                }

                case -7: {
                    System.out.println("Error: Not taking an enemy pawn when it is possible!");
                    break;
                }

                case -10: {
                    System.out.println("Clone error!");
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



    protected boolean checkTakingPlayer (int r, int c) {
        if (this.rPrevTake == -1 && this.cPrevTake == -1) {
            return true;
        }

        if (r == this.rPrevTake && c == this.cPrevTake) {
            return true;
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
        int[] step = {rMov - rCurr, cMov - cCurr};

        int rStep = Math.abs(step[0]);
        int cStep = Math.abs(step[1]);

        // Check for stepping on a white field
        if (rStep != cStep) {
            return null;
        }

        // Check a standard move
        if (rStep == 1 && this.fields[rCurr + step[0]][cCurr + step[1]] == 0) {
            // Check for backward movement
            if (this.isWhite(rCurr, cCurr) && step[0] > 0) {
                return null;
            } 
            if (this.isBlack(rCurr, cCurr) && step[0] < 0) {
                return null;
            } 

            return step;
        }

        // Check for taking an enemy pawn
        if (rStep == 2 && this.checkPawnTake(rCurr, cCurr, step[0], step[1])) {
            return step;
        }

        return null;
    }



    protected boolean checkPawnTake (int rCurr, int cCurr, int rStep, int cStep) {
        if (!this.isOnBoard(rCurr + rStep, cCurr + cStep)) {
            return false;
        }

        if (!(Math.abs(rStep) == 2)) {
            return false;
        }

        if (this.fields[rCurr + rStep][cCurr + cStep] != 0) {
            return false;
        }

        if (this.fields[rCurr + (rStep / 2)][cCurr + (cStep / 2)] == 0) {
            return false;
        }

        if (this.fields[rCurr + (rStep / 2)][cCurr + (cStep / 2)] == this.fields[rCurr][cCurr]) {
            return false;
        }

        return true;
    } 



    protected void pawnTake (int rCurr, int cCurr, int rMov, int cMov) {
        int rDir = (int)Math.signum(rMov - rCurr);
        int cDir = (int)Math.signum(cMov - cCurr);

        this.fields[rMov][cMov] = this.fields[rCurr][cCurr];
        this.fields[rCurr + rDir][cCurr + cDir] = 0;
        this.fields[rCurr][cCurr] = 0;

        if (this.getState() == GameState.WHITE) {
            this.blackPawns--;
        }
        else if (this.getState() == GameState.BLACK) {
            this.whitePawns--;
        }
    }


    
    protected int longestPawnTake (int r, int c) throws CloneNotSupportedException {
        // this.display();
        if (!this.isOnBoard(r, c)) {
            return 0;
        }
        
        int[] moveLengths = new int[4];

        if (this.checkPawnTake(r, c, 2, 2)) {
            Board bc = (Board)this.clone();
            bc.pawnTake(r, c, r + 2, c + 2);

            moveLengths[0] = 1 + bc.longestPawnTake(r + 2, c + 2);
        }

        if (this.checkPawnTake(r, c, 2, -2)) {
            Board bc = (Board)this.clone();
            bc.pawnTake(r, c, r + 2, c - 2);

            moveLengths[0] = 1 + bc.longestPawnTake(r + 2, c - 2);
        }

        if (this.checkPawnTake(r, c, -2, 2)) {
            Board bc = (Board)this.clone();
            bc.pawnTake(r, c, r - 2, c + 2);

            moveLengths[0] = 1 + bc.longestPawnTake(r - 2, c + 2);
        }

        if (this.checkPawnTake(r, c, -2, -2)) {
            Board bc = (Board)this.clone();
            bc.pawnTake(r, c, r - 2, c - 2);

            moveLengths[0] = 1 + bc.longestPawnTake(r - 2, c - 2);
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

        if (this.getState() == GameState.WHITE && rMov == 0) {
            return true;
        }

        if (this.getState() == GameState.BLACK && rMov == this.size - 1) {
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



    protected boolean checkQueenTake (int rCurr, int cCurr, int rStep, int cStep) {
        int rMov = rCurr + rStep;
        int cMov = cCurr + cStep;

        if (!this.isOnBoard(rMov, cMov)) {
            return false;
        }

        if (this.fields[rMov][cMov] != 0) {
            return false;
        }

        if (Math.abs(rStep) < 2) {
            return false;
        }

        int rDir = (int)Math.signum(rStep);
        int cDir = (int)Math.signum(cStep);
        
        int enemyCount = 0;
        int rTmp = rCurr + rDir;
        int cTmp = cCurr + cDir;

        if (this.getState() == GameState.WHITE) {
            while (rTmp != rMov && cTmp != cMov) {
                if (this.isWhite(rTmp, cTmp)) {
                    return false;
                }

                if (this.isBlack(rTmp, cTmp)) {
                    if (this.isBlack(rTmp + rDir, cTmp + cDir)) {
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
                if (this.isBlack(rTmp, cTmp)) {
                    return false;
                }

                if (this.isWhite(rTmp, cTmp)) {
                    if (this.isWhite(rTmp + rDir, cTmp + cDir)) {
                        return false;
                    }

                    enemyCount++;
                }

                rTmp += rDir;
                cTmp += cDir;
            }
        }

        if (enemyCount == 1) {
            return true;
        }

        return false;
    }



    protected void queenTake (int rCurr, int cCurr, int rMov, int cMov) {
        this.fields[rMov][cMov] = this.fields[rCurr][cCurr];

        int rDir = (int)Math.signum(rMov - rCurr);
        int cDir = (int)Math.signum(cMov - cCurr);
        int rTmp = rCurr;
        int cTmp = cCurr;

        if (this.getState() == GameState.WHITE) {
            while (rTmp != rMov && cTmp != cMov) {
                if (this.isBlack(rTmp, cTmp)) {
                    this.fields[rTmp][cTmp] = 0;
                    this.blackPawns--;
                }

                rTmp += rDir;
                cTmp += cDir;
            }
        }
        else if (this.getState() == GameState.BLACK) {
            while (rTmp != rMov && cTmp != cMov) {
                if (this.isWhite(rTmp, cTmp)) {
                    this.fields[rTmp][cTmp] = 0;
                    this.whitePawns--;
                }

                rTmp += rDir;
                cTmp += cDir;
            }
        }

        this.fields[rCurr][cCurr] = 0;
    }



    // TODO
    protected int longestQueenTake (int r, int c) throws CloneNotSupportedException {
        if (!this.isOnBoard(r, c)) {
            return 0;
        }
        
        int[] moveLengths = new int[4];
        int rDir, cDir;
        int rStep, cStep;


        rDir = 1;
        cDir = 1;
        rStep = 2 * rDir;
        cStep = 2 * cDir;
        while (this.isOnBoard(r + rStep, c + cStep)) {
            if (this.checkQueenTake(r, c, rStep, cStep)) {
                Board bc = (Board)this.clone();
                bc.queenTake(r, c, r + rStep, c + cStep);

                int length = 1 + bc.longestQueenTake(r + rStep, c + cStep);
                if (length > moveLengths[0]) {
                    moveLengths[0] = length;
                }
            }
            
            rStep += rDir;
            cStep += cDir;
        }


        rDir = 1;
        cDir = -1;
        rStep = 2 * rDir;
        cStep = 2 * cDir;
        while (this.isOnBoard(r + rStep, c + cStep)) {
            if (this.checkQueenTake(r, c, rStep, cStep)) {
                Board bc = (Board)this.clone();
                bc.queenTake(r, c, r + rStep, c + cStep);

                int length = 1 + bc.longestQueenTake(r + rStep, c + cStep);
                if (length > moveLengths[1]) {
                    moveLengths[1] = length;
                }
            }
            
            rStep += rDir;
            cStep += cDir;
        }


        rDir = -1;
        cDir = 1;
        rStep = 2 * rDir;
        cStep = 2 * cDir;
        while (this.isOnBoard(r + rStep, c + cStep)) {
            if (this.checkQueenTake(r, c, rStep, cStep)) {
                Board bc = (Board)this.clone();
                bc.queenTake(r, c, r + rStep, c + cStep);

                int length = 1 + bc.longestQueenTake(r + rStep, c + cStep);
                if (length > moveLengths[2]) {
                    moveLengths[2] = length;
                }
            }
            
            rStep += rDir;
            cStep += cDir;
        }


        rDir = -1;
        cDir = -1;
        rStep = 2 * rDir;
        cStep = 2 * cDir;
        while (this.isOnBoard(r + rStep, c + cStep)) {
            if (this.checkQueenTake(r, c, rStep, cStep)) {
                Board bc = (Board)this.clone();
                bc.queenTake(r, c, r + rStep, c + cStep);

                int length = 1 + bc.longestQueenTake(r + rStep, c + cStep);
                if (length > moveLengths[3]) {
                    moveLengths[3] = length;
                }
            }
            
            rStep += rDir;
            cStep += cDir;
        }



        int maxLength = 0;
        for (int i = 0; i < 4; i++) {
            maxLength = Math.max(maxLength, moveLengths[i]);
        }

        return maxLength;
    }



    protected int longestTake () {
        try {
            int longestTake = 0;

            for (int r = 0; r < this.size; r++) {
                for (int c = 0; c < this.size; c++) {
                    int takeLength = 0;
                    if (this.checkPlayer(r, c)) {
                        if (this.isQueen(r, c)) {
                            takeLength = this.longestQueenTake(r, c);
                        }
                        else {
                            takeLength = this.longestPawnTake(r, c);
                        }
                    }

                    if (takeLength > longestTake) {
                        longestTake = takeLength;
                    }
                }
            }

            return longestTake;
        }
        catch (CloneNotSupportedException e) {
            System.out.println("Clone error!");
            return -1;
        }
    }



    public int longestMove (int r, int c) {
        if (!this.checkPlayer(r, c)) {
            return -1;
        }
        
        try {
            if (this.isQueen(r, c)) {
                int lqt = this.longestQueenTake(r, c);
                if (lqt > 0) {
                    return lqt;
                }

                return 1;
            }
            
            int lpt = this.longestPawnTake(r, c);
            if (lpt > 0) {
                return lpt;
            }

            return 1;
        }
        catch (CloneNotSupportedException e) {
            System.out.println("Clone error!");
        }


        return 0;
    }
}
