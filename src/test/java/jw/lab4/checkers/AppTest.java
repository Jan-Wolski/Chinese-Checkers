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
  public synchronized void sixGames() {
    ExecutorService exec = Executors.newFixedThreadPool(8);
    exec.execute(new GameExecutor(true));
    for (int i = 0; i < 7; i++) {
      exec.execute(new GameExecutor(false));
    }
    try {
      wait();
    } catch (InterruptedException e) {
    }
  }

  class GameExecutor implements Runnable {

    Game game;
    boolean server;

    GameExecutor(boolean server) {
      this.server = server;
    }

    @Override
    public void run() {
      game = new Game(server);

    }

  }
}
