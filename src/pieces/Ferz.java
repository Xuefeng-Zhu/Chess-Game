package pieces;

import management.Player;
import board.Location;

/**
 * Class defines ferz's movement
 * 
 * @author Xuefeng Zhu
 *
 */
public class Ferz extends Piece {

	/**
	 * Construct Ferz and define its directions
	 * 
	 * @param location
	 * @param player
	 */
	public Ferz(Location location, Player player) {
		super(location, player);

		this.directions = new int[][] { { 1, 1, 1 }, { 1, -1, 1 },
				{ -1, 1, 1 }, { -1, -1, 1 } };
		this.iconDir = "res/" + this.color + "/ferz.png";
	}
}
