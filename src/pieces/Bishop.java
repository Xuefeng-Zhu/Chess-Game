package pieces;

import management.Player;
import board.Location;

/**
 * Class defines bishop's movement
 * 
 * @author Xuefeng Zhu
 *
 */
public class Bishop extends Piece {

	/**
	 * Construct Bishop and define its directions
	 * 
	 * @param location
	 * @param player
	 */
	public Bishop(Location location, Player player) {
		super(location, player);

		this.directions = new int[][] { { 1, 1, 0 }, { 1, -1, 0 },
				{ -1, 1, 0 }, { -1, -1, 0 } };
		this.iconDir = "res/" + this.color + "/bishop.png";
	}
}
