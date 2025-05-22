package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Player;


/**
 * Represents an action that is triggered when a player lands on a tile.
 * Implementing classes define the effect and destination of the action.
 */
public interface TileAction{
    /**
     * Executes the tile action for a given player and board context.
     *
     * @param player the player who landed on the tile
     * @param board the game board the action applies to
     */
    void execute(Player player, Board board); 

    /**
     * Returns the destination tile position the player will be moved to, if
     * applicable. If the action does not involve movement, this may return -1
     * or a neutral value.
     *
     * @return the destination tile index or -1 if not applicable
     */
    int getDestination(); 
}
