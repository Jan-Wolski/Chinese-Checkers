package jw.lab4.checkers;

public class EntryPoint {
  public static void main(String[] args) {
    Game game = new Game();
    game.start(Boolean.parseBoolean(args[0]));
  }
}
