package edu.ntnu.boardgame.actions;



import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;

public class LadderAction implements TileAction{

    private final int destination;

    public LadderAction(int destination){
        this.destination = destination; 
    }

    @Override
    public void execute(Player player, Board board){
        player.setPosition(destination, board); 
    }

}
