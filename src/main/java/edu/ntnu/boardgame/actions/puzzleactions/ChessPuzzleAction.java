package edu.ntnu.boardgame.actions.puzzleactions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.view.laddergame.chesspuzzle.ChessPuzzleView;

public class ChessPuzzleAction {

    private Player player;
    private Board board;

    public ChessPuzzleAction(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    public void launchPuzzle() {
        new ChessPuzzleView(player, board).show();
    }
}
