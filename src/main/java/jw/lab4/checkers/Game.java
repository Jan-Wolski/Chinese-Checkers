package jw.lab4.checkers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
  User[] users;
  Board board;
  ExecutorService executor;
  int player = -1;

  Game() {

  }

  Game(boolean server) {
    start(server);
  }

  public synchronized void start(boolean server) {
    board = new Board();
    board.createBoard();
    if (server) {
      users = new User[1];
      executor = Executors.newFixedThreadPool(7);
      users[0] = new UserInternet(true, executor);
      users[0].gameSet(this);
      users[0].start();
    } else {
      users = new User[2];
      executor = Executors.newFixedThreadPool(2);
      users[0] = new UserGI();
      users[0].gameSet(this);
      users[1] = new UserInternet(false, executor);
      users[1].gameSet(this);
      users[1].start();
    }
    try {
      wait();
    } catch (InterruptedException e) {
      System.exit(0);
    }
  }

  public synchronized void move(MoveInstructions instr) throws InvalidMove {
    if (instr == null) {
      throw new InvalidMove("No move given");
    }

    if(instr.state == MoveInstructions.STATE.ERROR){
      System.out.println("Desynchronized");
      System.exit(-1);
    }

    if (instr.player != -1) {
      instr.player = player;
    }
    
    try {
      instr = board.interpretMove(instr);
    } catch (InvalidMove e) {
      throw e;
    }

    for (User user : users) {
      user.move(instr);
    }
  }
}