package edu.ntnu.boardgame.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartScreen {
    public Scene getScene(Stage stage){

        VBox root = new VBox(); 
        root.setSpacing(20); 


        Label titleLabel = new Label("Velkommen til Brettspillet"); 
        root.getChildren().add(titleLabel); 

        return new Scene(root, 600, 400);  
    }
    
}
