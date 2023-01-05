package com.CheckersGame.Server.States;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Game state behaviour interface
 */
public interface GameStateBahaviour {
    public GameState getState (); /** @return this */
    public GameStateBahaviour startGame (); /** @return GameState */
    public GameStateBahaviour switchPlayer (); /** @return GameState */
    public GameStateBahaviour endGame (); /** @return GameState */
}
