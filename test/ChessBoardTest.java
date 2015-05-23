import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import board.*;
import pieces.*;
import management.*;

public class ChessBoardTest {

	@Before
	public void setUp() {
		Chessboard.initInstance(8, 8);
		Location location = new Location(2, 2);
		Player player = new Player(Piece.WHITE);
		new Pawn(location, player);
	}

	/**
	 * Test if getInstance method works
	 */
	@Test
	public void testGetInstance() {
		assertNotEquals(null, Chessboard.getInstance());
	}

	/**
	 * Test with one valid location and one non-valid location
	 */
	@Test
	public void testValidateLocation() {
		Chessboard chessboard = Chessboard.getInstance();

		Location location = new Location(1, 1);
		assertTrue(chessboard.validateLocation(location));

		location = new Location(10, 10);
		assertFalse(chessboard.validateLocation(location));
	}

	/**
	 * Test with a non-occupied location
	 */
	@Test
	public void testGetPiece1() {
		Chessboard chessboard = Chessboard.getInstance();

		Location location = new Location(1, 1);
		assertNull(chessboard.getPiece(location));
	}

	/**
	 * Test with a non-valid location
	 */
	@Test
	public void testGetPiece2() {
		Chessboard chessboard = Chessboard.getInstance();

		Location location = new Location(10, 10);
		try {
			chessboard.getPiece(location);
		} catch (ArrayIndexOutOfBoundsException e) {
			assertEquals("10", e.getMessage());
		}
	}

	/**
	 * Add valid piece to the board
	 */
	@Test
	public void testAddPiece1() {
		Chessboard chessboard = Chessboard.getInstance();

		Location location = new Location(1, 1);
		Player player = new Player(Piece.WHITE);
		Pawn pawn = new Pawn(location, player);
		assertEquals(pawn, chessboard.getPiece(location));

		assertFalse(chessboard.addPiece(pawn, location));
	}

	/**
	 * Add a non-valid piece to the board
	 */
	@Test
	public void testAddPiece2() {
		Location location = new Location(10, 10);

		try {
			Player player = new Player(Piece.WHITE);
			new Pawn(location, player);
		} catch (ArrayIndexOutOfBoundsException e) {
			assertEquals("10", e.getMessage());
		}
	}

	/**
	 * Remove a existing piece from the board
	 */
	@Test
	public void testRemovePiece1() {
		Chessboard chessboard = Chessboard.getInstance();

		Location location = new Location(2, 2);
		assertTrue(chessboard.removePiece(location));

		assertEquals(null, chessboard.getPiece(location));
	}

	/**
	 * Remove a non-existing piece and a non-valid location
	 */
	@Test
	public void testRemovePiece2() {
		Chessboard chessboard = Chessboard.getInstance();

		Location location = new Location(1, 1);
		assertFalse(chessboard.removePiece(location));

		location = new Location(10, 10);
		try {
			chessboard.removePiece(location);
		} catch (ArrayIndexOutOfBoundsException e) {
			assertEquals("10", e.getMessage());
		}
	}

	/**
	 * Test to get a diagonal path
	 */
	@Test
	public void testGetPath1() {
		Chessboard chessboard = Chessboard.getInstance();

		Location start = new Location(1, 1);
		Location end = new Location(5, 5);

		assertEquals(3, chessboard.getPath(start, end).size());

	}

	/**
	 * Test to get a horizontal path
	 */
	@Test
	public void testGetPath2() {
		Chessboard chessboard = Chessboard.getInstance();

		Location start = new Location(1, 1);
		Location end = new Location(1, 6);

		assertEquals(4, chessboard.getPath(start, end).size());

	}

	/**
	 * Test with a same location
	 */
	@Test
	public void testGetPath3() {
		Chessboard chessboard = Chessboard.getInstance();

		Location start = new Location(1, 1);
		Location end = new Location(1, 1);

		assertEquals(0, chessboard.getPath(start, end).size());

	}

	/**
	 * Test with two location do not have a path between them
	 */
	@Test
	public void testGetPath4() {
		Chessboard chessboard = Chessboard.getInstance();

		Location start = new Location(1, 1);
		Location end = new Location(2, 2);

		assertEquals(0, chessboard.getPath(start, end).size());

	}

}
