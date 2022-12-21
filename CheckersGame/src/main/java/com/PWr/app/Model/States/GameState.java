package com.PWr.app.Model.States;





public enum GameState {
    RESTING {
        public GameStateBahaviour getStateBahaviour () {
            return new RestingState();
        }
    },



    WHITE {
        public GameStateBahaviour getStateBahaviour () {
            return new WhiteState();
        }
    },



    BLACK {
        public GameStateBahaviour getStateBahaviour () {
            return new BlackState();
        }
    };



    // Default
    public GameStateBahaviour getStateBahaviour () {
        return null;
    }
}
