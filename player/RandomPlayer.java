/* RandomPlayer.java */

package player;
import java.lang.Math;
import list.*;

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


  /**
   * Returns a new move by "this" player.  Internally records the move (updates
   * the internal game board) as a move by "this" player.
   *
   * @return a random move chosen from the currently valid moves
   */
  public Move chooseMove() {
    List possible = theBoard.validMoves();
    try {
    return ((Move) possible.nth(((int) (Math.random() * possible.length()))).item());
    }
    catch (InvalidNodeException e) {
      System.out.println("InvalidNode Exception thrown in RandomPlayer.ChooseMove");
      return null;
    }
  }

  /** If the Move m is legal, records the move as a move by the opponent
   * (updates the internal game board) and returns true.  If the move is
   * illegal, returns false without modifying the internal state of "this"
   * player.  This method allows your opponents to inform you of their moves.'
   *
   * @param Move m
   * @return true if successful
   */
  public boolean opponentMove(Move m) {
    if (theBoard.validMove(m)) {
      theBoard.doMove(m);
      return true;
    }
    else {
      return false;
    }
  }

  /** If the Move m is legal, records the move as a move by "this" player
   * (updates the internal game board) and returns true.  If the move is
   * illegal, returns false without modifying the internal state of "this"
   * player.  This method is used to help set up "Network problems" for your
   * player to solve.
   *
   * @param Move m
   * @return true if successful
   */
  public boolean forceMove(Move m) {
    if (theBoard.validMove(m)) {
      theBoard.doMove(m);
      return true;
    }
    else {
      return false;
    }
  }

}
