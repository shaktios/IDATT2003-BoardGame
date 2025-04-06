package edu.ntnu.boardgame.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartScreen {

    private ComboBox<String> gameSelector; 
    private Spinner<Integer> playerCountSpinner; 
    private Button nextButton; 
    List<TextField> playerNameFields = new ArrayList<>();
    List<ComboBox<String>> playerTokenChoices = new ArrayList<>();



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

            root.getChildren().clear(); 

            // tømmer listen i tilfelle noen klikker "Neste" flere ganger
            playerNameFields.clear();

            for (int i = 1; i <= amountOfPlayers; i++) {

                Label playerNumberLabel = new Label("Spiller " + i); 

                TextField textField = new TextField();
                textField.setPromptText("Skriv inn navnet til spiller " + i);
                playerNameFields.add(textField); 


                ComboBox<String> tokenOptions = new ComboBox<>(); 
                tokenOptions.getItems().addAll("RaceCar", "Hat", "Dog", "Cat");
                tokenOptions.setValue("RaceCar"); // valgfritt standardvalg
                playerTokenChoices.add(tokenOptions);

                root.getChildren().addAll(playerNumberLabel, textField, tokenOptions);
                
            }


            for (TextField field : playerNameFields) {
                System.out.println("Spillernavn: " + field.getText());
            }

            

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
