package edu.ntnu.boardgame.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;

/**
 * Utility class for reading and writing player data to/from JSON or CSV files.
 */
public class PlayerFileHandler {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // === JSON ===
    /**
     * Reads a list of players from a JSON file.
     */
    public static List<Player> readFromJSON(File file) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<ArrayList<Player>>() {
            }.getType();
            List<Player> players = gson.fromJson(reader, type);

            Tile startTile = new Tile(1); // Placeholder, set in controller
            for (Player p : players) {
                p.setCurrentTile(startTile);
            }
            return players;
        }
    }

    /**
     * Writes players to a JSON file.
     */
    public static void writeToJSON(File file, List<Player> players) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(players, writer);
        }
    }

    // === CSV ===
    /**
     * Reads players from a CSV file. Format: name,age,token
     */
    public static List<Player> readFromCSV(File file) throws IOException {
        List<Player> players = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    continue;
                }

                String name = parts[0].trim();
                int age = Integer.parseInt(parts[1].trim());
                String token = parts[2].trim();

                Player player = new Player(name, new Tile(1), age);
                player.setToken(token);
                players.add(player);
            }
        }
        return players;
    }

    /**
     * Writes players to a CSV file in format: name,age,token
     */
    public static void writeToCSV(File file, List<Player> players) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Player p : players) {
                writer.write(p.getName() + "," + p.getAge() + "," + p.getToken());
                writer.newLine();
            }
        }
    }
}
