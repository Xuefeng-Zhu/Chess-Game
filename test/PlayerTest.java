import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import board.*;
import pieces.*;
import management.*;

public class PlayerTest {

	@Before
	public void setUp() {
		GameManager gameManager = GameManager.initInstance();
		Chessboard chessBoard = Chessboard.getInstance();

		Player currentPlayer = gameManager.getCurrentPlayer();
		Location location = chessBoard.getLocatoin(1, 1);
		new King(location, currentPlayer);

		Player opponentPlayer = gameManager.getOpponent(currentPlayer);
		location = chessBoard.getLocatoin(5, 6);
		new King(location, opponentPlayer);

	}

	/**
	 * Test the restart function in player
	 */
	@Test
	public void testRestart() {
		GameManager gameManager = GameManager.initInstance();
		gameManager.initPieces();
		
		Player currentPlayer = gameManager.getCurrentPlayer();
		assertTrue(currentPlayer.getPieces().size() != 0);
		
		currentPlayer.isChecked = true;
		currentPlayer.restart();
		assertTrue(currentPlayer.getPieces().size() == 0);
		assertFalse(currentPlayer.isChecked);
	}
	
	@Test
	public void testAddReomvePlayer() {
		GameManager gameManager = GameManager.getInstance();

		Player currentPlayer = gameManager.getCurrentPlayer();
		Location location = new Location(2, 2);
		new Rook(location, currentPlayer);

		assertTrue(currentPlayer.getPieces().size() == 1);

		location = new Location(3, 2);
		Piece piece = new Pawn(location, currentPlayer);

		assertTrue(currentPlayer.getPieces().size() == 2);

		currentPlayer.removePiece(piece);
		assertTrue(currentPlayer.getPieces().size() == 1);
	}

	@Test
	public void testUpdateStat() {
		GameManager gameManager = GameManager.getInstance();
		Chessboard chessBoard = Chessboard.getInstance();

		Player currentPlayer = gameManager.getCurrentPlayer();
		Player opponentPlayer = gameManager.getOpponent(currentPlayer);

		Location location = chessBoard.getLocatoin(4, 3);
		Piece queen = new Queen(location, opponentPlayer);

		opponentPlayer.updateStat();
		assertFalse(currentPlayer.isChecked);

		location = chessBoard.getLocatoin(3, 3);
		queen.moveTo(location);
		opponentPlayer.updateStat();
		assertTrue(currentPlayer.isChecked);
	}

	/**
	 * The king is checked, but has other location to move
	 */
	@Test
	public void testIsCheckmate1() {
		GameManager gameManager = GameManager.getInstance();
		Chessboard chessBoard = Chessboard.getInstance();

		Player currentPlayer = gameManager.getCurrentPlayer();
		Player opponentPlayer = gameManager.getOpponent(currentPlayer);

		Location location = chessBoard.getLocatoin(4, 3);
		new Queen(location, opponentPlayer);

		opponentPlayer.updateStat();
		assertFalse(currentPlayer.isChecked);

		location = chessBoard.getLocatoin(3, 3);
		new Queen(location, opponentPlayer);

		opponentPlayer.updateStat();
		assertTrue(currentPlayer.isChecked);
		currentPlayer.updateStat();
		assertFalse(currentPlayer.isCheckmate());
	}

	/**
	 * King is checked, has no location to move, but has piece to capture the
	 * checkpiece
	 */
	@Test
	public void testIsCheckmate2() {
		GameManager gameManager = GameManager.getInstance();
		Chessboard chessBoard = Chessboard.getInstance();

		Player currentPlayer = gameManager.getCurrentPlayer();
		Player opponentPlayer = gameManager.getOpponent(currentPlayer);

		Location location = chessBoard.getLocatoin(3, 3);
		new Queen(location, opponentPlayer);

		opponentPlayer.updateStat();
		assertTrue(currentPlayer.isChecked);
		currentPlayer.updateStat();
		assertFalse(currentPlayer.isCheckmate());

		location = chessBoard.getLocatoin(3, 0);
		new Rook(location, opponentPlayer);
		location = chessBoard.getLocatoin(3, 2);
		new Rook(location, opponentPlayer);
		location = chessBoard.getLocatoin(2, 3);
		new Rook(location, opponentPlayer);
		location = chessBoard.getLocatoin(0, 3);
		new Rook(location, opponentPlayer);

		opponentPlayer.updateStat();
		assertTrue(currentPlayer.isChecked);
		currentPlayer.updateStat();
		assertTrue(currentPlayer.isCheckmate());

		location = chessBoard.getLocatoin(3, 5);
		new Rook(location, currentPlayer);

		opponentPlayer.updateStat();
		assertTrue(currentPlayer.isChecked);
		currentPlayer.updateStat();
		assertFalse(currentPlayer.isCheckmate());
	}

