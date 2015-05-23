package pieces;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;
import org.junit.Before;

import board.*;
import pieces.*;
import management.*;

public class PieceTest {

	@Before
	public void setUp() {
		Chessboard.initInstance(8, 8);
	}

	/**
	 * Test to move to available locations
	 */
	@Test
	public void testMoveTo1() {
		Location location = new Location(0, 0);
		Player player = new Player(Piece.WHITE);
		Rook rook = new Rook(location, player);

		HashSet<Location> availableLocations = rook.updateAvaiableLocations();

		for (Location availableLocation : availableLocations) {
			assertTrue(rook.moveTo(availableLocation));
		}
	}

	/**
	 * Test to move to available locations, which can capture the enemy
	 */
	@Test
	public void testMoveTo2() {
		Location location = new Location(4, 4);
		Player playerW = new Player(Piece.WHITE);
		Bishop bishop = new Bishop(location, playerW);

		location = new Location(3, 3);
		Player playerB = new Player(Piece.BLACK);
		new Rook(location, playerB);

		HashSet<Location> availableLocations = bishop.updateAvaiableLocations();

		for (Location availableLocation : availableLocations) {
			if (availableLocation.equals(location)) {
				assertTrue(bishop.moveTo(availableLocation));
			}
		}
	}
	
	/**
	 * Test to undo previous movement 
	 */
	@Test
	public void testUndoMove() {
		Location location = new Location(0, 0);
		Player player = new Player(Piece.WHITE);
		Rook rook = new Rook(location, player);

		HashSet<Location> availableLocations = rook.updateAvaiableLocations();

		for (Location availableLocation : availableLocations) {
			assertTrue(rook.moveTo(availableLocation));
			rook.undoMove(location);
			assertEquals(location, rook.getLocation());
		}
	}
}
