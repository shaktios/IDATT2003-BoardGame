package edu.ntnu.boardgame;

import edu.ntnu.boardgame.constructors.Dice;

/**
 *
 *
 */
public class App {

  public static void main(String[] args) {
    Dice dice = new Dice(6, 2); // 2 terninger med 6 sider hver
      System.out.println("Rolled: " + dice.roll());
  }
}
