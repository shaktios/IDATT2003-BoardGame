package edu.ntnu.boardgame.view.laddergame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ntnu.boardgame.BoardgameApp;
import edu.ntnu.boardgame.actions.puzzleactions.PuzzleTileAction;
import edu.ntnu.boardgame.actions.tileactions.BackAction;
import edu.ntnu.boardgame.actions.tileactions.LadderAction;
import edu.ntnu.boardgame.actions.tileactions.ResetAction;
import edu.ntnu.boardgame.actions.tileactions.SkipTurnAction;
import edu.ntnu.boardgame.actions.tileactions.TeleportRandomAction;
import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * View class for displaying the main game screen for ladder-style board games
 * (e.g., Snake & Ladder). Handles rendering the board, showing player
 * positions, drawing special tiles (ladders, snakes, etc.), and providing
 * interactive buttons like "Throw Dice", "Next Turn", "Save Board", and "Return
 * Home".
 *
 * This class does not control game logic; it is purely for GUI rendering and
 * event binding.
 */
public class LadderGameScreen {

  private static final int TILE_SIZE = 90;

  private Canvas canvas;
  private Label messageLabel;
  private Button throwDiceButton;
  private Button nextTurnButton;
  private Boardgame boardgame;
  private final Map<String, Image> playerTokens = new HashMap<>();
  private VBox messageBox;
  private Board board;
  private List<Player> players;
  private int lastRoll;
  private Button saveBoardButton;
  private Button returnHomeButton; 

    /**
     * Creates and returns the full JavaFX scene for the ladder game.
     *
     * @param stage the current JavaFX stage
     * @param boardgame the game logic object
     * @param board the game board
     * @param players the list of players in the game
     * @return a complete JavaFX Scene for the ladder game
     */
  public Scene createScene(Stage stage, Boardgame boardgame, Board board, List<Player> players) {
    this.board = board;
    this.players = players;
    this.boardgame = boardgame;


    int canvasWidth = board.getColumns() * TILE_SIZE;
    int canvasHeight = board.getRows() * TILE_SIZE;
    canvas = new Canvas(canvasWidth, canvasHeight);

    messageLabel = new Label("Spillet starter!");
    messageLabel.setWrapText(true);
    messageLabel.getStyleClass().add("message-label");

    throwDiceButton = new Button("Kast Terning");
    throwDiceButton.getStyleClass().add("game-button");

    nextTurnButton = new Button("Neste tur");
    nextTurnButton.getStyleClass().add("game-button");
    
    
    saveBoardButton = new Button("Lagre brett");
    saveBoardButton.getStyleClass().add("game-button");

    returnHomeButton = new Button("Tilbake til hovedmenyen");
    returnHomeButton.getStyleClass().add("game-button");

    messageBox = new VBox(messageLabel, createSpacer(), new FlowPane(10, 10, throwDiceButton, nextTurnButton, saveBoardButton, returnHomeButton));
    VBox.setMargin(messageBox, new Insets(30, 0, 0, 0)); // top, right, bottom, left
    messageBox.setPadding(new Insets(15));
    messageBox.setPrefWidth(400);
    messageBox.setPrefHeight(200);
    messageBox.getStyleClass().add("message-box");

    VBox legendBox = createLegend();
    VBox rightSide = new VBox(10, messageBox, legendBox);

    VBox leftSide = new VBox(canvas);
    HBox layout = new HBox(20, leftSide, rightSide);

    for (Player player : players) {
      Image tokenImage = new Image(getClass().getResourceAsStream("/images/" + player.getToken() + ".png"));
      playerTokens.put(player.getName(), tokenImage);
    }

    Scene scene = new Scene(layout, canvasWidth + 650, canvasHeight + 400);
    scene.getStylesheets().add(getClass().getResource("/styles/ladderGame.css").toExternalForm());
    stage.setResizable(false);

    drawBoard();
    return scene;
  }

