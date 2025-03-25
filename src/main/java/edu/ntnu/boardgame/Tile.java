package edu.ntnu.boardgame;


/* Representerer ett felt på spillbrettet. I denne mappen har vi sagt at felt skal henge etter hverandre.
Derfor

* har et felt alltid en referanse til «neste felt». Dersom det ikke finnes et «neste felt» er feltet det
* siste feltet i spillet (altså mål).
* Et felt kan ha tilknyttet en «aksjon/handling» (engelsk: Action) som blir utført på den spilleren
* som lander på feltet. 
*/

public class Tile {
  private final int position; //en tile burde være immutabel, posisjonen flyttes ikke på brettet --> bruker derfor final...
  private Tile nextTile;
    
  public Tile(int position) {
    if (position <= 0) {
    throw new IllegalArgumentException("Posisjonen må være et positivt tall.");
  }

    this.position = position; 
  }

  public int getPosition() {
    return position; 
  }

  public void setNextTile(Tile nextTile) {
    this.nextTile = nextTile;
}

public Tile getNextTile() {
    return this.nextTile;
}

    
}
