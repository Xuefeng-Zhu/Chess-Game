package pieces;

import java.util.HashSet;

import management.GameManager;
import management.Movement;
import management.Player;
import board.Chessboard;
import board.Location;

/**
 * Pieces defines the property of a piece, move a piece, and check the available
 * locations to move
 * 
 * @author Xuefeng Zhu
 *
 */
public abstract class Piece {
	public static final String WHITE = "white";
	public static final String BLACK = "black";

	protected String color;
	protected Location location;
	protected Player player;
	// The directions the piece is able to move directions is a 2d array.
	// For the inner array, the first element stands row step, second element
	// stands for column step, third element stands for if the movement is
	// single
	protected int[][] directions;
	protected String iconDir;

	protected HashSet<Location> availableLocations;

	/**
	 * Construct Piece, add the piece to chessboard, and add it to player
	 * 
	 * @param location
	 * @param player
	 */
	public Piece(Location location, Player player) {
		this.color = player.getColor();

		this.location = location;
		Chessboard chessboard = Chessboard.getInstance();
		chessboard.addPiece(this, location);

		this.player = player;
		this.player.addPiece(this);
	}

	/**
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return iconDir
	 */
	public String getIconDir() {
		return iconDir;
	}

	/**
	 * @return availableLocation
	 */
	public HashSet<Location> getAvailableLocations() {
		return availableLocations;
	}

	/**
	 * Move the piece to specific location, and capture the opponent piece,
	 * which is at the location
	 * 
	 * @param location
	 * @return
	 */
	public boolean moveTo(Location location) {
		if (availableLocations.contains(location)) {
			Chessboard chessboard = Chessboard.getInstance();

			Movement movement = new Movement(this);
			// check if there is opponent at the location
			Piece opponent = chessboard.getPiece(location);
			if (opponent != null) {
				captureOpponent(opponent);
				movement.capturedPiece = opponent;
			}

			chessboard.movePiece(this, location);
			this.location = location;

			GameManager gameManager = GameManager.getInstance();
			gameManager.setPreMovement(movement);
			return true;
		}

		return false;
	}

	/**
	 * Helper function for moveTo to capture opponent piece
	 * 
	 * @param opponent
	 */
	protected void captureOpponent(Piece opponent) {
		Chessboard chessboard = Chessboard.getInstance();
		chessboard.removePiece(opponent.location);
		opponent.player.removePiece(opponent);
	}

	/**
	 * Move the piece back to previous location
	 * 
	 * @param location
	 *            previous location
	 */
	public void undoMove(Location location) {
		Chessboard chessboard = Chessboard.getInstance();
		chessboard.movePiece(this, location);
		this.location = location;
	}

	/**
	 * Update the available locations based on latest chessboard condition
	 * 
	 * @return
	 */
	public HashSet<Location> updateAvaiableLocations() {
		HashSet<Location> result = new HashSet<Location>();

		for (int[] direction : directions) {
			locationScanner(result, direction);
		}

		availableLocations = result;
		return result;
	}

	/**
	 * Helper function for updateAvaiableLocations to search for available
	 * locations at specific direction
	 * 
	 * @param result
	 * @param direction
	 */
	protected void locationScanner(HashSet<Location> result, int[] direction) {
		int rowOffset = direction[0];
		int colOffset = direction[1];
		Boolean singleMove = direction[2] == 1 ? true : false;

		Chessboard chessboard = Chessboard.getInstance();
		Location targetLocation = new Location(location);

		do {
			targetLocation.shiftLocation(rowOffset, colOffset);

			try {
				Piece target = chessboard.getPiece(targetLocation);
				// empty location
				if (target == null) {
					result.add(chessboard.getLocatoin(targetLocation.getRow(),
							targetLocation.getCol()));
				} else {
					// opponent piece located at the location
					if (target.color != color) {
						result.add(chessboard.getLocatoin(
								targetLocation.getRow(),
								targetLocation.getCol()));
					}
					break;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
		} while (!singleMove);
	}
}
