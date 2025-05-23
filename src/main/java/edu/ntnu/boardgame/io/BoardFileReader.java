package edu.ntnu.boardgame.io;

import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.exceptions.InvalidBoardFileException;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This interface reads board structures for the set boards for a game
 * This means it has the ability to read colomns, rows and other
 * key component for reading a board.
 */
public interface BoardFileReader {

  /**
   * Reads only the board structure (tiles, actions, etc.) from a file.
   *
   * @param path the path to the board JSON file
   * @return a Board object
   * @throws IOException if the file cannot be read
   * @throws InvalidBoardFileException if the file format is invalid
   */
  Board readBoard(Path path) throws IOException, InvalidBoardFileException;

  /**
   * Reads the complete board game configuration including board and dice.
   *
   * @param path the path to the board game JSON file
   * @return a Boardgame object
   * @throws IOException if the file cannot be read
   * @throws InvalidBoardFileException if the file format is invalid
   */
  Boardgame readBoardgame(Path path) throws IOException, InvalidBoardFileException;
}
