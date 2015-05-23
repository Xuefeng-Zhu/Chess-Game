package pieces;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import board.*;
import pieces.*;
import management.*;

public class NightriderTest {

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
		Nightrider nightrider = new Nightrider(location, player);

		assertEquals(6, nightrider.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations in the center. Test available locations will be
	 * blocked by companion
	 */
	@Test
	public void testGetAvaiableLocation2() {
		Location location = new Location(4, 4);
		Player player = new Player(Piece.WHITE);
		Nightrider nightrider = new Nightrider(location, player);

		assertEquals(12, nightrider.updateAvaiableLocations().size());

		location = new Location(6, 5);
		new Nightrider(location, player);

		assertEquals(11, nightrider.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations will be blocked by enemy
	 */
	@Test
	public void testGetAvaiableLocation3() {
		Location location = new Location(4, 4);
		Player playerW = new Player(Piece.WHITE);
		Nightrider nightrider = new Nightrider(location, playerW);

		assertEquals(12, nightrider.updateAvaiableLocations().size());

		location = new Location(6, 5);
		Player playerB = new Player(Piece.BLACK);
		new Nightrider(location, playerB);

		assertEquals(12, nightrider.updateAvaiableLocations().size());
	}

}
