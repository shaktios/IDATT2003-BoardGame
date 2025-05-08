package edu.ntnu.boardgame.controllers;

import java.util.List;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.actions.puzzleactions.PuzzleTileAction;
import edu.ntnu.boardgame.actions.tileactions.TileAction;
import edu.ntnu.boardgame.constructors.Dice;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.observer.BoardGameObserver;
import edu.ntnu.boardgame.view.laddergame.LadderGameScreen;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller responsible for the logic of the LadderGame. Handles player turns,
 * dice rolls, tile actions, and communication between model and view.
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
    public LadderGameController(Boardgame boardgame, LadderGameScreen view, Stage stage, String gameVariant) {
        this.boardgame = boardgame;
        this.board = boardgame.getBoard();
        this.players = boardgame.getPlayers();
        this.currentPlayerIndex = 0;
        this.view = view;
        this.stage = stage;
        this.gameVariant = gameVariant;

        boardgame.registerObserver(new GameObserver());
    }

    /**
     * Handles the dice roll and player movement.
     */
    public void handleDiceRoll(Stage stage) {
        Player player = players.get(currentPlayerIndex);
        Dice dice = new Dice(6, 1);
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
     * Handles when the player lands on a special tile.
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
     * Switches to the next player's turn.
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
     * Displays a win message and redirects to StartScreenView.
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

    private class GameObserver implements BoardGameObserver {

        @Override
        public void onPlayerMove(Player player) {
            view.redrawBoard();
        }

        @Override
        public void onGameWon(Player winner) {
            showWinMessage(winner);
        }
    }
}
