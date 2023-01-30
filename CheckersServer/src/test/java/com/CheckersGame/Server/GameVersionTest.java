package com.CheckersGame.Server;

import com.CheckersGame.Server.Boards.*;
import com.CheckersGame.Server.GameThreadHandlers.Game;
import com.CheckersGame.Server.GameThreadHandlers.MultiPlayerGame;
import com.CheckersGame.Server.Versions.*;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;






/**
 * Tests the correctness of starting new games
 */
public class GameVersionTest {
    @Test
    public void versionTest () {
        Game game = new MultiPlayerGame();

        game.newGame("polish");
        assertThat(game.getVersion(), instanceOf(PolishVersion.class));
        assertThat(game.getBoard(), instanceOf(PolishBoard.class));

        game.newGame("russian");
        assertThat(game.getVersion(), instanceOf(RussianVersion.class));
        assertThat(game.getBoard(), instanceOf(RussianBoard.class));

        game.newGame("canadian");
        assertThat(game.getVersion(), instanceOf(CanadianVersion.class));
        assertThat(game.getBoard(), instanceOf(CanadianBoard.class));
    }
}
