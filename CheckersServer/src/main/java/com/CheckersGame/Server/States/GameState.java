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
    RESTING {
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
    WHITE {
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
    BLACK {
        /**
         * Returns BLACK state's behaviour
         * @return BlackState
         */
        public GameStateBahaviour getStateBahaviour () {
            return new BlackState();
        }
    };



    /**
     * Default getStateBehaviour method
     * @return null
     */
    public GameStateBahaviour getStateBahaviour () {
        return null;
    }
}
