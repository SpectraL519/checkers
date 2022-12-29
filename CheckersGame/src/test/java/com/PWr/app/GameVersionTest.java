package com.PWr.app;

import org.junit.Test;

import com.PWr.app.Server.Game;
import com.PWr.app.Server.Versions.*;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.instanceOf;






/**
 * Initial test
 */
public class GameVersionTest {
    @Test
    public void shouldAnswerWithTrue () {
        Game game = new Game();

        game.newGame("polish");
        assertThat(game.getVersion(), instanceOf(PolishVersion.class));

        game.newGame("russian");
        assertThat(game.getVersion(), instanceOf(RussianVersion.class));

        game.newGame("canadian");
        assertThat(game.getVersion(), instanceOf(CanadianVersion.class));
    }
}
