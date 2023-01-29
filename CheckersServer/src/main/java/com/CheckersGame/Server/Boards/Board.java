package com.CheckersGame.Server.Boards;

import com.CheckersGame.Server.States.*;

import javafx.util.Pair;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Checkers game board operation handling abstract class
 */
public abstract class Board {
    protected int size; /** The number of fields in one row or column on the board */
    protected int pawnRows; /** The number of rows in which the pawns are placed when initializing the board */

    protected int[][] fields; /** The board structure */
    protected int whitePawns; /** The number of white pawns on the board */
    protected int blackPawns; /** The number of black pawns on the board */

    protected int rPrevTake; /** Stores the row value of the previously taking pawn `movement` position */
    protected int cPrevTake; /** Stores the column value of the previously taking pawn `movement` position */

    protected GameStateBahaviour state; /** Handles the current game state */

    protected final static int MOVE_ALLOWED = 1; /** Pawn movement return code: given move is allowed */
    protected final static int TAKE_ALLOWED = 2; /** Pawn movement return code: given move is allowed and an enemy pawn can be taken */
    protected final static int SEQUENTIAL_TAKE_ALLOWED = 3; /** Pawn movement return code: given move is allowed and multiple enemy pawns can be taken */
    public final static int WHITE_WINS = 10; /** Pawn movement return code: player WHITE wins */
    public final static int BLACK_WINS = 20; /** Pawn movement return code: player BLACK wins */

    protected final static int NOT_OPTIMAL_TAKE = 0; /** Pawn movement return code: Not optimal take error */
    protected final static int GAME_NOT_STARED = -1; /** Pawn movement return code: Game not started error */
    protected final static int CURR_NOT_ON_BOARD = -2; /** Pawn movement return code: Position `current` is not on board */
    protected final static int MOV_NOT_ON_BOARD = -3; /** Pawn movement return code: Position `movement` is not on board */
    protected final static int INVALID_PLAYER = -4; /** Pawn movement return code: The position `current` is invalid for the current player */
    protected final static int PAWN_ON_MOV = -5; /** Pawn movement return code: There is already a pawn on the position `movement` */
    protected final static int INVALID_STEP = -6; /** Pawn movement return code: Invalid move step */
    protected final static int FORCED_TAKE_ERROR = -7; /** Pawn movement return code: Not taking an enemy pawn when it's possible */
    protected final static int SEQUENTIAL_TAKE_ERROR = -8; /** Pawn movement return code: Player tries to take enemy pawns sequentially with different pawns */
    protected final static int CLONE_ERROR = -9; /** Pawn movement return code: Clone error */
    public final static int UNKNOWN_ERROR = -10; /** Pawn movement return code: Unknown error */



    /** 
     * Checks whether moving a pawn from the position `current` to the position `movement` is allowed
     * @param rCurr
     * @param cCurr
     * @param rMov
     * @param cMov
     * @return int
     */
    protected abstract int checkMove (int rCurr, int cCurr, int rMov, int cMov);
    
    
    
    /**
     * Initializes the game board and pawns
     */
    public void init () {
        // Init white pawns
        int limit = this.size - this.pawnRows - 1;
        for (int r = this.size - 1; r > limit; r--) {
            int startPos = (r + 1) % 2;
            for (int c = startPos; c < this.size; c += 2) {
                this.fields[r][c] = 1;
            }
        }
        
        // Init black pawns
        for (int r = 0; r < this.pawnRows; r++) {
            int startPos = (r + 1) % 2;
            for (int c = startPos; c < this.size; c += 2) {
                this.fields[r][c] = 2;
            }
        }

        this.whitePawns = this.pawnRows * this.size / 2;
        this.blackPawns = this.whitePawns;

        this.state = GameState.RESTING.getStateBahaviour();
        this.state = this.state.startGame();
    }



    
    /** 
     * Returns the current game state
     * @return GameState
     */
    public GameState getState () {
        return this.state.getState();
    }



    
    /** 
     * Returns a description of the current game board in the following format:
     * `boardSize;pawn_1_raw,pawn_1_column,pawn_1_type;(...);pawn_n_raw,pawn_n_column,pawn_n_type`
     * @return String
     */
    public String getDescription () {
        String description = "board:" + String.valueOf(this.size);

        for (int r = 0; r < this.size; r++) {
            for (int c = 0; c < this.size; c++) {
                if (this.fields[r][c] > 0) {
                    description += ";" + String.valueOf(r) + "," + String.valueOf(c) + "," + String.valueOf(this.fields[r][c]);
                }
            }
        }
        
        return description;
    }



    
    /** 
     * Tries to move a pawn on the current board from the position `current` to the position `movement`.
     * @param rCurr
     * @param cCurr
     * @param rMov
     * @param cMov
     * @return int
     */
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
                case Board.MOVE_ALLOWED: {
                    // Moving the pawn
                    this.fields[rMov][cMov] = this.fields[rCurr][cCurr];
                    this.fields[rCurr][cCurr] = 0;

                    if (this.toQueen(rMov, cMov)) {
                        this.fields[rMov][cMov] *= 10;
                    }

                    this.state = this.state.switchPlayer();
                    break;
                }

