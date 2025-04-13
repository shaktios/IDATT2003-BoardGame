package edu.ntnu.boardgame.gui;

import edu.ntnu.boardgame.Boardgame;
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

        int antallRader = 10;
        int antallKolonner = 9;

        for (int row = 0; row < antallRader ;row++) {
            for (int col = 0; col < antallKolonner; col++) {
                StackPane tile = new StackPane(); 
                Rectangle bg = new Rectangle(50, 50); 
                Label number = new Label("7"); 


                tile.getChildren().addAll(bg, number);
                gridPane.add(tile, col, row);
                
            }
            
        }


        return new Scene(gridPane, 800, 600); 
    }
    
}
