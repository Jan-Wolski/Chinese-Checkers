package jw.Lab4.checkers;

/**
 * Manages flow of the game.
 */
public class Board {
    
    private int playersNumber=0;

    private int turn=0;
    private int playerTurn=0;

    private Field[] fields;


    Board(){

    }

    public void createBoard(int playersNumber){
        this.playersNumber = playersNumber;
    }


    public void move(int field, int dir){
        fields[field].move(dir);
    }

    public void undo(){

    }

    public void changePlayer(int player){

    }

    public void skip(){

    }
}
