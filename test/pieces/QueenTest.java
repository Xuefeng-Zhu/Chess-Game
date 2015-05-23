package pieces;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import board.*;
import pieces.*;
import management.*;

public class QueenTest {

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
		Queen queen = new Queen(location, player);

		assertEquals(21, queen.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations in the center. Test available locations will be
	 * blocked by companion
	 */
	@Test
	public void testGetAvaiableLocation2() {
		Location location = new Location(4, 4);
		Player player = new Player(Piece.WHITE);
		Queen queen = new Queen(location, player);

		assertEquals(27, queen.updateAvaiableLocations().size());

		location = new Location(6, 6);
		new Queen(location, player);

		assertEquals(25, queen.updateAvaiableLocations().size());

		location = new Location(4, 5);
		new Queen(location, player);

		assertEquals(22, queen.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations will be blocked by enemy
	 */
	@Test
	public void testGetAvaiableLocation3() {
		Location location = new Location(4, 4);
		Player playerW = new Player(Piece.WHITE);
		Queen queen = new Queen(location, playerW);

		assertEquals(27, queen.updateAvaiableLocations().size());

		location = new Location(6, 6);
		Player playerB = new Player(Piece.BLACK);
		new Queen(location, playerB);

		assertEquals(26, queen.updateAvaiableLocations().size());

		location = new Location(4, 5);
		new Queen(location, playerB);

		assertEquals(24, queen.updateAvaiableLocations().size());
	}

}
