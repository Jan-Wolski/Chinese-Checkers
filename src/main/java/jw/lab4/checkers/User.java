package jw.lab4.checkers;

/**
 * IO interface
 */
public abstract class User {

  protected Thread mainThread = null;
  protected Game game;

  abstract public void error();
  abstract public void move(MoveInstructions instr);
  abstract public void start();

  public void setMainThread(Thread thread) {
    mainThread = thread;
  }

  public void gameSet(Game game) {
    this.game = game;
  }

  public void gameMove(MoveInstructions move) {
    game.move(move);
  }

  public void gameStart() {
    this.game.requestStart();
  }

  public void gameStart(int players) {
    this.game.startGame(players);
  }


}
