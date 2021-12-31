package jw.lab4.checkers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.WildcardType;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ComponentOrientation;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserGI extends User {

  private JFrame frame;
  private JButton[] fields;
  private JLabel warning;

  int field = -1;
  int dir = -1;

  UserGI() {

  }

  @Override
  public void start() {
    int width = game.board.width;
    int height = game.board.height;

    frame = new JFrame("Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 800);
    frame.setLayout(new FlowLayout(FlowLayout.LEFT));
    frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    JPanel board = new JPanel();
    board.setLayout(new GridLayout(height,width));
    board.setSize(800, 800);

    Font font = new Font("Arial", Font.BOLD, 24);

    fields = new JButton[width * height];

    warning = new JLabel("Warning");

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

    JButton startButton = new JButton("Ready");
    startButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent action) {
        ready();
      }

    });

    board.setBounds(0, 0, 800, 800);
    frame.add(board);
    warning.setBounds(0, 800, 600, 900);
    frame.add(warning);
    startButton.setBounds(600, 800, 800, 900);
    frame.add(startButton);
    frame.setVisible(true);
    move(null);
  }

  public void execute(int f) {
    if (field == -1) {
      field = f;
    } else if (field == f) {
      field = -1;
    } else {
      MoveInstructions m = new MoveInstructions(field, f);
      processMove(m);
    }

  }

  @Override
  public void error(String str) {
    warning.setText(str);

  }

  @Override
  public void move(MoveInstructions instr) {
    for (int i = 0; i < game.board.height; i++) {
      for (int j = 0; j < game.board.width; j++) {
        if (game.board.fieldsPos[i][j] != null) {
          int p = game.board.fieldsPos[i][j].base;
          if (p > -1) {
            fields[i * game.board.width + j].setText(Integer.toString(p));
          } else {
            fields[i * game.board.width + j].setText("-");
          }
          p = game.board.fieldsPos[i][j].player;
          if (p > -1) {
            fields[i * game.board.width + j].setText("-"+Integer.toString(p)+"-");
          }
          
        }
      }
    }

  }

}
