package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;

public class ResetAction implements TileAction {


    //actionen flytter spilleren tilbake til første tile. 
    private final int destination = 1;  // Startfeltet er alltid posisjon 1

    @Override
    public void execute(Player player, Board board) {
        player.setPosition(destination, board);  // Flytt til start
    }

    @Override
    public int getDestination() {
        return destination;
    }
}
