package edu.ntnu.boardgame.exceptions;


/**
 * Custom exception thrown when a board file (typically JSON) is invalid or
 * cannot be read properly.
 * Used to signal format errors, missing fields, or unexpected data while
 * parsing board or game configuration files.
 */
public class InvalidBoardFileException extends Exception {


  /**
   * Constructs an exception with a specific error message.
   *
   * @param message the description of what went wrong
   */
  public InvalidBoardFileException(String message) {
    super(message);
  }

  /**
   * Constructs an exception with a message and an underlying cause.
   *
   * @param message the description of what went wrong
   * @param cause the original exception that caused this error
   */
  public InvalidBoardFileException(String message, Throwable cause) {
    super(message, cause);
  }
}
