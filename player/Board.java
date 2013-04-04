/* Board.java  */
package player;
import list.*;

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
         * Returns an 8 element array of pieces representing the neighbors of
         * the parameter piece, or null if a neighboring spot is out of bounds.
         *
         * @param Piece p
         * @return Piece[8] of surroundings
         */
        public Piece[] getSurroundings(Piece p) {
          Piece[] surround = new Piece[8];
          surround[0] = getPiece(p.getX() - 1, p.getY() - 1);
          surround[1] = getPiece(p.getX(), p.getY() - 1);
          surround[2] = getPiece(p.getX() + 1, p.getY() - 1);
          surround[3] = getPiece(p.getX() - 1, p.getY());
          surround[4] = getPiece(p.getX() + 1, p.getY());
          surround[5] = getPiece(p.getX() - 1, p.getY() + 1);
          surround[6] = getPiece(p.getX(), p.getY() + 1);
          surround[7] = getPiece(p.getX() + 1, p.getY() + 1);
          return surround;
        }

        /**
         * getSurroundings for specific parameters.
         *
         * @param int color, int x, int y
         * @return Piece[8] of surroundings
         */
        public Piece[] getSurroundings(int color, int x, int y) {
          Piece p = new Piece(color, x, y);
          return getSurroundings(p);
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
		if (p1.getColor() == p.getColor()) {
		    inCluster++;
		}
		if (inCluster > 1) {
		    return false;
		}
		for (Piece p2: getSurroundings(p1)) {
		    if (p1.getColor() == p.getColor() && p2.getColor() == p.getColor()) {
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
          if (m.moveKind == Move.STEP) {
            Piece from = this.getPiece(m.x1, m.y1);
            if (from.getColor() != nextPlayer) {
              return false;
            }
            if ((!inBounds(from.getX(), from.getY()))) {
              return false;
            }
          }

          if (!(inBounds(to.getX(), to.getY())) || inOpponentGoal(to.getColor(), to.getX(), to.getY()) || to.getColor() != EMPTY || makesCluster(to)) {
	      return false;
          }
          return true;
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
        public DList validMoves() {
          DList empties = new DList();
          for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
		  if (getPiece(i, j).getColor() == EMPTY && !(inOpponentGoal(nextPlayer, i, j) && !(makesCluster(nextPlayer, i, j))) && inBounds(i,j)) {
		    empties.insertBack(getPiece(i, j));
		  }
	    }
	  }

          DList valids = new DList();
          for (DListNode empty : empties) {
        	Piece emptyPiece = new Piece(-1, -1, EMPTY); //this janky method needs to get fixed.
			try {
				emptyPiece = ((Piece) empty.item());
			} catch (InvalidNodeException e) {
				e.printStackTrace();
				assert false;
			}
            int emptyX = emptyPiece.getX(), emptyY = emptyPiece.getY();
            if (numMoves <= 10) {
              valids.insertBack(new Move(emptyX, emptyY));
            }
            else {
              Piece[] froms = getSurroundings(emptyPiece);
              for (Piece from : froms) {
                if (from.getColor() == nextPlayer) {
                  valids.insertBack(new Move(emptyX, emptyY, from.getX(), from.getY()));
                }
              }
            }
          }
          return valids;
        }

	/**
	 * Returns the identity of the winner, or EMPTY if there is none.
	 * TODO
	 * @return Which player has won
	 */
	public int winner() {
		return EMPTY;
	}
	
	/*
	 * findWinningNetwork module begins here
	 */
	
	private Network findWinningNetwork() {
		return null;
	}
	
	private class Network {
		public Network() {
			
		}
	}
	
	/*
	 * findWinningNetwork module ends here
	 */

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
         * Evaluates a board on a scale from -1 to 1: -1 meaning black has a winning
         * network, 1 meaning white has a winning network. Numbers in between mean
         * that either team has an advantage. The value is calculated by giving each
         * side a certain amount of points for the different favorable conditions,
         * then subtracting black's points from white's and dividing that difference
         * b the total number of points. This will create an advantage range from -1
         * to 1.
         * Advantages are given by...
         * having series of connections on the board
         *  a series values 2^(n+m) for n connected Pieces with m in goals
         *
         * @return this board's evaluation
         */
        public double boardEval() {
          int whiteScore = 0;
          int blackScore = 0;

          int winner = this.winner();
          if (winner == WHITE) {
            return WHITE;
          }
          if (winner == BLACK) {
            return BLACK;
          }

          int[] conn = findAllConnections();
          whiteScore += conn[0];
          blackScore += conn[1];

          return (whiteScore - blackScore) / (whiteScore + blackScore);

        }

        /**
         * Returns information about the connections (two pieces connected in
         * an orthogonal or diagonal direction not blocked by an opponent's
         * piece) currently on the board.
         *
         * @return [# white conections, # black connections]
         */
        public int[] findAllConnections() {
          DList[] connDict = new DList[java.lang.Math.min(getNumMoves(), 20)]; //dictionary where keys (first entry) are the Piece, and entries are Pieces it's connected to
          int dictInd = 0;
          for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
               if (inBounds(i, j) && getPiece(i, j).getColor() != EMPTY) {
                 connDict[dictInd] = findPieceConnections(getPiece(i, j));
                 connDict[dictInd].insertFront(getPiece(i,j));
                 dictInd++;
               }
            }
          }
          int whiteTotal = 0, blackTotal = 0;
          for (DList conn:connDict) {
            try {
            if (((Piece) conn.front().item()).getColor() == WHITE) {
              whiteTotal += conn.length()-1;
            }
            else {
              blackTotal += conn.length()-1;
            }
            }
            catch (InvalidNodeException e) {System.out.println("INException in findAllConnections. Shouldn't happen.");}
          }
          int[] counts = {whiteTotal, blackTotal};
          return counts;
        }

        /**
         * Returns a DList whose DListNodes contain the Pieces connected to
         * Piece p.
         *
         * @param Piece p
         * @return DList of Pieces
         */
        public DList findPieceConnections(Piece p) {
          int x = p.getX(), y = p.getY();
          DList connections = new DList();
          for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
              if (i != 0 || j != 0) {
                Piece p2 = connInDirection(p, i, j);
                if (p2 != null) {
                  connections.insertBack(p2);
                }
              }
            }
          }
          return connections;
        }

        /**
         * Returns a connected piece in the direction determined by yDelt and
         * xDelt. For example, if looking for a connected Piece in the up
         * direction, do yDelt 1, xDelt 0.
         *
         * @param p
         * @param yDelt
         * @param xDelt
         * @return connected Piece or null
         */

        public Piece connInDirection(Piece p, int xDelt, int yDelt) {
          int x = p.getX() + xDelt, y = p.getY() + yDelt;
          while (inBounds(x, y)) {
            if (p.getColor() == getPiece(x, y).getColor()) {
              return getPiece(x, y);
            }
            else if (getPiece(x, y).getColor() != EMPTY) {
              return null;
            }
            x += xDelt;
            y += yDelt;
          }
          return null;
        }


        /**
	 * Adds the Piece p to this Board. Returns true if successful, false if another piece
	 * is already at that location. May throw ArrayIndexOutOfBounds exceptions if an invalid
	 * location is specified.
	 * @param p the piece
	 * @return whether we were successful in adding the piece
	 */
	public boolean addPiece(Piece p) {
		Piece prev = pieces[p.getX()][p.getY()];
		if (prev.getColor() != Piece.EMPTY) {
			return false;
		}
		pieces[p.getX()][p.getY()] = p;
		return true;
	}

	/**
	 * Adds the Piece p to this Board. Returns true if successful, false if another piece
	 * is already at that location. May throw ArrayIndexOutOfBounds exceptions if an invalid
	 * location is specified.
	 * @param x the x-coordinate of the Piece
	 * @param y the y-coordinate of the Piece
	 * @param color the Color of the piece
	 * @return
	 */
	public boolean addPiece(int x, int y, int color) {
		return this.addPiece(new Piece(x, y, color));
	}

        /**
	 * Returns a String representation of a board.
	 */
	public String toString() {
		String eol = System.getProperty("line.separator");
		String boardBreak = " |";
		String top = "  ";
		for (int a = 0; a < 2 * this.getDimension() - 1; a++){
			boardBreak += "-";
		}
		for (int a = 0; a < this.getDimension(); a++) {
			top += a % 10 + " ";
		}
		boardBreak += "|";
		String ans = top + eol + boardBreak + eol;

		for (int i = 0; i < this.getDimension(); i++) {
			ans += i % 10;
			ans += "|";
			for (int j = 0; j < this.getDimension(); j++) {
				switch (pieces[j][i].getColor()) {
				case Piece.BLACK:
					ans += "B|";
					break;
				case Piece.WHITE:
					ans += "W|";
					break;
				default:
					ans += " |";
				}
			}
			ans += eol + boardBreak + eol;
		}
		return ans;
	}

	/**
	 * Asserts that our invariants are true. Throws exceptions otherwise.
	 * @return always returns true
	 */
	@SuppressWarnings("unused")
	private boolean testInvariants() {
		assert true;
		return true;
	}

	public static void main(String[] args) {
		System.out.println("Hello World");
	}
}
