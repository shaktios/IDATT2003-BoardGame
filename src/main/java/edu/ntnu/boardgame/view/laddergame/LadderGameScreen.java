package edu.ntnu.boardgame.view.laddergame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.actions.tileactions.*;
import edu.ntnu.boardgame.constructors.Dice;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.observer.BoardGameObserver;
import edu.ntnu.boardgame.view.common.StartScreen;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

public class LadderGameScreen {

    private static final int TILE_SIZE = 90;

    private int currentPlayerIndex = 0;
    private List<Player> players;
    private Board board;
    private Canvas canvas;
    private VBox messageBox;
    private Label messageLabel;
    private Button throwDiceButton;
    private Button nextTurnButton;

    private Map<String, Image> playerTokens = new HashMap<>();

    public Scene getScene(Stage stage, Boardgame boardgame) {
        boardgame.registerObserver(new GameObserver());

        this.players = boardgame.getPlayers();
        this.board = boardgame.getBoard();

        throwDiceButton = new Button("Kast Terning");
        nextTurnButton = new Button("Neste tur");

        int canvasWidth = board.getColumns() * TILE_SIZE;
        int canvasHeight = board.getRows() * TILE_SIZE;
        canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        messageLabel = new Label("Spillet starter!");
        messageLabel.setWrapText(true);
        messageLabel.getStyleClass().add("message-label");

        messageBox = new VBox(messageLabel);
        messageBox.setPadding(new Insets(15, 10, 10, 10));
        messageBox.getStyleClass().add("message-box");
        messageBox.setPrefWidth(400);
        messageBox.setPrefHeight(200);
        messageBox.setMaxHeight(200);

        // Laster spillere og tokens
        for (Player player : players) {
            String token = player.getToken();
            Image tokenImage = new Image(getClass().getResourceAsStream("/images/" + token + ".png"));
            playerTokens.put(player.getName(), tokenImage);
        }

        drawBoard(gc);

        throwDiceButton.setDisable(false);
        throwDiceButton.setOnAction(event -> handleDiceRoll(boardgame));

        nextTurnButton.setDisable(true);
        nextTurnButton.setOnAction(event -> handleNextTurn());

        // Layout
        HBox layout = new HBox(20);
        VBox leftSide = new VBox(10);
        FlowPane buttonPane = new FlowPane();
        buttonPane.setHgap(10); // Avstand mellom knappene
        buttonPane.getChildren().addAll(throwDiceButton, nextTurnButton);

        Region spacer = new Region();
        spacer.setPrefHeight(30);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        messageBox.getChildren().clear();
        messageBox.getChildren().addAll(messageLabel, spacer, buttonPane);

        leftSide.getChildren().addAll(canvas);

        layout.getChildren().addAll(leftSide, messageBox);
        HBox.setMargin(messageBox, new Insets(30, 0, 0, 0));

        Scene scene = new Scene(layout, canvasWidth + 650, canvasHeight + 400);
        scene.getStylesheets().add(getClass().getResource("/styles/ladderGame.css").toExternalForm());

        stage.setResizable(false);
        return scene;
    }

    private void handleDiceRoll(Boardgame boardgame) {
        Player player = players.get(currentPlayerIndex);
        Dice dice = new Dice(6, 1);
        int roll = dice.roll();

        int newPosition = Math.min(player.getPosition() + roll, board.getSize());
        player.setPosition(newPosition, board);
        Tile newTile = board.getTile(newPosition);
        player.setCurrentTile(newTile);

        if (newTile.getAction() != null) {
            messageLabel.setText(player.getName() + " landet på rute " + newPosition + " og aktiviserte en handling...");
            throwDiceButton.setDisable(true);
            nextTurnButton.setDisable(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> {
                newTile.executeAction(player, board);
                boardgame.notifyPlayerMoved(player);
                drawBoard(canvas.getGraphicsContext2D());
                messageLabel.setText(player.getName() + " er nå på rute " + player.getPosition());
                if (player.getPosition() == board.getSize()) {
                    boardgame.notifyGameWon(player);
                } else {
                    nextTurnButton.setDisable(false);
                }
            });
            pause.play();
        } else {
            messageLabel.setText(player.getName() + " kastet " + roll + " og flyttet til rute " + newPosition);
            boardgame.notifyPlayerMoved(player);
            if (player.getPosition() == board.getSize()) {
                boardgame.notifyGameWon(player);
            }
            throwDiceButton.setDisable(true);
            nextTurnButton.setDisable(false);
        }
    }

    private void handleNextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        Player nextPlayer = players.get(currentPlayerIndex);

        if (nextPlayer.shouldSkipTurn()) {
            messageLabel.setText(nextPlayer.getName() + " må stå over denne runden!");
            nextPlayer.setSkipNextTurn(false);
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> nextTurnButton.fire());
            pause.play();
        } else {
            messageLabel.setText("Spiller sin tur: " + nextPlayer.getName());
            throwDiceButton.setDisable(false);
            nextTurnButton.setDisable(true);
        }
    }

    private void drawBoard(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int cols = board.getColumns();
        int rows = board.getRows();

        for (int pos = 1; pos <= board.getSize(); pos++) {
            Tile tile = board.getTile(pos);
            int rowIndex = (rows * cols - pos) / cols;
            int colIndex = (pos - 1) % cols;
            if ((rows - rowIndex) % 2 == 0) {
                colIndex = cols - 1 - colIndex;
            }
            double x = colIndex * TILE_SIZE;
            double y = rowIndex * TILE_SIZE;

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
            } else {
                gc.setFill(javafx.scene.paint.Color.WHITE);
            }

            gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);
            gc.setStroke(javafx.scene.paint.Color.BLACK);
            gc.strokeRect(x, y, TILE_SIZE, TILE_SIZE);
            gc.setFill(javafx.scene.paint.Color.BLACK);
            gc.fillText(String.valueOf(pos), x + 5, y + 15);
        }

        for (Player player : players) {
            int pos = player.getPosition();
            int rowIndex = (rows * cols - pos) / cols;
            int colIndex = (pos - 1) % cols;
            if ((rows - rowIndex) % 2 == 0) {
                colIndex = cols - 1 - colIndex;
            }
            double x = colIndex * TILE_SIZE;
            double y = rowIndex * TILE_SIZE;

            Image tokenImage = playerTokens.get(player.getName());
            if (tokenImage != null) {
                gc.drawImage(tokenImage, x + 5, y + 5, TILE_SIZE - 10, TILE_SIZE - 10);
            }
        }
    }

    private class GameObserver implements BoardGameObserver {
        @Override
        public void onPlayerMove(Player player) {
            messageLabel.setText(player.getName() + " er nå på rute " + player.getPosition());
            drawBoard(canvas.getGraphicsContext2D());
        }

        @Override
        public void onGameWon(Player winner) {
            messageLabel.setText(winner.getName() + " vant spillet!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spillet er ferdig");
            alert.setHeaderText(winner.getName() + " har vunnet spillet!");
            alert.setContentText("Du sendes tilbake til startskjermen.");

            alert.showAndWait();

            StartScreen startScreen = new StartScreen();
            Stage stage = (Stage) canvas.getScene().getWindow();
            stage.setScene(startScreen.getScene(stage));
        }
    }
}