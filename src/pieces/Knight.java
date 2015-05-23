package pieces;

import management.Player;
import board.Location;

/**
 * Class defines knight's movement
 * 
 * @author Xuefeng Zhu
 *
 */
public class Knight extends Piece {

	/**
	 * Construct Knight and define its directions
	 * 
	 * @param location
	 * @param player
	 */
	public Knight(Location location, Player player) {
		super(location, player);

		this.directions = new int[][] { { 2, 1, 1 }, { 2, -1, 1 },
				{ -2, 1, 1 }, { -2, -1, 1 }, { 1, 2, 1 }, { 1, -2, 1 },
				{ -1, 2, 1 }, { -1, -2, 1 } };
		this.iconDir = "res/" + this.color + "/knight.png";
	}
}
