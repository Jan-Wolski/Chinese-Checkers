package jw.Lab4.checkers;

/**
 * Manages flow of the game.
 */
public class Board {

  private int playersNumber = 0;

  private int turn = 0;
  private int playerTurn = 0;

  private Fields fields;

  Board() {

  }

  public void createBoard(int playersNumber) {
    this.playersNumber = playersNumber;
  }

  public void move(MoveInstructions instr) {
    fields.move(field, dir);
  }

  public void undo() {

  }

  public void changePlayer(int player) {

  }

  public void skip() {

  }
}
