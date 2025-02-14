package edu.ntnu.boardgame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TileTest {
    
    @Test
    void testTilePosition(){
        Tile tile = new Tile(4); 
        assertEquals(4,tile.getPosition(), "Tile posisjonen er feil"); 
    }

}
