package jw.lab4.checkers;

public class MoveInstructions {

  public int player = -1;
  public int field = -1;
  public int dir = -1;

  public enum STATE {
    PLAY,
    REQUEST,
    START,
    ERROR
  }

  public STATE state = STATE.PLAY;

  public MoveInstructions() {
  }

  public MoveInstructions(STATE state) {
    this.state = state;
  }

  public MoveInstructions(int player) {
    state = STATE.REQUEST;
    this.player = player;
  }

  public MoveInstructions(int field, int dir) {
    this.field = field;
    this.dir = dir;
  }

  public String serialize() {
    String str = "";
    if (state == STATE.PLAY) {
      if (player >= -1) {
        str = Integer.toString(player);
      }
      str += field + ";" + dir;

    } else if (state == STATE.REQUEST) {
      if (player >= -1) {
        str = Integer.toString(player);
      }

    } else {
      str = state.toString();
    }
    return str;
  }

  public void deserialize(String str) {
    String[] tokens = str.split(";");
    if (tokens.length == 0) {

    } else if (tokens.length == 1) {
      try {
        player = Integer.parseInt(tokens[0]);
        state = STATE.REQUEST;
      } catch (NumberFormatException e) {
        state = STATE.valueOf(tokens[0]);
      }
    } else if (tokens.length == 2) {
      state = STATE.PLAY;
      field = Integer.parseInt(tokens[0]);
      dir = Integer.parseInt(tokens[1]);
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
    if (field != move.field) {
      return false;
    }
    if (dir != move.dir) {
      return false;
    }

    return true;
  }
}
