package edu.ntnu.boardgame.actions.puzzleactions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.actions.tileactions.TileAction;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.controllers.ChessPuzzleViewController;

/**
 * Action that triggers a chess puzzle when a player lands on the tile.
 */
public class ChessPuzzleAction implements PuzzleTileAction, TileAction {

    private Boardgame boardgame;

    /**
     * Constructs a ChessPuzzleAction linked to the main boardgame.
     *
     * @param boardgame the boardgame this action belongs to
     */
    public ChessPuzzleAction(Boardgame boardgame) {
        this.boardgame = boardgame;
    }

    /**
     * Executes the chess puzzle view when a player lands on the tile. This
     * version is used when no specific callback is needed.
     *
     * @param player the player who landed on the tile
     * @param board the board the player is playing on
     */
    @Override
    public void execute(Player player, Board board) {
        execute(player, board, () -> {
        });
    }

    /**
     * Executes the chess puzzle view with a callback after the puzzle is
     * solved. Checks for win after the puzzle is completed.
     *
     * @param player the player who landed on the tile
     * @param board the board the player is playing on
     * @param onPuzzleComplete the callback to run after puzzle is done
     */
    @Override
    public void execute(Player player, Board board, Runnable onPuzzleComplete) {
        Runnable callback = () -> {
            onPuzzleComplete.run();
            boardgame.checkForWin(player);
        };

        ChessPuzzleViewController controller = new ChessPuzzleViewController(
                player,
                board,
                callback,
                boardgame
        );
        controller.startPuzzle();
    }

    /**
     * Chess puzzles do not lead to a fixed destination.
     *
     * @return -1 to indicate no destination
     */
    @Override
    public int getDestination() {
        return -1;
    }

    /**
     * Sets boardgame after deserialization from JSON.
     *
     * @param boardgame the boardgame to assign
     */
    public void setBoardgame(Boardgame boardgame) {
        this.boardgame = boardgame;
    }
}
