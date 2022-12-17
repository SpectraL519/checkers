package com.PWr.app.Model.States;





public class RestingState implements GameStateBahaviour {
    @Override
    public GameState getState () {
        return GameState.RESTING;
    }



    @Override
    public GameStateBahaviour startGame () {
        // The game has already started
        return GameState.WHITE.getStateBahaviour();
    }



    @Override
    public GameStateBahaviour switchPlayer () {
        // Cannot switch player in the resting state
        return this;
    }



    @Override
    public GameStateBahaviour endGame () {
        // Game hasn't started yet
        return this;
    }
}
