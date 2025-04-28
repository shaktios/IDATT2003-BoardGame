package edu.ntnu.boardgame.controllers;

import java.util.List;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.actions.puzzleactions.PuzzleTileAction;
import edu.ntnu.boardgame.actions.tileactions.TileAction;
import edu.ntnu.boardgame.constructors.Dice;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.view.laddergame.LadderGameScreen;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller responsible for the logic of the LadderGame. Handles player turns,
 * dice rolls, tile actions, and communication between model and view.
 */
public class LadderGameController {

    private Boardgame boardgame;
    private Board board;
    private List<Player> players;
    private int currentPlayerIndex;
    private LadderGameScreen view;

    /**
     * Constructs the LadderGameController.
     *
     * @param boardgame the Boardgame instance
     * @param view the LadderGameScreen view
     */
    public LadderGameController(Boardgame boardgame, LadderGameScreen view) {
        this.boardgame = boardgame;
        this.board = boardgame.getBoard();
        this.players = boardgame.getPlayers();
        this.currentPlayerIndex = 0;
        this.view = view;
    }

    /**
     * Handles the dice roll and player movement.
     *
     * @param stage the current stage (for potential scene changes)
     */
    public void handleDiceRoll(Stage stage) {
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

        // Tegn brett på nytt NÅ
        view.redrawBoard();

        view.updateMessage(player.getName() + " kastet " + roll + " og flyttet til rute " + newPosition);

        if (newTile.getAction() instanceof PuzzleTileAction puzzleAction) {
            view.disableDiceAndNextTurnButtons();
            puzzleAction.execute(player, board, () -> {
                boardgame.notifyPlayerMoved(player);
                view.redrawBoard();
                view.updateMessage(player.getName() + " er nå på rute " + player.getPosition());
                if (player.getPosition() == board.getSize()) {
                    view.showWinMessage(player,stage);
                } else {
                    view.enableNextTurnButton();
                }
            });
            return;
        }

        if (newTile.getAction() != null) {
            handleTileAction(player, newTile);
        } else {
            boardgame.notifyPlayerMoved(player);
            if (player.getPosition() == board.getSize()) {
                view.showWinMessage(player,stage);
            }
            view.disableDiceButton();
            view.enableNextTurnButton();
        }
    }

    /**
     * Handles when the player lands on a special tile.
     *
     * @param player the player
     * @param tile the tile
     */
    public void handleTileAction(Player player, Tile tile) {
        view.disableDiceAndNextTurnButtons();

        String actionType = tile.getAction().getClass().getSimpleName();
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
                "blir teleportert til et randomt sted";
            default ->
                "ble påvirket av en handling";
        };

        view.updateMessage(player.getName() + " landet på en spesialrute og " + actionText);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            TileAction tileAction = (TileAction) tile.getAction();
            tileAction.execute(player, board);
            boardgame.notifyPlayerMoved(player);
            view.redrawBoard();
            view.updateMessage(player.getName() + " er nå på rute " + player.getPosition());

            if (player.getPosition() == board.getSize()) {
                Stage currentStage = (Stage) view.getCanvas().getScene().getWindow();
                view.showWinMessage(player, currentStage);
            } else {
                view.enableNextTurnButton();
            }
        });
        pause.play();
    }

    /**
     * Handles switching to the next player.
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

}
