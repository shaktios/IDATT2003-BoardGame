package edu.ntnu.boardgame.io;

import edu.ntnu.boardgame.Player;
import edu.ntnu.boardgame.Tile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFileHandlerTest {

    @Test
    void testReadFromCSV() throws Exception {
        File testFile = new File("src/test/resources/players_test.csv");
        List<Player> players = PlayerFileHandler.readFromCSV(testFile);

        assertEquals(4, players.size(), "Skal lese 4 spillere fra CSV");

        assertEquals("Arne", players.get(0).getName());
        assertEquals("TopHat", players.get(0).getToken());

        assertEquals("Atle", players.get(3).getName());
        assertEquals("Thimble", players.get(3).getToken());
    }

    @Test
    void testWriteToCSV() throws Exception {
        File tempFile = new File("src/test/resources/temp_output.csv");

        // 1. Lager en liste med spillere
        List<Player> originalPlayers = List.of(
                new Player("Test1", new Tile(1)), //tile kan ikke være null så vi bare lager en dummytile rn
                new Player("Test2", new Tile(1)) //tile kan ikke være null så vi bare lager en dummytile rn
        );
        originalPlayers.get(0).setToken("Icon1");
        originalPlayers.get(1).setToken("Icon2");

        // 2. Skriver spillerne til en midlertidig CSV-fil
        PlayerFileHandler.writeToCSV(tempFile, originalPlayers);

        // 3. Leser spillerne tilbake fra filen
        List<Player> loadedPlayers = PlayerFileHandler.readFromCSV(tempFile);

        // 4. Sjekker at dataen ble skrevet og lest korrekt
        assertEquals(2, loadedPlayers.size());
        assertEquals("Test1", loadedPlayers.get(0).getName());
        assertEquals("Icon1", loadedPlayers.get(0).getToken());
    }
}
