package jw.lab4.checkers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
  User[] users;
  Board board;
  ExecutorService executor;

  Game() {

  }

  Game(boolean server) {
    start(server);
  }

  public synchronized void start(boolean server) {
    board = new Board();
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

  public synchronized void setPlayers(int players) {
    board.createBoard(players);
    MoveInstructions m = new MoveInstructions();
    m.start = true;
    
    move(m);
  }

  public synchronized void move(MoveInstructions instr) {
    if (board.move(instr) != null) {
      for (User user : users) {
        user.move(instr);
      }
    }

  }
}