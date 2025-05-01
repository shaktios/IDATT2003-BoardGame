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

    // Beholder handlers så de kan gjenbrukes etter restart
    private javafx.event.EventHandler<javafx.event.ActionEvent> nextButtonHandler;
    private javafx.event.EventHandler<javafx.event.ActionEvent> startGameButtonHandler;

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

        // Gjenoppretter tidligere EventHandler hvis satt
        if (nextButtonHandler != null) {
            nextButton.setOnAction(nextButtonHandler);
        }
        if (startGameButtonHandler != null) {
            startGameButton.setOnAction(startGameButtonHandler);
        }

        root.getChildren().addAll(
                titleLabel,
                gameLabel, gameSelector,
                playerLabel, playerCountSpinner,
                nextButton
        );

        return new Scene(root, 1200, 800);
    }

    /**
     * Sets the action handler for the "Next" button.
     *
     * @param handler the event handler to be triggered when the "Next" button
     * is clicked
     */
    public void setNextButtonAction(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        this.nextButtonHandler = handler;
        if (nextButton != null) {
            nextButton.setOnAction(handler);
        }
    }

    /**
     * Sets the action handler for the "Start Game" button.
     *
     * @param handler the event handler to be triggered when the "Start Game"
     * button is clicked
     */
    public void setStartGameButtonAction(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        this.startGameButtonHandler = handler;
        if (startGameButton != null) {
            startGameButton.setOnAction(handler);
        }
    }

    /**
     * Returns the game variant selected by the user in the drop-down menu.
     *
     * @return the selected game variant as a String
     */
    public String getSelectedGameVariant() {
        return gameSelector.getValue();
    }

    /**
     * Returns the number of players selected by the user using the spinner.
     *
     * @return the number of players
     */
    public int getSelectedPlayerCount() {
        return playerCountSpinner.getValue();
    }

    /**
     * Generates input fields for the specified number of players. Each player
     * is given a name field and a combo box for selecting a token. Also ensures
     * the "Start Game" button is included and properly wired.
     *
     * @param numberOfPlayers the number of players to generate inputs for
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

        // Start-knappen må alltid legges til med handler
        if (!root.getChildren().contains(startGameButton)) {
            if (startGameButtonHandler != null) {
                startGameButton.setOnAction(startGameButtonHandler);
            }
            root.getChildren().add(startGameButton);
        }
    }

    /**
     * Displays a warning alert dialog with a title and message.
     *
     * @param title the title of the alert window
     * @param message the warning message to display
     */
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Returns the list of text fields containing player names.
     *
     * @return the list of player name fields
     */
    public List<TextField> getPlayerNameFields() {
        return playerNameFields;
    }


    /**
     * Returns the list of combo boxes for selecting player tokens.
     *
     * @return the list of token selection combo boxes
     */
    public List<ComboBox<String>> getPlayerTokenChoices() {
        return playerTokenChoices;
    }
}
