package edu.ntnu.boardgame.actions.tileactions;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.Boardgame;
import edu.ntnu.boardgame.constructors.Player;

public class TeleportRandomAction implements TileAction {

  private Boardgame boardgame;

  @Override
  public void execute(Player player, Board board) {
    int boardSize = board.getSize();
    int randomDestination = (int) (Math.random() * boardSize) + 1;

    player.setPosition(randomDestination, board);

    if (boardgame != null) {
      boardgame.checkForWin(player);
    }
  }

  @Override
  public int getDestination() {
        return -1;
    }

  public void setBoardgame(Boardgame boardgame) {
        this.boardgame = boardgame;
    }
}
