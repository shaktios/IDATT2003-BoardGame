package edu.ntnu.boardgame.io;

import java.io.FileWriter;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import edu.ntnu.boardgame.actions.tileactions.LadderAction;
import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;

/**
 * Tests for {@link BoardFileWriterGson} using mocking to avoid actual file
 * writing.
 */
public class BoardFileWriterGsonTest {

    /**
     * Tests that writing a simple board to file does not throw and uses
     * FileWriter.
     */
    @Test
    void testWriteBoardUsesFileWriter() throws Exception {
        Board board = new Board(2, 2);
        board.getTile(1).setX(0);
        board.getTile(1).setY(0);

        Path fakePath = Path.of("dummy.json");
        BoardFileWriterGson writer = new BoardFileWriterGson();

        try (MockedConstruction<FileWriter> mocked = Mockito.mockConstruction(FileWriter.class)) {
            assertDoesNotThrow(() -> writer.writeBoard(fakePath, board));
            assertEquals(1, mocked.constructed().size(), "FileWriter should be constructed once");
        }
    }

    /**
     * Tests that writing a boardgame with dice does not throw and uses
     * FileWriter.
     */
    @Test
    void testWriteBoardgameUsesFileWriter() throws Exception {
        Board board = new Board(2, 2);
        board.getTile(1).setAction(new LadderAction(4));

        Boardgame game = new Boardgame(board, 2, 6);
        game.addPlayer("Test", 20);

        Path fakePath = Path.of("game.json");
        BoardFileWriterGson writer = new BoardFileWriterGson();

        try (MockedConstruction<FileWriter> mocked = Mockito.mockConstruction(FileWriter.class)) {
            assertDoesNotThrow(() -> writer.writeBoardgame(fakePath, game));
            assertEquals(1, mocked.constructed().size(), "FileWriter should be constructed once");
        }
    }
}
