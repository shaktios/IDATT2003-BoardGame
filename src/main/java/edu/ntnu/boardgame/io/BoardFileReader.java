package edu.ntnu.boardgame.io;

import java.io.IOException;
import java.nio.file.Path;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.exceptions.InvalidBoardFileException;

public interface BoardFileReader {

    Board readBoard(Path path) throws IOException, InvalidBoardFileException;

    
}
