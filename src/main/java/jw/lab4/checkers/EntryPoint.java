package jw.lab4.checkers;

/**
 * Main class creating and starting game instance.
 */
public class EntryPoint {
  public static void main(String[] args) {
    Game game = new Game();
    boolean server = false;
    int port = 8000;
    String host = "localhost";
    if (args.length > 0) {
      server = Boolean.parseBoolean(args[0]);
    }
    if (args.length > 1) {
      port = Integer.parseInt(args[1]);
    }
    if (args.length > 2) {
      host = args[2];
    }
    game.start(server, false, host, port);
  }
}
