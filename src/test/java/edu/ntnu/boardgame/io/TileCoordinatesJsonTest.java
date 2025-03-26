package edu.ntnu.boardgame.io;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Tile;

public class TileCoordinatesJsonTest {
    @Test
    void testWriteAndReadCoordinates() throws Exception {

        //Lager et brett med koordinater
        Board board = new Board(3);
        board.getTile(1).setX(0);
        board.getTile(1).setY(0);
        board.getTile(2).setX(1);
        board.getTile(2).setY(0);
        board.getTile(3).setX(2);
        board.getTile(3).setY(0);

        //Skriver til JSON
        Path path = Path.of("src/test/resources/temp_board_with_coords.json");
        BoardFileWriter writer = new BoardFileWriterGson();
        writer.writeBoard(path, board);

        //Leser tilbake
        BoardFileReader reader = new BoardFileReaderGson();
        Board loaded = reader.readBoard(path);

        //Sjekk om koordinater samsvarer 
        Tile tile2 = loaded.getTile(2);
        assertEquals(1, tile2.getX());
        assertEquals(0, tile2.getY());
    }

}