    /**
     * Updates the text shown in the message label.
     *
     * @param text the message to display
     */
  public void updateMessageLabel(String text) {
        messageLabel.setText(text);
    }

    /**
     * Enables or disables the "Throw Dice" button.
     *
     * @param enabled true to enable, false to disable
     */
  public void setThrowDiceButtonEnabled(boolean enabled) {
        throwDiceButton.setDisable(!enabled);
    }

    /**
     * Enables or disables the "Next Turn" button.
     *
     * @param enabled true to enable, false to disable
     */
  public void setNextTurnButtonEnabled(boolean enabled) {
        nextTurnButton.setDisable(!enabled);
    }

    /**
     * Returns the "Throw Dice" button, so external controllers can bind
     * actions.
     *
     * @return the throwDiceButton
     */
  public Button getThrowDiceButton() {
        return throwDiceButton;
    }

    /**
     * Returns the "Next Turn" button, so external controllers can bind actions.
     *
     * @return the nextTurnButton
     */
  public Button getNextTurnButton() {
        return nextTurnButton;
    }

    /**
     * Redraws the board and all player positions. Called whenever player
     * positions or board visuals change.
     */
  public void updateBoard() {
        drawBoard();
    }

    /**
     * Displays a win popup and returns the user to the main menu when closed.
     *
     * @param winner the player who won the game
     * @param stage the current stage to reset
     */
  public void showWinMessage(Player winner, Stage stage) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Spillet er ferdig");
    alert.setHeaderText(winner.getName() + " har vunnet spillet!");
    alert.setContentText("Du sendes tilbake til startskjermen.");

    // Når brukeren lukker vinduet, gå til startskjermen
    alert.setOnHidden(e -> {
      Scene freshStartScene = BoardgameApp.createFreshStartScene(stage);
      stage.setScene(freshStartScene);
    });

