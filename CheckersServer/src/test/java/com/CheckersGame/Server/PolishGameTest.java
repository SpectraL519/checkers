package com.CheckersGame.Server;

import com.CheckersGame.Server.States.GameState;
import com.CheckersGame.Server.Versions.PolishVersion;
import com.CheckersGame.Server.Boards.PolishBoard;
import com.CheckersGame.Server.GameThreadHandlers.Game;
import com.CheckersGame.Server.GameThreadHandlers.MultiPlayerGame;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;





/**
 * Checks the correctnes of operations performed on the polish game board
 */
public class PolishGameTest {
    @Test
    public void gameFunctionalityTest () {
        Game game = new MultiPlayerGame();

        // End game
        game.newGame("polish");
        game.end();
        assertThat(game.getVersion(), instanceOf(PolishVersion.class));
        assertTrue(game.getBoard() == null);
        assertEquals(game.movePawn(6, 1, 5, 2), -10);

        // Check game version and initial states
        game.newGame("polish");
        assertThat(game.getVersion(), instanceOf(PolishVersion.class));
        assertThat(game.getBoard(), instanceOf(PolishBoard.class));
        assertEquals(game.getState(), GameState.WHITE);



        // Trying to move a black pawn first
        assertEquals(game.movePawn(3, 0, 4, 1), -4);

        // Incorrect player (none)
        assertEquals(game.movePawn(6, 0, 5, 2), -4);
        
        // Trying to move a white pawn incorrectly
        // Curr not on board
        assertEquals(game.movePawn(-6, 1, 5, 2), -2);
        assertEquals(game.movePawn(6, -1, 5, 2), -2);

        // Mov not on board
        assertEquals(game.movePawn(6, 1, -5, 2), -3);
        assertEquals(game.movePawn(6, 1, 5, -2), -3);

        // Pawn on Mov
        assertEquals(game.movePawn(6, 1, 3, 4), -5);

        // Incorrect step
        assertEquals(game.movePawn(6, 1, 5, 1), -6); // Not diagonal movement
        assertEquals(game.movePawn(6, 1, 4, 3), -6); // Step = 2 without taking an enemy pawn

        // Simulating a gameplay
        assertEquals(game.movePawn(6, 9, 5, 8), 1); // white

        // Trying to move a white pawn again
        assertEquals(game.movePawn(5, 2, 4, 3), -4);

        assertEquals(game.movePawn(3, 6, 4, 5), 1); // black

        assertEquals(game.movePawn(6, 1, 5, 0), 1); // white

        // Moving the black pawn backwards
        assertEquals(game.movePawn(4, 5, 3, 6), -6);
        
        assertEquals(game.movePawn(2, 7, 3, 6), 1); // black

        // Moving the white pawn backwards
        assertEquals(game.movePawn(5, 0, 6, 1), -6);

        assertEquals(game.movePawn(6, 5, 5, 6), 1); // white

        assertEquals(game.movePawn(4, 5, 5, 4), 1); // black

        // White taking 3 black pawns
        assertEquals(game.movePawn(6, 3, 4, 5), 3);
        assertEquals(game.movePawn(4, 5, 2, 7), 3);
        assertEquals(game.movePawn(2, 7, 4, 9), 2);



        // Check game restart function
        System.out.println("\n\nRestarting the game...");
        game.restart();
        assertEquals(game.getState(), GameState.WHITE);

        // Mocking an engame situation
        System.out.println("\n\nMocking an end situation (white wins)...");
        game.mockEndgame("white");
        assertEquals(game.movePawn(5, 4, 3, 6), 10);

        System.out.println();
        game.newGame("polish");
        System.out.println("Mocking an end situation (black wins)...");
        game.mockEndgame("black");
        assertEquals(game.movePawn(4, 5, 6, 3), 20);



        // Mocking a queen engamge situation
        System.out.println();
        game.newGame("polish");
        System.out.println("Mocking a queen end situation (white wins)...");
        game.mockQueenEndgame("white");
        assertEquals(game.movePawn(4, 5, 2, 7), 3);
        assertEquals(game.movePawn(2, 7, 0, 5), 3);
        assertEquals(game.movePawn(0, 5, 4, 1), 3);
        assertEquals(game.movePawn(4, 1, 7, 4), 10); // white wins

        // Mocking a queen engamge situation
        System.out.println();
        game.newGame("polish");
        System.out.println("Mocking a queen end situation (black wins)...");
        game.mockQueenEndgame("black");
        assertEquals(game.movePawn(3, 2, 5, 0), 3);
        assertEquals(game.movePawn(5, 0, 7, 2), 3);
        assertEquals(game.movePawn(7, 2, 3, 6), 3);
        assertEquals(game.movePawn(3, 6, 0, 3), 20); // black wins



        // Mocking a pawn to queen situation
        System.out.println();
        game.newGame("polish");
        System.out.println("Mocking a pawn to queen situation (white)...");
        game.mockPawnToQueen("white");
        assertEquals(game.movePawn(1, 8, 0, 7), 1);
        assertEquals(game.movePawn(1, 6, 2, 5), 1);
        assertEquals(game.movePawn(0, 7, 4, 3), 10); // white wins
    
        // Mocking a pawn to queen situation
        System.out.println();
        game.newGame("polish");
        System.out.println("Mocking a pawn to queen situation (black)...");
        game.mockPawnToQueen("black");
        assertEquals(game.movePawn(8, 1, 9, 2), 1);
        assertEquals(game.movePawn(8, 3, 7, 4), 1);
        assertEquals(game.movePawn(9, 2, 5, 6), 20); // white wins
    }
}
