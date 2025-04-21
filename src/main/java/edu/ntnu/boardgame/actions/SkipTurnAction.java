package edu.ntnu.boardgame.actions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.constructors.Player;

public class SkipTurnAction implements TileAction {

    @Override
    public void execute(Player player, Board board) {
        player.setSkipNextTurn(true);  // Forteller at spilleren skal stå over neste tur
    }

    @Override
    public int getDestination() {
        return -1; // Ikke relevant, denne actionen ikke flytter spilleren
    }
}
