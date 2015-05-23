package pieces;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import board.*;
import pieces.*;
import management.*;

public class FerzTest {

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
		Ferz ferz = new Ferz(location, player);

		assertEquals(1, ferz.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations in the center. Test available locations will be
	 * blocked by companion
	 */
	@Test
	public void testGetAvaiableLocation2() {
		Location location = new Location(4, 4);
		Player player = new Player(Piece.WHITE);
		Ferz ferz = new Ferz(location, player);

		assertEquals(4, ferz.updateAvaiableLocations().size());

		location = new Location(5, 5);
		new Ferz(location, player);

		assertEquals(3, ferz.updateAvaiableLocations().size());

		location = new Location(3, 3);
		new Ferz(location, player);

		assertEquals(2, ferz.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations will be blocked by enemy
	 */
	@Test
	public void testGetAvaiableLocation3() {
		Location location = new Location(4, 4);
		Player playerW = new Player(Piece.WHITE);
		Ferz ferz = new Ferz(location, playerW);

		assertEquals(4, ferz.updateAvaiableLocations().size());

		location = new Location(5, 5);
		Player playerB = new Player(Piece.BLACK);
		new Ferz(location, playerB);

		assertEquals(4, ferz.updateAvaiableLocations().size());

		location = new Location(3, 3);
		new Ferz(location, playerB);

		assertEquals(4, ferz.updateAvaiableLocations().size());
	}

}
