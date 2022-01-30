package jw.lab4.checkers;

/**
 * Structure for passing move instructions.
 */
public class MoveInstructions {

  private final int paramNum = 5;

  /** Decides which player to move, or select player.*/
  public int player = -1;

  /** Field id from which the player moves.*/
  public int field1 = -1;

  /** Field id to which the player moves.*/
  public int field2 = -1;

  /** Field id to which the player moves.*/
  public String nick = "-";

  public enum STATE {
    PLAY,
    READY,
    LOAD,
    NEXT,
    JOIN,
    ERROR,
    CHANGE
  }

  /** Type of action to execute. */
  public STATE state = STATE.PLAY;

  public MoveInstructions() {
  }

  public MoveInstructions(STATE state) {
    this.state = state;
  }

  public MoveInstructions(String name) {
    state = STATE.READY;
    this.nick = name;
  }

  public MoveInstructions(int player) {
    state = STATE.READY;
    this.player = player;
  }

  public MoveInstructions(int field1, int field2) {
    this.field1 = field1;
    this.field2 = field2;
  }

  private String serializeInt(int vari) {
    String str = ";";
    str += Integer.toString(vari);
    return str;
  }

  private String serializeString(String vars) {
    String str = ";";
    str += vars;
    return str;
  }

  public String serialize() {
    String str = "";
    str = state.toString();
    str += serializeInt(player);
    str += serializeInt(field1);
    str += serializeInt(field2);
    str += serializeString(nick);
    return str;
  }

  public void deserialize(String str) {
    String[] tokens = str.split(";");
    if (tokens.length == paramNum) {
      state = STATE.valueOf(tokens[0]);
      player = Integer.parseInt(tokens[1]);
      field1 = Integer.parseInt(tokens[2]);
      field2 = Integer.parseInt(tokens[3]);
      nick = tokens[4];
    }
  }

  public boolean compare(MoveInstructions move) {
    if (move == null) {
      return false;
    }

    if (state != move.state) {
      return false;
    }
    if (player != move.player) {
      return false;
    }
    if (field1 != move.field1) {
      return false;
    }
    if (field2 != move.field2) {
      return false;
    }

    return true;
  }
}
