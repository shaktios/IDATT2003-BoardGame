package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.model.laddergame.chesspuzzle.ChessPuzzleModel;
import edu.ntnu.boardgame.view.laddergame.chesspuzzle.ChessPuzzleView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Controller responsible for coordinating the chess puzzle logic and view.
 */
public class ChessPuzzleViewController {

    private final Player player;
    private final Board board;
    private final Runnable onPuzzleComplete;
    private final ChessPuzzleModel model;
    private final Boardgame boardgame; 
    private ChessPuzzleView view;

    private static final int CORRECT_MOVE_FORWARD = 5;
    private static final int WRONG_MOVE_BACKWARD = 3;

    /**
     * Constructs the ChessPuzzleViewController.
     *
     * @param player the player involved
     * @param board the board being played on
     * @param onPuzzleComplete callback to execute after the puzzle finishes
     */
    public ChessPuzzleViewController(Player player, Board board, Runnable onPuzzleComplete, Boardgame boardgame) {
        this.player = player;
        this.board = board;
        this.onPuzzleComplete = onPuzzleComplete;
        this.boardgame = boardgame;
        this.model = new ChessPuzzleModel();
    }

    /**
     * Initializes and displays the puzzle view with model data.
     */
    public void startPuzzle() {
        model.selectRandomPuzzle();
        String image = model.getSelectedImage();
        String correctMove = model.getCorrectMove();
        var options = model.generateOptions();

        // Opprett View og send inn this::handleUserMove som callback
        this.view = new ChessPuzzleView(image, correctMove, options, this::handleUserMove);
        view.show();
    }

    /**
     * Handles the move selected by the user.
     *
     * @param selectedMove the move selected by the user
     */
    public void handleUserMove(String selectedMove) {
        boolean isCorrect = selectedMove.equals(model.getCorrectMove());

        if (isCorrect) {
            view.showResult("Riktig! Du går fremover " + CORRECT_MOVE_FORWARD + " felt!");
            movePlayer(CORRECT_MOVE_FORWARD);
        } else {
            view.showResult("Feil! Du går bakover " + WRONG_MOVE_BACKWARD + " felt!");
            movePlayer(-WRONG_MOVE_BACKWARD);
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e -> {
            onPuzzleComplete.run();
            view.closeStage();
        });
        pause.play();
    }

    private void movePlayer(int offset) {
        int newPosition = Math.max(1, Math.min(player.getPosition() + offset, board.getSize()));
        player.setPosition(newPosition, board);
        player.setCurrentTile(board.getTile(newPosition));
        boardgame.checkForWin(player);
    }
}
