package jw.lab4.checkers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;

import javax.swing.text.StyledEditorKit.BoldAction;

public class UserInternet extends User {
  private Socket socket;
  private Scanner in;
  private PrintWriter out;

  public UserInternet(boolean server) {
    setup("localhost",8000, server);
  }

  public UserInternet(String address, int port) {
    setup(address, port, false);
  }

  public UserInternet(int port) {
    setup("", port, true);
  }

  public void setup(String address, int port, boolean server) {
    while (socket == null) {
      try {
        if (server) {
          ServerSocket serverS = new ServerSocket(port);
          socket = serverS.accept();
        } else {
          socket = new Socket(address, port);
        }
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
      } catch (IOException e) {
        System.out.println(e);
      }
    }
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
  }

  @Override
  public void error() {
    out.println("Sync error");
    
  }

  @Override
  public void accept() {
    out.println(moveIn.serialize());
  }

}
