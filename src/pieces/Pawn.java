package pieces;

import java.util.HashSet;

import management.Player;
import board.Chessboard;
import board.Location;

/**
 * Class defines pawn's movement
 * 
 * @author Xuefeng Zhu
 *
 */
public class Pawn extends Piece {

	/**
	 * Construct Pawn and define its directions
	 * 
	 * @param location
	 * @param player
	 */
	public Pawn(Location location, Player player) {
		super(location, player);

		if (this.color == WHITE) {
			this.directions = new int[][] { { -1 }, { -2, 0 }, { -1, 1 },
					{ -1, -1 } };
		} else {
			this.directions = new int[][] { { 1 }, { 2, 0 }, { 1, 1 },
					{ 1, -1 } };
		}
		this.iconDir = "res/" + this.color + "/pawn.png";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pieces.Piece#updateAvaiableLocations()
	 */
	@Override
	public HashSet<Location> updateAvaiableLocations() {
		HashSet<Location> result = new HashSet<Location>();

		checkFront(result, directions[0][0]);

		// check if the pawn is at the initial position
		if ((color == WHITE && location.getRow() == 6)
				|| (color == BLACK && location.getRow() == 1)) {
			checkFront(result, directions[1][0]);
		}

		checkCorner(result, directions[2]);
		checkCorner(result, directions[3]);

		availableLocations = result;
		return result;
	}

	/**
	 * Helper function for pawn' updateAvailable Locations Check if the location
	 * in front of the pawn if available
	 * 
	 * @param result
	 * @param stepSize
	 *            how many steps need to move forward
	 */
	private void checkFront(HashSet<Location> result, int stepSize) {
		Chessboard chessboard = Chessboard.getInstance();
		Location targetLocation = new Location(location);

		targetLocation.shiftLocation(stepSize, 0);
		if (chessboard.validateLocation(targetLocation)
				&& chessboard.getPiece(targetLocation) == null) {
			result.add(chessboard.getLocatoin(targetLocation.getRow(),
					targetLocation.getCol()));
		}
	}

	/**
	 * Helper function for pawn' updateAvailable Locations Check if there is
	 * enemy located at the left or right corner of the pawn
	 * 
	 * @param result
	 * @param direction
	 */
	private void checkCorner(HashSet<Location> result, int[] direction) {
		int rowOffset = direction[0];
		int colOffset = direction[1];

		Chessboard chessboard = Chessboard.getInstance();
		Location targetLocation = new Location(location);

		targetLocation.shiftLocation(rowOffset, colOffset);
		try {
			Piece targetPiece = chessboard.getPiece(targetLocation);
			if (targetPiece != null && targetPiece.color != color) {
				result.add(chessboard.getLocatoin(targetLocation.getRow(),
						targetLocation.getCol()));
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// do nothing
		}

	}

}
