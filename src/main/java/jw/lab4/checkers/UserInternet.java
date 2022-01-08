package jw.lab4.checkers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

/**
 * User handling internet connection.
 */
public class UserInternet extends User implements Runnable {

  String address;
  int port;

  boolean server;
  ExecutorService executor;
  Communicator[] players;
  int playersNum = 0;
  int sender = -1;

  int maxPlayers = 6;

  public UserInternet(boolean server, ExecutorService ex) {
    setup("localhost", 8000, server, ex);
  }

  public UserInternet(String address, int port, ExecutorService ex) {
    setup(address, port, false, ex);
  }

  public UserInternet(int port, ExecutorService ex) {
    setup("", port, true, ex);
  }

  public void setup(String address, int port, boolean server, ExecutorService ex) {
    this.address = address;
    this.port = port;
    this.server = server;
    this.executor = ex;
    players = new Communicator[maxPlayers];

  }

  @Override
  public void start() {
    executor.execute(this);
  }

  @Override
  public void run() {
    if (server) {
      System.out.println("I'm server");
      asServer();
    } else {
      System.out.println("I'm client");
      asClient();
    }
  }

  private void asServer() {
    ServerSocket serverS;
    try {
      serverS = new ServerSocket(port);
      while (true) {
        Socket newPlayer = serverS.accept();
        addClient(newPlayer);
      }
    } catch (IOException e) {
      System.out.print(e);
    }
  }


  private synchronized void addClient(Socket socket) {
    if (playersNum == -1) {
      incPlayer();
      if (playersNum == -1) {
        return;
      }
    }

    players[playersNum] = new Communicator(playersNum, socket, this);
    executor.execute(players[playersNum]);
    System.out.println("New player: " + playersNum);
    players[playersNum].send("Ex:" + playersNum);
    incPlayer();
    setPlayersNumber(countPlayers());
  }

  /**
   * Increas player counter.
   */
  public synchronized void incPlayer() {
    while (playersNum < maxPlayers && players[playersNum] != null) {
      playersNum++;
    }
    if (playersNum == maxPlayers) {
      playersNum = -1;
    }
  }
  
  /**
   * Count number of players.
   * @return Number of players.
   */
  public synchronized int countPlayers() {
    int count = 0;
    for (int i = 0; i < maxPlayers; i++) {
      if (players[i] != null) {
        count++;
      }
    }
    return count;
  }

  private void asClient() {
    try {
      players = new Communicator[1];
      players[0] = new Communicator(0, new Socket(address, port), this);
      players[0].run();
    } catch (UnknownHostException e) {
      System.out.print(e);
    } catch (IOException e) {
      System.out.print(e);
    }
  }

  /**
   * Process received data.
   * @param player Player from which data was received.
   * @param line Received data string.
   */
  private void process(int player, String line) {
    System.out.println(line);
    if (line.substring(0, 3).equals("Ex:")) {
      choosePlayer(Integer.parseInt(line.substring(3)));
    } else {
      sender = player;
      MoveInstructions instr = new MoveInstructions();
      instr.deserialize(line);
      if (server) {
        instr.player = player;
      }
      processMove(instr);
    }
  }

  @Override
  public void error(String str) {
    MoveInstructions instr = new MoveInstructions(MoveInstructions.STATE.ERROR);
    players[sender].send(instr.serialize());
    sender = -1;
  }

  @Override
  public void move(MoveInstructions instr) {
    String line = instr.serialize();
    for (Communicator pl : players) {
      if (pl != null && pl.playerNum != sender) {
        pl.send(line);
      }
    }
    sender = -1;
  }

  /**
   * Remove disconnected player.
   * @param num Position to which move player counter.
   */
  public synchronized void clean(int num) {
    players[num] = null;
    playersNum = num;
  }

  /**
   * Class for sending and receiving data through internet.
   */
  private class Communicator implements Runnable {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    UserInternet parent;
    int playerNum;

    @Override
    public void run() {
      receiveLoop();
    }

    Communicator(int playerNum, Socket socket, UserInternet parent) {
      setup(playerNum, socket, parent);
    }

    public void setup(int playerNum, Socket socket, UserInternet parent) {
      try {
        this.socket = socket;
        this.parent = parent;
        this.playerNum = playerNum;
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
      } catch (IOException e) {
        System.out.print(e);
      }
    }

    public void receiveLoop() {
      String line;
      while (in.hasNextLine()) {
        line = in.nextLine();
        parent.process(playerNum, line);
      }
      System.out.println("Player " + playerNum + " disconnected");
      parent.clean(playerNum);
    }

    public void send(String line) {
      out.println(line);
    }

  }

}
