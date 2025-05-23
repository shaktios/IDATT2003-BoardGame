package edu.ntnu.boardgame.view.tictactoegame;

import java.util.HashMap;
import java.util.Map;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.controllers.MainPageController;
import edu.ntnu.boardgame.controllers.TicTacToeController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * View for displaying the Tic Tac Toe game board and messages. Can be embedded
 * in the main scene or switched to via scene management.
 */
public class TicTacToeGameScreen {
    private final GridPane grid;
    private final Label messageLabel;
    private final VBox layout;
    private final Map<Tile, Button> tileButtonMap = new HashMap<>();
    private TicTacToeController controller;
    private final Board board;

    /**
     * Constructs the Tic Tac Toe game screen.
     *
     * @param stage the JavaFX stage
     * @param board the 3x3 board used for Tic Tac Toe
     */
    public TicTacToeGameScreen(Stage stage, Board board) {
        this.board = board;

        messageLabel = new Label("Velkommen til Tic Tac Toe!");
        messageLabel.getStyleClass().add("label");

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.getStyleClass().add("grid-pane");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int pos = row * 3 + col + 1;
                Tile tile = board.getTile(pos);

                Button btn = new Button();
                btn.setPrefSize(100, 100);
                btn.getStyleClass().add("button");

                btn.setOnAction(e -> controller.handleMove(tile));

                grid.add(btn, col, row);
                tileButtonMap.put(tile, btn);
            }
        }

        Button resetBtn = new Button("Start på nytt");
        resetBtn.setOnAction(e -> controller.resetGame());

        Button backBtn = new Button("Tilbake til hovedmenyen");
        backBtn.setOnAction(e -> {
            MainPageController controller = new MainPageController(stage);
            Scene mainScene = controller.getMainScene();
            stage.setScene(mainScene);
        });

        resetBtn.getStyleClass().add("button");
        backBtn.getStyleClass().add("button");

        layout = new VBox(15, messageLabel, grid, resetBtn, backBtn);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("layout-root");
    }

    /**
     * Returns the root node of the view layout to be displayed in the scene.
     *
     * @return the root VBox container
     */
    public Pane getRoot() {
        return layout;
    }


    /**
     * Updates the message label above the board.
     *
     * @param message the message to display
     */
    public void updateMessage(String message) {
        messageLabel.setText(message);
    }


    /**
     * Updates the visual content of a given tile on the board.
     *
     * @param tile the tile whose button should be updated
     * @param text the text to display on the tile (typically "X" or "O")
     */
    public void updateTile(Tile tile, String text) {
        Button btn = tileButtonMap.get(tile);
        if (btn != null) {
            btn.setText(text);
        }
    }


    /**
     * Clears the entire board visually and logically by resetting all tile
     * tokens.
     */
    public void clearBoard() {
        for (Tile tile : tileButtonMap.keySet()) {
            tile.setToken(null);
            updateTile(tile, "");
        }
    }

    /**
     * Sets the controller for this view. Required to delegate user actions.
     *
     * @param controller the controller responsible for handling game logic
     */
    public void setController(TicTacToeController controller) {
        this.controller = controller;
    }
}
