package pieces;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import board.*;
import pieces.*;
import management.*;

public class BishopTest {

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
		Bishop bishop = new Bishop(location, player);

		assertEquals(7, bishop.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations in the center. Test available locations will be
	 * blocked by companion
	 */
	@Test
	public void testGetAvaiableLocation2() {
		Location location = new Location(4, 4);
		Player player = new Player(Piece.WHITE);
		Bishop bishop = new Bishop(location, player);

		assertEquals(13, bishop.updateAvaiableLocations().size());

		location = new Location(6, 6);
		new Bishop(location, player);

		assertEquals(11, bishop.updateAvaiableLocations().size());

		location = new Location(1, 1);
		new Bishop(location, player);

		assertEquals(9, bishop.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations will be blocked by enemy
	 */
	@Test
	public void testGetAvaiableLocation3() {
		Location location = new Location(4, 4);
		Player playerW = new Player(Piece.WHITE);
		Bishop bishop = new Bishop(location, playerW);

		assertEquals(13, bishop.updateAvaiableLocations().size());

		location = new Location(6, 6);
		Player playerB = new Player(Piece.BLACK);
		new Bishop(location, playerB);

		assertEquals(12, bishop.updateAvaiableLocations().size());

		location = new Location(1, 1);
		new Bishop(location, playerB);

		assertEquals(11, bishop.updateAvaiableLocations().size());
	}

}
