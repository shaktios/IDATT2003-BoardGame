package edu.ntnu.boardgame;



import java.io.IOException;
import java.nio.file.Path;

import edu.ntnu.boardgame.actions.BackAction;
import edu.ntnu.boardgame.actions.LadderAction;
import edu.ntnu.boardgame.exceptions.InvalidBoardFileException;
import edu.ntnu.boardgame.io.BoardFileReader;
import edu.ntnu.boardgame.io.BoardFileReaderGson;


/*
    * A factory class for creating different types of board games.
    *
 */
public class BoardGameFactory {

    // 1. Klassisk brettspill med stiger og slanger
    public static Boardgame createClassicGame() {
        Board board = new Board(10,9);


        //Stiger
        board.getTile(3).setAction(new LadderAction(22));   // Tidlig stige
        board.getTile(8).setAction(new LadderAction(26));   // Tidlig stige
        board.getTile(20).setAction(new LadderAction(38));  // Midt i spillet
        board.getTile(28).setAction(new LadderAction(55));  // Midt i spillet
        board.getTile(50).setAction(new LadderAction(72));  // Litt over midten

        // Slanger
        board.getTile(62).setAction(new BackAction(45));   // Midt i spillet
        board.getTile(66).setAction(new BackAction(52));   // Midt-sent
        board.getTile(74).setAction(new BackAction(48));   // Sen strek
        board.getTile(88).setAction(new BackAction(69));   // Rett før mål
    

        return new Boardgame(board, 2, 6); // standard terninger
    }

    // 2. Kort variant for testing/demonstrasjon
    public static Boardgame createMiniGame() {
        Board board = new Board(10,2);
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
