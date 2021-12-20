package jw.lab4.checkers;

import java.util.Scanner;

public class UserCI extends User {

  private Scanner in;

  public UserCI(){
    setup();
  }


  public void setup() {
    in = new Scanner(System.in);

  }

  @Override
  public void run() {
    String line;
    while (in.hasNextLine()) {
      line = in.nextLine();
      MoveInstructions move = new MoveInstructions();
      move.deserialize(line);
      setMoveOut(move);
    }

  }


  @Override
  public void display() {
    System.out.println("Executed move " + moveIn.serialize());
    
  }


  @Override
  public void error() {
    System.out.println("Wrong move");
    
  }


  @Override
  public void accept() {
    setMoveOut(moveIn);
  }

}
