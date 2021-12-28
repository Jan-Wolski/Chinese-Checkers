package jw.lab4.checkers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UserGI extends User {

  private JFrame frame;
  private JButton[] fields = new JButton[5 * 5];

  int field = -1;
  int dir = -1;

  UserGI() {
    int width = game.board.width;
    int height = game.board.height;
    frame = new JFrame("Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 400);
    JPanel board = new JPanel();
    // frame.setLayout(new (width, height));
    board.setLayout(new GridLayout(width, height));

    Font font = new Font("Arial", Font.BOLD, 48);

    fields = new JButton[width * height];

    for (int i = 0; i < fields.length; i++) {
      fields[i] = new JButton();
      fields[i].setFont(font);
      fields[i].setActionCommand(Integer.toString(i));
      fields[i].addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent action) {
          execute(Integer.parseInt(action.getActionCommand()));
        }

      });
      board.add(fields[i]);
    }
    JButton startButton = new JButton("Start");
    startButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent action) {
        gameStart();
      }

    });
    frame.add(board);
    frame.add(startButton);
    frame.setVisible(true);
  }

  public void execute(int f) {
    if (field == -1) {
      field = f;
    } else if (field == f) {
      field = -1;
    } else {
      System.out.println("Moving from: " + field);
      MoveInstructions m = null;
      if (f == field + 1)
        m = new MoveInstructions(field, 0);
      else if (f == field - 1)
        m = new MoveInstructions(field, 2);
      else if (f == field + 5)
        m = new MoveInstructions(field, 1);
      else if (f == field - 5)
        m = new MoveInstructions(field, 3);
      else
        field = -1;
      if (m != null) {
        field = -1;
        gameMove(m);
      }
    }

  }

  public void update() {

  }

  @Override
  public void error() {
    // TODO Auto-generated method stub

  }

  @Override
  public void move(MoveInstructions instr) {
    for (int i = 0; i < game.board.fieldsPos.length; i++) {
      for (int j = 0; j < game.board.fieldsPos[i].length; i++) {
        if(game.board.fieldsPos[i][j]!=null){
          int p = game.board.fieldsPos[i][j].player;
          if (p > -1) {
            fields[i].setText(Integer.toString(p));
          } else {
            fields[i].setText("-0-");
          }
        }
      }
    }

  }

  @Override
  public void start() {
    // TODO Auto-generated method stub

  }
}
