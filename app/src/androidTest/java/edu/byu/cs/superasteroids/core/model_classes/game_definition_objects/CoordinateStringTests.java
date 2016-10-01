package edu.byu.cs.superasteroids.core.model_classes.game_definition_objects;

import android.test.AndroidTestCase;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.CoordinateString;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Marshall Garey
 * JUnit tests for the CoordinateString class
 */
public class CoordinateStringTests extends AndroidTestCase {

    public void setXYTest() {
        int x = 123;
        int y = 456;
        String position = "123,456";
        CoordinateString pos = new CoordinateString(position);
        assertEquals(pos.getxPos(), x);
        assertEquals(pos.getyPos(), y);
        x = 1;
        y = 240;
        position = "1,240";
        pos = new CoordinateString(position);
        assertEquals(pos.getxPos(), x);
        assertEquals(pos.getyPos(), y);
    }
}
