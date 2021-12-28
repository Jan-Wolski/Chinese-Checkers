package jw.lab4.checkers;

/**
 * Manages flow of the game.
 */
public class Board {

  private int playersNumber = 0;

  private int turn = 0;
  private int playerTurn = 0;

  public Field[] fields;
  public Field[][] fieldsPos;

  public int width;
  public int height;

  Board() {

  }

  public int getPlayer() {
    return playerTurn;
  }

  public void createBoard() {
    FieldsConstructor construct = new FieldsConstructor();
    construct.constructStar();
    fields = construct.getFields();
    fieldsPos = construct.getFieldsPos();
    width = construct.width;
    height = construct.height;
  }

  public setPlayers(int players){
    playersNumber = players;
  }

  public MoveInstructions interpretMove(MoveInstructions instr) {
    if (instr != null) {
      switch (instr.state) {
        case START:
          start();
          break;
        case ERROR:
          error();
        case PLAY:
          move(instr);
        case REQUEST:
          setPlayers(instr.player);
        default:
          break;
      }
    }
    return instr;
  }

  public MoveInstructions move(MoveInstructions instr) {

    if (!fields[instr.field].move(instr.dir)) {
      return null;
    }
    nextTurn();
  }return instr;

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
