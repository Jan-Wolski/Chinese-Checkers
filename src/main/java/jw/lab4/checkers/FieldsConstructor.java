package jw.lab4.checkers;

public class FieldsConstructor {
  static Field[] construct() {
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
}
