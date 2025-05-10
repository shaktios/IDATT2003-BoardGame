package edu.ntnu.boardgame.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.boardgame.constructors.Player;
import edu.ntnu.boardgame.constructors.Tile;

/*
    * A class for reading and writing player data to and from a CSV file.
    * The class has a method for reading player data from a CSV file.
    * The class has a method for writing player data to a CSV file.
 */
public class PlayerFileHandler {

    public static List<Player> readFromCSV(File file) throws IOException {
        List<Player> players = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length != 3) {
                    continue; // Hopper over ugyldige linjer
                }

                String name = tokens[0].trim();
                String token = tokens[1].trim();
                int age = Integer.parseInt(tokens[2].trim());

                Player player = new Player(name, new Tile(1), age); // startTile settes senere i BoardGame, har bare satt den som 1 nå for å unngå feil.
                player.setToken(token); // spillerens brikke (TopHat, RaceCar, osv.)
                players.add(player);
            }
        }

        return players;
    }

    public static void writeToCSV(File file, List<Player> players) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Player player : players) {
                writer.write(player.getName() + "," + player.getToken() + "," + player.getAge());
                writer.newLine();
            }
        }
    }
}
