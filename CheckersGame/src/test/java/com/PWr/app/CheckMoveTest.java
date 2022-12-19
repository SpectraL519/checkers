package com.PWr.app;

import com.PWr.app.Model.GameModel;
import com.PWr.app.Model.Versions.*;
import com.PWr.app.Model.States.GameState;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.instanceOf;





public class CheckMoveTest {
    @Test
    public void shouldAnswerWithTrue () {
        GameModel model = new GameModel();

        // End game
        model.setVersion("Russian");
        model.endGame();
        assertTrue(model.getVersion() == null);

        // Check game version and initial states
        model.setVersion("Russian");
        assertThat(model.getVersion(), instanceOf(RussianVersion.class));
        assertEquals(model.getState(), GameState.RESTING);
        assertEquals(model.movePawn(5, 6, 4, 7), -1);
        model.initBoard();
        assertEquals(model.getState(), GameState.WHITE);
        model.displayBoard();



        // Trying to move a black pawn first
        assertEquals(model.movePawn(2, 1, 3, 0), -4);

        // Trying to move a white pawn incorrectly
        // Curr not on board
        assertEquals(model.movePawn(-5, 2, 4, 3), -2);
        assertEquals(model.movePawn(5, -2, 4, 3), -2);

        // Mov not on board
        assertEquals(model.movePawn(5, 2, -4, 3), -3);
        assertEquals(model.movePawn(5, 2, 4, -3), -3);

        // Incorrect player
        assertEquals(model.movePawn(5, 1, 4, 3), -4);

        // Pawn on Mov
        assertEquals(model.movePawn(5, 2, 2, 3), -5);

        // Incorrect step
        assertEquals(model.movePawn(5, 2, 4, 2), -6); // Not diagonal movement
        assertEquals(model.movePawn(5, 2, 3, 4), -6); // Step = 2 without taking an enemy pawn

        // Simulating a gameplay
        // Moving a white pawn
        assertEquals(model.movePawn(5, 6, 4, 7), 1);

        // Trying to move a white pawn again
        assertEquals(model.movePawn(4, 3, 3, 4), -4);

        assertEquals(model.movePawn(2, 5, 3, 4), 1); // black

        assertEquals(model.movePawn(5, 0, 4, 1), 1); // white

        // Moving the black pawn backwards
        assertEquals(model.movePawn(3, 4, 2, 5), -6);
        
        assertEquals(model.movePawn(1, 6, 2, 5), 1); // black

        // Moving the white pawn backwards
        assertEquals(model.movePawn(4, 1, 5, 0), -6);

        assertEquals(model.movePawn(6, 5, 5, 6), 1); // white

        assertEquals(model.movePawn(3, 4, 4, 3), 1); // black

        // White taking 2 black pawns
        assertEquals(model.movePawn(5, 2, 3, 4), 2);
        assertEquals(model.movePawn(3, 4, 1, 6), 1);



        // Check game restart function
        System.out.printf("\n\nRestarting the game...\n");
        model.restartGame();
        assertEquals(model.getState(), GameState.WHITE);
        model.displayBoard();

        // Mocking an engame situation
        System.out.printf("\n\nMocking an endgame situation (white wins)...\n");
        model.mockEndgame("White");
        model.displayBoard();
        assertEquals(model.movePawn(4, 3, 2, 5), 10);

        model.setVersion("Russian");
        System.out.printf("\n\nMocking an endgame situation (black wins)...\n");
        model.mockEndgame("Black");
        model.displayBoard();
        assertEquals(model.movePawn(3, 4, 5, 2), 20);
    }
}
