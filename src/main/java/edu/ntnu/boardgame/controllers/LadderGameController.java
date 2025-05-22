package edu.ntnu.boardgame.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.ntnu.boardgame.actions.puzzleactions.PuzzleTileAction;
import edu.ntnu.boardgame.actions.tileactions.TileAction;
import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Dice;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.observer.BoardGameObserver;
import edu.ntnu.boardgame.view.laddergame.LadderGameScreen;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller responsible for the logic of the LadderGame. Manages player turns,
 * dice rolling, tile interactions, and coordination between model and view
 * components such as updating the board and messages.
 */
public class LadderGameController {

  private final Boardgame boardgame;
  private final Board board;
  private final List<Player> players;
  private int currentPlayerIndex;
  private final LadderGameScreen view;
  private final Stage stage;
  private final String gameVariant;
 

  /**
   * Constructs the LadderGameController.
   *
   * @param boardgame the Boardgame instance
   * @param view the LadderGameScreen view
   * @param stage the JavaFX stage
   * @param gameVariant the selected game variant name
   */
  public LadderGameController(Boardgame boardgame, LadderGameScreen view, Stage stage, String gameVariant, int startingPlayerIndex) {
    this.boardgame = boardgame;
    this.board = boardgame.getBoard();
    this.players = boardgame.getPlayers();
    this.currentPlayerIndex = startingPlayerIndex;
    this.view = view;
    this.stage = stage;
    this.gameVariant = gameVariant;

    boardgame.registerObserver(new GameObserver());

    view.setSaveBoardAction(this::handleSaveBoard);
    view.setReturnHomeAction(this::handleReturnHome);

  }

    /**
     * Handles the dice roll logic, moves the player accordingly, triggers tile
     * actions, and updates the game state and UI.
     *
     * @param stage the JavaFX stage used for displaying alerts or dialogs
     */
  public void handleDiceRoll(Stage stage) {
    Player player = players.get(currentPlayerIndex);
    Dice dice = boardgame.getDice();
    int roll = dice.roll();
    view.setLastRoll(roll);

    int newPosition = Math.min(player.getPosition() + roll, board.getSize());
    player.setPosition(newPosition, board);
    Tile newTile = board.getTile(newPosition);
    player.setCurrentTile(newTile);

    view.redrawBoard();

    if (newTile.getAction() instanceof PuzzleTileAction puzzleAction) {
      view.disableDiceAndNextTurnButtons();
      puzzleAction.execute(player, board, () -> {
        view.updateMessage(player.getName() + " landet på felt " + player.getPosition());
        boardgame.notifyPlayerMoved(player);
        view.redrawBoard();

        if (player.getPosition() == board.getSize()) {
          showWinMessage(player);
        } else {
          view.enableNextTurnButton();
        }
      });
      return;
    }

    if (newTile.getAction() != null) {
      handleTileAction(player, newTile);
    } else {
      view.updateMessage(player.getName() + " kastet " + roll + " og flyttet til rute " + newPosition);
      boardgame.notifyPlayerMoved(player);
      if (player.getPosition() == board.getSize()) {
        showWinMessage(player);
      }
      view.disableDiceButton();
      view.enableNextTurnButton();
    }
  }

    /**
     * Handles logic when a player lands on a special tile. Executes the tile's
     * action and updates the board view.
     *
     * @param player the player who landed on the tile
     * @param tile the tile with an action to execute
     */
  public void handleTileAction(Player player, Tile tile) {
    view.disableDiceAndNextTurnButtons();

    String actionType = tile.getAction().getClass().getSimpleName();
    String actionText = switch (actionType) {
      case "LadderAction" ->
           " og brukte en stige";
      case "BackAction" ->
           " og traff en slange";
      case "SkipTurnAction" ->
           " og mister neste tur";
      case "ResetAction" ->
           " og må tilbake til start";
      case "TeleportRandomAction" ->
           " og blir teleportert til et tilfeldig sted";
      default ->
           " og ble påvirket av en handling";
    };

    view.updateMessage(player.getName() + " landet på " + actionType + actionText);

    PauseTransition pause = new PauseTransition(Duration.seconds(2));
    pause.setOnFinished(e -> {
      TileAction action = (TileAction) tile.getAction();
      action.execute(player, board);

      boardgame.notifyPlayerMoved(player);
      view.redrawBoard();
      view.updateMessage(player.getName() + " er nå på rute " + player.getPosition());

      if (player.getPosition() == board.getSize()) {
        showWinMessage(player);
      } else {
        view.enableNextTurnButton();
      }
    });
    pause.play();
  }

