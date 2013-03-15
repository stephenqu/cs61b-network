/* RandomPlayer.java */

package player;
import java.util.Random;

/**
 *  An implementation of an automatic Network player that makes random moves.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class RandomPlayer extends Player {

  private int color;
  private int opponentColor;
  private Board theBoard;


  /**
   *  Creates a random player with the given color.  Color is either Board.BLACK or 
   *  Board.WHITE.
   * @param color Either Board.BLACK or Board.WHITE
   */
  public RandomPlayer(int color) {
    this.color = color;
    if (color == Board.BLACK) {
    	this.opponentColor = Board.WHITE;
    }
    this.theBoard = new Board();
  }


  /** Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
   * 
   */
public Move chooseMove() {
    Move[] possible = theBoard.validMoves(); //generate a list of all valid moves
    Random rando = new Random();
    return possible[((int) (rando.nextDouble() * possible.length))]; //return a random element of the possible moves
  }

  /** If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.'
   * 
   */
  public boolean opponentMove(Move m) {
    if (theBoard.validMove(m)) {
      theBoard.doMove(m, opponentColor);
      return true;
    }
    else {
      return false;
    }
  }

  /** If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
   * 
   */
  public boolean forceMove(Move m) {
    if (theBoard.validMove(m)) {
      theBoard.doMove(m, color);
      return true;
    }
    else {
      return false;
    }
  }

}
