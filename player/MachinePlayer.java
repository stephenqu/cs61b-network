/* MachinePlayer.java */

package player;
import list.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
 /**
  * myName is the MachinePLayer's name as recognized by the game Network.
  * COLOR is the color this machine player is playing as.
  * maxDepth is the maximum search depth for this MachinePlayer.
  * board is this MachinePlayer's internal representation of a game board.
  * variableDepth is a boolean that indicates whether this MachinePlayer
  *   uses a variable search depth.
  **/

  String myName = "machine";
  private int COLOR;
  private final int maxDepth;
  private Board board;
  private boolean variableDepth = false;


 /**
  *  Creates a machine player with the given color.  Color is either 0 (black)
  *  or 1 (white).  (White has the first move.)
  *
  * @param color is the color of this MachinePLayer
  **/
  public MachinePlayer(int color) {
      this(color, 4);
      variableDepth = true;
  }

 /**
  * Creates a machine player with the given color and search depth.  Color is
  *  either 0 (black) or 1 (white).  (White has the first move.)
  *
  * @param color is the color of this MachinePlayer, searchDepth is the max
  * depth for searching the game tree.
  *
  **/
  public MachinePlayer(int color, int searchDepth) {
      if (color == 0){
	  this.COLOR = Board.BLACK;
      }else{
	  this.COLOR = Board.WHITE;
      }
      this.maxDepth = searchDepth;
      this.board = new Board();
  }

 /**
  * Returns a new move by "this" player.  Internally records the move (updates
  * the internal game board) as a move by "this" player.
  *
  * @return the best move found by minimax/alpha-beta pruning
  **/
  public Move chooseMove() {
      Move theChosen;
      if (variableDepth && (board.getNumMoves() > 20)){
          theChosen = chooseMove(this.COLOR, -1, 1, maxDepth-2).bestMove;
      }else{
          theChosen = chooseMove(this.COLOR, -1, 1, maxDepth).bestMove;
      }
      assert theChosen != null;
      board.doMove(theChosen);
      //System.out.println("Computer color is " + this.COLOR);
      //System.out.println("Next player is "+board.getNextPlayer());
      //System.out.println(board);
      return theChosen;
  }

 /**
  * Performs the minimax search algorithm with alpha-beta pruning to find the
  * best move within this MachinePlayer's specified search depth
  *
  * @param side is the player that is about to make their move
  *        alpha is the best move that the computer can achieve
  *        beta is the best move that the opponent can achieve
  *        depth is the maximum search depth of the algorithm
  * @return a Best object that stores the best Move and the score of that Move.
  **/
  public Best chooseMove(int side, double alpha, double beta, int depth){
      //System.out.println("" + depth);
      Best bestMove = new Best();
      Best bestReply;
      DList moves = board.validMoves();
      double boardScore = board.boardEval();
      if (this.COLOR == Board.BLACK){
      boardScore = -1*board.boardEval(); // Flips the scale when Computer is BLACK, because 1 is the case where WHITE wins, and -1 is the case where BLACK wins.
      }
      if (depth == maxDepth) {
        //System.out.println(moves);
      }
      //System.out.println(board.boardEval());
      if ( (depth == 0) || (boardScore == 1) || (boardScore == -1) || (moves.length()==0) ){
	  return new Best(boardScore);
      }
      if (moves.length() == 0){
	  bestMove.bestMove = new Move();
	  bestMove.score = 0;
	  return bestMove;  //Returns a quit-move if there are no available moves.
      }

      if (side == this.COLOR){
	  bestMove.score = alpha;
      }else{
	  bestMove.score = beta;
      }
      try{
      for (DListNode m: moves){
	  board.doMove( (Move) (m.item()) );
      	  bestReply = chooseMove(board.getNextPlayer(), alpha, beta, depth-1);
          bestReply.score *= 0.99; // a little bit less because we want boards that are the same score to be higher if it is fewer moves away
	  board.reverseMove( (Move) (m.item()) );
	  if ( (side == this.COLOR) && (bestReply.score > bestMove.score)){
	      bestMove.bestMove = (Move) m.item();
	      bestMove.score = bestReply.score;
	      alpha = bestReply.score;
	  }
	  if ( (side == board.otherPlayer(this.COLOR)) && (bestReply.score < bestMove.score)){
	      bestMove.bestMove = (Move) m.item();
	      bestMove.score = bestReply.score;
	      beta = bestReply.score;
	  }
	  if (alpha >= beta){
	      return bestMove;
	  }
      }
      }catch (InvalidNodeException e){
    	  assert false;
      }
      return bestMove;
  }

 /**
  * If the Move m is legal, records the move as a move by the opponent
  * (updates the internal game board) and returns true.  If the move is
  * illegal, returns false without modifying the internal state of "this"
  * player.  This method allows your opponents to inform you of their moves.
  *
  * @param m is the move that the opponent is going to make
  * @return true if the move was successful
  **/
  public boolean opponentMove(Move m) {
      boolean opp = board.doMove(m);
      //System.out.println("The opponent has moved!");
      //System.out.println(board);
      return opp;
  }

 /**
  * If the Move m is legal, records the move as a move by "this" player
  * (updates the internal game board) and returns true.  If the move is
  * illegal, returns false without modifying the internal state of "this"
  * player.  This method is used to help set up "Network problems" for your
  * player to solve.
  *
  * @param m is the move that this MachinePlayer is going to make
  * @return true if the move was successful
  **/
  public boolean forceMove(Move m) {
      return board.doMove(m);
  }

}

/**
 * A private class that stores a Move and a score associated with performing
 * that Move.
 **/

class Best{
    /**
     * bestMove is the bestMove of a given player.
     * score is the score of the board as a result of perfoming bestMove
     **/

    public Move bestMove;
    public double score;

    /**
     * Creates a new Best.
     **/
    public Best(){
    }

    /**
     * Creates a new Best with the given score
     *
     * @param score is the score of this move.
     **/

    public Best(double score){
	this.score = score;
    }
}
