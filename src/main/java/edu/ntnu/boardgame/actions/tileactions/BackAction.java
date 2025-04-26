package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;



public class BackAction implements TileAction {

    private final int destination;

    public BackAction(int destination) {
        this.destination = destination;
    }

    @Override
    public void execute(Player player, Board board) {
        player.setPosition(destination, board);
    }

    @Override
    public int getDestination(){
        return destination; 
    }
}
