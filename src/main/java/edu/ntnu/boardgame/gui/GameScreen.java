package edu.ntnu.boardgame.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.actions.BackAction;
import edu.ntnu.boardgame.actions.ChessPuzzleAction;
import edu.ntnu.boardgame.actions.LadderAction;
import edu.ntnu.boardgame.actions.ResetAction;
import edu.ntnu.boardgame.actions.SkipTurnAction;
import edu.ntnu.boardgame.actions.TeleportRandomAction;
import edu.ntnu.boardgame.constructors.Dice;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.observer.BoardGameObserver;
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

public class GameScreen {

    private static final int TILE_SIZE = 90;

    private int currentPlayerIndex = 0;
    private List<Player> players;
    private Board board;
    private Label currentPlayerLabel = new Label("Spillet starter!");
    private Canvas canvas;
    private VBox messageBox;
    private Label messageLabel;


    private Button throwDiceButton;
    private Button nextTurnButton;

    private ChessPuzzleAction chessPuzzleAction;

    // Lagrer spillerens navn og tilhørende bilde én gang
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
        messageBox.setPadding(new Insets(10));
        messageBox.getStyleClass().add("message-box");
        messageBox.setPadding(new Insets(15, 10, 10, 10)); // top, right, bottom, left
        messageBox.setPrefWidth(400);
        messageBox.setPrefHeight(200);
        messageBox.setMaxHeight(200);
        VBox.setVgrow(messageBox, Priority.NEVER);

        // Laster inn spillerbrikkebilder og lagrer i map
        for (Player player : players) {
            String token = player.getToken();
            Image tokenImage = new Image(getClass().getResourceAsStream("/images/" + token + ".png"));
            playerTokens.put(player.getName(), tokenImage);
        }

        // Tegner brett og spillere første gang
        drawBoard(gc);

