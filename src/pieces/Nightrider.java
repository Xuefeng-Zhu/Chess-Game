package pieces;

import management.Player;
import board.Location;

/**
 * Class defines nightrider's movement
 * 
 * @author Xuefeng Zhu
 *
 */
public class Nightrider extends Piece {

	/**
	 * Construct Nightrider and define its directions
	 * 
	 * @param location
	 * @param player
	 */
	public Nightrider(Location location, Player player) {
		super(location, player);

		this.directions = new int[][] { { 2, 1, 0 }, { 2, -1, 0 },
				{ -2, 1, 0 }, { -2, -1, 0 }, { 1, 2, 0 }, { 1, -2, 0 },
				{ -1, 2, 0 }, { -1, -2, 0 } };
		this.iconDir = "res/" + this.color + "/nightrider.png";
	}
}
