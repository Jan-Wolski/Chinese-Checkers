package jw.lab4.checkers.Database;

public class Move {
  int id;
  int moveId;
  int fieldOne;
  int fieldTwo;


  
  public int getId() {
    return id;
  }



  public void setId(int id) {
    this.id = id;
  }



  public int getMoveId() {
    return moveId;
  }



  public void setMoveId(int moveId) {
    this.moveId = moveId;
  }


  public int getFieldOne() {
    return fieldOne;
  }



  public void setFieldOne(int fieldOne) {
    this.fieldOne = fieldOne;
  }



  public int getFieldTwo() {
    return fieldTwo;
  }



  public void setFieldTwo(int fieldTwo) {
    this.fieldTwo = fieldTwo;
  }



  public Move(int fo, int ft) {
    fieldOne = fo;
    fieldTwo = ft;
  }
}
