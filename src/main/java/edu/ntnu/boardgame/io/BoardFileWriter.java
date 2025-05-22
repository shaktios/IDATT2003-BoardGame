package edu.ntnu.boardgame.io;

import java.io.IOException;
import java.nio.file.Path;

import edu.ntnu.boardgame.constructors.Board;



public interface BoardFileWriter {
    void writeBoard(Path path, Board board) throws IOException;
}
