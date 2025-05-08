package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.TicTacToe;
import edu.ntnu.boardgame.view.tictactoegame.TicTacToeGameScreen;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Controller for handling Tic Tac Toe game logic and interactions between view and model.
 */
public class TicTacToeController {

    private final Player player1;
    private final Player player2;
    private final TicTacToe logic;
    private final TicTacToeGameScreen view;
    private int player1Score = 0;
    private int player2Score = 0;

    public TicTacToeController(Player player1, Player player2, TicTacToeGameScreen view) {
        this.player1 = player1;
        this.player2 = player2;
        this.logic = new TicTacToe(player1, player2);
        this.view = view;

        player1.setToken("X");
        player2.setToken("O");

        if (player1.getName() == null || player1.getName().isEmpty()) player1.setName("Player 1");
        if (player2.getName() == null || player2.getName().isEmpty()) player2.setName("Player 2");

        view.setScoreboard(player1.getName(), player1Score, player2.getName(), player2Score);
        view.updateMessage("Turn: " + logic.getCurrentPlayer().getName() + " (" + logic.getCurrentPlayer().getToken() + ")");
    }

    public void handleMove(int row, int col, javafx.stage.Stage stage) {
        if (!logic.makeMove(row, col)) {
            view.updateMessage("Not valid move");
            return;
        }

        view.updateTile(row, col, logic.getCell(row, col));

        String winner = logic.checkWinner();
        if (winner != null) {
            Player current = logic.getCurrentPlayer();
            if ("X".equals(winner)) {
                player1Score++;
            } else {
                player2Score++;
            }
            view.setScoreboard(player1.getName(), player1Score, player2.getName(), player2Score);
            view.updateMessage(current.getName() + " har vunnet!");

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> resetGame());
            pause.play();
            return;
        }

        if (logic.isBoardFull()) {
            view.updateMessage("Uavgjort! Ingen fikk tre på rad.");
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> resetGame());
            pause.play();
            return;
        }

        logic.switchTurn();
        Player next = logic.getCurrentPlayer();
        view.updateMessage("Turn: " + next.getName() + " (" + next.getToken() + ")");
    }

    public void resetGame() {
        logic.resetBoard();
        view.clearBoard();
        view.updateMessage("Turn: " + logic.getCurrentPlayer().getName() + " (" + logic.getCurrentPlayer().getToken() + ")");
    }
}
