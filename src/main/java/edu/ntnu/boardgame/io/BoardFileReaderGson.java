package edu.ntnu.boardgame.io;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.exceptions.InvalidBoardFileException;

public class BoardFileReaderGson implements BoardFileReader {

    @Override
    public Board readBoard(Path path) throws InvalidBoardFileException {
        try (FileReader reader = new FileReader(path.toFile())) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            int size = root.get("size").getAsInt();
            Board board = new Board(size);

            if (root.has("tiles")) {
                JsonArray tilesArray = root.getAsJsonArray("tiles");

                for (int i = 0; i < tilesArray.size(); i++) {
                    JsonObject tileObj = tilesArray.get(i).getAsJsonObject();
                    int position = tileObj.get("position").getAsInt();
                    board.getTile(position); // Gjenbruker eksisterende tile
                }
            }

            return board;

        } catch (IOException e) {
            throw new InvalidBoardFileException("IO-feil ved lesing av brettspill: " + path.getFileName(), e);
        } catch (JsonParseException e) {
            throw new InvalidBoardFileException("JSON-formatfeil i brettfil: " + path.getFileName(), e);
        } catch (NullPointerException e) {
            throw new InvalidBoardFileException("Ugyldig eller manglende data i brettfil: " + path.getFileName(), e);
        }
    }
}
