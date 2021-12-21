// package jw.lab4.checkers;

// import java.util.Scanner;

// public class UserCI extends User implements Runnable{

//   private Scanner in;

//   public UserCI(){
//     setup();
//   }


//   public void setup() {
//     in = new Scanner(System.in);

//   }

//   @Override
//   public void run() {
//     String line;
//     while (in.hasNextLine()) {
//       line = in.nextLine();
//       MoveInstructions move = new MoveInstructions();
//       move.deserialize(line);
//       gameMove(move);
//     }

//   }

// }
