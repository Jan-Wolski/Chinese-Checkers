package jw.lab4.checkers;

import java.util.PrimitiveIterator;
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

  protected void start(boolean server) {
    board = new Board();
    board.createBoard(2);
    executor = Executors.newFixedThreadPool(2);
    users = new User[2];
    if (server) {
      users[0] = new UserInternet(true);
      users[1] = new UserCI();
    } else {
      users[0] = new UserCI();
      users[1] = new UserInternet(false);

    }

    for (int i = 0; i < users.length; i++) {
      users[i].setMainThread(Thread.currentThread());
      executor.execute(users[i]);
    }

    mainLoop();
  }

  // protected User userFactory(String type) {
  // switch (type) {
  // case "CLI":
  // return new UserCI();
  // // case "GUI":
  // // return new UserGI();
  // case "Internet":
  // return new UserInternet(false);
  // default:
  // return null;
  // }
  // }

  private synchronized void mainLoop() {
    int player = board.getPlayer();
    MoveInstructions move = null;
    int feedback = 0;
    while (true) {

      try {
        player = board.getPlayer();
        System.out.println("Player: " + player);
        if (Thread.interrupted()) {
          throw new InterruptedException();
        }
        wait();

      } catch (InterruptedException e) {
        if (feedback > 0) {
          for (User user : users) {
            if (move.compare(user.getMoveOut())) {
              feedback--;
            }
          }
        } else {
          // try {
          move = board.move(users[player].getMoveOut(true));
          // }
          // catch (InvalidMove e) {

          // }

          if (move != null) {
            feedback = users.length - 1;
            for (User user : users) {
              user.setMoveIn(move);
            }
          }
        }
      }
    }
  }
}