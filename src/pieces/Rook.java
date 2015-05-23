package pieces;

import management.Player;
import board.Location;

/**
 * Class defines rock's movement
 * 
 * @author Xuefeng Zhu
 *
 */
public class Rook extends Piece {

	/**
	 * Construct Rook and define its directions
	 * 
	 * @param location
	 * @param player
	 */
	public Rook(Location location, Player player) {
		super(location, player);

		this.directions = new int[][] { { 1, 0, 0 }, { -1, 0, 0 }, { 0, 1, 0 },
				{ 0, -1, 0 } };
		this.iconDir = "res/" + this.color + "/rook.png";
	}
}
