package com.CheckersGame.Server;

import com.CheckersGame.Server.States.GameState;
import com.CheckersGame.Server.Versions.CanadianVersion;
import com.CheckersGame.Server.Boards.CanadianBoard;
import com.CheckersGame.Server.GameThreadHandlers.Game;
import com.CheckersGame.Server.GameThreadHandlers.MultiPlayerGame;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;





/**
 * Checks the correctnes of operations performed on the canadian game board
 */
public class CanadianGameTest {
    @Test
    public void gameFunctionalityTest () {
        Game game = new MultiPlayerGame();

        // End game
        game.newGame("canadian");
        game.end();
        assertThat(game.getVersion(), instanceOf(CanadianVersion.class));
        assertTrue(game.getBoard() == null);
        assertEquals(game.movePawn(7, 2, 6, 1), -10);

        // Check game version and initial states
        game.newGame("canadian");
        assertThat(game.getVersion(), instanceOf(CanadianVersion.class));
        assertThat(game.getBoard(), instanceOf(CanadianBoard.class));
        assertEquals(game.getState(), GameState.WHITE);



        // Trying to move a black pawn first
        assertEquals(game.movePawn(4, 1, 5, 0), -4);

        // Incorrect player (none)
        assertEquals(game.movePawn(7, 1, 6, 1), -4);
        
        // Trying to move a white pawn incorrectly
        // Curr not on board
        assertEquals(game.movePawn(-7, 2, 6, 1), -2);
        assertEquals(game.movePawn(7, -2, 6, 1), -2);

        // Mov not on board
        assertEquals(game.movePawn(7, 2, -6, 1), -3);
        assertEquals(game.movePawn(7, 2, 6, -1), -3);

        // Pawn on Mov
        assertEquals(game.movePawn(7, 2, 4, 5), -5);

        // Incorrect step
        assertEquals(game.movePawn(7, 2, 6, 2), -6); // Not diagonal movement
        assertEquals(game.movePawn(7, 2, 5, 0), -6); // Step = 2 without taking an enemy pawn

        // Simulating a gameplay
        assertEquals(game.movePawn(7, 2, 6, 1), 1); // white

        // Trying to move a white pawn again
        assertEquals(game.movePawn(6, 1, 5, 0), -4);

        assertEquals(game.movePawn(4, 7, 5, 6), 1); // black

        assertEquals(game.movePawn(6, 1, 5, 0), 1); // white

        // Moving the black pawn backwards
        assertEquals(game.movePawn(5, 6, 4, 7), -6);
        
        assertEquals(game.movePawn(3, 8, 4, 7), 1); // black

        // Moving the white pawn backwards
        assertEquals(game.movePawn(5, 0, 6, 1), -6);

        assertEquals(game.movePawn(7, 10, 6, 11), 1); // white

        assertEquals(game.movePawn(5, 6, 6, 5), 1); // black

        // White taking 3 black pawns
        assertEquals(game.movePawn(7, 4, 5, 6), 3);
        assertEquals(game.movePawn(5, 6, 3, 8), 3);
        assertEquals(game.movePawn(3, 8, 5, 10), 2);



        // Check game restart function
        System.out.println("\n\nRestarting the game...");
        game.restart();
        assertEquals(game.getState(), GameState.WHITE);

        // Mocking an engame situation
        System.out.println("\n\nMocking an endgame situation (white wins)...");
        game.mockEndgame("white");
        assertEquals(game.movePawn(6, 5, 4, 7), 10);

        System.out.println();
        game.newGame("canadian");
        System.out.println("Mocking an endgame situation (black wins)...");
        game.mockEndgame("black");
        assertEquals(game.movePawn(5, 6, 7, 4), 20);



        // Mocking a queen engamge situation
        System.out.println();
        game.restart();
        System.out.println("Mocking a queen endgame situation (white wins)...");
        game.mockQueenEndgame("white");
        assertEquals(game.movePawn(4, 5, 2, 7), 3);
        assertEquals(game.movePawn(2, 7, 0, 5), 3);
        assertEquals(game.movePawn(0, 5, 4, 1), 3);
        assertEquals(game.movePawn(4, 1, 7, 4), 10); // white wins

        // Mocking a queen engamge situation
        System.out.println();
        game.restart();
        System.out.println("Mocking a queen endgame situation (black wins)...");
        game.mockQueenEndgame("black");
        assertEquals(game.movePawn(3, 2, 5, 0), 3);
        assertEquals(game.movePawn(5, 0, 7, 2), 3);
        assertEquals(game.movePawn(7, 2, 3, 6), 3);
        assertEquals(game.movePawn(3, 6, 0, 3), 20); // black wins



        // Mocking a pawn to queen situation
        System.out.println();
        game.restart();
        System.out.println("Mocking a pawn to queen situation (white)...");
        game.mockPawnToQueen("white");
        assertEquals(game.movePawn(1, 10, 0, 9), 1);
        assertEquals(game.movePawn(1, 8, 2, 7), 1);
        assertEquals(game.movePawn(0, 9, 4, 5), 10); // white wins
    
        // Mocking a pawn to queen situation
        System.out.println();
        game.restart();
        System.out.println("Mocking a pawn to queen situation (black)...");
        game.mockPawnToQueen("black");
        assertEquals(game.movePawn(10, 1,11, 2), 1);
        assertEquals(game.movePawn(10, 3, 9, 4), 1);
        assertEquals(game.movePawn(11, 2, 7, 6), 20); // white wins
    }
}
