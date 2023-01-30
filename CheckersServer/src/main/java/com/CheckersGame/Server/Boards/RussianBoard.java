package com.CheckersGame.Server.Boards;

import com.CheckersGame.Server.States.GameState;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Class handling the russian checkers game board operations
 */
public class RussianBoard extends Board implements Cloneable {
    /**
     * RussianBoard class constructor
     */
    public RussianBoard () {
        this.size = 8;
        this.pawnRows = 3;
        this.fields = new int[this.size][this.size];

        this.whitePawns = 0;
        this.blackPawns = 0;

        this.rPrevTake = - 1;
        this.cPrevTake = - 1;

        this.state = GameState.RESTING.getStateBahaviour();
    }



    /** 
     * Clones the instance of RussianBoard
     * @return Board
     */
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



    /** 
     * Checks whether moving a pawn from the position `current` to the position `movement` is allowed
     * @param rCurr
     * @param cCurr
     * @param rMov
     * @param cMov
     * @return int
     */
    @Override
    public int checkMove (int rCurr, int cCurr, int rMov, int cMov) {
        // Check if the game has started
        if (this.state.getState() == GameState.RESTING) {
            return Board.GAME_NOT_STARED;
        }
        // Check if Curr is on the board
        if (!this.isOnBoard(rCurr, cCurr)) {
            return Board.CURR_NOT_ON_BOARD;
        }
        
        // Check if Mov is on the board
        if (!this.isOnBoard(rMov, cMov)) {
            return Board.MOV_NOT_ON_BOARD;
        }

        // Check player
        if (!this.checkPlayer(rCurr, cCurr)) {
            return Board.INVALID_PLAYER;
        }

        // Check if there is a pawn on Mov
        if (this.fields[rMov][cMov] > 0) {
            return Board.PAWN_ON_MOV;
        }


        boolean queen = this.isQueen(rCurr, cCurr);

        if (queen) {
            int[] queenStep = this.checkQueenStep(rCurr, cCurr, rMov, cMov);
            if (queenStep == null) {
                return Board.INVALID_STEP;
            }

            if (this.checkQueenTake(rCurr, cCurr, queenStep[0], queenStep[1])) {
                // Check for further movement possibilities
                try {
                    Board bc = this.clone();
                    bc.queenTake(rCurr, cCurr, rMov, cMov);
                    if (bc.longestQueenTake(rMov, cMov).getKey() > 0) {
                        return Board.SEQUENTIAL_TAKE_ALLOWED;
                    }

                    return Board.TAKE_ALLOWED;
                }
                catch (CloneNotSupportedException e) {
                    return Board.CLONE_ERROR;
                }
            }

            // Check if there is any take possible on the board
            int lt = this.longestTake().getKey();
            if (lt > 0) {
                return Board.FORCED_TAKE_ERROR; // Not taking an enemy pawn when it's possible
            }
            if (lt < 0) {
                return Board.CLONE_ERROR; // Clone error
            }

            return Board.MOVE_ALLOWED;
        }


        int[] pawnStep = this.checkPawnStep(rCurr, cCurr, rMov, cMov);
        if (pawnStep == null) {
            return Board.INVALID_STEP;
        }
        
        if (this.checkPawnTake(rCurr, cCurr, pawnStep[0], pawnStep[1])) {
            try {
                // Check for further movement possibilities
                Board bc = this.clone();
                bc.pawnTake(rCurr, cCurr, rMov, cMov);
                if (bc.longestPawnTake(rMov, cMov).getKey() > 0) {
                    return Board.SEQUENTIAL_TAKE_ALLOWED;
                }

                return Board.TAKE_ALLOWED;
            }
            catch (CloneNotSupportedException e) {
                return Board.CLONE_ERROR;
            }
        }

        // Check if there is any take possible on the board
        int lt = this.longestTake().getKey();
        if (lt > 0) {
            return Board.FORCED_TAKE_ERROR; // Not taking an enemy pawn when it's possible
        }
        if (lt < 0) {
            return Board.CLONE_ERROR; // Clone error
        }

        return Board.MOVE_ALLOWED;
    }
}
