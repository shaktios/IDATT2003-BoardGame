package edu.ntnu.boardgame.controllers;

import edu.ntnu.boardgame.constructors.BoardGameFactory;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.exceptions.InvalidBoardFileException;
import edu.ntnu.boardgame.io.PlayerFileHandler;
import edu.ntnu.boardgame.utils.InputValidator;
import edu.ntnu.boardgame.view.common.StartScreenView;
import edu.ntnu.boardgame.view.laddergame.LadderGameScreen;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller for handling all logic related to the start screen. Manages user
 * input, validation, file import/export of players, and initializing the
 * selected game.
 */
public class StartScreenController {

  private final Stage stage;
  private final StartScreenView view;

  /**
   * Constructs a StartScreenController and wires up all necessary handlers
   * for buttons and actions in the view.
   *
   * @param stage the current JavaFX stage
   * @param view the associated StartScreenView
   */
  public StartScreenController(Stage stage, StartScreenView view) {
    this.stage = stage;
    this.view = view;

    view.setNextButtonAction(e -> handleNextButton());
    view.setStartGameButtonAction(e -> handleStartGameButton());
    view.setImportPlayersHandler(this::handleImportPlayers);

    // Saves the current player configuration to a CSV file using the PlayerFileHandler
    view.setSavePlayersHandler(() -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Lagre spillere som CSV");
      File file = fileChooser.showSaveDialog(stage);
      if (file != null) {
        try {
          List<Player> playersToSave = new ArrayList<>();
          for (int i = 0; i < view.getPlayerNameFields().size(); i++) {
            String name = view.getPlayerNameFields().get(i).getText();
            String token = view.getPlayerTokenChoices().get(i).getValue();
            int age = Integer.parseInt(view.getPlayerAgeFields().get(i).getText());
            Player p = new Player(name, new Tile(1), age);
            p.setToken(token);
            playersToSave.add(p);
          }

          PlayerFileHandler.writeToCsv(file, playersToSave);
          view.showAlert("Suksess", "Spillere lagret til " + file.getName());
        } catch (Exception ex) {
          view.showAlert("Feil", "Kunne ikke lagre spillere:\n" + ex.getMessage());
        }
      }
    });
  }

  /**
   * Called when the user presses the "Next" button. Generates input fields
   * for the selected number of players.
   */
  private void handleNextButton() {
    int amountOfPlayers = view.getSelectedPlayerCount();
    view.generatePlayerInputs(amountOfPlayers);
  }

  /**
   * Called when the user presses the "Start Game" button. Validates all
   * player inputs and starts the appropriate board game. Loads game from
   * factory or file based on user selection and transitions to the game
   * screen.
   */
  private void handleStartGameButton() {
    Boardgame boardgame;

    switch (view.getSelectedGameVariant()) {
      case "Liten Stigespill" ->
        boardgame = BoardGameFactory.createMiniGame();
      case "Stort Stigespill" ->
        boardgame = BoardGameFactory.createClassicGame();
      case "Importer eget brett (.json)" -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Velg JSON-fil");
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
          return;
        }

        try {
          boardgame = BoardGameFactory.createGameFromFile(
                  Paths.get(selectedFile.getAbsolutePath())
          );
        } catch (IOException | InvalidBoardFileException ex) {
          view.showAlert("Feil ved lasting av fil", ex.getMessage());
          return;
        }
      }
      default -> {
        view.showAlert("Feil", "Ugyldig spillvariant valgt.");
        return;
      }
    }

    if (!InputValidator.areAllFieldsFilled(view.getPlayerNameFields())) {
      view.showAlert("Ugyldig navn", "Alle spillere må skrive inn et navn");
      return;
    }
    if (!InputValidator.areNamesUnique(view.getPlayerNameFields())) {
      view.showAlert("Duplikatnavn", "Alle spillere må ha unike navn");
      return;
    }
    if (!InputValidator.areTokensUnique(view.getPlayerTokenChoices())) {
      view.showAlert("Duplikattoken", "Alle spillere må velge en unik brikke");
      return;
    }

    List<Player> players = new ArrayList<>();
    Tile startTile = boardgame.getBoard().getTile(1);
    List<TextField> ageFields = view.getPlayerAgeFields();

    int youngestAge = Integer.MAX_VALUE;
    int youngestIndex = 0;

    for (int i = 0; i < view.getPlayerNameFields().size(); i++) {
      String name = view.getPlayerNameFields().get(i).getText();
      String token = view.getPlayerTokenChoices().get(i).getValue();
      int age;

      try {
        age = Integer.parseInt(ageFields.get(i).getText());
      } catch (NumberFormatException e) {
        view.showAlert("Ugyldig alder", "Alder må være et heltall for spiller " + (i + 1));
        return;
      }

      Player player = new Player(name, startTile, age);
      player.setToken(token);
      players.add(player);
      boardgame.addExistingPlayer(player);

      if (age < youngestAge) {
        youngestAge = age;
        youngestIndex = i;
      }
    }

    LadderGameScreen gameScreen = new LadderGameScreen();
    Scene ladderGameScene = gameScreen.createScene(stage, boardgame, boardgame.getBoard(), players);
    String selectedVariant = view.getSelectedGameVariant();

    LadderGameController ladderGameController = new LadderGameController(
        boardgame, gameScreen, stage, selectedVariant, youngestIndex
    );
    gameScreen.disableNextTurnButton();
    gameScreen.enableDiceButton();
    gameScreen.getThrowDiceButton().setOnAction(e -> ladderGameController.handleDiceRoll(stage));
    gameScreen.getNextTurnButton().setOnAction(e -> ladderGameController.handleNextTurn());

    stage.setScene(ladderGameScene);
  }

  /**
   * Returns the scene for the start screen.
   *
   * @return the start screen Scene object
   */
  public Scene getStartScene() {
    return view.createScene(stage);
  }


  /**
   * Opens a file chooser to import players from a CSV file. Populates the
   * input fields in the view with the loaded player data. Shows an alert if
   * the file is invalid or cannot be read.
   */
  private void handleImportPlayers() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Velg CSV-fil med spillere");
    File file = fileChooser.showOpenDialog(stage);

    if (file == null) {
      return;
    }

    try {
      List<Player> importedPlayers = PlayerFileHandler.readFromCsv(file);
      if (importedPlayers.isEmpty()) {
        view.showAlert("Feil", "Fant ingen spillere i CSV-filen.");
        return;
      }

      view.generatePlayerInputs(importedPlayers.size());

      for (int i = 0; i < importedPlayers.size(); i++) {
        Player p = importedPlayers.get(i);
        view.getPlayerNameFields().get(i).setText(p.getName());
        view.getPlayerAgeFields().get(i).setText(String.valueOf(p.getAge()));
        view.getPlayerTokenChoices().get(i).setValue(p.getToken());
      }

    } catch (IOException | NumberFormatException ex) {
      view.showAlert("Feil", "Kunne ikke laste spillere fra fil:\n" + ex.getMessage());
    }
  }



}
