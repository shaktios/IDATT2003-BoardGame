package edu.ntnu.boardgame;

import edu.ntnu.boardgame.constructors.Tile;

import java.util.ArrayList;
import java.util.List;

/*
    * A class representing a board in a board game.
    * The class has a constructor that takes the size of the board as an argument.
    * The class has a method that returns the tile at a given position.
    *
 */
public class Board {
  private final List<Tile> tiles;
  private final int size;

  /*
    * Constructor for the Board class.
    * @param size The size of the board.
    * @throws IllegalArgumentException if the size is less than 1.
   */
  public Board(int size){
    if (size <= 0) {
      throw new IllegalArgumentException("Board-størrelsen må være større enn 0.");
    }

    this.tiles = new ArrayList<>(); 
    this.size = size; 

    for (int i = 1; i <= size; i++) {
      tiles.add(new Tile(i)); 
    }
  }

  /*
    * Returns the tile at a given position.
    * @param position The position of the tile.
    * @return The tile at the given position.
    * @throws IllegalArgumentException if the position is less than 1 or greater than the size of the board.
   */
  public Tile getTile(int position){
    if (position >= 1 && position <= tiles.size()) {
      return tiles.get(position - 1);
    }
    throw new IllegalArgumentException("Ugyldig posisjon: " + position);
  }

  /*
    * Returns the size of the board.
    * @return The size of the board.
   */
  public int getSize() {
    return tiles.size();
  }


  //metode for å sette fra en tile til en annen tile
  public void setNextTile(int from, int to) {
    if (from < 1 || from > size || to < 1 || to > size) {
        throw new IllegalArgumentException("Ugyldig posisjon: " + from + " -> " + to);
    }
    tiles.get(from - 1).setNextTile(tiles.get(to - 1));
}

    
}
