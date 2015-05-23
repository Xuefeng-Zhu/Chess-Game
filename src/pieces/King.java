package pieces;

import java.util.HashSet;

import management.GameManager;
import management.Player;
import board.Location;

/**
 * Class defines king's movement
 * 
 * @author Xuefeng Zhu
 *
 */
public class King extends Piece {

	/**
	 * Construct King and define its directions
	 * 
	 * @param location
	 * @param player
	 */
	public King(Location location, Player player) {
		super(location, player);

		this.directions = new int[][] { { 1, 0, 1 }, { -1, 0, 1 }, { 0, 1, 1 },
				{ 0, -1, 1 }, { 1, 1, 1 }, { 1, -1, 1 }, { -1, 1, 1 },
				{ -1, -1, 1 } };
		this.iconDir = "res/" + this.color + "/king.png";
	}

	@Override
	public HashSet<Location> updateAvaiableLocations() {
		HashSet<Location> result = super.updateAvaiableLocations();

		GameManager gameManger = GameManager.getInstance();
		Player opponent = gameManger.getOpponent(player);

		// remove the locations, whick will cause the king check
		for (Piece opponentPiece : opponent.getPieces()) {
			result.removeAll(opponentPiece.updateAvaiableLocations());
		}

		return result;
	}

}
