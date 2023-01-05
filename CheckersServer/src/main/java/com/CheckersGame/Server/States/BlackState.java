package com.CheckersGame.Server.States;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Class handling the BLACK player's turn (state'
 */
public class BlackState implements GameStateBahaviour {
    
    /** 
     * @return GameState.BLACK
     */
    @Override
    public GameState getState () {
        return GameState.BLACK;
    }



    
    /** 
     * @return BlackState (this)
     */
    @Override
    public GameStateBahaviour startGame () {
        // The game has already started
        return this;
    }



    
    /** 
     * @return WhiteState instance
     */
    @Override
    public GameStateBahaviour switchPlayer () {
        // Switch player to black
        return GameState.WHITE.getStateBahaviour();
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
