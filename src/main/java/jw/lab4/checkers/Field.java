package jw.lab4.checkers;

/**
 * Board field class.
 */
public class Field {

  public Field[] neighbours;

  /** Player on this field. */
  public int player = -1;

  /** Player finishing base on this field. */
  public int base = -1;

  public boolean jumped = false;

  public Field() {

  }

  public Field(int count) {
    neighbours = new Field[count];
  }

  /**
   * Executes move from this field.
   * @param nowPlay Player to move.
   * @param dir Direction in which to move
   * @return Return finishing field.
   * @throws InvalidMove Threw when move is illegal.
   */
  public Field move(int nowPlay, int dir) throws InvalidMove {
    if (player != nowPlay) {
      throw new InvalidMove("Field taken.");
    }
    Field landing = neighbours[dir].move(dir, false, this);
    if (landing != null) {
      player = -1;
      jumped = false;
    }
    return landing;
  }

  Field move(int dir, boolean taken, Field startField) throws InvalidMove {
    if (taken && player >= 0) {
      throw new InvalidMove("Field taken.");
    } else if (player >= 0) {
      return neighbours[dir].move(dir, true, startField);
    } else {
      if (taken) {
        jumped = true;
      } else {
        if (startField.jumped) {
          throw new InvalidMove("Unable to combine jump and move.");
        }
      }
      player = startField.player;
      if (startField.base == startField.player && startField.base != base) {
        throw new InvalidMove("Field out of base");
      }

      return this;
    }
  }
}
