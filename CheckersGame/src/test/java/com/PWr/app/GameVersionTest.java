package com.PWr.app;

import com.PWr.app.Model.GameModel;
import com.PWr.app.Model.Versions.*;

import org.junit.Test;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.instanceOf;






/**
 * Initial test
 */
public class GameVersionTest {
    @Test
    public void shouldAnswerWithTrue () {
        GameModel model = new GameModel();

        model.setVersion("Polish");
        assertThat(model.getVersion(), instanceOf(PolishVersion.class));

        model.setVersion("Russian");
        assertThat(model.getVersion(), instanceOf(RussianVersion.class));

        model.setVersion("Canadian");
        assertThat(model.getVersion(), instanceOf(CanadianVersion.class));
    }
}
