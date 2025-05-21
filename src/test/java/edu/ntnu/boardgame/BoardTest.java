package edu.ntnu.boardgame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.boardgame.constructors.Tile;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void testBoardInitialization() {
        Board board = new Board(10, 10);
        assertEquals(10, board.getSize(), "Board-størrelsen er feil.");
    }

    @Test
    void testGetTileValidPosition() {
        Board board = new Board(10, 10);
        Tile tile = board.getTile(5);
        assertNotNull(tile, "Tile burde ikke være null.");
        assertEquals(5, tile.getPosition(), "Tile-posisjonen er feil.");
    }

    @Test
    void testGetTileInvalidPosition() {
        Board board = new Board(10, 10);
        assertThrows(IllegalArgumentException.class, () -> board.getTile(0), "Bør kaste exception for posisjon 0.");
        assertThrows(IllegalArgumentException.class, () -> board.getTile(11), "Bør kaste exception for posisjon større enn brettets størrelse.");
    }

    @Test
    void testInvalidBoardSize() {
        assertThrows(IllegalArgumentException.class, () -> new Board(0, 0), "Bør kaste exception for ugyldig brettstørrelse.");
        assertThrows(IllegalArgumentException.class, () -> new Board(-5, -4), "Bør kaste exception for negativ brettstørrelse.");
    }
    
}
