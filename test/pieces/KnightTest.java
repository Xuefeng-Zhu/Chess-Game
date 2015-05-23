package pieces;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import board.*;
import pieces.*;
import management.*;

public class KnightTest {

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
		Knight knight = new Knight(location, player);

		assertEquals(2, knight.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations in the center. Test available locations will be
	 * blocked by companion
	 */
	@Test
	public void testGetAvaiableLocation2() {
		Location location = new Location(4, 4);
		Player player = new Player(Piece.WHITE);
		Knight knight = new Knight(location, player);

		assertEquals(8, knight.updateAvaiableLocations().size());

		location = new Location(6, 5);
		new Knight(location, player);

		assertEquals(7, knight.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations will be blocked by enemy
	 */
	@Test
	public void testGetAvaiableLocation3() {
		Location location = new Location(4, 4);
		Player playerW = new Player(Piece.WHITE);
		Knight knight = new Knight(location, playerW);

		assertEquals(8, knight.updateAvaiableLocations().size());

		location = new Location(6, 5);
		Player playerB = new Player(Piece.BLACK);
		new Knight(location, playerB);

		assertEquals(8, knight.updateAvaiableLocations().size());
	}

}
