package jw.lab4.checkers;

import java.io.Console;

/**
 * IO interface
 */
public abstract class User implements Runnable {

  protected MoveInstructions moveIn = null;
  protected MoveInstructions moveOut = null;
  protected MoveInstructions moveCheck = null;
  protected Thread mainThread = null;

  public abstract void display();

  public abstract void error();

  public abstract void accept();

  public synchronized MoveInstructions getMoveIn() {
    if (this.moveIn != null) {
      MoveInstructions moveIn;
      moveIn = this.moveIn;
      this.moveIn = null;
      return moveIn;
    }
    return null;
  }

  public synchronized MoveInstructions getMoveOut(boolean check) {
    if (check == true) {
      if (this.moveOut != null) {
        moveCheck = moveOut;
        return getMoveOut();
      }
      return null;
    } else {
      return getMoveOut();
    }
  }

  public synchronized MoveInstructions getMoveOut() {
    if (this.moveOut != null) {
      MoveInstructions moveOut;
      moveOut = this.moveOut;
      this.moveOut = null;
      return moveOut;
    }
    return null;
  }

  public synchronized void setMoveOut(MoveInstructions instr) {
    if(moveCheck == null){
      moveOut = instr;
      mainThread.interrupt();
    }
  }

  public synchronized void setMoveIn(MoveInstructions instr) {
    moveIn = instr;
    if (moveCheck != null) {
      if (moveCheck.compare(moveIn)) {
        display();
      } else {
        error();
      }
      moveCheck = null;
    } else {
      display();
      accept();
    }

    moveIn = null;
  }

  public void setMainThread(Thread thread) {
    mainThread = thread;
  }
}
