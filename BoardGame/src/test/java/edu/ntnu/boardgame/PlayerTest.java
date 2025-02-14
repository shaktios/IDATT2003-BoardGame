package edu.ntnu.boardgame;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {
    @Test
    void testPlayerInitialization(){
        //Tile startTile = new Tile(1); 
        Player player = new Player("Abdi"/* ,startTile */);

        assertEquals("Abdi", player.getName(), "Navnet på spilleren er feil");
        //assertEquals(startTile, player.getCurrentTile(), "Startfeltet er feil");

    }

    @Test
    void testInvalidPlayerCreation() {
        assertThrows(IllegalArgumentException.class, () -> new Player(""), "Bør kaste exception for tomt navn.");
        assertThrows(IllegalArgumentException.class, () -> new Player(null), "Bør kaste exception for null navn.");
    }
    
}