        // Terningkast-knapp
        throwDiceButton.setDisable(false);
        throwDiceButton.setOnAction(event -> {
            Player player = players.get(currentPlayerIndex);
            Dice dice = new Dice(6, 1);
            int roll = dice.roll();
            throwDiceButton.getStyleClass().add("game-button");


            int newPosition = player.getPosition() + roll;
            if (newPosition > board.getSize()) {
                newPosition = board.getSize();
            }

            player.setPosition(newPosition, board);
            Tile newTile = board.getTile(newPosition);
            player.setCurrentTile(newTile);

            // 🧠 Sjekk for sjakkoppgave-felt
            if (newPosition == 42 || newPosition == 73) {
                messageLabel.setText(player.getName() + " landet på en sjakkoppgave!");

                throwDiceButton.setDisable(true);
                nextTurnButton.setDisable(true);

                chessPuzzleAction.launchPuzzle();

                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e2 -> nextTurnButton.setDisable(false));
                pause.play();

                return; // Stopper videre utførelse
            }


            // Hvis feltet har en handling, vis beskrivelse og utfør etter pause
            if (newTile.getAction() != null) {
                String actionType = newTile.getAction().getClass().getSimpleName();
                String actionText = switch (actionType) {
                    case "LadderAction" ->
                        "brukte en stige";
                    case "BackAction" ->
                        "traff en slange";
                    case "SkipTurnAction" ->
                        "mister neste tur";
                    case "ResetAction" ->
                        "må tilbake til start";
                    case "TeleportRandomAction" ->
                        "blir teleportert til et randomt sted på brettet";
                    default ->
                        "ble påvirket av en handling";
                };

                messageLabel.setText(player.getName() + " landet på rute " + newPosition
                        + " og " + actionText + "...");

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
                messageLabel.setText(player.getName() + " kastet " + roll
                        + " og flyttet til rute " + newPosition);
                boardgame.notifyPlayerMoved(player);
                if (player.getPosition() == board.getSize()) {
                    boardgame.notifyGameWon(player);
                }
                throwDiceButton.setDisable(true);
                nextTurnButton.setDisable(false);
            }
        });

        // Neste tur knapp med logikk for å hoppe over tur
        throwDiceButton.setDisable(false);
        nextTurnButton.setDisable(true);
        nextTurnButton.setOnAction(event -> {
            nextTurnButton.getStyleClass().add("game-button");
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            Player nextPlayer = players.get(currentPlayerIndex);

            if (nextPlayer.shouldSkipTurn()) {
                messageLabel.setText(nextPlayer.getName() + " må stå over denne runden!");
                nextPlayer.setSkipNextTurn(false);

                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> nextTurnButton.fire());
                pause.play();
                return;
            }

            messageLabel.setText("Spiller sin tur: " + nextPlayer.getName());
            throwDiceButton.setDisable(false);
            nextTurnButton.setDisable(true);
        });

        // GUI layout
        HBox layout = new HBox(20);

        VBox leftSide = new VBox(10);
        FlowPane buttonPane = new FlowPane();
        buttonPane.setHgap(10); // Avstand mellom knappene
        buttonPane.getChildren().addAll(throwDiceButton, nextTurnButton);
        VBox.setMargin(buttonPane, new Insets(10, 0, 0, 0)); // top, right, bottom, left

        // Legg til brett og knapper på venstresiden
        leftSide.getChildren().addAll(canvas);

        // Legg knapper og tekst i meldingsboksen (høyresiden)
        Region spacer = new Region();
        spacer.setPrefHeight(30); // juster høyde etter ønske
        VBox.setVgrow(spacer, Priority.ALWAYS);

        messageBox.getChildren().clear();
        messageBox.getChildren().addAll(messageLabel, spacer, buttonPane);

        // Fullfør layout
        layout.getChildren().addAll(leftSide, messageBox);
        HBox.setMargin(messageBox, new Insets(30, 0, 0, 0)); // Flytt boksen litt ned
        messageBox.setPrefWidth(400);
        stage.setResizable(false); // Lås størrelsen på vinduet

        Scene scene = new Scene(layout, canvasWidth + 650, canvasHeight + 400);
        scene.getStylesheets().add(getClass().getResource("/styles/ladderGame.css").toExternalForm());
        messageBox.getStyleClass().add("message-box");
        messageLabel.getStyleClass().add("message-label");
        throwDiceButton.getStyleClass().add("game-button");
        nextTurnButton.getStyleClass().add("game-button");

        stage.setResizable(false); // Lås størrelsen på vinduet
        return scene;
    }

    private void drawBoard(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int cols = board.getColumns();
        int rows = board.getRows();

        // Tegner alle rutene og farger dem basert på handlingstype
        for (int pos = 1; pos <= board.getSize(); pos++) {
            Tile tile = board.getTile(pos);
            int rowIndex = (rows * cols - pos) / cols;
            int colIndex = (pos - 1) % cols;

            // Zigzag-layout for kolonnevis bevegelse
            if ((rows - rowIndex) % 2 == 0) {
                colIndex = cols - 1 - colIndex;
            }

            double x = colIndex * TILE_SIZE;
            double y = rowIndex * TILE_SIZE;

            // Velg bakgrunnsfarge for feltet basert på action-type
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
            } else if (pos == 42 || pos == 73) {
                gc.setFill(javafx.scene.paint.Color.PURPLE);
            } else {
                gc.setFill(javafx.scene.paint.Color.WHITE);
            }


            gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);
            gc.setStroke(javafx.scene.paint.Color.BLACK);
            gc.strokeRect(x, y, TILE_SIZE, TILE_SIZE);
            gc.setFill(javafx.scene.paint.Color.BLACK);
            gc.fillText(String.valueOf(pos), x + 5, y + 15); // Skriv ut posisjonsnummer på feltet

            // Hvis det er en handling med destinasjon (stige/slange/reset), vis "→ X"
            if (tile.getAction() instanceof LadderAction
                    || tile.getAction() instanceof BackAction
                    || tile.getAction() instanceof ResetAction) {
                int destination = tile.getAction().getDestination();
                if (destination > 0 && destination != pos) {
                    String arrowText = "→ " + destination;
                    gc.setFill(javafx.scene.paint.Color.BLANCHEDALMOND);
                    gc.fillText(arrowText, x + 5, y + 30);
                }
            }
        }

        // Highlighter målfelt for stige og slange
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
                gc.setFill(javafx.scene.paint.Color.LAWNGREEN);
            } else if (tile.getAction() instanceof BackAction) {
                gc.setFill(javafx.scene.paint.Color.LIGHTSALMON);
            }

            gc.fillRect(destX, destY, TILE_SIZE, TILE_SIZE);
            gc.setStroke(javafx.scene.paint.Color.BLACK);
            gc.strokeRect(destX, destY, TILE_SIZE, TILE_SIZE);
            gc.setFill(javafx.scene.paint.Color.BLACK);
            gc.fillText(String.valueOf(destination), destX + 5, destY + 15);
        }

        // Tegn alle spillerbrikker
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
                gc.drawImage(tokenImage, x + 5, y + 5, TILE_SIZE - 10, TILE_SIZE - 10); // Tegn spillerbrikke
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
            stage.setResizable(false);
            stage.setScene(startScreen.getScene(stage));
        }
    }
}
