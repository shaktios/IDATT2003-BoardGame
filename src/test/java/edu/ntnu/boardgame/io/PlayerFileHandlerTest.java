package edu.ntnu.boardgame.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;

/**
 * Unit tests for {@link PlayerFileHandler} using mocked I/O.
 */
public class PlayerFileHandlerTest {

    /**
     * Tests that writeToCSV creates and uses a BufferedWriter without error.
     */
    @Test
    void testWriteToCSV() throws Exception {
        Player player = new Player("Abdi", new Tile(1), 22);
        player.setToken("X");

        List<Player> players = Arrays.asList(player);
        File dummyFile = new File("dummy.csv");

        try (MockedConstruction<FileWriter> mockedWriter = mockConstruction(FileWriter.class); MockedConstruction<BufferedWriter> mockedBuffered = mockConstruction(BufferedWriter.class)) {

            assertDoesNotThrow(() -> PlayerFileHandler.writeToCSV(dummyFile, players));

            // Bekreft at BufferedWriter ble brukt
            BufferedWriter writer = mockedBuffered.constructed().get(0);
            verify(writer).write("Abdi,22,X");
            verify(writer).newLine();
        }
    }

    /**
     * Tests that readFromCSV reads player data from mocked BufferedReader.
     */
    @Test
    void testReadFromCSV() throws Exception {
        String csvLine = "Layla,20,O";

        try (MockedConstruction<FileReader> mockedReader = mockConstruction(FileReader.class); MockedConstruction<BufferedReader> mockedBuffered = mockConstruction(BufferedReader.class,
                (mock, context) -> when(mock.readLine())
                        .thenReturn(csvLine)
                        .thenReturn(null))) {

            File dummyFile = new File("fake.csv");
            List<Player> players = PlayerFileHandler.readFromCSV(dummyFile);

            assertEquals(1, players.size());
            assertEquals("Layla", players.get(0).getName());
            assertEquals(20, players.get(0).getAge());
            assertEquals("O", players.get(0).getToken());
        }
    }

    /**
     * Tests that writeToJSON runs without error and uses FileWriter.
     */
    @Test
    void testWriteToJSON() throws Exception {
        Player player = new Player("Fatima", new Tile(1), 25);
        player.setToken("Z");

        List<Player> players = Arrays.asList(player);
        File dummyFile = new File("dummy.json");

        try (MockedConstruction<FileWriter> mocked = mockConstruction(FileWriter.class)) {
            assertDoesNotThrow(() -> PlayerFileHandler.writeToJSON(dummyFile, players));
        }
    }

}
