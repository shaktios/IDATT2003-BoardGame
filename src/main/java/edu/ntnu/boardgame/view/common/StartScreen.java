package edu.ntnu.boardgame.view.common;

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
import edu.ntnu.boardgame.view.laddergame.LadderGameScreen;
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

    public Scene getScene(Stage stage) {
        VBox root = new VBox();
        root.setSpacing(20);

        Label titleLabel = new Label("Velkommen til Brettspillet");
        gameSelector = new ComboBox<>();
        gameSelector.getItems().addAll("Classic", "Mini", "Fra JSON-fil");
        gameSelector.setValue("Classic");
        Label gameLabel = new Label("Velg spillvariant: ");

        playerCountSpinner = new Spinner<>(1, 5, 2);
        Label playerLabel = new Label("Velg antall spillere");

        nextButton = new Button("Neste");
        Button startGameButton = new Button("Start spill");

        // Start Game Action
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
                            showAlert("Feil ved lasting av fil", ex.getMessage());
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }

            Tile startTile = boardgame.getBoard().getTile(1);

            if (!InputValidator.areAllFieldsFilled(playerNameFields)) {
                showAlert("Ugyldig navn", "Alle spillere må skrive inn et navn");
                return;
            }
            if (!InputValidator.areNamesUnique(playerNameFields)) {
                showAlert("Duplikatnavn", "Alle spillere må ha unike navn");
                return;
            }
            if (!InputValidator.areTokensUnique(playerTokenChoices)) {
                showAlert("Duplikattoken", "Alle spillere må velge en unik brikke");
                return;
            }

            for (int i = 0; i < playerNameFields.size(); i++) {
                String name = playerNameFields.get(i).getText();
                String token = playerTokenChoices.get(i).getValue();
                Player player = new Player(name, startTile);
                player.setToken(token);
                players.add(player);
                boardgame.addExistingPlayer(player);
            }

            LadderGameScreen gameScreen = new LadderGameScreen();
            stage.setScene(gameScreen.getScene(stage, boardgame));
        });

        // Neste-knappen
        nextButton.setOnAction(e -> {
            int amountOfPlayers = playerCountSpinner.getValue();
            root.getChildren().clear();
            playerNameFields.clear();
            playerTokenChoices.clear();

            for (int i = 1; i <= amountOfPlayers; i++) {
                Label playerNumberLabel = new Label("Spiller " + i);
                TextField nameField = new TextField();
                nameField.setPromptText("Navn til spiller " + i);
                playerNameFields.add(nameField);

                ComboBox<String> tokenChoice = new ComboBox<>();
                tokenChoice.getItems().addAll("Bishop", "Horse", "Pawn", "Queen", "Rook");
                tokenChoice.setValue("Bishop");
                playerTokenChoices.add(tokenChoice);

                root.getChildren().addAll(playerNumberLabel, nameField, tokenChoice);
            }

            if (!root.getChildren().contains(startGameButton)) {
                root.getChildren().add(startGameButton);
            }
        });

        root.getChildren().addAll(
                titleLabel,
                gameLabel, gameSelector,
                playerLabel, playerCountSpinner,
                nextButton
        );

        stage.setResizable(false);
        return new Scene(root, 1200, 800);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
