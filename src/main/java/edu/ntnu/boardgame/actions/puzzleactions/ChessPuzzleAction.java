package edu.ntnu.boardgame.actions.puzzleactions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.actions.tileactions.TileAction;
import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.controllers.ChessPuzzleViewController;

/**
 * Action that triggers a chess puzzle when a player lands on the tile.
 */
public class ChessPuzzleAction implements PuzzleTileAction, TileAction { // 

    private Boardgame boardgame;
    

    /**
     * Constructs a ChessPuzzleAction linked to the main boardgame.
     *
     * @param boardgame the main Boardgame instance to update the game after
     * puzzle
     */
    public ChessPuzzleAction(Boardgame boardgame) {
        this.boardgame = boardgame;
    }

    /**
     * Executes the chess puzzle view when a player lands on the tile.
     *
     * @param player the player who landed on the tile
     * @param board the board the player is playing on
     */
    @Override
    public void execute(Player player, Board board) {
        // Siden TileAction krever denne execute-metoden
        // Vi bare kaller execute(...) med en dummy
        execute(player, board, () -> {
        });
    }

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
     * Chess puzzles do not have a fixed destination like ladders/snakes.
     *
     * @return -1 (no destination)
     */
    @Override
    public int getDestination() {
        return -1;
    }

    /**
     * Sets Boardgame after an action is made (for JSON-import).
     *
     * @param boardgame the boardgame to link
     */
    public void setBoardgame(Boardgame boardgame) {
        this.boardgame = boardgame;
    }

}
