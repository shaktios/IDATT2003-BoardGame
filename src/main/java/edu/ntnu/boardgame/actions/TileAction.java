package edu.ntnu.boardgame.actions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;



public interface TileAction{
    void execute(Player player, Board board); 
}
