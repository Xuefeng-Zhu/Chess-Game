package management;

import board.Chessboard;
import board.Location;
import pieces.*;

/**
 * GameManger initiate and manage all information related the game
 * 
 * @author Xuefeng Zhu
 *
 */
public class GameManager {
	public static final String CLASSIC = "Classic";
	public static final String CUSTOM = "Custom";

	public static final String CHECKMATE = "checkmate";
	public static final String STALEMATE = "stalemate";
	public static final String INPROGRESS = "in progress";

	private static GameManager gameManger = null;

	private Player playerW;
	private Player playerB;
	private Player currentPlayer;
	private Movement preMovement = null; // store previous player's movement
	private String mode;
	private String status;

	/**
	 * Construct a new GameManager and initialize players and chessboard
	 */
	protected GameManager() {
		playerW = new Player(Piece.WHITE);
		playerB = new Player(Piece.BLACK);
		currentPlayer = playerW;
		mode = CLASSIC;
		status = INPROGRESS;

		Chessboard.initInstance(8, 8);
	}

	/**
	 * Initialize the GameManager
	 * 
	 * @return
	 */
	public static GameManager initInstance() {
		gameManger = new GameManager();
		return gameManger;
	}

	/**
	 * Get the static instance of Gamemanager
	 * 
	 * @return
	 */
	public static GameManager getInstance() {
		if (gameManger == null) {
			gameManger = new GameManager();
		}
		return gameManger;
	}

	/**
	 * Helper function to be called when game restarts
	 */
	public void restart() {
		playerW.restart();
		playerB.restart();
		currentPlayer = playerW;
		status = INPROGRESS;

		Chessboard.initInstance(8, 8);
		initPieces();
	}

	/**
	 * Initilize all pieces and place them on the board
	 */
	public void initPieces() {
		initPiecesHelper(playerW, 7, 6);
		initPiecesHelper(playerB, 0, 1);

		if (mode == CUSTOM) {
			initCustomPiecesHelper(playerW, 4);
			initCustomPiecesHelper(playerB, 3);
		}

		updateGameStat();
	}

	/**
	 * Helper function for initPeces
	 * 
	 * @param player
	 * @param rearRow
	 *            the index of rear row of the player
	 * @param frontRow
	 *            the index of front row of the player
	 */
	private void initPiecesHelper(Player player, int rearRow, int frontRow) {
		Chessboard chessBoard = Chessboard.getInstance();

		Location location = chessBoard.getLocatoin(rearRow, 0);
		new Rook(location, player);
		location = chessBoard.getLocatoin(rearRow, 7);
		new Rook(location, player);

		location = chessBoard.getLocatoin(rearRow, 1);
		new Knight(location, player);
		location = chessBoard.getLocatoin(rearRow, 6);
		new Knight(location, player);

		location = chessBoard.getLocatoin(rearRow, 2);
		new Bishop(location, player);
		location = chessBoard.getLocatoin(rearRow, 5);
		new Bishop(location, player);

		location = chessBoard.getLocatoin(rearRow, 3);
		new Queen(location, player);

		location = chessBoard.getLocatoin(rearRow, 4);
		new King(location, player);

		for (int col = 0; col < chessBoard.getWidth(); col++) {
			location = chessBoard.getLocatoin(frontRow, col);
			new Pawn(location, player);
		}

	}

	/**
	 * Helper function for initializing custom pieces
	 * @param player
	 * @param row
	 */
	private void initCustomPiecesHelper(Player player, int row) {
		Chessboard chessBoard = Chessboard.getInstance();

		Location location = chessBoard.getLocatoin(row, 0);
		new Ferz(location, player);

		location = chessBoard.getLocatoin(row, 7);
		new Ferz(location, player);

		location = chessBoard.getLocatoin(row, 3);
		new Nightrider(location, player);

		location = chessBoard.getLocatoin(row, 4);
		new Nightrider(location, player);
	}

	/**
	 * @return preMovement
	 */
	public Movement getPreMovement() {
		return preMovement;
	}

	/**
	 * Set the preMovement to movement
	 * 
	 * @param movement
	 */
	public void setPreMovement(Movement movement) {
		preMovement = movement;
	}

	/**
	 * @return mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Set the mode to the specific mode
	 * 
	 * @param mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Switch to another player
	 * 
	 * @return next player
	 */
	public Player switchPlayer() {
		if (currentPlayer == playerW) {
			currentPlayer = playerB;
		} else {
			currentPlayer = playerW;
		}
		return currentPlayer;
	}

	/**
	 * Get the opponent of the player
	 * 
	 * @param player
	 * @return opponent Player
	 */
	public Player getOpponent(Player player) {
		if (player == playerW) {
			return playerB;
		} else {
			return playerW;
		}
	}

	/**
	 * Check if current player's movement is valid (not causing king check)
	 * Update opponent statistics to see if opponent is checkmate
	 * 
	 * @return true if everything goes well, false if the previous movement if
	 *         not valid
	 */
	public boolean updateGameStat() {
		Player opponent = getOpponent(currentPlayer);
		opponent.updateStat();
		if (currentPlayer.isChecked) {
			return false;
		}

		currentPlayer.updateStat();
		if (opponent.isCheckmate()) {
			status = CHECKMATE;
		}
		if (opponent.isStalemate()) {
			status = STALEMATE;
		}

		return true;
	}

	/**
	 * Undo the previous movement
	 */
	public void undoPreMovement() {
		preMovement.piece.undoMove(preMovement.preLocation);

		// check if the previous movement captured enemy piece
		if (preMovement.capturedPiece != null) {
			Location location = preMovement.capturedPiece.getLocation();
			Chessboard chessboard = Chessboard.getInstance();
			chessboard.addPiece(preMovement.capturedPiece, location);
			Player player = preMovement.capturedPiece.getPlayer();
			player.addPiece(preMovement.capturedPiece);
		}

		preMovement = null;
	}
}
