package com.CheckersGame.Server.States;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Enum containing the game states
 */
public enum GameState {
    /**
     * RESTING state (no player's turn)
     * @see getStateBehaviour
     */
    RESTING ("resting") {
        /**
         * Returns RESTING state's behaviour
         * @return RestingState
         */
        public GameStateBahaviour getStateBahaviour () {
            return new RestingState();
        }
    },



    /**
     * WHITE player's turn (state)
     * @see getStateBehaviour
     */
    WHITE ("white") {
        /**
         * Returns WHITE state's behaviour
         * @return WhiteState
         */
        public GameStateBahaviour getStateBahaviour () {
            return new WhiteState();
        }
    },



    /**
     * BLACK player's turn (state)
     * @see getStateBehaviour
     */
    BLACK ("black") {
        /**
         * Returns BLACK state's behaviour
         * @return BlackState
         */
        public GameStateBahaviour getStateBahaviour () {
            return new BlackState();
        }
    };



    private final String name;

    private GameState (String name) {
        this.name = name;
    }

    public String getName () {
        return this.name;
    }



    /**
     * Default getStateBehaviour method
     * @return null
     */
    public GameStateBahaviour getStateBahaviour () {
        return null;
    }
}
