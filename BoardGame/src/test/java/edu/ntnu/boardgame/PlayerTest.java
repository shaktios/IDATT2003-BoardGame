package edu.ntnu.boardgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;



public class PlayerTest {
    @Test
    void testPlayerInitialization(){
        //Tile startTile = new Tile(1); 
        Player player = new Player("Abdi"/* ,startTile */);

        assertEquals("Abdi", player.getName(), "Navnet på spilleren er feil");
        //assertEquals(startTile, player.getCurrentTile(), "Startfeltet er feil");

    }
    
}
