package edu.ntnu.boardgame.io;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import edu.ntnu.boardgame.Board;
import edu.ntnu.boardgame.actions.puzzleactions.ChessPuzzleAction;
import edu.ntnu.boardgame.actions.tileactions.BackAction;
import edu.ntnu.boardgame.actions.tileactions.LadderAction;
import edu.ntnu.boardgame.actions.tileactions.ResetAction;
import edu.ntnu.boardgame.actions.tileactions.SkipTurnAction;
import edu.ntnu.boardgame.actions.tileactions.TeleportRandomAction;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.exceptions.InvalidBoardFileException;

/*
    * A class for reading a board from a file using the Gson library.
 */
public class BoardFileReaderGson implements BoardFileReader {

    @Override
    public Board readBoard(Path path) throws InvalidBoardFileException {
        try (FileReader reader = new FileReader(path.toFile())) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            int size = root.get("size").getAsInt();
            int rows = root.get("rows").getAsInt();
            int columns = root.get("columns").getAsInt();

            Board board = new Board(rows,columns);

            if (root.has("tiles")) {
                JsonArray tilesArray = root.getAsJsonArray("tiles");

                for (int i = 0; i < tilesArray.size(); i++) {
                    JsonObject tileObj = tilesArray.get(i).getAsJsonObject();
                    int position = tileObj.get("position").getAsInt();
                    board.getTile(position); // Gjenbruker eksisterende tile

                    Tile tile = board.getTile(position);

                    if(tileObj.has("x")){
                        tile.setX(tileObj.get("x").getAsInt()); 
                    }

                    if (tileObj.has("y")) {
                        tile.setY(tileObj.get("y").getAsInt());
                    }
                    if (tileObj.has("action")) {
                    JsonObject actionObj = tileObj.getAsJsonObject("action");
                    String type = actionObj.get("type").getAsString();

                    switch (type) {
                        case "LADDER":
                            int destUp = actionObj.get("destination").getAsInt();
                            tile.setAction(new LadderAction(destUp));
                            break;
                        case "BACK":
                            int destDown = actionObj.get("destination").getAsInt();
                            tile.setAction(new BackAction(destDown));
                            break;
                        case "RESET":
                            tile.setAction(new ResetAction());
                            break;
                        case "SKIP":
                            tile.setAction(new SkipTurnAction());
                            break;
                        case "TELEPORT_RANDOM":
                            tile.setAction(new TeleportRandomAction());
                            break;
                        case "CHESSPUZZLE":
                            tile.setAction(new ChessPuzzleAction(null)); 
                            break;
                        default:
                            System.out.println("Ukjent action-type: " + type);
                    }
                }

                    
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
