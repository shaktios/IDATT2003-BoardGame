package edu.ntnu.boardgame.actions.puzzleactions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.actions.tileactions.TileAction;
import edu.ntnu.boardgame.controllers.ChessPuzzleViewController;

public class ChessPuzzleAction implements TileAction {

    @Override
    public void execute(Player player, Board board) {
        ChessPuzzleViewController viewController = new ChessPuzzleViewController(player, board);
        viewController.startPuzzle();
    }

    @Override
    public int getDestination() {
        return -1; // ChessPuzzleAction har ingen destinasjon
    }
}