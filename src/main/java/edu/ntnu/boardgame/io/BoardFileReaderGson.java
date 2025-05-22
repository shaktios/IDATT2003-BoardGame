package edu.ntnu.boardgame.io;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import edu.ntnu.boardgame.actions.puzzleactions.ChessPuzzleAction;
import edu.ntnu.boardgame.actions.tileactions.BackAction;
import edu.ntnu.boardgame.actions.tileactions.LadderAction;
import edu.ntnu.boardgame.actions.tileactions.ResetAction;
import edu.ntnu.boardgame.actions.tileactions.SkipTurnAction;
import edu.ntnu.boardgame.actions.tileactions.TeleportRandomAction;
import edu.ntnu.boardgame.constructors.Board;
import edu.ntnu.boardgame.constructors.Boardgame;
import edu.ntnu.boardgame.constructors.Tile;
import edu.ntnu.boardgame.exceptions.InvalidBoardFileException;

/**
 * A concrete implementation of the {@link BoardFileReader} interface that uses
 * the Gson library to read board configuration and boardgame data from a JSON
 * file.
 * <p>
 * Supports reading tile positions, actions, and game metadata such as dice
 * count and sides.
 */
public class BoardFileReaderGson implements BoardFileReader {

    /**
     * Reads a {@link Board} from a JSON file. This method parses the board's
     * row and column count, initializes the tiles, and assigns any defined tile
     * actions (e.g., LadderAction, BackAction).
     *
     * @param path the path to the JSON file
     * @return the constructed {@link Board} object
     * @throws InvalidBoardFileException if the file cannot be read or contains
     * invalid content
     */
    @Override
    public Board readBoard(Path path) throws InvalidBoardFileException {
        try (FileReader reader = new FileReader(path.toFile())) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            int rows = root.get("rows").getAsInt();
            int columns = root.get("columns").getAsInt();

            Board board = new Board(rows, columns);

            if (root.has("tiles")) {
                JsonArray tilesArray = root.getAsJsonArray("tiles");

                for (int i = 0; i < tilesArray.size(); i++) {
                    JsonObject tileObj = tilesArray.get(i).getAsJsonObject();
                    int position = tileObj.get("position").getAsInt();
                    Tile tile = board.getTile(position);

                    if (tileObj.has("x")) {
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

        } catch (IOException | JsonParseException | NullPointerException e) {
            throw new InvalidBoardFileException("Feil ved lesing av brettfil: " + path.getFileName(), e);
        }
    }

    /**
     * Reads a complete {@link Boardgame} from a JSON file, including the board
     * structure and dice configuration.
     * 
     * After reading the board, it connects the {@link Boardgame} instance to
     * any {@link ChessPuzzleAction} instances found in the tiles.
     *
     * @param path the path to the JSON file
     * @return the constructed {@link Boardgame} object
     * @throws InvalidBoardFileException if the file cannot be parsed or
     * contains invalid structure
     */
    public Boardgame readBoardgame(Path path) throws InvalidBoardFileException {
        Board board = readBoard(path);

        try (FileReader reader = new FileReader(path.toFile())) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            if (!root.has("dice")) {
                throw new InvalidBoardFileException("Manglende 'dice'-objekt i JSON: " + path.getFileName());
            }

            JsonObject diceObj = root.getAsJsonObject("dice");
            int count = diceObj.get("count").getAsInt();
            int sides = diceObj.get("sides").getAsInt();

            Boardgame boardgame = new Boardgame(board, count, sides);

            // Koble boardgame inn i eventuelle sjakkfelter
            for (int i = 1; i <= board.getSize(); i++) {
                if (board.getTile(i).getAction() instanceof ChessPuzzleAction action) {
                    action.setBoardgame(boardgame);
                }
            }

            return boardgame;

        } catch (IOException | JsonParseException | NullPointerException e) {
            throw new InvalidBoardFileException("Feil ved lesing av boardgame/dice fra fil: " + path.getFileName(), e);
        }
    }
}
