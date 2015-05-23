package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import management.GameManager;
import management.Movement;
import pieces.Piece;
import board.Chessboard;
import board.Location;

/**
 * The class for create chessboard Panel
 * 
 * @author Xuefeng Zhu
 *
 */
public class ChessBoardPanel extends JPanel {
	private static final int SQUARESIZE = 80;

	private JButton chessSquares[][];
	private JButton selectedPiece = null;

	/**
	 * Constructor for ChessBoardPanel
	 * 
	 * @param height
	 * @param width
	 */
	public ChessBoardPanel(int height, int width) {
		super(new GridLayout(height, width));
		this.setPreferredSize(new Dimension(width * SQUARESIZE, height
				* SQUARESIZE));
		chessSquares = new JButton[height][width];

		// add chess square into the panel
		Chessboard chessboard = Chessboard.getInstance();
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				JButton chessSquare = new JButton();
				chessSquare.setOpaque(true);
				chessSquare.setBorderPainted(false);
				chessSquares[row][col] = chessSquare;

				// check if there is piece at the location
				Piece piece = chessboard.getPiece(new Location(row, col));
				if (piece != null) {
					chessSquare.setIcon(new ImageIcon(piece.getIconDir()));
				}

				if ((row + col) % 2 == 0) {
					chessSquare.setBackground(new Color(152, 104, 14));
					chessSquare.putClientProperty("color", new Color(152, 104,
							14));
				} else {
					chessSquare.setBackground(new Color(255, 255, 153));
					chessSquare.putClientProperty("color", new Color(255, 255,
							153));
				}

				chessSquare.putClientProperty("location",
						chessboard.getLocatoin(row, col));
				chessSquare.addActionListener(new SelectSquareAction());
				this.add(chessSquare);
			}
		}
	}

	/**
	 * Undo previous movement
	 */
	protected void undoMove() {
		GameManager gameManager = GameManager.getInstance();
		Movement movement = gameManager.getPreMovement();

		// check if there is a captured piece
		Location location = movement.piece.getLocation();
		int row = location.getRow();
		int col = location.getCol();
		if (movement.capturedPiece == null) {
			chessSquares[row][col].setIcon(null);
		} else {
			chessSquares[row][col].setIcon(new ImageIcon(movement.capturedPiece
					.getIconDir()));
		}

		// put the piece back to previous location
		location = movement.preLocation;
		row = location.getRow();
		col = location.getCol();
		chessSquares[row][col].setIcon(new ImageIcon(movement.piece
				.getIconDir()));

		gameManager.undoPreMovement();
		gameManager.updateGameStat();
		gameManager.switchPlayer();

		if (selectedPiece != null){
			colorselectedPiece(false);
		}
		selectedPiece = null;
		
		ChessGameFrame chessGameFrame = ChessGameFrame.getInstance();
		chessGameFrame.gameStatPanel.switchPlayer();
	}

	protected void colorselectedPiece(boolean isSelect) {
		Chessboard chessboard = Chessboard.getInstance();
		Color color;

		if (isSelect) {
			color = Color.red;
		} else {
			color = (Color) selectedPiece.getClientProperty("color");
		}
		selectedPiece.setBackground(color);

		Location location = (Location) selectedPiece
				.getClientProperty("location");
		Piece piece = chessboard.getPiece(location);
		for (Location availableLocation : piece.getAvailableLocations()) {
			int row = availableLocation.getRow();
			int col = availableLocation.getCol();

			JButton chessSquare = chessSquares[row][col];
			if (isSelect) {
				color = Color.green;
			} else {
				color = (Color) chessSquare.getClientProperty("color");
			}
			chessSquare.setBackground(color);
		}
	}
	
	/**
	 * Action Listener for performance select chess square operation
	 * 
	 * @author Xuefeng Zhu
	 *
	 */
	private class SelectSquareAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Chessboard chessboard = Chessboard.getInstance();
			GameManager gameManager = GameManager.getInstance();

			JButton selectedSquare = (JButton) e.getSource();
			Location location = (Location) selectedSquare
					.getClientProperty("location");
			Piece piece = chessboard.getPiece(location);

			// when no piece has been selected
			if (piece != null
					&& piece.getPlayer() == gameManager.getCurrentPlayer()) {
				selectPiece(selectedSquare);
				return;
			}

			if (selectedPiece != null) {
				moveToSquare(selectedSquare);
			}
		}

		private void selectPiece(JButton selectedSquare) {
			if (selectedPiece != null) {
				colorselectedPiece(false);
			}
			selectedPiece = selectedSquare;
			colorselectedPiece(true);
		}

		private void moveToSquare(JButton selectedSqure) {
			Chessboard chessboard = Chessboard.getInstance();

			Location curLocation = (Location) selectedPiece
					.getClientProperty("location");
			Piece piece = (Piece) chessboard.getPiece(curLocation);
			Location targetLocation = (Location) selectedSqure
					.getClientProperty("location");

			colorselectedPiece(false);
			if (piece.moveTo(targetLocation)) {
				GameManager gameManager = GameManager.getInstance();
				if (!gameManager.updateGameStat()) {
					gameManager.undoPreMovement();
					gameManager.updateGameStat();
					JOptionPane.showMessageDialog(null, "Protect your king!",
							null, JOptionPane.ERROR_MESSAGE);
					selectedPiece = null;
					return;
				}

				selectedPiece.setIcon(null);
				selectedPiece = null;
				selectedSqure.setIcon(new ImageIcon(piece.getIconDir()));

				ChessGameFrame chessGameFrame = ChessGameFrame.getInstance();
				if (gameManager.getStatus() == GameManager.CHECKMATE) {
					JOptionPane.showMessageDialog(null, GameManager.CHECKMATE);
					chessGameFrame.gameStatPanel
							.increasePlayerScore(gameManager.getCurrentPlayer());
					chessGameFrame.restart();
					return;
				} else if (gameManager.getStatus() == GameManager.STALEMATE) {
					JOptionPane.showMessageDialog(null, GameManager.STALEMATE);
					chessGameFrame.restart();
					return;
				} else if (gameManager.switchPlayer().isChecked) {
					JOptionPane.showMessageDialog(null, "You are checked");
				}

				chessGameFrame.gameStatPanel.switchPlayer();
			} else {
				colorselectedPiece(true);
				JOptionPane.showMessageDialog(null, "Invalid move.", null,
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

}
