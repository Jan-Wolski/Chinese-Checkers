package jw.lab4.checkers;

import static org.junit.Assert.assertTrue;

import jw.lab4.checkers.MoveInstructions.STATE;

import org.junit.Test;



/**
 * Tests the game.
 */
public class AppTest {

  int port = 8000;

  @Test
  public void differentGames() {
    for (int g = 2; g < 7; g++) {
      System.out.println("Number of players: " + g);
      if (g == 5) {
        continue;
      }
      Game[] games = new Game[g];
      final Game server = new Game(true, false, port);
      justSleep(100);

      for (int i = 0; i < g; i++) {
        games[i] = new Game(false, false, port);
      }
      justSleep(100);

      int toStart = g;
      if (g == 7) {
        toStart = 6;
      }
      startGames(games, toStart);
      justSleep(100);
      assertTrue(server.board.started);
      ((UserInternet) server.users[0]).close();
      port++;
    }
  }

  @Test
  public void winGame() {
    Game[] games = new Game[4];
    final Game server = new Game(true, false,port);
    justSleep(100);

    for (int i = 0; i < 4; i++) {
      games[i] = new Game(false, false,port);
    }

    startGames(games);
    justSleep(100);
    assertTrue(server.board.started);

    for (int p = 3; p >= 0; p--) { // sets win for specific player
      for (int i = 0; i < 4; i++) { // sets win for all games
        for (int f = 0; f < games[0].board.fields.length; f++) { // players in proper bases
          if (games[i].board.fields[f].base == p) {
            games[i].board.fields[f].player = games[i].board.fields[f].base;
          }
        }
      }
      MoveInstructions instr = new MoveInstructions(STATE.NEXT);
      try {
        games[games[0].board.getPlayer()].move(instr);
        System.out.println(games[p].place);
      } catch (InvalidMove e) {
        System.out.println(e);
      }
      justSleep(100);
      assertTrue("Game: " + p + " got " + games[p].place + " place.", games[p].place == 3 - p);
      port++;
    }
    ((UserInternet) server.users[0]).close();

  }

  @Test
  public void connectingReconnecting() {
    Game[] games = new Game[7];
    final Game server = new Game(true, false,port);
    justSleep(100);
    games[1] = new Game(false, false,port);
    games[2] = new Game(false, false,port);
    games[3] = new Game(false, false,port);

    justSleep(100);
    ((UserInternet) games[2].users[1]).close(0);
    games[2] = new Game(false, false,port);

    justSleep(100);
    ((UserInternet) games[2].users[1]).close(0);
    games[2] = new Game(false, false,port);

    justSleep(100);
    ((UserInternet) games[1].users[1]).close(0);
    games[1] = new Game(false, false,port);

    justSleep(100);
    ((UserInternet) games[1].users[1]).close(0);
    games[1] = new Game(false, false,port);

    games[0] = new Game(false, false,port);

    justSleep(100);
    startGames(games, 4);
    justSleep(100);
    assertTrue(server.board.started);
    ((UserInternet) server.users[0]).close();
    port++;

  }

  public void startGames(Game[] games) {
    startGames(games, games.length);
  }

  public void startGames(Game[] games, int count) {
    for (int i = 0; i < count; i++) {
      justSleep(100);
      startGame(games[i]);
    }
  }

  public synchronized void justSleep(int mili) {
    try {
      Thread.sleep(mili);
    } catch (InterruptedException e) {

    }
  }

  public synchronized void startGame(Game game) {
    MoveInstructions instr = new MoveInstructions(MoveInstructions.STATE.READY);
    instr.player = game.player;
    try {
      game.move(instr);
    } catch (InvalidMove e) {
      System.out.println(e.getMessage());
      assertTrue(false);
    }
  }
}
