/* Board.java  */
package player;

/**
 * A public class for storing a Board.
 */

public class Board {

	private Piece[][] pieces;
	private int nextPlayer;
	private int numMoves;
	public static final int BLACK = Piece.BLACK;
	public static final int WHITE = Piece.WHITE;
	public static final int EMPTY = Piece.EMPTY;

	/**
	 * Constructs a new board of width 8 and length 8.
	 */
	public Board() {
		this(8);
	}
	/**
	 * This constructor is private because we don't want to call anything of not length 8.
	 * @param length
	 */
	private Board(int length) {
		nextPlayer = Piece.WHITE;
		numMoves = 0;
		this.pieces = new Piece[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				pieces[i][j] = new Piece(i, j, Piece.EMPTY); // initialize all spaces to EMPTY
			}
		}
	}

        /**
         * Returns the row/column length of the board.
         *
         * @return this board's dimension
         */
        public int getDimension() {
          return pieces.length;
        }

        /**
         * Returns the piece at X and Y position.
         *
         * @param positions x and y
         * @return Piece at position x,y
         */
        public Piece getPiece(int x, int y){
          if (inBounds(x, y)) {
            return pieces[x][y];
          }
          else {
	    return null;
          }
	}

        /**
         * Returns true if the x and y coordinates are in the board.
         *
         * @param positions x and y
         * @return true if coordinates are within the board
         */
        public boolean inBounds(int x, int y) {
	    return (!(x < 0 || x >= this.getDimension() || y < 0 || y >= this.getDimension() || ((x == 0 || x == this.getDimension() - 1) && (y == 0 || y == this.getDimension() - 1))));
	}
        /**
         * Returns true if a Piece of color == color at coordinates x, y
         * will create a cluster of three touching pieces of the same color.
         *
         * @param color, x, y
         * @return true if making pieces.[x][y] color creates a 'cluster'
         */
        public boolean makesCluster(int color, int x, int y) {
          Piece p = new Piece(color, x, y);
    	  return makesCluster(p);
        }

        /**
         * Returns true if this Piece will create a cluster of three 
         * touching pieces of the same color.
         *
         * @param piece
         * @return true if adding this piece creates a 'cluster'
         */
	public boolean makesCluster(Piece p) {
	    int inCluster = 0;
	    for (Piece p1: getSurroundings(p)) {
		if (p1.getColor() == color) {
		    inCluster++;
		}
		if (inCluster > 1) {
		    return false;
		}
		for (Piece p2: getSurroundings(p1)) {
		    if (p1.getColor() == color && p2.getColor() == color) {
			return false;
		    }
		}
	    }
	    return true;
        }

        /**
         * Returns true if a Piece of color color in the position x, y
         * will be in its opponent's goal.
         *
         * @param color, x, y
         * @return true if a piece of this color at position x,y is in the opponent's goal
         */
        public boolean inOpponentGoal(int color, int x, int y) {
	    return ((color == WHITE && (y == 0 || y == this.getDimension()-1)) || (color == BLACK && (x == 0 || x == this.getDimension()-1)));
        }

	/** 
	 * Returns true if this Piece will be in its opponent's goal.
	 *
	 * @param piece
	 * @return true if piece will be in its opponent's goal
	 */
	public boolean inOpponentGoal(Piece piece){
	    return inOpponentGoal(piece.getColor(), piece.getX(), piece.getY());
	}


	/**
	 * Decides if the Move m is a valid move on this board
	 * TODO
	 * @param m
	 * @return Whether the move is valid
	 */
	public boolean validMove(Move m) {
		Piece to = this.getPiece(m.x2, m.y2);

          //Piece to must be within the bounds of the board
          if (!(inBounds(to.getX(), to.getY()))) {
            return false;
          }

          //On a step, the from must be the player's color
          if (m.moveKind == Move.STEP) {
            Piece from = this.getPiece(m.x1, m.y1);
            if (from.getColor() != nextPlayer) {
              return false;
            }
            //Piece from must be within the bounds of the board
            if ((!inBounds(from.getX(), from.getY()))) {
              return false;
            }
          }

          //Cannot move to an occupied space
          if (to.getColor() != EMPTY) {
            return false;
          }

          //Can't create a group of 3
          if (makesGroup(nextPlayer, to)) {
            return false;
          }

          //Can't place a piece in the opponent's goal
          if (inOpponentGoal(to.getColor(), to.getX(), to.getY())) {
	      return false;
          }
	}

        /**
         * Returns a list of all valid moves.
         * First, makes a list of references to all Pieces of color == EMPTY not
         * connected to two other same-colored pieces, then for each of those, adds to a list of
         * Moves the "place" move to that Piece and any "step" moves to that
         * piece.
         * TODO
         *
         * @param m, heldPieces
         * @return array of valid moves.
         */
        public DList validMoves(Move m, Piece[] heldPieces) {
          DList empties = new DList();
          for (i = 0; i < getDimension(); i++) {
            for (j = 0; j < getDimension(); j++) {
		  if (getPiece(i, j).getColor() == EMPTY && !(inOpponentGoal(nextPlayer, i, j) && !(makesGroup(nextPlayer, i, j))) && inBounds(i,j)) {
		    empties.insertBack(getPiece(i, j));
		  }
	    }
	  }
	

          DList valids = new DList();
          for (DListNode empty : empties) {
            Piece emptyPiece = ((Piece) DListNode.item());
            int emptyX = emptyPiece.getX(), emptyY = emptyPiece.getY();
            valids.insertBack(new Move(emptyX, emptyY));
            Piece[] froms = emptyPiece.getNeighbors();
            for (Piece from : froms) {
              if (from.getColor() == nextPlayer) {
                valids.insertBack(new Move(emptyX, emptyY, from.getX(), from.getY()));
              }
            }
          }
          return valids;
        }









	/**
	 * Returns true if a player has won, false if not.
	 * TODO
	 * @return Whether the player has won
	 */
	public boolean winner() {
		return false;
	}

	/**
	 * Executes the Move m in the parameter if it is valid.
	 * TODO
	 * @param m
	 */
	public void doMove(Move m) {
	}

	/**
	 * Returns the player's color who will perform the next move.
	 * @return the player's color
	 */
	public int getNextPlayer() {
		return nextPlayer;
	}

	/**
	 * Returns the number of moves that have occurred so far in the game.
	 * @return the number of moves
	 */
	public int getNumMoves() {
		return numMoves;
	}

	/**
	 * Returns a String representation of a board.
	 * TODO
	 */
	public String toString() {
		return super.toString();
	}

	/**
	 * Asserts that our invariants are true. Throws exceptions otherwise.
	 * @return always returns true
	 */
	@SuppressWarnings("unused")
	private boolean testInvariants() throws AssertionError {
		assert true;
		return true;
	}
}
