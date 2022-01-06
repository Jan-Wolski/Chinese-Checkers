package jw.lab4.checkers;

public class FieldsConstructor {

  protected Field[] fields;
  protected Field[][] fieldsPos;
  int height;
  int width;

  public void constructStar() {
    fields = new Field[121];
    height = 17;
    width = 13;
    fieldsPos = new Field[height][width];

    for (int i = 0; i < fields.length; i++) {
      fields[i] = new Field(6);
    }

    int p = 0;

    p = triangle(p, 0, 4, 4, false, false, 0);
    p = triangle(p, 4, 5, 13, true, true, 1);
    p = triangle(p, 9, 4, 13, false, true, 2);
    p = triangle(p, 13, 4, 4, true, false, 3);

    populateNeighbours();
  }

  public Field[] getFields() {
    return fields;
  }

  public Field[][] getFieldsPos() {
    return fieldsPos;
  }

  private int triangle(int p, int row, int rowNum, int colMax, boolean desc, boolean dist, int player) {
    int d = 0;
    if (desc) {
      d = 1;
    }

    for (int i = 0; i < rowNum; i++) {
      int pad = i * d + (1 - d) * (rowNum - 1 - i);
      for (int j = 0; j < colMax - pad; j++) {
        fieldsPos[row + i][(width - colMax + pad) / 2 + j] = fields[p];
        if (dist) {
          if (j < 4 - pad) {
            fields[p].base = 6 - player;
          } else if (j > colMax - pad - 5 + pad) {
            fields[p].base = player;
          }
        } else {
          fields[p].base = player;
        }
        p++;
      }
    }
    return p;
  }

  private void populateNeighbours() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (fieldsPos[i][j] != null) {
          try {
            fieldsPos[i][j].neighbours[0] = fieldsPos[i][j + 1];
            fieldsPos[i][j].neighbours[1] = fieldsPos[i + 1][j + 1 - ((i + 1) % 2)];
            fieldsPos[i][j].neighbours[2] = fieldsPos[i + 1][j - ((i + 1) % 2)];
            fieldsPos[i][j].neighbours[3] = fieldsPos[i][j - 1];
            fieldsPos[i][j].neighbours[4] = fieldsPos[i - 1][j - ((i + 1) % 2)];
            fieldsPos[i][j].neighbours[5] = fieldsPos[i - 1][j + 1 - ((i + 1) % 2)];
          } catch (IndexOutOfBoundsException e) {

          }
        }
      }
    }
  }

}
