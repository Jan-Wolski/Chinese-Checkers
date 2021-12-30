package jw.lab4.checkers;

public class FieldsConstructor {

  protected Field[] fields;
  protected Field[][] fieldsPos;
  int height;
  int width;

  static Field[] constructSquare() {
    Field[] fields = new Field[5 * 5];
    for (int i = 0; i < fields.length; i++) {
      fields[i] = new Field(4);
    }
    for (int i = 0; i < fields.length; i++) {
      if (i < fields.length - 1)
        fields[i].neighbours[0] = fields[i + 1];
      if (i > 0)
        fields[i].neighbours[2] = fields[i - 1];

      if (i < fields.length - 5)
        fields[i].neighbours[1] = fields[i + 5];

      if (i > 4)
        fields[i].neighbours[3] = fields[i - 5];
    }
    fields[0].player = 0;
    fields[5].player = 1;
    fields[20].player = 2;
    return fields;
  }

  public void constructStar() {
    Field[] fields = new Field[73];
    height = 17;
    width = 13;
    Field[][] fieldsPos = new Field[height][width];

    for (int i = 0; i < fields.length; i++) {
      fields[i] = new Field(6);
    }
    
    int p = 0;

    p = triangle(p, 0, 4, 4, false, false, 0);
    p = triangle(p, 4, 5, 13, true, true, 1);
    p = triangle(p, 9, 4, 13, false, true, 3);
    p = triangle(p, 13, 4, 4, true, false, 5);

    for (int i = 0; i < 5; i++) {
      for (int j = width; j > 0; j--) {
        fieldsPos[i][width / 2 - i + j] = fields[p];
        fields[p].player = 0;
        p++;
      }
    }

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
      for (int j = 0; j < colMax - i * d - (1 - d) * (colMax - i); j++) {
        fieldsPos[row + i][(width - colMax) / 2 + i + j] = fields[p];
        if (dist) {
          if (j < rowNum - i) {
            fields[p].player = player;
          } else if (j > colMax - rowNum + i) {
            fields[p].player = player + 1;
          }
        } else {
          fields[p].player = player;
        }
        p++;
      }
    }
    return p;
  }

  private void populateNeighbours() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if(fieldsPos[i][j] != null){
          fieldsPos[i][j].neighbours[0]=fieldsPos[i][j+1];
          fieldsPos[i][j].neighbours[1]=fieldsPos[i+1][j+1];
          fieldsPos[i][j].neighbours[2]=fieldsPos[i+1][j];
          fieldsPos[i][j].neighbours[3]=fieldsPos[i][j-1];
          fieldsPos[i][j].neighbours[4]=fieldsPos[i-1][j];
          fieldsPos[i][j].neighbours[5]=fieldsPos[i-1][j+1];
        }
      }
    }
  }

}
