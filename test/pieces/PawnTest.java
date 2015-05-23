package pieces;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import board.*;
import pieces.*;
import management.*;

public class PawnTest {

	@Before
	public void setUp() {
		Chessboard.initInstance(8, 8);
	}

	/**
	 * Test available locations at the initial position
	 */
	@Test
	public void testGetAvaiableLocation1() {
		Location location = new Location(1, 0);
		Player player = new Player(Piece.BLACK);
		Pawn pawn = new Pawn(location, player);

		assertEquals(2, pawn.updateAvaiableLocations().size());
	}

	/**
	 * Test available locations at the center Test available location will be
	 * blocked by enemy
	 */
	@Test
	public void testGetAvaiableLocation2() {
		Location location = new Location(4, 4);
		Player player = new Player(Piece.BLACK);
		Pawn pawn = new Pawn(location, player);

		assertEquals(1, pawn.updateAvaiableLocations().size());

		location = new Location(5, 4);
		new Pawn(location, player);

		assertEquals(0, pawn.updateAvaiableLocations().size());
	}

	/**
	 * Test the available locations, which will capture the enemy
	 */
	@Test
	public void testGetAvaiableLocation3() {
		Location location = new Location(4, 4);
		Player playerW = new Player(Piece.BLACK);
		Pawn pawn = new Pawn(location, playerW);

		assertEquals(1, pawn.updateAvaiableLocations().size());

		location = new Location(5, 5);
		Player playerB = new Player(Piece.WHITE);
		new Pawn(location, playerB);

		assertEquals(2, pawn.updateAvaiableLocations().size());
	}

}
