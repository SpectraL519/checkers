package com.PWr.app.Model.States;





public interface GameStateBahaviour {
    public GameState getState ();
    public GameStateBahaviour startGame ();
    public GameStateBahaviour switchPlayer ();
    public GameStateBahaviour endGame ();
}