                case Board.TAKE_ALLOWED: {
                    if (!this.checkTakingPawn(rCurr, cCurr)) {
                        return Board.SEQUENTIAL_TAKE_ERROR;
                    }
                    
                    // Moving the pawn and taking an enemy pawn
                    if (this.isQueen(rCurr, cCurr)) {
                        this.queenTake(rCurr, cCurr, rMov, cMov);
                    }
                    else {
                        this.pawnTake(rCurr, cCurr, rMov, cMov);
                    }

                    if (this.toQueen(rMov, cMov)) {
                        this.fields[rMov][cMov] *= 10;
                    }

                    if (this.getState() == GameState.WHITE && this.blackPawns == 0) {
                        this.state = this.state.endGame();
                        return Board.WHITE_WINS;
                    }

                    if (this.getState() == GameState.BLACK && this.whitePawns == 0) {
                        this.state = this.state.endGame();
                        return Board.BLACK_WINS;
                    }

                    this.rPrevTake = -1;
                    this.cPrevTake = -1;
                    this.state = this.state.switchPlayer();
                    break;
                }

                case Board.SEQUENTIAL_TAKE_ALLOWED: {
                    if (!this.checkTakingPawn(rCurr, cCurr)) {
                        return Board.SEQUENTIAL_TAKE_ERROR;
                    }

                    // Moving the pawn and taking an enemy pawn
                    if (this.isQueen(rCurr, cCurr)) {
                        this.queenTake(rCurr, cCurr, rMov, cMov);
                    }
                    else {
                        this.pawnTake(rCurr, cCurr, rMov, cMov);
                    }

                    this.rPrevTake = rMov;
                    this.cPrevTake = cMov;
                    break;
                }

