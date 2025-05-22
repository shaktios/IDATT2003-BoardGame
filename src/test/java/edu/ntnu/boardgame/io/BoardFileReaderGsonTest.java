package edu.ntnu.boardgame.io;

import java.io.FileReader;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ntnu.boardgame.constructors.Board;

/**
 * Unit tests for {@link BoardFileReaderGson} using mocked FileReader and
 * JsonParser. Ensures board data can be parsed from JSON without real file
 * access.
 */
public class BoardFileReaderGsonTest {

    /**
     * Tests that {@link BoardFileReaderGson#readBoard(Path)} returns a valid
     * Board object when given mocked JSON content with rows and columns.
     *
     * @throws Exception if mocking fails or reader logic throws unexpectedly
     */
    @Test
    void testReadBoardWithMockedJson() throws Exception {
        Path dummyPath = Path.of("dummy.json");

        // Create a fake JSON structure
        JsonObject root = new JsonObject();
        root.addProperty("rows", 2);
        root.addProperty("columns", 2);

        // Convert to JsonElement as expected by JsonParser
        JsonElement element = JsonParser.parseString(root.toString());

        try (
            MockedConstruction<FileReader> mockedReader = Mockito.mockConstruction(FileReader.class); MockedStatic<JsonParser> parserMock = Mockito.mockStatic(JsonParser.class)) {
            // Mock JsonParser.parseReader to return our fake JSON structure
            parserMock.when(() -> JsonParser.parseReader(Mockito.any(FileReader.class)))
                    .thenReturn(element);

            BoardFileReaderGson reader = new BoardFileReaderGson();
            Board board = reader.readBoard(dummyPath);

            assertNotNull(board, "Board should not be null");
            assertEquals(4, board.getSize(), "Board should contain 2×2 = 4 tiles");
            assertEquals(2, board.getRows(), "Board rows should be 2");
            assertEquals(2, board.getColumns(), "Board columns should be 2");
        }
    }
}
