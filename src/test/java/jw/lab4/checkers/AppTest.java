package jw.lab4.checkers;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Executable;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * Tests the game
 */
public class AppTest {

  @Test
  public synchronized void sevenGames() {
    Game[] games = new Game[7];
    Game server = new Game(true, false);
    for (int i = 0; i < 7; i++) {
      games[i] = new Game(false, false);
    }

    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {

    }

    startGames(games, 6);
    assertTrue(server.board.started);
  }

  @Test
  public synchronized void connectingReconnecting() {
    Game[] games = new Game[7];
    Game server = new Game(true, false);
    games[1] = new Game(false, false);
    games[2] = new Game(false, false);
    games[4] = new Game(false, false);
    games[3] = new Game(false, false);
    games[2] = new Game(false, false);
    games[1] = new Game(false, false);
    games[5] = new Game(false, false);
    games[1] = new Game(false, false);
    games[0] = new Game(false, false);
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {

    }
    startGames(games, 5);
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {

    }
    assertTrue(server.board.started);

  }

  public synchronized void startGames(Game[] games) {
    startGames(games, games.length);
  }

  public synchronized void startGames(Game[] games, int count) {
    for (int i = 0; i < count; i++) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {

      }
      startGame(games[i]);
    }
    try {
      Thread.sleep(100);
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
