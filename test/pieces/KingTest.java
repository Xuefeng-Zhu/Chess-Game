package pieces;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import board.*;
import pieces.*;
import management.*;

public class KingTest {

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
		King king = new King(location, player);

		assertEquals(3, king.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations in the center. Test available locations will be
	 * blocked by companion
	 */
	@Test
	public void testGetAvaiableLocation2() {
		Location location = new Location(4, 4);
		Player player = new Player(Piece.WHITE);
		King king = new King(location, player);

		assertEquals(8, king.updateAvaiableLocations().size());

		location = new Location(5, 5);
		new King(location, player);

		assertEquals(7, king.updateAvaiableLocations().size());

		location = new Location(4, 5);
		new King(location, player);

		assertEquals(6, king.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations will be blocked by enemy
	 */
	@Test
	public void testGetAvaiableLocation3() {
		Location location = new Location(4, 4);
		Player playerW = new Player(Piece.WHITE);
		King king = new King(location, playerW);

		assertEquals(8, king.updateAvaiableLocations().size());

		location = new Location(5, 5);
		Player playerB = new Player(Piece.BLACK);
		new King(location, playerB);

		assertEquals(8, king.updateAvaiableLocations().size());

		location = new Location(4, 5);
		new King(location, playerB);

		assertEquals(8, king.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations, which do not cause king check
	 */
	@Test
	public void testGetAvaiableLocation4() {
		GameManager gamegameManger = GameManager.getInstance();

		Location location = new Location(4, 4);
		Player playerW = gamegameManger.getCurrentPlayer();
		King king = new King(location, playerW);

		assertEquals(8, king.updateAvaiableLocations().size());

		location = new Location(5, 6);
		Player playerB = gamegameManger.getOpponent(playerW);
		new Rook(location, playerB);

		assertEquals(5, king.updateAvaiableLocations().size());

		location = new Location(0, 5);
		new Rook(location, playerB);

		assertEquals(3, king.updateAvaiableLocations().size());
	}

}