    alert.show(); // VIKTIG: IKKE bruk showAndWait her
  }

    /**
     * Draws the entire game board onto the canvas, including:
     * <ul>
     * <li>Tiles in zigzag order based on rows and columns</li>
     * <li>Colored tiles based on their associated TileAction</li>
     * <li>Arrows from action tiles (like ladders or snakes) showing
     * destination</li>
     * <li>Highlighting target tiles of ladders (light green) and snakes (light
     * salmon)</li>
     * <li>Rendering all player tokens on their current positions</li>
     * </ul>
     * <p>
     * This method is automatically called when the board needs to be refreshed,
     * such as after a player moves or an action is triggered.
     */
  private void drawBoard() {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    int cols = board.getColumns();
    int rows = board.getRows();

    // tegn alle feltene
    for (int pos = 1; pos <= board.getSize(); pos++) {
      Tile tile = board.getTile(pos);
      int row = (rows * cols - pos) / cols;
      int col = (pos - 1) % cols;
      if ((rows - row) % 2 == 0) {
        col = cols - 1 - col;
      }
      double x = col * TILE_SIZE;
      double y = row * TILE_SIZE;

      // Farge basert på action
      if (tile.getAction() instanceof LadderAction) {
        gc.setFill(javafx.scene.paint.Color.GREEN);
      } else if (tile.getAction() instanceof BackAction) {
        gc.setFill(javafx.scene.paint.Color.RED);
      } else if (tile.getAction() instanceof ResetAction) {
        gc.setFill(javafx.scene.paint.Color.DEEPSKYBLUE);
      } else if (tile.getAction() instanceof TeleportRandomAction) {
        gc.setFill(javafx.scene.paint.Color.GOLD);
      } else if (tile.getAction() instanceof SkipTurnAction) {
        gc.setFill(javafx.scene.paint.Color.ORANGERED);
      } else if (tile.getAction() instanceof PuzzleTileAction) {
        gc.setFill(javafx.scene.paint.Color.PURPLE);
      } else {
        gc.setFill(javafx.scene.paint.Color.WHITE);
      }

      gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);
      gc.setStroke(javafx.scene.paint.Color.BLACK);
      gc.strokeRect(x, y, TILE_SIZE, TILE_SIZE);
      gc.setFill(javafx.scene.paint.Color.BLACK);
      gc.fillText(String.valueOf(pos), x + 5, y + 15);

      // Tegn pil på felter som har LadderAction, BackAction, ResetAction
        if (tile.getAction() instanceof LadderAction
              || tile.getAction() instanceof BackAction
              || tile.getAction() instanceof ResetAction) {

          int destination = tile.getAction().getDestination();
          if (destination > 0 && destination != pos) {
            String arrowText = " -> " + destination;
            gc.fillText(arrowText, x + 5, y + 30);
          }
        }
    }

    // tegn lysere farger på målruter (topp av stige, enden av slange)
    for (int pos = 1; pos <= board.getSize(); pos++) {
      Tile tile = board.getTile(pos);
      if (tile.getAction() == null) {
        continue;
      }

      int destination = tile.getAction().getDestination();
      if (destination < 1 || destination > board.getSize() || destination == 1) {
        continue;
      }

      int destRow = (rows * cols - destination) / cols;
      int destCol = (destination - 1) % cols;
      if ((rows - destRow) % 2 == 0) {
        destCol = cols - 1 - destCol;
      }

      double destX = destCol * TILE_SIZE;
      double destY = destRow * TILE_SIZE;

      if (tile.getAction() instanceof LadderAction) {
        gc.setFill(javafx.scene.paint.Color.LAWNGREEN); // topp på stige
      } else if (tile.getAction() instanceof BackAction) {
        gc.setFill(javafx.scene.paint.Color.LIGHTSALMON); // enden av slange
      } else {
        continue; // andre skal ikke tegnes spesielt
      }

      gc.fillRect(destX, destY, TILE_SIZE, TILE_SIZE);
      gc.setStroke(javafx.scene.paint.Color.BLACK);
      gc.strokeRect(destX, destY, TILE_SIZE, TILE_SIZE);
      gc.setFill(javafx.scene.paint.Color.BLACK);
      gc.fillText(String.valueOf(destination), destX + 5, destY + 15);
    }

    // Til slutt: tegn spillernes brikker
    for (Player player : players) {
      int pos = player.getPosition();
      int row = (rows * cols - pos) / cols;
      int col = (pos - 1) % cols;
      if ((rows - row) % 2 == 0) {
        col = cols - 1 - col;
      }

      double x = col * TILE_SIZE;
      double y = row * TILE_SIZE;

      Image tokenImage = playerTokens.get(player.getName());
      if (tokenImage != null) {
        gc.drawImage(tokenImage, x + 5, y + 5, TILE_SIZE - 10, TILE_SIZE - 10);
      }
    }
  }

    /**
     * Creates a legend (forklaring) for the different tile types used in the
     * game. Each tile type is represented with a color and label, rendered onto
     * a canvas.
     *
     * @return a VBox containing the rendered legend canvas
     */
  private VBox createLegend() {
    Canvas legendCanvas = new Canvas(500, 400);
    GraphicsContext gc = legendCanvas.getGraphicsContext2D();
    int tileSize = 30;
    int spacing = 35;
    int[] y = {0};

    drawLegendItem(gc, tileSize, y, spacing, javafx.scene.paint.Color.GREEN, "Starten på stigen");
    drawLegendItem(gc, tileSize, y, spacing, javafx.scene.paint.Color.LAWNGREEN, "Toppen av stigen");
    drawLegendItem(gc, tileSize, y, spacing, javafx.scene.paint.Color.RED, "Starten på slangen");
    drawLegendItem(gc, tileSize, y, spacing, javafx.scene.paint.Color.LIGHTSALMON, "Enden på slangen");
    drawLegendItem(gc, tileSize, y, spacing, javafx.scene.paint.Color.GOLD, "Teleportfelt");
    drawLegendItem(gc, tileSize, y, spacing, javafx.scene.paint.Color.DEEPSKYBLUE, "Tilbake til start");
    drawLegendItem(gc, tileSize, y, spacing, javafx.scene.paint.Color.ORANGERED, "Mister en tur");
    drawLegendItem(gc, tileSize, y, spacing, javafx.scene.paint.Color.PURPLE, "Sjakkquiz");
    drawLegendItem(gc, tileSize, y, spacing, javafx.scene.paint.Color.WHITE, "Vanlig felt");

    return new VBox(legendCanvas);
  }

    /**
     * Draws a single legend item on the given canvas context. This includes a
     * colored square and its corresponding label.
     *
     * @param gc the GraphicsContext to draw on
     * @param tileSize size of the square representing a tile
     * @param y array containing current y-position, updated after drawing
     * @param spacing vertical spacing between items
     * @param color the color to fill the square with
     * @param text the label describing the tile type
     */
  private void drawLegendItem(GraphicsContext gc, int tileSize, int[] y, int spacing, javafx.scene.paint.Color color, String text) {
    gc.setFill(color);
    gc.fillRect(0, y[0], tileSize, tileSize);
    gc.setStroke(javafx.scene.paint.Color.BLACK);
    gc.strokeRect(0, y[0], tileSize, tileSize);
    gc.setFill(javafx.scene.paint.Color.BLACK);
    gc.fillText(text, tileSize + 5, y[0] + 20);
    y[0] += spacing;
  }

    /**
     * Creates a vertical spacer region for layout padding in the message box.
     *
     * @return a Region with fixed height and grow priority set
     */
  private Region createSpacer() {
    Region spacer = new Region();
    spacer.setPrefHeight(30);
    VBox.setVgrow(spacer, Priority.ALWAYS);
    return spacer;
  }

    /**
     * Disables both the "Throw Dice" and "Next Turn" buttons. Typically used
     * mid-turn or when awaiting an event.
     */
  public void disableDiceAndNextTurnButtons() {
    throwDiceButton.setDisable(true);
    nextTurnButton.setDisable(true);
  }


  /**
     * Disables only the "Throw Dice" button. Used when dice rolling is no
     * longer allowed in the current turn.
     */
  public void disableDiceButton() {
        throwDiceButton.setDisable(true);
    }

    /**
     * Enables the "Next Turn" button. Called after a player completes their
     * move and is ready to proceed.
     */
  public void enableNextTurnButton() {
        nextTurnButton.setDisable(false);
    }


  /**
    * Redraws the entire game board and player positions. Typically called
    * after a player has moved or an action has changed the board.
    */
  public void redrawBoard() {
        drawBoard();
    }

    /**
     * Updates the message text shown in the message box.
     *
     * @param text the new message to display
     */
  public void updateMessage(String text) {
        messageLabel.setText(text);
    }

    /**
     * Enables the "Throw Dice" button. Allows the current player to roll the
     * dice.
     */
  public void enableDiceButton() {
        throwDiceButton.setDisable(false);
    }

    /**
     * Disables the "Next Turn" button. Used to prevent players from ending
     * their turn prematurely.
     */
  public void disableNextTurnButton() {
        nextTurnButton.setDisable(true);
    }

    /**
     * Returns the canvas used for drawing the game board.
     *
     * @return the JavaFX Canvas object
     */
  public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Stores the result of the player's last dice roll.
     *
     * @param roll the result from the dice
     */
  public void setLastRoll(int roll) {
        this.lastRoll = roll;
    }

    /**
     * Sets a handler to be called when the "Save Board" button is clicked.
     *
     * @param handler the action to run
     */
  public void setSaveBoardAction(Runnable handler) {
      saveBoardButton.setOnAction(e -> handler.run());
  }


    /**
     * Sets a handler to be called when the "Return Home" button is clicked.
     *
     * @param handler the action to run
     */
  public void setReturnHomeAction(Runnable handler) {
      returnHomeButton.setOnAction(e -> handler.run());
  }

}