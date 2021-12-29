package jw.lab4.checkers;

public class InvalidMove extends Exception {
  public InvalidMove(){}

  public InvalidMove(String message){
    super(message);
  }
}
