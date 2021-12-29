package jw.lab4.checkers;

/**
 * IO interface
 */
public abstract class User {

  protected Thread mainThread = null;
  protected Game game;
  protected int player;

  abstract public void error();
  abstract public void move(MoveInstructions instr);
  abstract public void start();

  public void setMainThread(Thread thread) {
    mainThread = thread;
  }

  public void gameSet(Game game) {
    this.game = game;
  }

  public void processMove(MoveInstructions instr){
    game.move(instr);
  }

}