    /**
     * Advances to the next player's turn. If the player must skip, waits
     * briefly before continuing to the next player.
     */
  public void handleNextTurn() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    Player nextPlayer = players.get(currentPlayerIndex);

    if (nextPlayer.shouldSkipTurn()) {
      view.updateMessage(nextPlayer.getName() + " må stå over denne runden!");
      nextPlayer.setSkipNextTurn(false);
      PauseTransition pause = new PauseTransition(Duration.seconds(2));
      pause.setOnFinished(e -> handleNextTurn());
      pause.play();
    } else {
      view.updateMessage("Spiller sin tur: " + nextPlayer.getName());
      view.enableDiceButton();
      view.disableNextTurnButton();
    }
  }

    /**
     * Displays a popup message indicating the player has won, then redirects to
     * the start screen with the same game variant selected.
     *
     * @param winner the player who won the game
     */
  public void showWinMessage(Player winner) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Spillet er ferdig");
    alert.setHeaderText(winner.getName() + " har vunnet spillet!");
    alert.setContentText("Du sendes tilbake til menyen for stigespill.");

    alert.setOnHidden(e -> {
      edu.ntnu.boardgame.view.common.StartScreenView startView = new edu.ntnu.boardgame.view.common.StartScreenView();
      startView.setSelectedGameVariant(gameVariant);
      StartScreenController controller = new StartScreenController(stage, startView);
      Scene scene = controller.getStartScene();
      stage.setScene(scene);
    });

    alert.show();
  }

    /**
     * Opens a file dialog to let the user choose where to save the current
     * board state. Attempts to serialize and save the game board and
     * configuration to a JSON file.
     */
  public void handleSaveBoard() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Velg hvor du vil lagre brettet");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON-filer", "*.json"));
    File file = fileChooser.showSaveDialog(stage);

    if (file != null) {
        try {
            edu.ntnu.boardgame.io.BoardFileWriterGson writer = new edu.ntnu.boardgame.io.BoardFileWriterGson();
            writer.writeBoardgame(file.toPath(), boardgame);
            view.updateMessage("Brett lagret til: " + file.getName());
        } catch (IOException ex) {
            view.updateMessage("Kunne ikke lagre brettet: " + ex.getMessage());
        }
    } else {
        view.updateMessage("Lagring avbrutt.");
    }
}
    /**
     * Navigates the user back to the main page view from the ladder game
     * screen.
     */
public void handleReturnHome() {
    MainPageController controller = new MainPageController(stage);
    Scene mainScene = controller.getMainScene();
    stage.setScene(mainScene);
}



    /**
     * Internal observer class for handling board game events. Listens for
     * player moves and game win events, and updates the UI accordingly.
     */
  private class GameObserver implements BoardGameObserver {

      /**
       * Triggered when a player moves. Redraws the board to reflect the
       * player's new position.
       *
       * @param player the player who moved
       */
    @Override
    public void onPlayerMove(Player player) {
      view.redrawBoard();
    }

      /**
       * Triggered when a player wins the game. Displays a win message and
       * resets the game view.
       *
       * @param winner the player who won the game
       */
    @Override
    public void onGameWon(Player winner) {
      showWinMessage(winner);
    }
  }
}
