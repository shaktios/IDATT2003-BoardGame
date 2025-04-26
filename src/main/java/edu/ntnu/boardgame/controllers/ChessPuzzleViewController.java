package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.view.laddergame.chesspuzzle.ChessPuzzleView;

public class ChessPuzzleViewController {

    private Player player;
    private Board board;

    public ChessPuzzleViewController(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    /**
     * Starts a chesspuzzle for the active player 
     */
    public void startPuzzle() {
        ChessPuzzleView puzzleView = new ChessPuzzleView(player, board);
        puzzleView.show();
    }
}
