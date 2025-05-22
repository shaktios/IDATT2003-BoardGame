package edu.ntnu.boardgame.constructors;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a board in a board game. The board consists of a number
 * of tiles arranged in rows and columns.
 */
public class Board {

    private final List<Tile> tiles;
    private final int rows;
    private final int columns;

    /**
     * Constructs a new Board with a given number of rows and columns.
     *
     * @param rows number of rows on the board
     * @param columns number of columns on the board
     * @throws IllegalArgumentException if rows or columns are less than 1
     */
    public Board(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Rader og kolonner må være større enn 0.");
        }

        this.rows = rows;
        this.columns = columns;
        this.tiles = new ArrayList<>();

        int totalTiles = rows * columns;
        for (int i = 1; i <= totalTiles; i++) {
            tiles.add(new Tile(i));
        }
    }

    /**
     * Returns the tile at a given position.
     *
     * @param position the position of the tile (1-based index)
     * @return the Tile object at the given position
     * @throws IllegalArgumentException if the position is invalid
     */
    public Tile getTile(int position) {
        if (position >= 1 && position <= tiles.size()) {
            return tiles.get(position - 1);
        }
        throw new IllegalArgumentException("Ugyldig posisjon: " + position);
    }

    /**
     * @return total number of tiles on the board
     */
    public int getSize() {
        return tiles.size();
    }

    /**
     * @return number of rows on the board
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return number of columns on the board
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Sets a tile to link to another tile (e.g., for ladders or shortcuts).
     *
     * @param from position to link from
     * @param to position to link to
     * @throws IllegalArgumentException if either position is invalid
     */
    public void setNextTile(int from, int to) {
        if (from < 1 || from > getSize() || to < 1 || to > getSize()) {
            throw new IllegalArgumentException("Ugyldig posisjon: " + from + " -> " + to);
        }
        tiles.get(from - 1).setNextTile(tiles.get(to - 1));
    }
}
