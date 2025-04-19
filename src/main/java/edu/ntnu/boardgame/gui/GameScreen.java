package edu.ntnu.boardgame.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.actions.BackAction;
import edu.ntnu.boardgame.actions.LadderAction;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameScreen {

    private static final int TILE_SIZE = 50;

    private int currentPlayerIndex = 0;
    private List<Player> players;
    private Board board;
    private Label currentPlayerLabel = new Label("Spillet starter!");
    private Canvas canvas;

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

        // laster inn spillerbrikkerbilder 
        for (Player player : players) {
            String token = player.getToken(); // f.eks. "Horse", "Queen" osv.
            Image tokenImage = new Image(getClass().getResourceAsStream("/images/" + token + ".png"));
            playerTokens.put(player.getName(), tokenImage);
        }


        // Tegn brett og brikker første gang
        drawBoard(gc);

        // Kast terning knapp
        throwDiceButton.setDisable(false);
        throwDiceButton.setOnAction(event -> {
            Player player = players.get(currentPlayerIndex);
            Dice dice = new Dice(6, 1);
            int roll = dice.roll();

            int newPosition = player.getPosition() + roll;
            if (newPosition > board.getSize()) {
                newPosition = board.getSize();
            }

            player.setPosition(newPosition, board);
            Tile newTile = board.getTile(newPosition);
            player.setCurrentTile(newTile);

            if (newTile.getAction() != null) {
            String actionType = newTile.getAction().getClass().getSimpleName();
            String actionText = "";

            if (actionType.equals("LadderAction")) {
                actionText = "brukte en stige";
            } else if (actionType.equals("BackAction")) {
                actionText = "traff en slange";
            } else {
                actionText = "ble påvirket av en handling";
            }

            currentPlayerLabel.setText(player.getName() + " landet på rute " + newPosition +
                    " og " + actionText + "...");

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> {
                newTile.executeAction(player, board);
                boardgame.notifyPlayerMoved(player);
                drawBoard(canvas.getGraphicsContext2D());

                // Oppdater tekst etter handling
                currentPlayerLabel.setText(player.getName() + " er nå på rute " + player.getPosition());
            });
            
            pause.play();
        } else {
            currentPlayerLabel.setText(player.getName() + " kastet " + roll +
                    " og flyttet til rute " + newPosition);
            boardgame.notifyPlayerMoved(player);
        }

            //sjekk om en spiller har vunnet... 
            if (newPosition == board.getSize()) {
                boardgame.notifyGameWon(player);
            }


            throwDiceButton.setDisable(true);      // spiller har kastet, disable
            nextTurnButton.setDisable(false);      // neste spiller kan klikkes
            
        });

        // Neste tur knapp
        throwDiceButton.setDisable(false); // kun aktiv spiller får kaste i starten
        nextTurnButton.setDisable(true);   // skal være deaktivert i starten
        nextTurnButton.setOnAction(event -> {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            Player nextPlayer = players.get(currentPlayerIndex);
            currentPlayerLabel.setText("Spiller sin tur: " + nextPlayer.getName());

            throwDiceButton.setDisable(false);     // ny spiller får kaste
            nextTurnButton.setDisable(true);       // disable til kastet er gjort

        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(canvas, currentPlayerLabel, throwDiceButton, nextTurnButton);

        return new Scene(layout, canvasWidth + 20, canvasHeight + 120);
    }

    private void drawBoard(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


        int cols = board.getColumns();
        int rows = board.getRows();


        // Tegner alle hovedruter (standard, mørkegrønn, mørkerød)
        for (int pos = 1; pos <= board.getSize(); pos++) {
            Tile tile = board.getTile(pos);
            int rowIndex = (rows * cols - pos) / cols;
            int colIndex = (pos - 1) % cols;


            // Zigzag: speilvendte kolonner for oddetallsrader
            if ((rows - rowIndex) % 2 == 0) {
                colIndex = cols - 1 - colIndex;
            }

            double x = colIndex * TILE_SIZE;
            double y = rowIndex * TILE_SIZE;

            // Farge bakgrunn basert på action
            if (tile.getAction() instanceof LadderAction) {
                gc.setFill(javafx.scene.paint.Color.GREEN);
            } else if (tile.getAction() instanceof BackAction) {
                gc.setFill(javafx.scene.paint.Color.RED);
            } else {
                gc.setFill(javafx.scene.paint.Color.WHITE);
            }

            gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);
            gc.setStroke(javafx.scene.paint.Color.BLACK);
            gc.strokeRect(x, y, TILE_SIZE, TILE_SIZE);
            gc.setFill(javafx.scene.paint.Color.BLACK);
            gc.fillText(String.valueOf(pos), x + 5, y + 15);
            if(tile.getAction() != null){
                int destination = tile.getAction().getDestination();
                String arrowText = "→ " + destination;
                gc.setFill(javafx.scene.paint.Color.BLANCHEDALMOND);
                gc.fillText(arrowText, x + 5, y + 30);
            }
        }

        //highlighter destinasjonsfeltene for Ladder/Back
        for (int pos = 1; pos <= board.getSize(); pos++) {
            Tile tile = board.getTile(pos);
            if (tile.getAction() == null) {
                continue;
            }

            int destination = tile.getAction().getDestination();
            Tile destTile = board.getTile(destination);
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

        //tegn spillere
        for (Player player : players) {
            int pos = player.getPosition();
            int rowIndex = (rows * cols - pos) / cols;
            int colIndex = (pos - 1) % cols;


            // ZIGZAG-SPEILING 
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
            currentPlayerLabel.setText(player.getName() + " flyttet til rute " + player.getPosition());
            drawBoard(canvas.getGraphicsContext2D());
        }

        @Override
        public void onGameWon(Player winner) {
            currentPlayerLabel.setText(winner.getName() + " vant spillet!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spillet er ferdig");
            alert.setHeaderText(winner.getName() + " har vunnet spillet!");
            alert.setContentText("Du sendes tilbake til startskjermen.");

            alert.showAndWait(); //  viser popup

            // Restart til StartScreen 
            StartScreen startScreen = new StartScreen();
            Stage stage = (Stage) canvas.getScene().getWindow();
            stage.setScene(startScreen.getScene(stage));
        }
    }
}
