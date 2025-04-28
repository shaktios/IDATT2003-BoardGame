package edu.ntnu.boardgame.view.common;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * View class for the Start Screen. Only responsible for showing buttons,
 * inputs, alerts etc.
 */
public class StartScreenView {

    private VBox root;
    private ComboBox<String> gameSelector;
    private Spinner<Integer> playerCountSpinner;
    private Button nextButton;
    private Button startGameButton;
    private List<TextField> playerNameFields = new ArrayList<>();
    private List<ComboBox<String>> playerTokenChoices = new ArrayList<>();

    public Scene createScene(Stage stage) {
        root = new VBox();
        root.setSpacing(20);

        Label titleLabel = new Label("Velkommen til Brettspillet");

        gameSelector = new ComboBox<>();
        gameSelector.getItems().addAll("Classic", "Mini", "Fra JSON-fil");
        gameSelector.setValue("Classic");
        Label gameLabel = new Label("Velg spillvariant:");

        playerCountSpinner = new Spinner<>(1, 5, 2);
        Label playerLabel = new Label("Velg antall spillere:");

        nextButton = new Button("Neste");
        startGameButton = new Button("Start spill");

        root.getChildren().addAll(
                titleLabel,
                gameLabel, gameSelector,
                playerLabel, playerCountSpinner,
                nextButton
        );

        return new Scene(root, 1200, 800);
    }

    /**
     * Setter action til Neste-knappen
     */
    public void setNextButtonAction(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        nextButton.setOnAction(handler);
    }

    /**
     * Setter action til Start spill-knappen
     */
    public void setStartGameButtonAction(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        startGameButton.setOnAction(handler);
    }

    /**
     * Returnerer valgt spillvariant
     */
    public String getSelectedGameVariant() {
        return gameSelector.getValue();
    }

    /**
     * Returnerer valgt antall spillere
     */
    public int getSelectedPlayerCount() {
        return playerCountSpinner.getValue();
    }

    /**
     * Genererer inputfelt for spillerne
     */
    public void generatePlayerInputs(int numberOfPlayers) {
        root.getChildren().clear();
        playerNameFields.clear();
        playerTokenChoices.clear();

        for (int i = 1; i <= numberOfPlayers; i++) {
            Label playerLabel = new Label("Spiller " + i);
            TextField nameField = new TextField();
            nameField.setPromptText("Navn til spiller " + i);
            playerNameFields.add(nameField);

            ComboBox<String> tokenChoice = new ComboBox<>();
            tokenChoice.getItems().addAll("Bishop", "Horse", "Pawn", "Queen", "Rook");
            tokenChoice.setValue("Bishop");
            playerTokenChoices.add(tokenChoice);

            root.getChildren().addAll(playerLabel, nameField, tokenChoice);
        }

        if (!root.getChildren().contains(startGameButton)) {
            root.getChildren().add(startGameButton);
        }
    }

    /**
     * Viser feilmelding til brukeren
     */
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    // Getters for kontrolleren å bruke
    public List<TextField> getPlayerNameFields() {
        return playerNameFields;
    }

    public List<ComboBox<String>> getPlayerTokenChoices() {
        return playerTokenChoices;
    }
    
}
