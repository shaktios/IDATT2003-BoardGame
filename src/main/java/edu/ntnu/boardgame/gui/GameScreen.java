package edu.ntnu.boardgame.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.constructors.Dice;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.observer.BoardGameObserver;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

            if (newPosition == board.getSize()) {
                boardgame.notifyGameWon(player);
            }

            boardgame.notifyPlayerMoved(player);

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

        // Tegn ruter
        for (int pos = 1; pos <= board.getSize(); pos++) {
            int rowIndex = (rows * cols - pos) / cols;
            int colIndex = (pos - 1) % cols;

            // Zigzag: speilvendte kolonner for oddetallsrader
            if ((rows - rowIndex) % 2 == 0) {
                colIndex = cols - 1 - colIndex;
            }

            double x = colIndex * TILE_SIZE;
            double y = rowIndex * TILE_SIZE;

            gc.strokeRect(x, y, TILE_SIZE, TILE_SIZE);
            gc.strokeText(String.valueOf(pos), x + 5, y + 15);
        }

        // Tegn spillere
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
            throwDiceButton.setDisable(true);
            nextTurnButton.setDisable(true);
        }
    }
}
