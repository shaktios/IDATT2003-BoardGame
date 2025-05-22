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

  public void updateMessageLabel(String text) {
        messageLabel.setText(text);
    }

  public void setThrowDiceButtonEnabled(boolean enabled) {
        throwDiceButton.setDisable(!enabled);
    }

  public void setNextTurnButtonEnabled(boolean enabled) {
        nextTurnButton.setDisable(!enabled);
    }

  public Button getThrowDiceButton() {
        return throwDiceButton;
    }

  public Button getNextTurnButton() {
        return nextTurnButton;
    }

  public void updateBoard() {
        drawBoard();
    }

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

  private void drawLegendItem(GraphicsContext gc, int tileSize, int[] y, int spacing, javafx.scene.paint.Color color, String text) {
    gc.setFill(color);
    gc.fillRect(0, y[0], tileSize, tileSize);
    gc.setStroke(javafx.scene.paint.Color.BLACK);
    gc.strokeRect(0, y[0], tileSize, tileSize);
    gc.setFill(javafx.scene.paint.Color.BLACK);
    gc.fillText(text, tileSize + 5, y[0] + 20);
    y[0] += spacing;
  }

  private Region createSpacer() {
    Region spacer = new Region();
    spacer.setPrefHeight(30);
    VBox.setVgrow(spacer, Priority.ALWAYS);
    return spacer;
  }

  public void disableDiceAndNextTurnButtons() {
    throwDiceButton.setDisable(true);
    nextTurnButton.setDisable(true);
  }

  public void disableDiceButton() {
        throwDiceButton.setDisable(true);
    }

  public void enableNextTurnButton() {
        nextTurnButton.setDisable(false);
    }

  public void redrawBoard() {
        drawBoard();
    }

  public void updateMessage(String text) {
        messageLabel.setText(text);
    }

  public void enableDiceButton() {
        throwDiceButton.setDisable(false);
    }

  public void disableNextTurnButton() {
        nextTurnButton.setDisable(true);
    }

  public Canvas getCanvas() {
        return canvas;
    }

  public void setLastRoll(int roll) {
        this.lastRoll = roll;
    }

  public void setSaveBoardAction(Runnable handler) {
      saveBoardButton.setOnAction(e -> handler.run());
  }


  public void setReturnHomeAction(Runnable handler) {
      returnHomeButton.setOnAction(e -> handler.run());
  }

}