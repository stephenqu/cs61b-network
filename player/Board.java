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
         * Returns the color of the other player.
         *
         * @param player
         * @return other color
         */

        public int otherPlayer(int player) {
          assert (player == WHITE || player == BLACK);
          if (player == WHITE) {
            return BLACK;
          }
          else {
            return WHITE;
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
                if (p1 == null) {
                  continue;
                }
                if (p1.getColor() == p.getColor()) {
		    inCluster++;
		}
		if (inCluster > 1) {
		    return false;
		}
		for (Piece p2: getSurroundings(p1)) {
		    if (p2 != null && p1.getColor() == p.getColor() && p2.getColor() == p.getColor()) {
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
	  Piece to = this.getPiece(m.x1, m.y1);
          if ((m.moveKind == Move.STEP && numMoves <= 20) || (m.moveKind == Move.ADD && numMoves > 20)) {
            return false;
          }
          if (m.moveKind == Move.STEP) {
            Piece from = this.getPiece(m.x2, m.y2);
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
          DList froms = new DList();
          for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
		  if (inBounds(i,j) && getPiece(i, j).getColor() == EMPTY && !(inOpponentGoal(nextPlayer, i, j) && !(makesCluster(nextPlayer, i, j)))) {
		    empties.insertBack(getPiece(i, j));
		  }
                  if (inBounds(i,j) && getPiece(i,j).getColor() == nextPlayer) {
                    froms.insertBack(getPiece(i,j));
                  }
	    }
	  }

          DList valids = new DList();
          System.out.println(empties);
          for (DListNode empty : empties) {
        	Piece emptyPiece = new Piece(-1, -1, EMPTY); //this janky method needs to get fixed.
			try {
				emptyPiece = ((Piece) empty.item());
			} catch (InvalidNodeException e) {
				e.printStackTrace();
				assert false;
			}
            int emptyX = emptyPiece.getX(), emptyY = emptyPiece.getY();
            if (numMoves <= 20) {
              valids.insertBack(new Move(emptyX, emptyY));
            }
            else {
              Piece from;
              for (DListNode dLN : froms) {
                try {
                  from = ((Piece) dLN.item());
                }
                catch (InvalidNodeException e) {
                  System.out.println("INException in validMoves.");
                  from = new Piece(-1, -1, EMPTY);
                }
                valids.insertBack(new Move(emptyX, emptyY, from.getX(), from.getY()));
              }
            }
          }
          return valids;
        }

    	/**
    	 * Returns the identity of the winner, or EMPTY if there is none.
    	 * This is an expensive operation; this should only be done once per Board.
    	 * TODO
    	 * @return Which player has won
    	 */
    	public int winner() {
    		DList networks = findWinningNetworks(); //get the winning networks
    		if (networks.length() == 0) { //if there aren't any, nobody has won
    			return EMPTY;
    		}

    		//find the color of the first network
    		ListNode cur = networks.front();
    		int winnerColor = EMPTY;
    		try {
    			Network n = (Network) cur.item();
    			winnerColor = n.color();
    			cur = cur.next();
    		} catch (InvalidNodeException e) {
    			assert false;
    		}

    		assert winnerColor != EMPTY : "Winner is EMPTY and should not be EMPTY";
    		//iterate through the networks
    		while (cur.isValidNode()) {
    			try {
    				Network n = (Network) cur.item();
    				if (n.color() != winnerColor) //that means that there's more than one color of winning network.
    					return nextPlayer;
    				cur = cur.next();
    			} catch (InvalidNodeException e) {
    				assert false;
    			}
    		}
    		//if we've gotten here, that means all the networks are of one color.
    		return winnerColor;
    	}

    	/*
    	 * findWinningNetwork module begins here
    	 */

    	/**
    	 * Finds a DList of Networks
    	 * @return
    	 */
    	private DList findWinningNetworks() {
    		boolean TRUNCATED = false; //if true, will only search for one network of each color
    		DList networks = new DList();
    		//checks for BLACK networks
    		for (int i = 1; i < this.getDimension() - 1; i++) {
    			Piece p = this.getPiece(0, i);
    			if (p.getColor() != Piece.EMPTY) {
    				Network n = findNetwork(p);
    				if (n != null) {
    					networks.insertBack(n);
    					if (TRUNCATED)
    						break;
    				}
    			}
    		}
    		//checks for WHITE networks
    		for (int i = 1; i < this.getDimension() - 1; i++) {
    			Piece q = this.getPiece(i, 0);
    			if (q.getColor() != Piece.EMPTY) {
    				Network n = findNetwork(q);
    				if (n != null) {
    					networks.insertBack(n);
    					if (TRUNCATED)
    						break;
    				}
    			}
    		}
    		return networks;
    	}

    	/**
    	 * Figures out if, from piece p, there exists a valid network. If so,
    	 * returns that network, otherwise, returns null.
    	 *
    	 * @param p
    	 * @return the network from p if it exists, null otherwise
    	 */
    	private Network findNetwork(Piece p) {
    		Piece[] pieceArray = new Piece[1];
    		pieceArray[0] = p;
    		Piece[] pieces;
    		try {
    			pieces = findNetwork(pieceArray);
    			if (pieces != null)
    				return new Network(pieces);
    			else
    				return null;
    		} catch (InvalidNodeException e) {
    			e.printStackTrace();
    			assert false;
    		}
    		return null;
    	}

    	private Piece[] findNetwork(Piece[] prevPieces) throws InvalidNodeException {
    		// find connections for the last Piece in prevPieces
    		DList conns = this
    				.findPieceConnections(prevPieces[prevPieces.length - 1]);
    		assert conns.length() > 0;

    		//iterate through conns
    		for (DListNode node : conns) {
    			Piece p = (Piece) node.item();
    			//if prevPieces doesn't contain p, and p isn't in the path of prevPieces
    			if (!containsPiece(prevPieces, p) && !inRayPath(prevPieces, p)) {
    				if (isInEndZone(p)) {
    					if (isInFarEndZoneIfAlreadyInEndZone(p)) {
    						if (prevPieces.length >= 5) { //this one will make 6!
    							return appendToPieceArray(prevPieces, p);
    						}
    					} else {// it's not in the far endzone, so it's in the near one
    						continue;
    					}
    				} else { //not in end zone
    					Piece[] attempt = findNetwork(appendToPieceArray(prevPieces, p));
    					if (attempt != null)
    						return attempt;
    				}
    			}
    		}
    		//if we haven't found any, return null
    		return null;
    	}

    	private boolean isInEndZone(Piece p) {
    		int x = p.getX();
    		int y = p.getY();
    		int last = this.getDimension() - 1;
    		return x == 0 || y == 0 || x >= last || y >= last;
    	}

    	private boolean isInFarEndZoneIfAlreadyInEndZone(Piece p) {
    		return p.getX() != 0 && p.getY() != 0;
    	}

    	private static boolean containsPiece(Piece[] pieces, Piece p) {
    		assert pieces.length > 0;
    		for (Piece i : pieces) {
    			if (i.equals(p))
    				return true;
    		}
    		return false;
    	}

    	private static Piece[] appendToPieceArray(Piece[] original, Piece p) {
    		assert p != null;
    		Piece[] ans = new Piece[original.length + 1];
    		for (int i = 0; i < original.length; i++) {
    			ans[i] = original[i];
    		}
    		ans[original.length] = p;
    		return ans;
    	}

    	private static boolean inRayPath(Piece[] pieces, Piece p) {
    		if (pieces.length < 2) // no ray!
    			return false;
    		int x1 = pieces[pieces.length - 2].getX();
    		int y1 = pieces[pieces.length - 2].getY();
    		int x2 = pieces[pieces.length - 1].getX();
    		int y2 = pieces[pieces.length - 1].getY();
    		int x3 = p.getX();
    		int y3 = p.getY();
    		return (x2 - x1) * (y3 - y2) == (x3 - x2) * (y2 - y1);
    	}

    	/**
    	 * A wrapper class for a Network of Pieces;
    	 *
    	 * @author Allen Li
    	 *
    	 */
    	private final class Network {
    		private final Piece[] pieces;
    		private final int color;

    		Network(Piece[] pieces) {
    			assert pieces.length >= 6;
    			this.pieces = pieces;
    			this.color = pieces[0].getColor();
    		}

    		/**
    		 * The color of this network
    		 *
    		 * @return color
    		 */
    		int color() {
    			return this.color;
    		}

    		/**
    		 * The array containing the nodes in the network.
    		 *
    		 * @return a DList
    		 */
    		@SuppressWarnings("unused")
    		Piece[] getPieces() {
    			return pieces;
    		}
    	}

	/*
	 * findWinningNetwork module ends here
	 */

	/**
	 * Executes the Move m in the parameter if it is valid.
	 *
	 * @param m
         * @return true if it does move, else false
	 */
	public boolean doMove(Move m) {
          if (validMove(m)) {
            if (m.moveKind == Move.STEP) {
              removePiece(getPiece(m.x2,m.y2));
            }
            addPiece(new Piece(m.x1, m.y1, otherPlayer(nextPlayer)));
            nextPlayer = otherPlayer(nextPlayer);
            numMoves++;
            return true;
	  }
          return false;
        }

        /**
         * Undoes the Move m
         *
	 * @param m
	 */
        public void reverseMove(Move m) {
          if (m.moveKind == Move.STEP) {
            pieces[m.x2][m.y2] = new Piece(m.x2, m.y2, otherPlayer(nextPlayer));
          }
          pieces[m.x1][m.y1] = new Piece(m.x1, m.y1, EMPTY);
          nextPlayer = otherPlayer(nextPlayer);
          numMoves--;
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

          if (whiteScore == 0 && blackScore == 0) {
            return 0;
          }
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
         * Removes the Piece p from this Board. It will be used by reverse move
         * and doMove.
         *
         * @param p
         */
        public void removePiece(Piece p) {
          if (p.getColor() == EMPTY) {
            System.out.println("Can't remove an empty Piece");
          }
          pieces[p.getX()][p.getY()] = p;
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
