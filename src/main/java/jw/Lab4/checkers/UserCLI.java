package jw.Lab4.checkers;

public class UserCLI implements User {

  private MoveInstructions move;
  private boolean newMove = false;


  @Override
  public void start() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public synchronized MoveInstructions getMove() {
    return move;
  }

  @Override
  public synchronized int getSettings() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public synchronized int setSettings() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public synchronized void feedback() {
    // TODO Auto-generated method stub

  }

}
