package jw.lab4.checkers;

/**
 * Manages flow of the game.
 */
public class Board {

  private int playersNumber = 0;

  private int turn = 0;
  private int playerTurn = 0;
  // private int players[] = 0;

  public Field[] fields;
  public Field[][] fieldsPos;

  public boolean started = false;

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

  public void setPlayers(int players) {
    playersNumber = players;
    switch (playersNumber) {
      case 0:
        activateBases(new int[] { -2 });
        break;
      case 1:
        activateBases(new int[] { 0 });
        break;
      case 2:
        activateBases(new int[] { 0, 3 });
        break;
      case 3:
        activateBases(new int[] { 0, 2, 4 });
        break;
      case 4:
        activateBases(new int[] { 0, 1, 3, 4 });
        break;
      case 5:
        activateBases(new int[] { 0, 1, 3, 4, 5 });
        break;
      case 6:
        activateBases(new int[] { 0, 1, 2, 3, 4, 5 });
        break;
    }
  }

  private void activateBases(int[] bases) {
    for (Field f : fields) {
      int i = 0;
      for (int b : bases) {
        if (f.base == b) {
          f.player = f.base;
          break;
        }
        i++;
      }
      if (i == bases.length) {
        f.player = -1;
      }
    }
  }

  public boolean start() throws InvalidMove {
    if (playersNumber == 5 || playersNumber > 6 || playersNumber <= 0) {
      throw new InvalidMove("Invalid number of players");
    }
    started = true;
    return true;
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
    if (instr.player != playerTurn) {
      throw new InvalidMove("Wrong player");
    }

    boolean ok = fields[instr.field].move(instr.player, instr.dir);

    if (!ok) {
      throw new InvalidMove("Illegal move");
    }

    checkWin();
    nextTurn();

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
