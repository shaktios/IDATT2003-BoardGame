package edu.ntnu.boardgame.io;

import edu.ntnu.boardgame.constructors.Board;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Interface for writing a {@link Board} to a file.
 * Implementations of this interface handle serialization of board data (rows,
 * columns, tiles, actions, etc.) to a specified format such as JSON.
 */
public interface BoardFileWriter {

  /**
   * Writes the board data to a file at the specified path.
   *
   * @param path the file path to write to
   * @param board the board to be saved
   * @throws IOException if an error occurs while writing to the file
   */
  void writeBoard(Path path, Board board) throws IOException;
}
