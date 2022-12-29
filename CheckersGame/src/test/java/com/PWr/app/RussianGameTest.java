package com.PWr.app;

import org.junit.Test;

import com.PWr.app.Server.Game;
import com.PWr.app.Server.States.GameState;
import com.PWr.app.Server.Versions.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.instanceOf;





public class RussianGameTest {
    @Test
    public void shouldAnswerWithTrue () {
        Game game = new Game();

        // End game
        game.newGame("russian");
        game.endGame();
        assertTrue(game.getVersion() == null);

        // Check game version and initial states
        game.newGame("russian");
        assertThat(game.getVersion(), instanceOf(RussianVersion.class));
        assertEquals(game.getState(), GameState.RESTING);
        assertEquals(game.movePawn(5, 0, 4, 1), -1);
        game.initBoard();
        assertEquals(game.getState(), GameState.WHITE);



        // Trying to move a black pawn first
        assertEquals(game.movePawn(2, 1, 3, 0), -4);

        // Incorrect player (none)
        assertEquals(game.movePawn(5, 1, 4, 3), -4);
        
        // Trying to move a white pawn incorrectly
        // Curr not on board
        assertEquals(game.movePawn(-5, 2, 4, 3), -2);
        assertEquals(game.movePawn(5, -2, 4, 3), -2);

        // Mov not on board
        assertEquals(game.movePawn(5, 2, -4, 3), -3);
        assertEquals(game.movePawn(5, 2, 4, -3), -3);

        // Pawn on Mov
        assertEquals(game.movePawn(5, 2, 2, 3), -5);

        // Incorrect step
        assertEquals(game.movePawn(5, 2, 4, 2), -6); // Not diagonal movement
        assertEquals(game.movePawn(5, 2, 3, 4), -6); // Step = 2 without taking an enemy pawn

        // Simulating a gameplay
        assertEquals(game.movePawn(5, 6, 4, 7), 1); // white

        // Trying to move a white pawn again
        assertEquals(game.movePawn(4, 3, 3, 4), -4);

        assertEquals(game.movePawn(2, 5, 3, 4), 1); // black

        assertEquals(game.movePawn(5, 0, 4, 1), 1); // white

        // Moving the black pawn backwards
        assertEquals(game.movePawn(3, 4, 2, 5), -6);
        
        assertEquals(game.movePawn(1, 6, 2, 5), 1); // black

        // Moving the white pawn backwards
        assertEquals(game.movePawn(4, 1, 5, 0), -6);

        assertEquals(game.movePawn(6, 5, 5, 6), 1); // white

        assertEquals(game.movePawn(3, 4, 4, 3), 1); // black

        // White taking 2 black pawns
        assertEquals(game.movePawn(5, 2, 3, 4), 3);
        assertEquals(game.movePawn(3, 4, 1, 6), 2);



        // Check game restart function
        System.out.println("\n\nRestarting the game...");
        game.restartGame();
        assertEquals(game.getState(), GameState.WHITE);

        // Mocking an engame situation
        System.out.println("\n\nMocking an endgame situation (white wins)...");
        game.mockEndgame("white");
        assertEquals(game.movePawn(4, 3, 2, 5), 10);

        System.out.println();
        game.newGame("russian");
        System.out.println("Mocking an endgame situation (black wins)...");
        game.mockEndgame("black");
        assertEquals(game.movePawn(3, 4, 5, 2), 20);



        // Mocking a queen engamge situation
        System.out.println();
        game.newGame("russian");
        System.out.println("Mocking a queen endgame situation (white wins)...");
        game.mockQueenEndgame("white");
        assertEquals(game.longestMove(4, 5), 4);
        assertEquals(game.movePawn(4, 5, 2, 7), 3);
        assertEquals(game.movePawn(2, 7, 0, 5), 3);
        assertEquals(game.movePawn(0, 5, 4, 1), 3);
        assertEquals(game.movePawn(4, 1, 7, 4), 10); // white wins

        // Mocking a queen engamge situation
        System.out.println();
        game.newGame("russian");
        System.out.println("Mocking a queen endgame situation (black wins)...");
        game.mockQueenEndgame("black");
        assertEquals(game.longestMove(3, 2), 4);
        assertEquals(game.movePawn(3, 2, 5, 0), 3);
        assertEquals(game.movePawn(5, 0, 7, 2), 3);
        assertEquals(game.movePawn(7, 2, 3, 6), 3);
        assertEquals(game.movePawn(3, 6, 0, 3), 20); // black wins



        // Mocking a pawn to queen situation
        System.out.println();
        game.newGame("russian");
        System.out.println("Mocking a pawn to queen situation (white)...");
        game.mockPawnToQueen("white");
        assertEquals(game.movePawn(1, 6, 0, 5), 1);
        assertEquals(game.movePawn(1, 4, 2, 3), 1);
        assertEquals(game.movePawn(0, 5, 4, 1), 10); // white wins
    
        // Mocking a pawn to queen situation
        System.out.println();
        game.newGame("russian");
        System.out.println("Mocking a pawn to queen situation (black)...");
        game.mockPawnToQueen("black");
        assertEquals(game.movePawn(6, 1, 7, 2), 1);
        assertEquals(game.movePawn(6, 3, 5, 4), 1);
        assertEquals(game.movePawn(7, 2, 3, 6), 20); // white wins
    }
}
