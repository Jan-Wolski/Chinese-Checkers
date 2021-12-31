package jw.lab4.checkers;

import java.lang.module.InvalidModuleDescriptorException;

/**
 * Manages flow of the game.
 */
public class Board {

  private int playersNum = 0;

  private int turn = 0;
  private int playerTurn = 0;
  private int[] readyPlayers;
  private int[] playerTrans;

  public Field[] fields;
  public Field[][] fieldsPos;

  public boolean started = false;

  public int width;
  public int height;

  public int maxPlayers = 6;

  Board() {
    readyPlayers = new int[maxPlayers];
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
    playersNum = players;
    switch (playersNum) {
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
    playerTrans = new int[bases.length];
    for (int i = 0; i < bases.length; i++) {
      playerTrans[i] = bases[i];
    }

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
    if (playersNum == 5 || playersNum > 6 || playersNum <= 0) {
      throw new InvalidMove("Invalid number of players");
    }

    int[] transform = new int[maxPlayers];

    for (int bNum = 0; bNum < maxPlayers; bNum++) {
      int b = 0;
      while (b < playerTrans.length && bNum != playerTrans[b]) {
        b++;
      }

      if (b < playerTrans.length) {

        int p = 0;
        while (b > 0) {
          if (readyPlayers[p] > 0) {
            b--;
          }
          p++;
        }

        transform[bNum] = p;
      } else {
        transform[bNum] = -1;
      }

    }

    for (Field f : fields) {
      if (f.base > -1) {
        f.base = (f.base + maxPlayers / 2) % maxPlayers;
        f.base = transform[f.base];
      }
      if (f.player > -1) {
        f.player = transform[f.player];

      }
    }

    started = true;
    return true;
  }

  public void setReady(int player) throws InvalidMove {
    readyPlayers[player] = 1;
    int count = 0;
    for (int r = 0; r < maxPlayers; r++) {
      count += readyPlayers[r];
    }
    if (count == playersNum) {
      start();
    } else if (count > playersNum) {
      throw new InvalidMove("Too many ready players");
    }
  }

  public int getDir(MoveInstructions instr) throws InvalidMove {
    int f1 = instr.field1;
    int f2 = instr.field2;
    int d;
    for (d = 0; d < fields[0].neighbours.length; d++) {
      if (fields[f1].neighbours[d] == fields[f2]) {
        break;
      }
    }

    if (d == fields[0].neighbours.length) {
      throw new InvalidMove("Invalid direction");
    }

    return d;
  }

  public MoveInstructions interpretMove(MoveInstructions instr) throws InvalidMove {
    if (instr != null) {
      switch (instr.state) {
        case JOIN:
          setPlayers(instr.player);
          break;
        case PLAY:
          move(instr);
          break;
        case READY:
          setReady(instr.player);
          break;
        case NEXT:
          nextTurn();
          break;
        default:
          break;
      }
    }
    return instr;
  }

  public MoveInstructions move(MoveInstructions instr) throws InvalidMove {
    if (instr.player != playerTurn) {
      throw new InvalidMove("Wrong player");
    }

    int dir = getDir(instr);

    boolean ok = fields[instr.field1].move(instr.player, dir);

    if (!ok) {
      throw new InvalidMove("Illegal move");
    }

    checkWin();

    return instr;
  }

  private void checkWin() {
    int[] pl = new int[6];
    for (Field f : fields) {
      if (f.player == f.base + 3 % 6) {
        pl[f.player]++;
      }
    }

  }

  public void nextTurn() {
    playerTurn++;
    turn++;
    if (playerTurn >= playersNum) {
      playerTurn = 0;
    }
  }
}
