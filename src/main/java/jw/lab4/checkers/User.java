package jw.lab4.checkers;

/**
 * Interface for sending and receiving data from the game.
 */
public abstract class User {

  protected Game game;

  public void error() {
    error("");
  }

  /**
   * Handles invalid move from this user.
   * 
   * @param str Cause of error.
   */
  public abstract void error(String str);

  /**
   * Action to execute after proper move.
   * @param instr successful move instruction.
   */
  public abstract void move(MoveInstructions instr);

  public abstract void start();

  public void gameSet(Game game) {
    this.game = game;
  }

  /**
   * Fuction to call when we want to execute move.
   * @param instr Instruction to execute.
   * @return
   */
  public boolean processMove(MoveInstructions instr) {
    try {
      game.move(instr);
      return true;
    } catch (InvalidMove e) {
      error(e.getMessage());
    }
    return false;
  }


  /**
   * Set player for this game instance.
   * @param player Player number to set.
   */
  public void choosePlayer(int player) {
    game.player = player;
  }

  /**
   * Set number of players in game.
   * @param num Number of players to set.
   */
  public void setPlayersNumber(int num) {
    MoveInstructions instr = new MoveInstructions(MoveInstructions.STATE.JOIN);
    instr.player = num;
    try {
      game.move(instr);
    } catch (InvalidMove e) {
      error(e.getMessage());
    }
  }

  /**
   * Executes ready with player of this game instance.
   */
  public void ready() {
    ready(-1);
  }

  /**
   * Set player in ready state.
   */
  public void ready(int player) {
    MoveInstructions instr = new MoveInstructions(MoveInstructions.STATE.READY);
    instr.player = player;
    try {
      game.move(instr);
    } catch (InvalidMove e) {
      error(e.getMessage());
    }
  }

  /**
   * Executes finishMove with player of this game instance.
   */
  public void finishMove() {
    finishMove(-1);
  }

  /**
   * Finishes the move.
   * @param player Player which finishes the move.
   */
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
