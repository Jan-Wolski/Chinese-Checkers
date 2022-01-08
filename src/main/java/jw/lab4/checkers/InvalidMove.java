package jw.lab4.checkers;

/**
 * Thrown when move is invalid.
 */
public class InvalidMove extends Exception {
  public InvalidMove() {
  }

  public InvalidMove(String message) {
    super(message);
  }
}
