package jw.lab4.checkers.Database;

import java.security.spec.ECPublicKeySpec;
import java.util.ArrayList;
import java.util.List;

import jw.lab4.checkers.MoveInstructions;

public class Game {
  int id;
  int playersNumber;
  int lastPlayer;

  List<Player> players;
  List<Move> moves;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPlayersNumber() {
    return playersNumber;
  }

  public void setPlayersNumber(int playersNumber) {
    this.playersNumber = playersNumber;
  }

  public int getLastPlayer() {
    return lastPlayer;
  }

  public void setLastPlayer(int lastPlayer) {
    this.lastPlayer = lastPlayer;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public List<Move> getMoves() {
    return moves;
  }

  public void setMoves(List<Move> moves) {
    this.moves = moves;
  }


  public Game(){
    players = new ArrayList<>();
    moves = new ArrayList<>();
  }

  public void update(MoveInstructions instr) {
    if (instr.state == MoveInstructions.STATE.PLAY) {
      lastPlayer = instr.player;
      Move turn = new Move(instr.field1, instr.field2);
      moves.add(turn);
    } else if (instr.state == MoveInstructions.STATE.READY) {
      Player p = new Player(instr.nick);
      if(players.size()>instr.player){
        players.add(instr.player, p);
      }else{
        players.add(p);
      }
    } else if (instr.state == MoveInstructions.STATE.JOIN) {
      playersNumber = instr.player;

    }
  }

  public void load(){

  }


}
