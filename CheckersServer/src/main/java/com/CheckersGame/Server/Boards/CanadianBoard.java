package com.CheckersGame.Server.Boards;

import com.CheckersGame.Server.States.GameState;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Class handling the canadian checkers game board operations
 */
public class CanadianBoard extends Board implements Cloneable {
    /**
     * CandaianBoard class constructor
     */
    public CanadianBoard () {
        this.size = 12;
        this.pawnRows = 5;
        this.fields = new int[this.size][this.size];

        this.whitePawns = 0;
        this.blackPawns = 0;

        this.rPrevTake = - 1;
        this.cPrevTake = - 1;

        this.state = GameState.RESTING.getStateBahaviour();
    }



    /** 
     * Clones the instance of CanadianBoard
     * @return Board
     */
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
        int lt = this.longestTake().getKey();

        if (queen) {
            int[] queenStep = this.checkQueenStep(rCurr, cCurr, rMov, cMov);
            if (queenStep == null) {
                return Board.INVALID_STEP;
            }

            if (this.checkQueenTake(rCurr, cCurr, queenStep[0], queenStep[1])) {
                try {
                    // Check for the optimal take
                    if (this.longestQueenTake(rCurr, cCurr).getKey() < lt) {
                        return Board.NOT_OPTIMAL_TAKE;
                    }

                    Board bc = this.clone();
                    bc.queenTake(rCurr, cCurr, rMov, cMov);
                    int lqtMov = bc.longestQueenTake(rMov, cMov).getKey();
                    if (1 + lqtMov < lt) {
                        return Board.NOT_OPTIMAL_TAKE;
                    }

                    // Check for further movement possibilities
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
                // Check for the optimal take
                if (this.longestPawnTake(rCurr, cCurr).getKey() < lt) {
                    return Board.NOT_OPTIMAL_TAKE;
                }
                
                // Check for further movement possibilities
                Board bc = this.clone();
                bc.pawnTake(rCurr, cCurr, rMov, cMov);
                int lptMov = bc.longestPawnTake(rMov, cMov).getKey();
                if (1 + lptMov < lt) {
                    return Board.NOT_OPTIMAL_TAKE;
                }
                
                // Check for further movement possibilities
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
        if (lt > 0) {
            return Board.FORCED_TAKE_ERROR; // Not taking an enemy pawn when it's possible
        }
        if (lt < 0) {
            return Board.CLONE_ERROR; // Clone error
        }

        return Board.MOVE_ALLOWED;
    }
}
