package jw.lab4.checkers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jw.lab4.checkers.MoveInstructions.STATE;

/**
 * Game graphical interface user.
 */
public class UserGI extends User {

  private JFrame frame;
  private FieldButton[] fields;
  private SpecialButton specialButton;
  private JLabel warning;
  Color[] colors;
  Color defaultColor = Color.lightGray;
  boolean yourTurn = false;

  int field = -1;

  UserGI() {

  }

  /**
   * Setups game graphical interface.
   */
  @Override
  public void start() {
    final int width = game.board.width;
    final int height = game.board.height;

    setColors();

    frame = new JFrame("Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    buttonsPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

    JPanel board = new JPanel();
    board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));

    JPanel[] rows = new JPanel[height];

    Font font = new Font("Arial", Font.BOLD, 24);

    fields = new FieldButton[game.board.fields.length];

    warning = new JLabel("Press ready when enough players joined.");

    int row = -1;
    int col = 0;

    for (int i = 0; i < fields.length; i++) {
      fields[i] = new FieldButton(i);
      fields[i].setFont(font);
      if (col == 0) {
        row++;
        int c = 0;
        while (game.board.fieldsPos[row][c] == null) {
          c++;
        }
        while (c < width && game.board.fieldsPos[row][c] != null) {
          col++;
          c++;
        }

        rows[row] = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rows[row].setSize(800, 800 / 17);
        board.add(rows[row]);

      }

      rows[row].add(fields[i]);
      col--;
    }

    specialButton = new SpecialButton();

    frame.add(board);
    frame.add(buttonsPanel);
    buttonsPanel.add(specialButton);
    buttonsPanel.add(warning);
    frame.setVisible(true);
    move(null);
    frame.pack();
  }

  /**
   * Executes move.
   * 
   * @param f Id of pressed field.
   */
  public void execute(int f) {

    if (field == -1) {
      if (game.board.checkPlayer(game.player, f)) {
        field = f;
        fields[field].selected = true;
      }
    } else if (field == f) {
      fields[field].selected = false;
      field = -1;
    } else {
      MoveInstructions instr = new MoveInstructions(field, f);
      if (processMove(instr)) {
        fields[field].selected = false;
        field = -1;
      }
    }

  }

  @Override
  public void error(String str) {
    warning.setText(str);

    move(null);
  }

  @Override
  public void move(MoveInstructions instr) {

    yourTurn = (game.board.getPlayer() == game.player);
    if (instr != null) {
      specialButton.changeState(instr);
    }

    for (int i = 0; i < game.board.fields.length; i++) {
      if (game.board.fields[i] != null) {
        int p = game.board.fields[i].player;
        if (p > -1) {
          fields[i].setBackground(colors[p]);
        } else {
          fields[i].setBackground(defaultColor);
        }

        p = game.board.fields[i].player;
        // if (p > -1) {
        // // fields[i].setForeground(colors[p]);
        // fields[i].setText("X");
        // } else {
        // fields[i].setText("");
        // }

      }
    }

  }

  /**
   * Set Players colors.
   */
  private void setColors() {
    colors = new Color[6];
    colors[0] = Color.red;
    colors[1] = Color.green;
    colors[2] = Color.blue;
    colors[3] = Color.yellow;
    colors[4] = Color.pink;
    colors[5] = Color.cyan;
  }

  /**
   * Class of Ready/Move button.
   */
  private class SpecialButton extends JButton implements ActionListener {

    private int state = 0;

    SpecialButton() {
      setReadyState();
      addActionListener(this);
    }

    public void setReadyState() {
      setText("Ready");
      state = 1;
    }

    public void setMoveState() {
      setText("Finish");
      state = 2;
    }

    public void setWaitState() {
      setText("Wait");
      state = 3;
    }

    public void changeState(MoveInstructions instr) {
      if (instr.player == game.player && instr.state == STATE.READY) {
        setWaitState();
      } else {

        if (instr.state == STATE.NEXT || (instr.state == STATE.READY && game.board.started)) {
          if (yourTurn) {
            setMoveState();
          } else {
            setWaitState();
          }
        }
      }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
      switch (state) {
        case 1:
          ready();
          break;
        case 2:
          finishMove();
          break;
        case 3:
          break;
        default:
          break;
      }
    }
  }

  /**
   * Class of field button.
   */
  private class FieldButton extends JButton implements ActionListener {

    public int fieldNum = -1;
    private final int thickLine = 3;
    public boolean selected = false;

    public FieldButton(int id) {

      fieldNum = id;
      setFocusable(false);

      Dimension size = getPreferredSize();
      size.width = size.height = Math.max(size.width, size.height);
      setPreferredSize(size);

      setContentAreaFilled(false);

      addActionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
      if (getModel().isArmed()) {
        g.setColor(getBackground());
      } else {
        g.setColor(getBackground());
      }
      g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

      super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
      g.setColor(Color.black);
      if (selected) {
        ((Graphics2D) g).setStroke(new BasicStroke(thickLine));
      } else {
        ((Graphics2D) g).setStroke(new BasicStroke(1));
      }
      g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }

    Shape shape;

    @Override
    public boolean contains(int x, int y) {
      if (shape == null || !shape.getBounds().equals(getBounds())) {
        shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
      }
      return shape.contains(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
      execute(fieldNum);
    }
  }

}
