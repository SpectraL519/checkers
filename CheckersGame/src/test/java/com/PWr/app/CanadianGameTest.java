package com.PWr.app;

import com.PWr.app.Model.GameModel;
import com.PWr.app.Model.Versions.*;
import com.PWr.app.Model.States.GameState;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.instanceOf;





public class CanadianGameTest {
    @Test
    public void shouldAnswerWithTrue () {
        GameModel model = new GameModel();

        // End game
        model.newGame("canadian");
        model.endGame();
        assertTrue(model.getVersion() == null);

        // Check game version and initial states
        model.newGame("canadian");
        assertThat(model.getVersion(), instanceOf(CanadianVersion.class));
        assertEquals(model.getState(), GameState.RESTING);
        assertEquals(model.movePawn(7, 2, 6, 1), -1);
        model.initBoard();
        assertEquals(model.getState(), GameState.WHITE);



        // Trying to move a black pawn first
        assertEquals(model.movePawn(4, 1, 5, 0), -4);

        // Incorrect player (none)
        assertEquals(model.movePawn(7, 1, 6, 1), -4);
        
        // Trying to move a white pawn incorrectly
        // Curr not on board
        assertEquals(model.movePawn(-7, 2, 6, 1), -2);
        assertEquals(model.movePawn(7, -2, 6, 1), -2);

        // Mov not on board
        assertEquals(model.movePawn(7, 2, -6, 1), -3);
        assertEquals(model.movePawn(7, 2, 6, -1), -3);

        // Pawn on Mov
        assertEquals(model.movePawn(7, 2, 4, 5), -5);

        // Incorrect step
        assertEquals(model.movePawn(7, 2, 6, 2), -6); // Not diagonal movement
        assertEquals(model.movePawn(7, 2, 5, 0), -6); // Step = 2 without taking an enemy pawn

        // Simulating a gameplay
        assertEquals(model.movePawn(7, 2, 6, 1), 1); // white

        // Trying to move a white pawn again
        assertEquals(model.movePawn(6, 1, 5, 0), -4);

        assertEquals(model.movePawn(4, 7, 5, 6), 1); // black

        assertEquals(model.movePawn(6, 1, 5, 0), 1); // white

        // Moving the black pawn backwards
        assertEquals(model.movePawn(5, 6, 4, 7), -6);
        
        assertEquals(model.movePawn(3, 8, 4, 7), 1); // black

        // Moving the white pawn backwards
        assertEquals(model.movePawn(5, 0, 6, 1), -6);

        assertEquals(model.movePawn(7, 10, 6, 11), 1); // white

        assertEquals(model.movePawn(5, 6, 6, 5), 1); // black

        // White taking 3 black pawns
        assertEquals(model.movePawn(7, 4, 5, 6), 3);
        assertEquals(model.movePawn(5, 6, 3, 8), 3);
        assertEquals(model.movePawn(3, 8, 5, 10), 2);



        // Check game restart function
        System.out.println("\n\nRestarting the game...");
        model.restartGame();
        assertEquals(model.getState(), GameState.WHITE);

        // Mocking an engame situation
        System.out.println("\n\nMocking an endgame situation (white wins)...");
        model.mockEndgame("white");
        assertEquals(model.movePawn(6, 5, 4, 7), 10);

        System.out.println();
        model.newGame("canadian");
        System.out.println("Mocking an endgame situation (black wins)...");
        model.mockEndgame("black");
        assertEquals(model.movePawn(5, 6, 7, 4), 20);



        // Mocking a queen engamge situation
        System.out.println();
        model.newGame("canadian");
        System.out.println("Mocking a queen endgame situation (white wins)...");
        model.mockQueenEndgame("white");
        assertEquals(model.longestMove(4, 5), 4);
        assertEquals(model.movePawn(4, 5, 2, 7), 3);
        assertEquals(model.movePawn(2, 7, 0, 5), 3);
        assertEquals(model.movePawn(0, 5, 4, 1), 3);
        assertEquals(model.movePawn(4, 1, 7, 4), 10); // white wins

        // Mocking a queen engamge situation
        System.out.println();
        model.newGame("canadian");
        System.out.println("Mocking a queen endgame situation (black wins)...");
        model.mockQueenEndgame("black");
        assertEquals(model.longestMove(3, 2), 4);
        assertEquals(model.movePawn(3, 2, 5, 0), 3);
        assertEquals(model.movePawn(5, 0, 7, 2), 3);
        assertEquals(model.movePawn(7, 2, 3, 6), 3);
        assertEquals(model.movePawn(3, 6, 0, 3), 20); // black wins



        // Mocking a pawn to queen situation
        System.out.println();
        model.newGame("canadian");
        System.out.println("Mocking a pawn to queen situation (white)...");
        model.mockPawnToQueen("white");
        assertEquals(model.movePawn(1, 10, 0, 9), 1);
        assertEquals(model.movePawn(1, 8, 2, 7), 1);
        assertEquals(model.movePawn(0, 9, 4, 5), 10); // white wins
    
        // Mocking a pawn to queen situation
        System.out.println();
        model.newGame("canadian");
        System.out.println("Mocking a pawn to queen situation (black)...");
        model.mockPawnToQueen("black");
        assertEquals(model.movePawn(10, 1,11, 2), 1);
        assertEquals(model.movePawn(10, 3, 9, 4), 1);
        assertEquals(model.movePawn(11, 2, 7, 6), 20); // white wins
    }
}
