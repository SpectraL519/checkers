package com.PWr.app.Server.States;





public class BlackState implements GameStateBahaviour {
    @Override
    public GameState getState () {
        return GameState.BLACK;
    }



    @Override
    public GameStateBahaviour startGame () {
        // The game has already started
        return this;
    }



    @Override
    public GameStateBahaviour switchPlayer () {
        // Switch player to black
        return GameState.WHITE.getStateBahaviour();
    }



    @Override
    public GameStateBahaviour endGame () {
        // Switch to resting state
        return GameState.RESTING.getStateBahaviour();
    }
}
