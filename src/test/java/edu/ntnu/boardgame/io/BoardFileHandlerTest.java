package edu.ntnu.boardgame.io;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import edu.ntnu.boardgame.Board;



public class BoardFileHandlerTest {

    @Test
    void testWriteAndReadBoard() throws Exception {
        // 1. Lager et Board-objekt
        Board originalBoard = new Board(10);

        // 2. Definer sti til midlertidig fil
        Path path = Path.of("src/test/resources/temp_board.json");

        // 3. Skriver til JSON
        BoardFileWriter writer = new BoardFileWriterGson();
        writer.writeBoard(path, originalBoard);

        // 4. Les fra JSON
        BoardFileReader reader = new BoardFileReaderGson();
        Board loadedBoard = reader.readBoard(path);

        // 5. Verifiser at innholdet stemmer
        assertNotNull(loadedBoard);
        assertEquals(originalBoard.getSize(), loadedBoard.getSize());
    }
    
}
