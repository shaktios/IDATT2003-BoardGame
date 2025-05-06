package edu.ntnu.boardgame.view.tictactoegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToeGameScreen {

    private final int SIZE = 3;
    private Button[][] buttons = new Button[SIZE][SIZE];
    private boolean xTurn = true;

    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        // Create buttons and add to grid
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button btn = new Button();
                btn.setPrefSize(100, 100);
                int r = row;
                int c = col;

                btn.setOnAction(e -> {
                    if (btn.getText().isEmpty()) {
                        btn.setText(xTurn ? "X" : "O");
                        xTurn = !xTurn;
                        // Add game logic here if needed (win check etc.)
                    }
                });

                buttons[row][col] = btn;
                grid.add(btn, col, row);
            }
        }

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
