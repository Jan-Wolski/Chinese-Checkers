package jw.lab4.checkers;

public class Field {
  public Field[] neighbours;
  public int player = -1;
  public int base = -1;

  public Field(){

  }


  public Field(int count){
    neighbours = new Field[count];
  }


  boolean move(int dir) {
    if(player == -1){
      return false;
    }
    boolean good = neighbours[dir].move(dir, player, false);
    if (good) {
      player = -1;
    }

    return good;
  }

  boolean move(int dir, int playerNew, boolean taken) {
    if (taken && player >= 0) {
      return false;
    } else if (player >= 0) {
      return neighbours[dir].move(dir, playerNew, true);
    } else {
      player = playerNew;
      return true;
    }
  }
}
