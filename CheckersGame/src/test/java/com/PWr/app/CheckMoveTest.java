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
        model.initBoard();
        
        System.out.println("Mocking a situation");
        model.displayBoard();
        assertEquals(model.movePawn(2, 5, 3, 4), 1);
        model.displayBoard();
        assertEquals(model.movePawn(3, 4, 4, 3), 1);
        model.displayBoard();
        assertEquals(model.movePawn(1, 6, 2, 5), 1);
        model.displayBoard();
        assertEquals(model.movePawn(5, 2, 3, 4), 2);
        model.displayBoard();
        assertEquals(model.movePawn(3, 4, 1, 6), 1);
        model.displayBoard();

        System.out.printf("\nBoard after mock:\n");
        model.displayBoard();

        // Other movement tests
    }
}
