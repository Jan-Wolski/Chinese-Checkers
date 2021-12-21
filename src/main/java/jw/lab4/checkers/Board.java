package jw.lab4.checkers;

/**
 * Manages flow of the game.
 */
public class Board {

  private int playersNumber = 0;

  private int turn = 0;
  private int playerTurn = 0;

  public Field[] fields;

  Board() {

  }

  public int getPlayer() {
    return playerTurn;
  }

  public void createBoard(int playersNumber) {
    this.playersNumber = playersNumber;
    fields = FieldsConstructor.construct();
  }

  public MoveInstructions move(MoveInstructions instr) {

    if (instr != null) {
      if (!fields[instr.field].move(instr.dir)) {
        return null;
      }
      nextTurn();
    }
    return instr;
  }

  public void changePlayer(int player) {

  }

  public void skip() {

  }

  public void nextTurn() {
    playerTurn++;
    turn++;
    if (playerTurn >= playersNumber) {
      playerTurn = 0;
    }
  }
}
