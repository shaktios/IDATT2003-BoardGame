package edu.ntnu.boardgame.gui;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.constructors.Tile;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage; 






public class GameScreen {


    public Scene getScene(Stage stage, Boardgame boardgame){

        GridPane gridPane = new GridPane();

        gridPane.setMinSize(400, 200);

        gridPane.setPadding(new Insets(10, 10, 10, 10));


        Board board = boardgame.getBoard();
        int rows = board.getRows(); 
        int cols = board.getColumns();
    

        for (int pos = 1; pos <= board.getSize(); pos++) {
            Tile tile = board.getTile(pos);

            int rowIndex = (rows * cols - pos) / cols;
            int colIndex = (pos - 1) % cols;

            StackPane cell = new StackPane();
            Rectangle bg = new Rectangle(50, 50);
            bg.setStyle("-fx-stroke: black; -fx-fill: white;");
            
            Label label = new Label(String.valueOf(pos));

            cell.getChildren().addAll(bg, label);
            gridPane.add(cell, colIndex, rowIndex);
        }


        return new Scene(gridPane, 800, 600); 
    }
    
}
