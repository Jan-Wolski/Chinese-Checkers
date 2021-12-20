package jw.lab4.checkers;

public class MoveInstructions {

  public int field;
  public int dir;

  // fields used for veryfying purpouses, idk. if it'll be implemented
  public int player;
  public int checksum;

  public MoveInstructions() {
  }

  public MoveInstructions(int field, int dir) {
    this.field = field;
    this.dir = dir;
  }

  public String serialize() {
    String str = "";
    str += field + ";" + dir;
    return str;
  }

  public void deserialize(String str) {
    String[] tokens = str.split(";");
    field = Integer.parseInt(tokens[0]);
    dir = Integer.parseInt(tokens[1]);

  }

  public boolean compare(MoveInstructions move) {
    if (move == null) {
      return false;
    }
    if (player != move.player) {
      return false;
    }
    if (dir != move.dir) {
      return false;
    }
    return true;
  }
}
