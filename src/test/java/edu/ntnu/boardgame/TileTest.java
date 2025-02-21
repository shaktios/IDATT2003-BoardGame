package edu.ntnu.boardgame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TileTest {
    
    @Test
    void testTilePosition(){
        Tile tile = new Tile(4); 
        assertEquals(4,tile.getPosition(), "Tile posisjonen er feil"); 
    }

    @Test
    void testInvalidTileCreation() {
        assertThrows(IllegalArgumentException.class, () -> new Tile(0), "Bør kaste exception for posisjon 0.");
        assertThrows(IllegalArgumentException.class, () -> new Tile(-2), "Bør kaste exception for negativ posisjon.");
    }

}
