package edu.ntnu.boardgame;



import java.io.IOException;
import java.nio.file.Path;

import edu.ntnu.boardgame.actions.puzzleactions.ChessPuzzleAction;
import edu.ntnu.boardgame.actions.tileactions.BackAction;
import edu.ntnu.boardgame.actions.tileactions.LadderAction;
import edu.ntnu.boardgame.actions.tileactions.ResetAction;
import edu.ntnu.boardgame.actions.tileactions.SkipTurnAction;
import edu.ntnu.boardgame.actions.tileactions.TeleportRandomAction;
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
        Boardgame boardgame = new Boardgame(board, 2, 6);


       //stiger
        board.getTile(3).setAction(new LadderAction(22));    // → 22 (mørk grønn)
        board.getTile(7).setAction(new LadderAction(26));    // → 26
        board.getTile(20).setAction(new LadderAction(38));   // → 38
        board.getTile(28).setAction(new LadderAction(55));   // → 55
        board.getTile(50).setAction(new LadderAction(72));   // → 72
 

        // Slanger
        board.getTile(62).setAction(new BackAction(45));     // → 45 (rød)
        board.getTile(66).setAction(new BackAction(52));     // → 52
        board.getTile(74).setAction(new BackAction(48));     // → 48
        board.getTile(88).setAction(new BackAction(69));     // → 69

      
        board.getTile(43).setAction(new ResetAction());  
        board.getTile(67).setAction(new ResetAction());
        board.getTile(82).setAction(new ResetAction());              // → 1 (blå)
        board.getTile(13).setAction(new SkipTurnAction());   
        board.getTile(39).setAction(new SkipTurnAction());          // står over
        board.getTile(6).setAction(new TeleportRandomAction());       // tilfeldig
        board.getTile(30).setAction(new TeleportRandomAction());       // tilfeldig
        board.getTile(59).setAction(new TeleportRandomAction());  
        board.getTile(47).setAction(new ChessPuzzleAction(boardgame)); //chesspuzzle
        board.getTile(73).setAction(new ChessPuzzleAction(boardgame));  // tilfeldig



       
        return boardgame; // standard terninger
    }

    public static Boardgame createMiniGame() {
        Board board = new Board(6, 5); // 6 kolonner × 5 rader = 30 ruter
        Boardgame boardgame = new Boardgame(board, 1, 6); // 1 terning
        // Ladder (opp)
        board.getTile(3).setAction(new LadderAction(12));
        board.getTile(14).setAction(new LadderAction(25));

        // BackAction (slanger)
        board.getTile(17).setAction(new BackAction(9));
        board.getTile(29).setAction(new BackAction(18));

        // Reset
        board.getTile(21).setAction(new ResetAction());

        // Skip turn
        board.getTile(7).setAction(new SkipTurnAction());

        // Teleport
        board.getTile(10).setAction(new TeleportRandomAction());
        board.getTile(27).setAction(new TeleportRandomAction());

        // Chess puzzle
        board.getTile(5).setAction(new ChessPuzzleAction(boardgame));
        board.getTile(20).setAction(new ChessPuzzleAction(boardgame));

        return boardgame;
    }

    // 3. Les spillbrett fra JSON-fil
    public static Boardgame createGameFromFile(Path path) throws IOException, InvalidBoardFileException {
        BoardFileReader reader = new BoardFileReaderGson();
        Board board = reader.readBoard(path);
        Boardgame boardgame = new Boardgame(board, 2, 6);

        // Setter riktig Boardgame-objekt i alle ChessPuzzleActions
        for (int i = 1; i <= board.getSize(); i++) {
            if (board.getTile(i).getAction() instanceof ChessPuzzleAction action) {
                action.setBoardgame(boardgame);
            }
        }

        return boardgame;
    }
    
}
