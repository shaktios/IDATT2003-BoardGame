package edu.ntnu.boardgame.view.tictactoegame;

import edu.ntnu.boardgame.controllers.TicTacToeController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

/**
 * View for displaying the Tic Tac Toe game board and messages.
 * Can be embedded in the main scene or switched to via scene management.
 */
public class TicTacToeGameScreen {
    private final GridPane grid;
    private final Label messageLabel;
    private final Button[][] buttons = new Button[3][3];
    private final VBox layout;
    private final TicTacToeController controller;

    /**
     * Constructs the TicTacToe screen.
     *
     * @param controller the controller that handles game logic and state
     */
    public TicTacToeGameScreen(TicTacToeController controller) {
        this.controller = controller;

        messageLabel = new Label("Velkommen til Tic Tac Toe!");
        messageLabel.setStyle("-fx-font-size: 16px;");

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setStyle("-fx-padding: 20px;");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = new Button();
                btn.setPrefSize(100, 100);
                btn.setStyle("-fx-font-size: 24px;");
                int r = row;
                int c = col;

                btn.setOnAction(e -> controller.handleMove(r, c, null)); // You pass stage separately if needed

                buttons[row][col] = btn;
                grid.add(btn, col, row);
            }
        }

        Button resetBtn = new Button("Start på nytt");
        resetBtn.setOnAction(e -> controller.resetGame());

        layout = new VBox(15, messageLabel, grid, resetBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px;");
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
     *
     * @param row the row index
     * @param col the column index
     * @param text the text to display (X/O)
     */
    public void updateTile(int row, int col, String text) {
        buttons[row][col].setText(text);
    }

    /**
     * Clears the board visually.
     */
    public void clearBoard() {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                buttons[r][c].setText("");
    }
}
