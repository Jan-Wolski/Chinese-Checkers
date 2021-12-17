package jw.Lab4.checkers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
  User[] users;
  Board board;
  ExecutorService executor;

  Game() {

  }

  void start(boolean server) {
    executor = Executors.newFixedThreadPool(2);
    users = new User[2];
    if (server) {
      users[0] = userFactory("Internet");
      users[1] = userFactory("CLI");
    } else {
      users[0] = userFactory("Internet");
      users[1] = userFactory("GUI");
    }

    for (int i = 0; i < users.length; i++) {
      executor.execute(users[i]);
    }
  }

  User userFactory(String type) {
    switch (type) {
      case "CLI":
        return UserCLI();
      case "GUI":
        return UserGUI();
      case "Internet":
        return UserInternet();
    }
  }

  void mainLoop() {
    while (true) {
      int player = board.getPlayer();
      int move;

      try {
        move = board.move(users[player].getMove());
      } catch (InvalidMove e) {

      }

      for (User user : users) {
        user.feedback();
      }

    }
  }

  private void toAllUsers() {
    for (User user : users) {
      sendToUser(user);
    }
  }

  private void sendToUser(int user) {

  }

  private class MoveGetter implements Runnable {
    private int user

    public MoveGetter(int user) {
      this.user = user;
    }

    @Override
    public void run() {
      // TODO Auto-generated method stub

    }

  }

}