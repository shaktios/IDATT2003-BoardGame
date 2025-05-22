package edu.ntnu.boardgame.constructors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Board} class.
 */
public class BoardTest {

    /**
     * Tests that the board initializes the correct number of tiles.
     */
    @Test
    void testBoardInitialization() {
        Board board = new Board(10, 10);
        assertEquals(100, board.getSize(), "Board size should be 100 for a 10x10 board.");
    }

    /**
     * Tests that a valid tile can be retrieved by position.
     */
    @Test
    void testGetTileValidPosition() {
        Board board = new Board(10, 10);
        Tile tile = board.getTile(5);
        assertNotNull(tile, "Tile should not be null.");
        assertEquals(5, tile.getPosition(), "Tile position is incorrect.");
    }

    /**
     * Tests that retrieving a tile with an invalid position throws an
     * exception.
     */
    @Test
    void testGetTileInvalidPosition() {
        Board board = new Board(10, 10);
        assertThrows(IllegalArgumentException.class, () -> board.getTile(0), "Should throw for position 0.");
        assertThrows(IllegalArgumentException.class, () -> board.getTile(101), "Should throw for position out of bounds.");
    }

    /**
     * Tests that creating a board with invalid dimensions throws an exception.
     */
    @Test
    void testInvalidBoardSize() {
        assertThrows(IllegalArgumentException.class, () -> new Board(0, 0), "Should throw for zero rows and columns.");
        assertThrows(IllegalArgumentException.class, () -> new Board(-5, -4), "Should throw for negative dimensions.");
    }

    /**
     * Tests that rows and columns return the correct values.
     */
    @Test
    void testGetRowsAndColumns() {
        Board board = new Board(7, 8);
        assertEquals(7, board.getRows(), "Number of rows should be 7.");
        assertEquals(8, board.getColumns(), "Number of columns should be 8.");
    }

    /**
     * Tests that one tile can be linked to another using setNextTile().
     */
    @Test
    void testSetNextTileLinking() {
        Board board = new Board(10, 10);
        board.setNextTile(5, 25);
        Tile next = board.getTile(5).getNextTile();
        assertNotNull(next, "Next tile should be set.");
        assertEquals(25, next.getPosition(), "Next tile should point to tile 25.");
    }

    /**
     * Tests that setNextTile throws an exception for invalid positions.
     */
    @Test
    void testSetNextTileInvalid() {
        Board board = new Board(10, 10);
        assertThrows(IllegalArgumentException.class, () -> board.setNextTile(0, 5), "Should throw for from = 0.");
        assertThrows(IllegalArgumentException.class, () -> board.setNextTile(5, 101), "Should throw for to > size.");
    }
}
