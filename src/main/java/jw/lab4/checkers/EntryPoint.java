package jw.lab4.checkers;


/**
 * Main class creating and starting game instance.
 */
public class EntryPoint {
  public static void main(String[] args) {
    Game game = new Game();
    boolean server = false;
    if (args.length > 0) {
      server = Boolean.parseBoolean(args[0]);
    }
    game.start(server);
  }
}
