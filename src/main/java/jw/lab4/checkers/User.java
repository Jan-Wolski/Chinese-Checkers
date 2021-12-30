package jw.lab4.checkers;

/**
 * IO interface
 */
public abstract class User {

  protected Thread mainThread = null;
  protected Game game;

  abstract public void error(String str);

  public void error() {
    error("");
  }

  abstract public void move(MoveInstructions instr);

  abstract public void start();

  public void setMainThread(Thread thread) {
    mainThread = thread;
  }

  public void gameSet(Game game) {
    this.game = game;
  }

  public void processMove(MoveInstructions instr) {
    try {
      game.move(instr);
    } catch (InvalidMove e) {
      error(e.getMessage());
    }
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
}
