package pieces;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;
import org.junit.Before;

import board.*;
import pieces.*;
import management.*;

public class RookTest {

	@Before
	public void setUp() {
		Chessboard.initInstance(8, 8);
	}

	/**
	 * Test available location at the corner
	 */
	@Test
	public void testGetAvaiableLocation1() {
		Location location = new Location(0, 0);
		Player player = new Player(Piece.WHITE);
		Rook rook = new Rook(location, player);

		assertEquals(14, rook.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations in the center. Test available locations will be
	 * blocked by companion
	 */
	@Test
	public void testGetAvaiableLocation2() {
		Location location = new Location(4, 4);
		Player player = new Player(Piece.WHITE);
		Rook rook = new Rook(location, player);

		assertEquals(14, rook.updateAvaiableLocations().size());

		location = new Location(4, 5);
		new Rook(location, player);

		assertEquals(11, rook.updateAvaiableLocations().size());

		location = new Location(1, 4);
		new Rook(location, player);

		assertEquals(9, rook.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations will be blocked by enemy
	 */
	@Test
	public void testGetAvaiableLocation3() {
		Location location = new Location(4, 4);
		Player playerW = new Player(Piece.WHITE);
		Rook rook = new Rook(location, playerW);

		assertEquals(14, rook.updateAvaiableLocations().size());

		location = new Location(4, 5);
		Player playerB = new Player(Piece.BLACK);
		new Rook(location, playerB);

		assertEquals(12, rook.updateAvaiableLocations().size());

		location = new Location(1, 4);
		new Rook(location, playerB);

		assertEquals(11, rook.updateAvaiableLocations().size());
	}

}
