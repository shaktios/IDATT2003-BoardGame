package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.constructors.Player;

public class LadderAction implements TileAction {

  private final int destination;
  private Boardgame boardgame; // Ikke final

  public LadderAction(int destination) {
        this.destination = destination;
    }

  public void setBoardgame(Boardgame boardgame) {
        this.boardgame = boardgame;
    }

  @Override
  public void execute(Player player, Board board) {
    player.setPosition(destination, board);
    if (boardgame != null) {
      boardgame.checkForWin(player);
    }
  }

  @Override
  public int getDestination() {
        return destination;
    }
}
