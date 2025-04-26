package edu.ntnu.boardgame.actions.puzzleactions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;

/**
 * Interface for puzzle-based tile actions.
 */
public interface PuzzleTileAction {

    /**
     * Executes a puzzle action.
     *
     * @param player the player who landed on the tile
     * @param board the board
     * @param onPuzzleComplete callback to run when puzzle is solved
     */
    void execute(Player player, Board board, Runnable onPuzzleComplete);

    /**
     * Gets the destination tile if the action moves the player directly.
     *
     * @return the destination tile number, or -1 if none
     */
    int getDestination();
}
