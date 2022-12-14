package com.PWr.app;

import com.PWr.app.Model.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.instanceOf;






/**
 * Initial test
 */
public class CheckMoveTest {
    @Test
    public void shouldAnswerWithTrue () {
        GameModel model = new GameModel();

        model.setVersion("Russian");
        assertThat(model.getVersion(), instanceOf(RussianVersion.class));
        model.initPawns();
        
        System.out.println();
        
        System.out.println("Move 1");
        model.displayPawns();
        assertEquals(model.movePawn(5, 2, 4, 1), 1);
        
        System.out.println("\nMove 2");
        model.displayPawns();
        assertEquals(model.movePawn(4, 1, 3, 0), 1);

        System.out.println("\nMove 3");
        model.displayPawns();
        assertEquals(model.movePawn(3, 0, 2, -1), -2);
    }
}
