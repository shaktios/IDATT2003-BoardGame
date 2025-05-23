/**
 * View class for the Start Screen. Responsible for displaying inputs, buttons,
 * and layout for starting the game.
 */
package edu.ntnu.boardgame.view.common;

import java.util.ArrayList;
import java.util.List;

import edu.ntnu.boardgame.controllers.MainPageController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartScreenView {

  private VBox root;
  private Stage stage;
  private ComboBox<String> gameSelector;
  private Spinner<Integer> playerCountSpinner;
  private Button nextButton;
  private Button startGameButton;
  private List<TextField> playerNameFields = new ArrayList<>();
  private List<ComboBox<String>> playerTokenChoices = new ArrayList<>();
  private Button backToMainMenuButton; // Ny knapp
  private Button readPlayersFromCsvButton; //knapp for å lese spillere fra csv fil
  private Runnable importPlayersHandler;
  private Button savePlayersToCsvButton; //knapp for å lagre en csv fil med spillerne også
  private Runnable savePlayersHandler;
  private Button backToGameMenuButton;

  private javafx.event.EventHandler<javafx.event.ActionEvent> nextButtonHandler;
  private javafx.event.EventHandler<javafx.event.ActionEvent> startGameButtonHandler;

    /**
     * Builds the entire start screen UI and returns the corresponding scene.
     * Initializes combo boxes, buttons, labels, and styling. Also sets up
     * button event handlers if they are already assigned.
     *
     * @param stage the current JavaFX stage (main window)
     * @return the fully constructed Scene for the start screen
     */
  public Scene createScene(Stage stage) {
    this.stage = stage;
    root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(40));
    root.setAlignment(Pos.CENTER);
    root.getStyleClass().add("start-screen-root");

    Label titleLabel = new Label("Velkommen til Spillplattformen!");
    titleLabel.getStyleClass().add("title-label");

    gameSelector = new ComboBox<>();
    gameSelector.getItems().addAll("Liten Stigespill", "Stort Stigespill", "Importer eget brett (.json)");
    gameSelector.setValue("Liten Stigespill");
    gameSelector.getStyleClass().add("custom-combo");

    Label gameLabel = new Label("\ud83c\udfb2 Velg spillvariant:");
    gameLabel.getStyleClass().add("section-label");

    playerCountSpinner = new Spinner<>(1, 5, 2);
    playerCountSpinner.getStyleClass().add("custom-spinner");

    readPlayersFromCsvButton = new Button("Importer spillere inn fra en CSV-fil");
    readPlayersFromCsvButton.getStyleClass().add("start-button");

    readPlayersFromCsvButton.setOnAction(e -> {
      if (importPlayersHandler != null) {
        importPlayersHandler.run();
      }
    });

    Label playerLabel = new Label("\uD83D\uDC64 Velg antall spillere:");
    playerLabel.getStyleClass().add("section-label");

    nextButton = new Button("Neste");
    nextButton.getStyleClass().add("start-button");

    startGameButton = new Button("Start spill");
    startGameButton.getStyleClass().add("start-button");

    if (nextButtonHandler != null) {
      nextButton.setOnAction(nextButtonHandler);
    }
    if (startGameButtonHandler != null) {
      startGameButton.setOnAction(startGameButtonHandler);
    }
    backToMainMenuButton = new Button("Tilbake til Hovedmeny");
    backToMainMenuButton.getStyleClass().add("start-button");

    backToMainMenuButton.setOnAction(e -> {
      MainPageController controller = new MainPageController(stage);
      Scene mainScene = controller.getMainScene();
      stage.setScene(mainScene);
    });

    root.getChildren().addAll(titleLabel, gameLabel, gameSelector, playerLabel, playerCountSpinner,readPlayersFromCsvButton, nextButton, backToMainMenuButton);

    Scene scene = new Scene(root, 1250, 1050);
    scene.getStylesheets().add(getClass().getResource("/styles/startScreen.css").toExternalForm());
    return scene;
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
   * A list of text fields where the user enters the age of each player. Each
   * index in the list corresponds to a player in the same order as name and
   * token selection.
   */
  private List<TextField> playerAgeFields = new ArrayList<>();


  /**
   * Returns the list of text fields for player ages. This is used in the
   * controller to retrieve and store each player's age input.
   *
   * @return a list of TextField objects containing player age inputs
   */
  public List<TextField> getPlayerAgeFields() {
        return playerAgeFields;
    }

 

    /**
     * Dynamically creates input fields and controls for the given number of
     * players. Each row contains a name field, age field, and token selector
     * with image preview. Also adds buttons for saving players and returning to
     * the main menu.
     *
     * @param numberOfPlayers the number of players to generate UI components
     * for
     */
  public void generatePlayerInputs(int numberOfPlayers) {
    root.getChildren().clear();
    playerNameFields.clear();
    playerTokenChoices.clear();

    for (int i = 1; i <= numberOfPlayers; i++) {
      Label playerLabel = new Label("Spiller " + i);
      playerLabel.getStyleClass().add("section-label");

      TextField nameField = new TextField();
      nameField.setPromptText("Navn til spiller " + i);
      nameField.getStyleClass().add("player-textfield");

      TextField ageField = new TextField();
      ageField.setPromptText("Alder til spiller " + i);
      ageField.getStyleClass().add("player-textfield");

      ComboBox<String> tokenChoice = new ComboBox<>();
      tokenChoice.getItems().addAll("Bishop", "Horse", "Pawn", "Queen", "Rook");
      tokenChoice.setValue("Bishop");
      tokenChoice.getStyleClass().add("custom-combo");

      // Live image preview
      ImageView tokenPreview = new ImageView(new Image(getClass().getResourceAsStream("/images/Bishop.png")));
      tokenPreview.setFitHeight(88);
      tokenPreview.setPreserveRatio(true);
      tokenPreview.getStyleClass().add("token-preview");

      tokenChoice.setOnAction(e -> {
        String selected = tokenChoice.getValue();
        tokenPreview.setImage(new Image(getClass().getResourceAsStream("/images/" + selected + ".png")));
            
      });

      HBox playerRow = new HBox(10, tokenPreview, nameField, ageField, tokenChoice);
      playerRow.setAlignment(Pos.CENTER);
      playerRow.getStyleClass().add("player-row");

        

      playerNameFields.add(nameField);
      playerTokenChoices.add(tokenChoice);
      playerAgeFields.add(ageField);
        


      root.getChildren().addAll(playerLabel, playerRow);
    }

    Button backToGameMenuButton = new Button("Tilbake til hovedmenyen");
    backToGameMenuButton.getStyleClass().add("start-button");
    backToGameMenuButton.setOnAction(e -> {
      MainPageController controller = new MainPageController(stage);
      Scene mainScene = controller.getMainScene();
      stage.setScene(mainScene);
    });

    root.getChildren().add(backToGameMenuButton);

    savePlayersToCsvButton = new Button("Lagre spillere til CSV-fil");
    savePlayersToCsvButton.getStyleClass().add("start-button");

    savePlayersToCsvButton.setOnAction(e -> {
      if (savePlayersHandler != null) {
        savePlayersHandler.run();
      }
    });

    root.getChildren().add(savePlayersToCsvButton);

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
    Platform.runLater(() -> {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle(title);
      alert.setHeaderText(message);
      alert.showAndWait();
    });
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

    /**
     * Sets the selected game variant in the combo box manually.
     *
     * @param variant the game variant to preselect (must match one of the combo
     * box items)
     */
  public void setSelectedGameVariant(String variant) {
    if (gameSelector != null) {
      gameSelector.setValue(variant);
    }
  }

    /**
     * Returns the button that brings the user back to the main menu. Can be
     * used by controllers to attach additional behavior.
     *
     * @return the "Back to Main Menu" button
     */
  public Button getBackToMainMenuButton() {
        return backToMainMenuButton;
    }

    /**
     * Sets a handler that will be called when the user clicks the "Import
     * players from CSV" button.
     *
     * @param handler the logic to run when the import button is pressed
     */
  public void setImportPlayersHandler(Runnable handler) {
        this.importPlayersHandler = handler;
    }

  /**
   * Sets a handler to be executed when the user clicks the button to save the
   * players to a CSV file.
   *
   * @param handler a Runnable containing the logic for saving player data
   */
  public void setSavePlayersHandler(Runnable handler) {
        this.savePlayersHandler = handler;
    }
}




