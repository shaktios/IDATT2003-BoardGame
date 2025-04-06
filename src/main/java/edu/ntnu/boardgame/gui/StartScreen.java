package edu.ntnu.boardgame.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartScreen {

    private ComboBox<String> gameSelector; 
    private Spinner<Integer> playerCountSpinner; 
    private Button nextButton; 



    public Scene getScene(Stage stage){

        VBox root = new VBox(); 
        root.setSpacing(20); 


        Label titleLabel = new Label("Velkommen til Brettspillet"); 
        root.getChildren().add(titleLabel); 

      


        //combobox for å velge spillvariant 
        gameSelector = new ComboBox<>(); 
        gameSelector.getItems().addAll("Classic", "Mini", "Fra JSON-fil"); 
        gameSelector.setValue("Classic"); //standardvalget
        Label gameLabel = new Label("Velg spillvariant: "); 

        //spinner for å velge antall spillere
        playerCountSpinner = new Spinner(1,5,2);
        Label playerLabel = new Label("Velg antall spillere"); 

        //next knapp
        nextButton = new Button("Neste"); 

        //klikklogikk (bare utskrift foreløpig...)
        nextButton.setOnAction(e ->{

            String choosenGame = gameSelector.getValue(); 
            int amountOfPlayers = playerCountSpinner.getValue(); 

            System.out.println("Valgt spill: " + choosenGame);
            System.out.println("Antall spillere: " + amountOfPlayers);

            //TODO: vis inputfelter for spillernavn
            //TODO: gå videre til neste skjerm


        });

        root.getChildren().addAll(
            titleLabel,
            gameLabel,gameSelector,
            playerLabel,playerCountSpinner, 
            nextButton
        );

        return new Scene(root, 600, 400);

    }
    
}
