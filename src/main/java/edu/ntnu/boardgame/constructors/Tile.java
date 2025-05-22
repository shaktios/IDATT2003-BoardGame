package edu.ntnu.boardgame.constructors;

import edu.ntnu.boardgame.actions.tileactions.TileAction;

/**
 * Represents a single tile on the game board. Each tile has a unique position
 * and may contain a reference to the next tile, allowing tiles to be linked in
 * a chain. A tile may also have an associated {@link TileAction} that is
 * executed when a player lands on it. Coordinates (x, y) are optional and used
 * for GUI placement.
 */
public class Tile {

    private final int position; //en tile burde være immutabel, posisjonen flyttes ikke på brettet --> bruker derfor final...
    private int x = -1;
    private int y = -1;
    private Tile nextTile;
    private TileAction action; // Nytt felt for tile-effekt

    /**
     * Constructs a new Tile with the given position.
     *
     * @param position the tile's position on the board (must be positive)
     * @throws IllegalArgumentException if the position is less than or equal to
     * 0
     */
    public Tile(int position) {
        if (position <= 0) {
            throw new IllegalArgumentException("Posisjonen må være et positivt tall.");
        }

        this.position = position;
    }

    /**
     * Sets the x-coordinate of this tile (used for GUI placement).
     *
     * @param x the x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of this tile (used for GUI placement).
     *
     * @param y the y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the tile.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the tile.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the position number of this tile.
     *
     * @return the tile's position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the reference to the next tile.
     *
     * @param nextTile the next tile in the board path
     */
    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
    }

    /**
     * Returns the next tile linked to this tile.
     *
     * @return the next tile
     */
    public Tile getNextTile() {
        return this.nextTile;
    }

    /**
     * Sets a tile action that should be executed when a player lands on this
     * tile.
     *
     * @param action the action to assign
     */
    public void setAction(TileAction action) {
        this.action = action;
    }

    /**
     * Returns the action associated with this tile, if any.
     *
     * @return the tile's action
     */
    public TileAction getAction() {
        return this.action;
    }

    /**
     * Executes the assigned action for a given player and board, if any exists.
     *
     * @param player the player who landed on the tile
     * @param board the game board
     */
    public void executeAction(Player player, Board board) {
        if (action != null) {
            action.execute(player, board);
        }
    }

}
