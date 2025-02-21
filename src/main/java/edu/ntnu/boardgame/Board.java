package edu.ntnu.boardgame;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final List<Tile> tiles; 

    public Board(int size){
        if (size <= 0) {
            throw new IllegalArgumentException("Board-størrelsen må være større enn 0.");
        }

        this.tiles = new ArrayList<>(); 
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

    
}
