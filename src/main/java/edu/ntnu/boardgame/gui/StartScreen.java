package edu.ntnu.boardgame.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.boardgame.BoardGameFactory;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.exceptions.InvalidBoardFileException;
import edu.ntnu.boardgame.utils.InputValidator;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox; 
import javafx.stage.FileChooser;
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

            if (!InputValidator.isPlayerCountValid(amountOfPlayers)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ugyldig antall spillere");
                alert.setHeaderText("Du må velge mellom 1 og 5 spillere");
                alert.showAndWait();
                return;
            }

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



            Button startGameButton = new Button("Start spill");
            startGameButton.setOnAction(event -> {

                List<Player> players = new ArrayList<>(); 

                Boardgame boardgame = null; 

                switch (gameSelector.getValue()) {
                    case "Classic" ->
                        boardgame = BoardGameFactory.createClassicGame();
                    case "Mini" ->
                        boardgame = BoardGameFactory.createMiniGame();
                    case "Fra JSON-fil" -> {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Velg JSON-fil");
                        File selectedFile = fileChooser.showOpenDialog(stage);
                        if (selectedFile != null) {
                            try {
                                boardgame = BoardGameFactory.createGameFromFile(Paths.get(selectedFile.getAbsolutePath()));
                            } catch (IOException | InvalidBoardFileException ex) {
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setTitle("Feil ved lasting av fil");
                                errorAlert.setHeaderText("Kunne ikke laste brett fra valgt fil.");
                                errorAlert.setContentText(ex.getMessage());
                                errorAlert.showAndWait();
                                return;
                            }
                        } else {
                            return; // Brukeren valgte ikke fil, avbryt opprettelse
                        }
                    }
                }



                Tile startTile = boardgame.getBoard().getTile(1); 
        
                if (!InputValidator.areAllFieldsFilled(playerNameFields)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ugyldig navn");
                    alert.setHeaderText("Alle spillere må skrive inn et navn");
                    alert.showAndWait();
                    return;
                }

                if (!InputValidator.areNamesUnique(playerNameFields)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Duplikatnavn");
                    alert.setHeaderText("Alle spillere må ha unike navn");
                    alert.showAndWait();
                    return;
                }

                if (!InputValidator.areTokensUnique(playerTokenChoices)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Duplikat-token");
                    alert.setHeaderText("Alle spillere må velge en unik brikke");
                    alert.showAndWait();
                    return;
                }

                for (int i = 0; i < playerNameFields.size(); i++) {
                    String name = playerNameFields.get(i).getText();
                    String token = playerTokenChoices.get(i).getValue();

                    Player player = new Player(name, startTile); 
                    player.setToken(token);
                    players.add(player);
                    boardgame.addExistingPlayer(player);

                    System.out.println("Spiller " + (i + 1) + ": " + name + " med brikke: " + token);
                }

                GameScreen gameScreen = new GameScreen();
                stage.setScene(gameScreen.getScene(stage, boardgame));


            }); 


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
