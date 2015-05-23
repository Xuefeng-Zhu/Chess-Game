import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import board.Chessboard;
import board.Location;
import management.*;

public class GameManagerTest {

	@Before
	public void setUp() {
		GameManager.initInstance();
	}

	@After
	public void tearDown() {
		GameManager.initInstance();
	}

	/**
	 * Test if it is able to get opponent
	 */
	@Test
	public void testGetOpponent() {
		GameManager gameManager = GameManager.getInstance();

		Player currentPlayer = gameManager.getCurrentPlayer();
		Player opponentPlayer = gameManager.getOpponent(currentPlayer);

		assertEquals(currentPlayer, gameManager.getOpponent(opponentPlayer));
	}

	/**
	 * Test if it is able to switch player
	 */
	@Test
	public void testSwitchPlayer() {
		GameManager gameManager = GameManager.getInstance();

		Player currentPlayer = gameManager.getCurrentPlayer();
		Player opponentPlayer = gameManager.getOpponent(currentPlayer);

		assertEquals(opponentPlayer, gameManager.switchPlayer());
	}

	/**
	 * Test if update game statistics work
	 */
	@Test
	public void testUpdateGameStat() {
		GameManager gameManager = GameManager.getInstance();
		gameManager.initPieces();
		assertTrue(gameManager.updateGameStat());
	}

	/**
	 * Test if restart function works
	 */
	@Test
	public void testRestart() {
		GameManager gameManager = GameManager.getInstance();
		Player currentPlayer = gameManager.getCurrentPlayer();
		gameManager.switchPlayer();

		assertNotEquals(currentPlayer, gameManager.getCurrentPlayer());

		gameManager.restart();
		assertEquals(currentPlayer, gameManager.getCurrentPlayer());

	}

	/**
	 * Test initPieces if works
	 */
	@Test
	public void testInitPieces() {
		GameManager gameManager = GameManager.getInstance();
		gameManager.initPieces();

		Player currentPlayer = gameManager.getCurrentPlayer();
		assertEquals(15, currentPlayer.getPieces().size());
	}

	/**
	 * Test if initCustomPieces works
	 */
	@Test
	public void testInitCustomPieces() {
		GameManager gameManager = GameManager.getInstance();
		gameManager.setMode(GameManager.CUSTOM);
		gameManager.initPieces();

		Player currentPlayer = gameManager.getCurrentPlayer();
		assertEquals(19, currentPlayer.getPieces().size());
	}

	/**
	 * Test undoPreMovement
	 */
	@Test
	public void testUndoPreMovement() {
		GameManager gameManager = GameManager.getInstance();
		Chessboard chessBoard = Chessboard.getInstance();

		Player currentPlayer = gameManager.getCurrentPlayer();
		Player opponentPlayer = gameManager.getOpponent(currentPlayer);

		Location location0 = chessBoard.getLocatoin(4, 3);
		Piece queen = new Queen(location0, currentPlayer);
		queen.updateAvaiableLocations();
		Location location1 = chessBoard.getLocatoin(5, 3);
		queen.moveTo(location1);
		gameManager.undoPreMovement();

		assertEquals(location0, queen.getLocation());

		Piece rook = new Rook(location1, opponentPlayer);
		queen.moveTo(location1);
		gameManager.undoPreMovement();

		assertEquals(location1, rook.getLocation());
	}
}
