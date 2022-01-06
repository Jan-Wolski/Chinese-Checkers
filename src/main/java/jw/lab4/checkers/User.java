package jw.lab4.checkers;

/**
 * IO interface.
 */
public abstract class User {

  protected Thread mainThread = null;
  protected Game game;

  public abstract void error(String str);

  public void error() {
    error("");
  }

  public abstract void move(MoveInstructions instr);

  public abstract void start();

  public void setMainThread(Thread thread) {
    mainThread = thread;
  }

  public void gameSet(Game game) {
    this.game = game;
  }

  public boolean processMove(MoveInstructions instr) {
    try {
      game.move(instr);
      return true;
    } catch (InvalidMove e) {
      error(e.getMessage());
    }
    return false;
  }

  public void choosePlayer(int player) {
    game.player = player;
  }

  public void setPlayersNumber(int num) {
    MoveInstructions instr = new MoveInstructions(MoveInstructions.STATE.JOIN);
    instr.player = num;
    try {
      game.move(instr);
    } catch (InvalidMove e) {
      error(e.getMessage());
    }
  }

  public void ready() {
    ready(-1);
  }

  public void ready(int player) {
    MoveInstructions instr = new MoveInstructions(MoveInstructions.STATE.READY);
    instr.player = player;
    try {
      game.move(instr);
    } catch (InvalidMove e) {
      error(e.getMessage());
    }
  }

  public void finishMove() {
    finishMove(-1);
  }
  
  public void finishMove(int player) {
    MoveInstructions instr = new MoveInstructions(MoveInstructions.STATE.NEXT);
    instr.player = player;
    try {
      game.move(instr);
    } catch (InvalidMove e) {
      error(e.getMessage());
    }
  }
}
