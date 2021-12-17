package jw.Lab4.checkers;

/**
 * Connector related to connecting application between server and clients
 */
public interface User extends Runnable {
  public synchronized MoveInstructions getMove();

  public void start();

  public synchronized int getSettings();

  public synchronized int setSettings();

  public synchronized void feedback();
}
