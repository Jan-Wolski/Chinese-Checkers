package jw.lab2.invoice;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests Invoice Manager.
 */
public class AppTest {


  /**
   * Keyboard mashing test.
   */
  @Test(expected = NoSuchElementException.class)
  public void randomInsertion() {
    
    int length = 1000;
    byte[] inputArray = new byte[length];
    for (int j = 0; j < length; j++) {
      byte option = (byte) (Math.random() * 125 + 32);
      if (j % 2 == 0) {
        inputArray[j] = '\n';
      } else if (option > 126 && option < 150) {
        inputArray[j] = 0;
      } else if (option > /*   */150) {
        inputArray[j] = '-';
      } else {
        inputArray[j] = option;
      }
    }

    System.out.println(inputArray);



    InputStream input = new ByteArrayInputStream(inputArray);
    System.setIn(input);

    Main userInterface = new Main();
    userInterface.start();
    
    assertTrue(false);
  }

  /**
   * Database test.
   */
  @Test
  public void databaseTest() {
    DatabaseAdapter database = new ClassDatabaseAdapter();
    database.addElement("Ala", "Kupno kota", "Kot", 2341, 1);
    database.addElement("Ala", "Kupno kota", "Kot", 1000, 2);
    database.addElement("Jola", "WyLojalność", "Wola", 123, 2);
    database.addElement("Jola", "WyLojalność", "Siła", 0, 2);
    database.addElement("Jola", "Zakupy", "Chleb", 2, 4);
    database.addElement("Jola", "Zakupy", "Ryż", 1, 10);
    database.addElement("Jola", "Zakupy", "Chleb", 3, 4);
    database.addElement("Jola", "Zakupy", "Ryrz", 1, 11);

    assertTrue(database.loadClients().length == 2);
    assertTrue(database.loadInvoices("Ala").length == 1);
    assertTrue(database.loadInvoices("Jola").length == 2);
    assertTrue(database.loadElements("Ala", "Kupno kota").length == 1);
    assertTrue(database.loadElements("Jola", "WyLojalność").length == 2);
    assertTrue(database.loadElements("Jola", "Zakupy").length == 3);
  }
}
