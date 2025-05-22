package edu.ntnu.boardgame.constructors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Tile} class.
 */
public class TileTest {
    
    /**
     * Tests that the tile position is correctly set.
     */
    @Test
    void testTilePosition(){
        Tile tile = new Tile(4);
        assertEquals(4,tile.getPosition(), "Tile posisjonen er feil"); 
    }

    /**
     * Tests that creating a tile with zero or negative position throws
     * exception.
     */
    @Test
    void testInvalidTileCreation() {
        assertThrows(IllegalArgumentException.class, () -> new Tile(0), "Bør kaste exception for posisjon 0.");
        assertThrows(IllegalArgumentException.class, () -> new Tile(-2), "Bør kaste exception for negativ posisjon.");
    }
    /**
     * Tests setting and getting X and Y coordinates.
     */
    @Test
    void testSetAndGetCoordinates() {
        Tile tile = new Tile(1);
        tile.setX(5);
        tile.setY(7);
        assertEquals(5, tile.getX(), "X coordinate incorrect.");
        assertEquals(7, tile.getY(), "Y coordinate incorrect.");
    }

    /**
     * Tests setting and getting the next tile.
     */
    @Test
    void testNextTileLinking() {
        Tile tile1 = new Tile(1);
        Tile tile2 = new Tile(2);
        tile1.setNextTile(tile2);
        assertEquals(tile2, tile1.getNextTile(), "Next tile was not set correctly.");
    }


    /**
     * Tests that executeAction does not crash when action is null.
     */
    @Test
    void testExecuteActionWithNull() {
        Tile tile = new Tile(1);
        // Should not throw even if no action is set
        assertDoesNotThrow(() -> tile.executeAction(null, null));
    }


/**
 * Unit tests for token handling in the Tile class.
     */
    @Test
    void testSetAndGetToken() {
        Tile tile = new Tile(1);

        // Initially, token should be null
        assertNull(tile.getToken(), "Token should initially be null");

        // Set token to "X" and check
        tile.setToken("X");
        assertEquals("X", tile.getToken(), "Token should be 'X'");

        // Change token to "O" and check
        tile.setToken("O");
        assertEquals("O", tile.getToken(), "Token should be 'O'");

        // Reset token to null and check
        tile.setToken(null);
        assertNull(tile.getToken(), "Token should be null again");
    }


}
