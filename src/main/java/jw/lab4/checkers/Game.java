package jw.lab4.checkers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main game class observer - mediator between Board class and User classes.
 */
public class Game {
  User[] users;
  Board board;
  ExecutorService executor;
  int player = -1;
  int place = -1;
  boolean admin = false;

  boolean serverS;
  boolean waitS;
  String hostS;
  int portS;

  Game() {

  }

  Game(boolean server) {
    start(server, true, "localhost", 8000);
  }

  Game(boolean server, boolean waitAfter) {
    start(server, waitAfter, "localhost", 8000);
  }


  Game(boolean server, boolean waitAfter,  int port) {
    start(server, waitAfter, "localhost", port);
  }

  Game(boolean server, boolean waitAfter, String host, int port) {
    start(server, waitAfter, host, port);
  }

  public synchronized void restart() {
    start(serverS, waitS, hostS, portS);
  }

  /**
   * Creates game components.
   * 
   * @param server    if this game instance should be server
   * @param waitAfter if thread should wait after creating a game
   */
  public synchronized void start(boolean server, boolean waitAfter, String host, int port) {
    serverS = server;
    waitS = waitAfter;
    hostS = host;
    portS = port;

    player = -1;
    admin = false;
    board = new Board();
    board.createBoard();
    if (server) {
      setStartingPlayer(drawStartingPlayer());
      users = new User[1];
      executor = Executors.newFixedThreadPool(7);
      users[0] = new UserInternet(port, executor);
      users[0].gameSet(this);
      users[0].start();
      admin = true;
    } else {
      users = new User[2];
      executor = Executors.newFixedThreadPool(2);
      users[0] = new UserGI();
      users[0].gameSet(this);
      users[0].start();
      users[1] = new UserInternet(host, port, executor);
      users[1].gameSet(this);
      users[1].start();

    }
    if (waitAfter) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }
  }

  /**
   * Executes move.
   * 
   * @param instr Instruction to execute
   * @throws InvalidMove
   */
  public synchronized void move(MoveInstructions instr) throws InvalidMove {
    if (instr == null) {
      throw new InvalidMove("No move given");
    }

    if (instr.state == MoveInstructions.STATE.ERROR) {
      System.out.println("Desynchronized");
      System.exit(-1);
    }

    if (instr.player == -1) {
      instr.player = player;
    }

    try {
      instr = board.interpretMove(instr);
    } catch (InvalidMove e) {
      throw e;
    }

    this.place = board.winners.indexOf(player);

    for (User user : users) {
      user.move(instr);
    }

  }

  /**
   * Draws starting player. number is lcm of starting number of players
   * posibilities.
   */
  public int drawStartingPlayer() {
    return (int) Math.floor(Math.random() * (12 + 1));
  }

  public void setStartingPlayer(int player) {
    board.startingPlayer = player;
  }

  /**
   * Set player for this game instance.
   * 
   * @param player Player number to set.
   */
  public void choosePlayer(int player) {
    this.player = player;
  }

}