                default: {
                    check = Board.UNKNOWN_ERROR;
                    this.state = this.state.endGame();
                    break;
                }
            }
        }

        return check;
    }



    public int botMovement (String bot) {
        System.out.println("bot: checkpoint 1");
        if (this.state.getState().getName().equals(bot)) {
            System.out.println("bot: checkpoint 2");
            
            Pair <Integer, Pair <Pair <Integer, Integer>, Pair <Integer, Integer>>> longestTake = this.longestTake();
            int takeLength = longestTake.getKey();
            Pair <Integer, Integer> curr = longestTake.getValue().getKey();
            Pair <Integer, Integer> mov = longestTake.getValue().getValue();

            System.out.printf("botMovement: longestTake = %d\n", takeLength);
            if (curr != null)
                System.out.printf("botMovement: LT.curr: (%d,%d)\n", curr.getKey(), curr.getValue());
            if (mov != null)
                System.out.printf("botMovement: LT.mov: (%d,%d)\n", mov.getKey(), mov.getValue());

            System.out.println("bot: checkpoint 3");
            if (takeLength == 0) {
                System.out.println("bot: checkpoint 4");
                for (int r = 0; r < this.size; r++) {
                    for (int c = 0; c < this.size; c++) {
                        int move = this.movePawn(r, c, r + 1, c + 1);
                        if (move > 0) {
                            return move;
                        }

                        move = this.movePawn(r, c, r + 1, c - 1);
                        if (move > 0) {
                            return move;
                        }

                        move = this.movePawn(r, c, r - 1, c + 1);
                        if (move > 0) {
                            return move;
                        }

                        move = this.movePawn(r, c, r - 1, c - 1);
                        if (move > 0) {
                            return move;
                        }
                    }
                }
            }
            
            System.out.println("bot: checkpoint 5");
            return this.movePawn(curr.getKey(), curr.getValue(), mov.getKey(), mov.getValue());
        }

        System.out.println("bot: checkpoint 6 (error)");
        return Board.UNKNOWN_ERROR;
    }



    /** 
     * Returns a pawn move message for the given status
     * @param status
     * @return String
     */
    public String getMoveMessage (int status) {
        String message = null;

        switch (status) {
            case Board.MOVE_ALLOWED: {
                message = "Move allowed";
                break;
            }

            case Board.TAKE_ALLOWED: {
                message = "Take allowed";
                break;
            }

            case Board.SEQUENTIAL_TAKE_ALLOWED: {
                message = "Sequential take allowed";
                break;
            }

            case Board.WHITE_WINS: {
                message = "White wins!";
                break;
            }

            case Board.BLACK_WINS: {
                message = "Black wins!";
                break;
            }

            case Board.NOT_OPTIMAL_TAKE: {
                message = "Error: Not optimal take";
                break;
            }

            case Board.GAME_NOT_STARED: {
                message = "Error: The game has not started yet";
                break;
            }

            case Board.CURR_NOT_ON_BOARD: {
                message = "Error: The selected `Current` position is not on the board";
                break;
            }

            case Board.MOV_NOT_ON_BOARD: {
                message = "Error: The selected `Movement` position is not on the board";
                break;
            }

            case Board.INVALID_PLAYER: {
                message = "Error: Invalid position selected for the current player: " + this.state.getState();
                break;
            }

            case Board.PAWN_ON_MOV: {
                message = "Error: There is a pawn on the `Movement` position";
                break;
            }

            case Board.INVALID_STEP: {
                message = "Error: Invalid step";
                break;
            }

            case Board.FORCED_TAKE_ERROR: {
                message = "Error: Not taking an enemy pawn when it is possible";
                break;
            }

            case Board.SEQUENTIAL_TAKE_ERROR: {
                message = "Error: Invalid sequential taking";
                break;
            }

            case Board.CLONE_ERROR: {
                message = "Error: Clone";
                break;
            }

            default: {
                message = "Error: Unknown";
                break;
            }
        }

        return message;
    }



    
    /** 
     * Returns true if the pawn on the given position is white
     * @param r
     * @param c
     * @return boolean
     */
    protected boolean isWhite (int r, int c) {
        return (this.fields[r][c] == 1 || this.fields[r][c] == 10);
    }



    
    /** 
     * Returns true if the pawn on the given position is black
     * @param r
     * @param c
     * @return boolean
     */
    protected boolean isBlack (int r, int c) {
        return (this.fields[r][c] == 2 || this.fields[r][c] == 20);
    }



    
    /** 
     * Checks wheter the pawn on the given position is valid for the curent player
     * @param r
     * @param c
     * @return boolean
     */
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



    
    /** 
     * Checks if a sequential enemy pawn taking is performed by a single pawn
     * @param r
     * @param c
     * @return boolean
     */
    protected boolean checkTakingPawn (int r, int c) {
        if (this.rPrevTake == -1 && this.cPrevTake == -1) {
            return true;
        }

        if (r == this.rPrevTake && c == this.cPrevTake) {
            return true;
        }

        return false;
    }



    
    /** 
     * Checks whether the given position is on the game board
     * @param r
     * @param c
     * @return boolean
     */
    protected boolean isOnBoard (int r, int c) {
        if (r < 0 || r >= this.size) {
            return false;
        }

        if (c < 0 || c >= this.size) {
            return false;
        }

        return true;
    }



    
    /** 
     * Checkt if the step from the position `current` to the position `movement` is valid. If not returns null
     * @param rCurr
     * @param cCurr
     * @param rMov
     * @param cMov
     * @return int[]
     */
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



    
    /** 
     * Checks whether enemeny pawn taking is performed correctly
     * @param rCurr
     * @param cCurr
     * @param rStep
     * @param cStep
     * @return boolean
     */
    protected boolean checkPawnTake (int rCurr, int cCurr, int rStep, int cStep) {
        if (!this.isOnBoard(rCurr + rStep, cCurr + cStep)) {
            return false;
        }

        if (Math.abs(rStep) != 2) {
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



    
    /** 
     * Performs pawn taking
     * @param rCurr
     * @param cCurr
     * @param rMov
     * @param cMov
     */
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


    
    
    /** 
     * Calculates the longest pawn take from the given position
     * @param r
     * @param c
     * @return int
     * @throws CloneNotSupportedException
     */
    protected Pair <Integer, Pair <Integer, Integer>> longestPawnTake (int r, int c) throws CloneNotSupportedException {
        // this.display();
        if (!this.isOnBoard(r, c)) {
            return new Pair <> (0, null);
        }
        
        int[] moveLengths = new int[4];
        Pair <Integer, Integer> [] moveCoordinates = new Pair[4];

        if (this.checkPawnTake(r, c, 2, 2)) {
            Board bc = (Board)this.clone();
            bc.pawnTake(r, c, r + 2, c + 2);

            moveLengths[0] = 1 + bc.longestPawnTake(r + 2, c + 2).getKey();
            moveCoordinates[0] = new Pair<>(r + 2, c + 2);
        }

        if (this.checkPawnTake(r, c, 2, -2)) {
            Board bc = (Board)this.clone();
            bc.pawnTake(r, c, r + 2, c - 2);

            moveLengths[1] = 1 + bc.longestPawnTake(r + 2, c - 2).getKey();
            moveCoordinates[1] = new Pair<>(r + 2, c - 2);
        }

        if (this.checkPawnTake(r, c, -2, 2)) {
            Board bc = (Board)this.clone();
            bc.pawnTake(r, c, r - 2, c + 2);

            moveLengths[2] = 1 + bc.longestPawnTake(r - 2, c + 2).getKey();
            moveCoordinates[2] = new Pair<>(r - 2, c + 2);
        }

        if (this.checkPawnTake(r, c, -2, -2)) {
            Board bc = (Board)this.clone();
            bc.pawnTake(r, c, r - 2, c - 2);

            moveLengths[3] = 1 + bc.longestPawnTake(r - 2, c - 2).getKey();
            moveCoordinates[3] = new Pair<>(r - 2, c - 2);
        }

        int maxLength = 0;
        Pair <Integer, Integer> maxCoordinates = null;
        for (int i = 0; i < 4; i++) {
            if (moveLengths[i] > maxLength) {
                maxLength = moveLengths[i];
                maxCoordinates = moveCoordinates[i];
            }
        }

        return new Pair <> (maxLength, maxCoordinates);
    }



    
    /** 
     * Checks for a pawn to queen conversion (after pawn movement has been performed)
     * @param rMov
     * @param cMov
     * @return boolean
     */
    protected boolean toQueen (int rMov, int cMov) {
        if (this.isQueen(rMov, cMov)) {
            return false;
        }

        if (this.isWhite(rMov, cMov) && rMov == 0) {
            return true;
        }

        if (this.isBlack(rMov, cMov) && rMov == this.size - 1) {
            return true;
        }

        return false;
    }



    
    /** 
     * Returns true if the pawn on the given position is a queen
     * @param r
     * @param c
     * @return boolean
     */
    protected boolean isQueen (int r, int c) {
        if ((this.fields[r][c] / 10) == 0) {
            return false;
        }

        return true;
    }



    
    /** 
     * Checkt if the queen's step from the position `current` to the position `movement` is valid. If not returns null
     * @param rCurr
     * @param cCurr
     * @param rMov
     * @param cMov
     * @return int[]
     */
    protected int[] checkQueenStep (int rCurr, int cCurr, int rMov, int cMov) {
        int rStep = rMov - rCurr;
        int cStep = cMov - cCurr;
        int[] step = {rStep, cStep};
    
        if (Math.abs(rStep) == Math.abs(cStep)) {
            return step;
        }

        return null;
    }



    
    /** 
     * Checks whether enemeny pawn taking is performed correctly (by a queen)
     * @param rCurr
     * @param cCurr
     * @param rStep
     * @param cStep
     * @return boolean
     */
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



    
    /** 
     * Performs pawn taking (by a queen)
     * @param rCurr
     * @param cCurr
     * @param rMov
     * @param cMov
     */
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



    
    /** 
     * Calculates the longest queen take from the given position
     * @param r
     * @param c
     * @return int
     * @throws CloneNotSupportedException
     */
    protected Pair <Integer, Pair <Integer, Integer>> longestQueenTake (int r, int c) throws CloneNotSupportedException {
        if (!this.isOnBoard(r, c)) {
            return new Pair <> (0, null);
        }
        
        int[] moveLengths = new int[4];
        Pair <Integer, Integer> [] moveCoordinates = new Pair[4];

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

                int length = 1 + bc.longestQueenTake(r + rStep, c + cStep).getKey();
                if (length > moveLengths[0]) {
                    moveLengths[0] = length;
                    moveCoordinates[0] = new Pair<>(r + rStep, c + cStep);
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

                int length = 1 + bc.longestQueenTake(r + rStep, c + cStep).getKey();
                if (length > moveLengths[1]) {
                    moveLengths[1] = length;
                    moveCoordinates[1] = new Pair<>(r + rStep, c + cStep);
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

                int length = 1 + bc.longestQueenTake(r + rStep, c + cStep).getKey();
                if (length > moveLengths[2]) {
                    moveLengths[2] = length;
                    moveCoordinates[2] = new Pair<>(r + rStep, c + cStep);
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

                int length = 1 + bc.longestQueenTake(r + rStep, c + cStep).getKey();
                if (length > moveLengths[3]) {
                    moveLengths[3] = length;
                    moveCoordinates[3] = new Pair <> (r + rStep, c + cStep);
                }
            }
            
            rStep += rDir;
            cStep += cDir;
        }



        int maxLength = 0;
        Pair <Integer, Integer> maxCoordinates = null;
        for (int i = 0; i < 4; i++) {
            if (moveLengths[i] > maxLength) {
                maxLength = moveLengths[i];
                maxCoordinates = moveCoordinates[i];
            }
        }

        return new Pair <> (maxLength, maxCoordinates);
    }



    
    /** 
     * Calculates the longest possible take on the board for the current player
     * @return int
     */
    protected Pair <Integer, Pair <Pair <Integer, Integer>, Pair <Integer, Integer>>> longestTake () {
        try {
            int longestTake = 0;
            Pair <Integer, Integer> curr = null;
            Pair <Integer, Integer> mov = null;

            for (int r = 0; r < this.size; r++) {
                for (int c = 0; c < this.size; c++) {
                    int takeLength = 0;
                    Pair <Integer, Pair <Integer, Integer>> take = null;

                    if (this.checkPlayer(r, c)) {
                        if (this.isQueen(r, c)) {
                            take = this.longestQueenTake(r, c);
                            takeLength = take.getKey();
                        }
                        else {
                            take = this.longestPawnTake(r, c);
                            takeLength = take.getKey();
                        }
                    }

                    if (takeLength > longestTake) {
                        longestTake = takeLength;
                        curr = new Pair <> (r, c);
                        mov = take.getValue();
                    }
                }
            }

            return new Pair <> (longestTake, new Pair <> (curr, mov));
        }
        catch (CloneNotSupportedException e) {
            System.out.println("Clone error!");
            return new Pair <> (-1, null);
        }
    }




        
    /** 
     * Mocks a simple endgame situation where the next (winning) move belongs to the specified `player`  
     * This method are of functionality verification purposes only
     * @param player
     */
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
    




    
    /** 
     * Mocks a queen endgame situation where the next (winning) move belongs to the specified `player`
     * This method are of functionality verification purposes only
     * @param player
     */
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
    

    
    
    /** 
     * Mocks an endgame situation in which the specified `player`'s move will change a pawn to a queen
     * This method are of functionality verification purposes only
     * @param player
     */
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
}
