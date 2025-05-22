package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Player;



public interface TileAction{
    void execute(Player player, Board board); 
    int getDestination(); 
}
