package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.view.laddergame.chesspuzzle.ChessPuzzleView;

/**
 * Controller responsible for managing the ChessPuzzleView. Only responsible for
 * showing the puzzle and handling the result.
 */
public class ChessPuzzleViewController {

    private Player player;
    private Board board;
    private Runnable onPuzzleComplete; // callback

    /**
     * Constructs a ChessPuzzleViewController.
     *
     * @param player the player
     * @param board the board
     * @param onPuzzleComplete callback to run after puzzle completion
     */
    public ChessPuzzleViewController(Player player, Board board, Runnable onPuzzleComplete) {
        this.player = player;
        this.board = board;
        this.onPuzzleComplete = onPuzzleComplete;
    }

    /**
     * Starts the chess puzzle.
     */
    public void startPuzzle() {
        ChessPuzzleView puzzleView = new ChessPuzzleView(player, board, onPuzzleComplete);
        puzzleView.show();
    }
}
