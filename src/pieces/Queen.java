package pieces;

import management.Player;
import board.Location;

/**
 * Class defines queen's movement
 * 
 * @author Xuefeng Zhu
 *
 */
public class Queen extends Piece {

	/**
	 * Construct Queen and define its directions
	 * 
	 * @param location
	 * @param player
	 */
	public Queen(Location location, Player player) {
		super(location, player);

		this.directions = new int[][] { { 1, 0, 0 }, { -1, 0, 0 }, { 0, 1, 0 },
				{ 0, -1, 0 }, { 1, 1, 0 }, { 1, -1, 0 }, { -1, 1, 0 },
				{ -1, -1, 0 } };
		this.iconDir = "res/" + this.color + "/queen.png";
	}
}
