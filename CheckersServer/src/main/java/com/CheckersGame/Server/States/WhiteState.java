package com.CheckersGame.Server.States;





public class WhiteState implements GameStateBahaviour {
    @Override
    public GameState getState () {
        return GameState.WHITE;
    }



    @Override
    public GameStateBahaviour startGame () {
        // The game has already started
        return this;
    }



    @Override
    public GameStateBahaviour switchPlayer () {
        // Switch player to black
        return GameState.BLACK.getStateBahaviour();
    }



    @Override
    public GameStateBahaviour endGame () {
        // Switch to resting state
        return GameState.RESTING.getStateBahaviour();
    }
}
