package com.CheckersGame.Server.States;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Class handling the game RESTING state (no player's turn)
 */
public class RestingState implements GameStateBahaviour {
    /** 
     * @return GameState.RESTING
     */
    @Override
    public GameState getState () {
        return GameState.RESTING;
    }



    /** 
     * @return WhiteState
     */
    @Override
    public GameStateBahaviour startGame () {
        // The game has already started
        return GameState.WHITE.getStateBahaviour();
    }



    /** 
     * @return WhiteState (this)
     */
    @Override
    public GameStateBahaviour switchPlayer () {
        // Cannot switch player in the resting state
        return this;
    }



    /** 
     * @return RestingState (this)
     */
    @Override
    public GameStateBahaviour endGame () {
        // Game hasn't started yet
        return this;
    }
}
