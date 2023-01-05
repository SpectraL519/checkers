package com.CheckersGame.Server.States;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Class handling the WHITE player's turn (state)
 */
public class WhiteState implements GameStateBahaviour {
    /** 
     * @return GameState.WHITE
     */
    @Override
    public GameState getState () {
        return GameState.WHITE;
    }



    /** 
     * @return WhiteState (this)
     */
    @Override
    public GameStateBahaviour startGame () {
        // The game has already started
        return this;
    }



    /** 
     * @return BlackState instance
     */
    @Override
    public GameStateBahaviour switchPlayer () {
        // Switch player to black
        return GameState.BLACK.getStateBahaviour();
    }



    /** 
     * @return RestingState instance
     */
    @Override
    public GameStateBahaviour endGame () {
        // Switch to resting state
        return GameState.RESTING.getStateBahaviour();
    }
}
