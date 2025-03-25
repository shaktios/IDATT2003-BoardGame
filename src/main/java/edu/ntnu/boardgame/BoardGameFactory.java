package edu.ntnu.boardgame;



import edu.ntnu.boardgame.exceptions.InvalidBoardFileException;
import edu.ntnu.boardgame.io.BoardFileReader;
import edu.ntnu.boardgame.io.BoardFileReaderGson;

import java.io.IOException;
import java.nio.file.Path;


public class BoardGameFactory {

    // 1. Klassisk brettspill med stiger og slanger
    public static Boardgame createClassicGame() {
        Board board = new Board(100);

        // Stiger
        board.setNextTile(4, 14);
        board.setNextTile(9, 31);
        board.setNextTile(21, 42);
        board.setNextTile(28, 84);
        board.setNextTile(51, 67);
        board.setNextTile(72, 91);

        // Slanger
        board.setNextTile(98, 78);
        board.setNextTile(95, 75);
        board.setNextTile(87, 24);
        board.setNextTile(62, 19);
        board.setNextTile(36, 6);

        return new Boardgame(board, 2, 6); // standard terninger
    }

    // 2. Kort variant for testing/demonstrasjon
    public static Boardgame createMiniGame() {
        Board board = new Board(20);
        board.setNextTile(3, 10);
        board.setNextTile(15, 5);

        return new Boardgame(board, 1, 6); // 1 terning
    }

    // 3. Les spillbrett fra JSON-fil
    public static Boardgame createGameFromFile(Path path) throws IOException, InvalidBoardFileException {
        BoardFileReader reader = new BoardFileReaderGson();
        Board board = reader.readBoard(path);
        return new Boardgame(board, 2, 6);
    }
    
}