	/**
	 * King is checked, has no location to move, but has piece to move to path
	 * between king and checkpiece to prevent check
	 */
	@Test
	public void testIsCheckmate3() {
		GameManager gameManager = GameManager.getInstance();
		Chessboard chessBoard = Chessboard.getInstance();

		Player currentPlayer = gameManager.getCurrentPlayer();
		Player opponentPlayer = gameManager.getOpponent(currentPlayer);

		Location location = chessBoard.getLocatoin(6, 6);
		new Queen(location, opponentPlayer);

		opponentPlayer.updateStat();
		assertTrue(currentPlayer.isChecked);
		currentPlayer.updateStat();
		assertFalse(currentPlayer.isCheckmate());

		location = chessBoard.getLocatoin(3, 0);
		new Rook(location, opponentPlayer);
		location = chessBoard.getLocatoin(3, 2);
		new Rook(location, opponentPlayer);
		location = chessBoard.getLocatoin(2, 3);
		new Rook(location, opponentPlayer);
		location = chessBoard.getLocatoin(0, 3);
		new Rook(location, opponentPlayer);

		opponentPlayer.updateStat();
		assertTrue(currentPlayer.isChecked);
		currentPlayer.updateStat();
		assertTrue(currentPlayer.isCheckmate());

		location = chessBoard.getLocatoin(2, 4);
		new Bishop(location, currentPlayer);

		opponentPlayer.updateStat();
		assertTrue(currentPlayer.isChecked);
		currentPlayer.updateStat();
		assertFalse(currentPlayer.isCheckmate());
	}

	/**
	 * Check isStalemate is working user three considtion 1: king cannot move
	 * and no extra piece 2: king cannot move but one piece can move 3: king
	 * cannot move and one piece also cannot move
	 */
	@Test
	public void testIsStalemate() {
		GameManager gameManager = GameManager.getInstance();
		Chessboard chessBoard = Chessboard.getInstance();

		Player currentPlayer = gameManager.getCurrentPlayer();
		Player opponentPlayer = gameManager.getOpponent(currentPlayer);

		Location location = chessBoard.getLocatoin(3, 0);
		new Rook(location, opponentPlayer);
		location = chessBoard.getLocatoin(3, 2);
		new Rook(location, opponentPlayer);
		location = chessBoard.getLocatoin(2, 3);
		new Rook(location, opponentPlayer);
		
		opponentPlayer.updateStat();
		currentPlayer.updateStat();
		assertFalse(currentPlayer.isStalemate());
		
		location = chessBoard.getLocatoin(0, 3);
		new Rook(location, opponentPlayer);

		opponentPlayer.updateStat();
		assertFalse(currentPlayer.isChecked);
		currentPlayer.updateStat();
		assertTrue(currentPlayer.isStalemate());

		location = chessBoard.getLocatoin(7, 6);
		new Pawn(location, currentPlayer);

		opponentPlayer.updateStat();
		assertFalse(currentPlayer.isChecked);
		currentPlayer.updateStat();
		assertFalse(currentPlayer.isStalemate());

		location = chessBoard.getLocatoin(6, 6);
		new Pawn(location, opponentPlayer);

		opponentPlayer.updateStat();
		assertFalse(currentPlayer.isChecked);
		currentPlayer.updateStat();
		assertTrue(currentPlayer.isStalemate());
		
		location = chessBoard.getLocatoin(5, 1);
		new Rook(location, opponentPlayer);
		
		opponentPlayer.updateStat();
		assertTrue(currentPlayer.isChecked);
		currentPlayer.updateStat();
		assertFalse(currentPlayer.isStalemate());

	}
	

}
