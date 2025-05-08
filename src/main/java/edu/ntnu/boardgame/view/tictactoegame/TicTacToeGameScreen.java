package edu.ntnu.boardgame.view.tictactoegame;

import edu.ntnu.boardgame.controllers.TicTacToeController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * View for displaying the Tic Tac Toe game board and messages.
 * Can be embedded in the main scene or switched to via scene management.
 */
public class TicTacToeGameScreen {
    private final GridPane grid;
    private final Label messageLabel;
    private final Label scoreboardLabel;
    private final Button[][] buttons = new Button[3][3];
    private final VBox layout;
    private TicTacToeController controller;

    /**
     * Constructs the TicTacToe screen.
     */
    public TicTacToeGameScreen() {
        messageLabel = new Label("Welcome to Tic Tac Toe!");
        messageLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: #333;");
        messageLabel.setFont(Font.font("Arial"));

        scoreboardLabel = new Label("Scoreboard");
        scoreboardLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #444;");
        scoreboardLabel.setFont(Font.font("Arial"));

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 40px;");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = new Button();
                btn.setPrefSize(180, 180);
                btn.setFont(Font.font("Arial", 50));
                int r = row;
                int c = col;

                btn.setOnAction(e -> controller.handleMove(r, c, null));

                buttons[row][col] = btn;
                grid.add(btn, col, row);
            }
        }

        Button resetBtn = new Button("Start på nytt");
        resetBtn.setFont(Font.font("Arial", 18));
        resetBtn.setOnAction(e -> controller.resetGame());

        layout = new VBox(20, messageLabel, scoreboardLabel, grid, resetBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #faf0ff, #e5d4f4); -fx-padding: 40px;");
        VBox.setVgrow(grid, Priority.ALWAYS);
    }

    /**
     * Returns the root layout pane to be embedded in a scene.
     *
     * @return the main container VBox
     */
    public Pane getRoot() {
        return layout;
    }

    /**
     * Updates the label shown above the board.
     *
     * @param message the new message
     */
    public void updateMessage(String message) {
        messageLabel.setText(message);
    }

    /**
     * Updates the visual state of a tile.
     * Uses red for X and green for O.
     *
     * @param row the row index
     * @param col the column index
     * @param text the text to display (X/O)
     */
    public void updateTile(int row, int col, String text) {
        Button btn = buttons[row][col];
        btn.setText(text);
        if ("X".equals(text)) {
            btn.setTextFill(Color.RED);
        } else if ("O".equals(text)) {
            btn.setTextFill(Color.GREEN);
        } else {
            btn.setTextFill(Color.BLACK);
        }
    }

    /**
     * Clears the board visually.
     */
    public void clearBoard() {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++) {
                buttons[r][c].setText("");
                buttons[r][c].setTextFill(Color.BLACK);
            }
    }

    public void setController(TicTacToeController controller) {
        this.controller = controller;
    }

    public void setScoreboard(String name1, int score1, String name2, int score2) {
        scoreboardLabel.setText(name1 + ": " + score1 + "  |  " + name2 + ": " + score2);
    }
}
