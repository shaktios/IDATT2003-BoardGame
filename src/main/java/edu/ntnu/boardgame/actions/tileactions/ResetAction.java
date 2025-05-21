package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.constructors.Player;

public class ResetAction implements TileAction {

  private final int destination = 1;  // Startfeltet
  private Boardgame boardgame;

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
