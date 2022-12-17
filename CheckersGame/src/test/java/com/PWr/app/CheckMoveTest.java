package com.PWr.app;

import com.PWr.app.Model.*;
import com.PWr.app.Model.States.GameState;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.instanceOf;





public class CheckMoveTest {
    @Test
    public void shouldAnswerWithTrue () {
        GameModel model = new GameModel();

        model.setVersion("Russian");
        assertThat(model.getVersion(), instanceOf(RussianVersion.class));
        assertEquals(model.getState(), GameState.RESTING);
        model.initBoard();
        assertEquals(model.getState(), GameState.WHITE);
        model.displayBoard();

        // Trying to move a black pawn first
        assertEquals(model.movePawn(2, 1, 3, 0), -3);



        // Trying to move a white pawn incorrectly
        // Curr not on board
        assertEquals(model.movePawn(-5, 2, 4, 3), -1);
        assertEquals(model.movePawn(5, -2, 4, 3), -1);

        // Mov not on board
        assertEquals(model.movePawn(5, 2, -4, 3), -2);
        assertEquals(model.movePawn(5, 2, 4, -3), -2);

        // Incorrect player
        assertEquals(model.movePawn(5, 1, 4, 3), -3);

        // Pawn on Mov
        assertEquals(model.movePawn(5, 2, 2, 3), -4);

        // Incorrect step ()
        assertEquals(model.movePawn(5, 2, 4, 2), -5); // Not diagonal movement
        assertEquals(model.movePawn(5, 2, 3, 4), -5); // Step = 2 without taking an enemy pawn



        // Moving a white pawn
        assertEquals(model.movePawn(5, 6, 4, 7), 1);
        model.displayBoard();

        // Trying to move a white pawn again
        assertEquals(model.movePawn(4, 3, 3, 4), -3);



        // Simulating a gameplay
        assertEquals(model.movePawn(2, 5, 3, 4), 1); // black
        model.displayBoard();

        assertEquals(model.movePawn(5, 0, 4, 1), 1); // white
        model.displayBoard();

        // Moving the black pawn backwards
        assertEquals(model.movePawn(3, 4, 2, 5), -5);
        
        assertEquals(model.movePawn(1, 6, 2, 5), 1); // black
        model.displayBoard();

        // Moving the white pawn backwards
        assertEquals(model.movePawn(4, 1, 5, 0), -5);

        assertEquals(model.movePawn(6, 5, 5, 6), 1); // white
        model.displayBoard();

        assertEquals(model.movePawn(3, 4, 4, 3), 1); // black
        model.displayBoard();

        // White taking 2 black pawns
        assertEquals(model.movePawn(5, 2, 3, 4), 2);
        model.displayBoard();
        assertEquals(model.movePawn(3, 4, 1, 6), 1);
        model.displayBoard();
    }
}
