package board;

import java.util.HashSet;

import pieces.*;

/**
 * ChessBoard stores information related to chessboard, including how pieces
 * arranged in the chessboard, width and height of chessboard
 * 
 * @author Xuefeng Zhu
 *
 */

public class Chessboard {
	private static Chessboard chessBoard = null;

	private int width;
	private int height;
	private Piece[][] board;	// store pieces on the chessboard
	private Location[][] locations;		// store Location Objects based on their position

	/**
	 * Construct a ChessBoard with certain width and height
	 * 
	 * @param width
	 * @param height
	 */
	protected Chessboard(int width, int height) {
		this.width = width;
		this.height = height;
		this.board = new Piece[width][height];
		this.locations = new Location[width][height];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				this.locations[row][col] = new Location(row, col);
			}
		}
	}

	/**
	 * Init a new ChessBoard
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public static Chessboard initInstance(int width, int height) {
		chessBoard = new Chessboard(width, height);
		return chessBoard;
	}

	/**
	 * Get the static chessboard Object if it exits Otherwises, create a new
	 * ChessBoard
	 * 
	 * @return
	 */
	public static Chessboard getInstance() {
		if (chessBoard == null) {
			chessBoard = new Chessboard(8, 8);
		}
		return chessBoard;
	}

	/**
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the Location correspond to row and column
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public Location getLocatoin(int row, int col) {
		return locations[row][col];
	}

	/**
	 * Validate if the location is within the range of the chessboard
	 * 
	 * @param location
	 * @return
	 */
	public boolean validateLocation(Location location) {
		int row = location.getRow();
		int col = location.getCol();

		if (row < 0 || row >= this.height) {
			return false;
		}

		if (col < 0 || col >= this.width) {
			return false;
		}

		return true;
	}

	/**
	 * Get the Piece at provided Location
	 * 
	 * @param location
	 * @return
	 */
	public Piece getPiece(Location location) {
		int row = location.getRow();
		int col = location.getCol();

		return board[row][col];
	}

	/**
	 * Remove the piece at provided Location
	 * 
	 * @param location
	 * @return true if a piece is removed, false is the location is not occupied
	 */
	public boolean removePiece(Location location) {
		int row = location.getRow();
		int col = location.getCol();

		if (board[row][col] != null) {
			board[row][col] = null;
			return true;
		}

		return false;
	}

	/**
	 * Add the piece at specific location in the chessboard
	 * 
	 * @param piece
	 * @param location
	 * @return true if piece added, false if the location is occupied
	 */
	public boolean addPiece(Piece piece, Location location) {
		int row = location.getRow();
		int col = location.getCol();

		if (board[row][col] == null) {
			board[row][col] = piece;
			return true;
		}

		return false;
	}

	/**
	 * Move the piece to specific location. Remove old piece if the location is
	 * occupied
	 * 
	 * @param piece
	 * @param location
	 * @return true if the operation is successful
	 */
	public boolean movePiece(Piece piece, Location location) {
		// try to remove old piece
		if (!removePiece(piece.getLocation())) {
			return false;
		}

		// try to add the new piece
		if (!addPiece(piece, location)) {
			addPiece(piece, piece.getLocation());
			return false;
		}

		return true;
	}

	/**
	 * Get a set of Location between two Locations
	 * 
	 * @param start
	 * @param end
	 * @return set of location in the path
	 */
	public HashSet<Location> getPath(Location start, Location end) {
		HashSet<Location> result = new HashSet<Location>();

		// validate the start and end locations
		if (!(validateLocation(start) && validateLocation(end))) {
			return result;
		}

		int rowOffset = end.getRow() - start.getRow();
		int colOffset = end.getCol() - start.getCol();

		// check if there is valid path between the locations
		if (rowOffset == 0 || colOffset == 0
				|| Math.abs(rowOffset / colOffset) == 1) {
			// normalized offset
			rowOffset = rowOffset != 0 ? rowOffset / Math.abs(rowOffset) : 0;
			colOffset = colOffset != 0 ? colOffset / Math.abs(colOffset) : 0;

			// get Location in the path
			Location curLocation = new Location(start);
			curLocation.shiftLocation(rowOffset, colOffset);
			while (!curLocation.equals(end)) {
				result.add(getLocatoin(curLocation.getRow(),
						curLocation.getCol()));
				curLocation.shiftLocation(rowOffset, colOffset);
			}
		}

		return result;
	}

}
