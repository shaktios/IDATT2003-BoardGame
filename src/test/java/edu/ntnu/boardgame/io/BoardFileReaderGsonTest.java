package edu.ntnu.boardgame.io;

import edu.ntnu.boardgame.Board;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class BoardFileReaderGsonTest {
    
    @Test
    void testReadBoardFromJson() throws Exception {
        Path path = Path.of("src/test/resources/board_test.json");

        BoardFileReader reader = new BoardFileReaderGson();
        Board board = reader.readBoard(path);

        assertNotNull(board);
        assertEquals(5, board.getSize(), "Board size should be 5");
        assertNotNull(board.getTile(1), "Tile 1 should exist");
        assertNotNull(board.getTile(5), "Tile 5 should exist");
    }
    
}
