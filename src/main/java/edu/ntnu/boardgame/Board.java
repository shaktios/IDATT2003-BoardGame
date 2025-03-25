package edu.ntnu.boardgame;

import java.util.ArrayList;
import java.util.List;

public class Board {
  private final List<Tile> tiles; 
  private final int size;

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

  public Tile getTile(int position){
    if (position >= 1 && position <= tiles.size()) {
      return tiles.get(position - 1);
    }
    throw new IllegalArgumentException("Ugyldig posisjon: " + position);
  }

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
