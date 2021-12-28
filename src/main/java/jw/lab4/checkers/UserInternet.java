package jw.lab4.checkers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class UserInternet extends User implements Runnable {

  String address;
  int port;

  boolean server;
  ExecutorService executor;
  Communicator[] players;
  Communicator player;
  int playerNumber = 0;
  int sender = -1;

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
    players = new Communicator[6];

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

  public void asServer() {
    ServerSocket serverS;
    try {
      serverS = new ServerSocket(port);
      while (true) {
        players[playerNumber] = new Communicator(playerNumber, serverS.accept(), this);
        executor.execute(players[playerNumber]);
        System.out.println("New player: " + playerNumber);
        processMove(new MoveInstructions());
      }
    } catch (IOException e) {
      System.out.print(e);
    }
  }

  public void asClient() {
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

  public void process(int player, String line) {
    sender = player;
    MoveInstructions move = new MoveInstructions();
    move.deserialize(line);
    if (move.start) {
      gameStart(3);
    } else {
      gameMove(move);
    }

  }

  @Override
  public void error() {
    // TODO Auto-generated method stub

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
    }

    public void send(String line) {
      out.println(line);
    }

  }

}
