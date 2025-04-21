package edu.ntnu.boardgame.actions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;

public class TeleportRandomAction implements TileAction {

    @Override
    public void execute(Player player, Board board) {
        int boardSize = board.getSize();                       // antall felter
        double randomValue = Math.random();                    // Tilfeldig verdi [0.0, 1.0)
        int randomDestination = (int) (randomValue * boardSize) + 1; // Gyldig tile mellom 1 og boardSize

        player.setPosition(randomDestination, board);
    }

    @Override
    public int getDestination() {
        return -1; //Ingen bestemt destinasjon, brukes bare til visuell markering. Ikke relevant for teleport (da det er tilfeldig)
    }
}

