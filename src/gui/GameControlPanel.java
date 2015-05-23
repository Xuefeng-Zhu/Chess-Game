package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import management.GameManager;
import management.Player;

/**
 * The class for creating game control panel
 * 
 * @author Xuefeng Zhu
 *
 */
public class GameControlPanel extends JPanel {
	/**
	 * Constructor for GameControl Panel
	 */
	public GameControlPanel() {
		super(new GridLayout(3, 1));

		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(new RestartAction());
		this.add(restartButton);

		JButton forfeitButton = new JButton("Forfeit");
		forfeitButton.addActionListener(new ForfeitAction());
		this.add(forfeitButton);

		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new UndoAction());
		this.add(undoButton);
	}

	/**
	 * Action Listener for performing restart operation
	 * 
	 * @author Xuefeng Zhu
	 *
	 */
	private class RestartAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int choice = JOptionPane.showConfirmDialog(null,
					"Do you agree to restart the game?", null,
					JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				ChessGameFrame.getInstance().restart();
			} else {
				JOptionPane.showMessageDialog(null,
						"Your opponent declines your restart request.");
			}
		}
	}

	/**
	 * Action Listener for performing forfeit operation
	 * 
	 * @author Xuefeng Zhu
	 *
	 */
	private class ForfeitAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int choice = JOptionPane.showConfirmDialog(null,
					"Do you want to forfeit current game?", null,
					JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				GameManager gameManger = GameManager.getInstance();
				Player currentPlayer = gameManger.getCurrentPlayer();
				Player opponentPlayer = gameManger.getOpponent(currentPlayer);

				ChessGameFrame chessGameFrame = ChessGameFrame.getInstance();
				chessGameFrame.gameStatPanel
						.increasePlayerScore(opponentPlayer);
				chessGameFrame.restart();
			}
		}
	}

	/**
	 * Action Listener for performing undo operation
	 * 
	 * @author Xuefeng Zhu
	 *
	 */
	private class UndoAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			GameManager gameManager = GameManager.getInstance();
			if (gameManager.getPreMovement() == null) {
				JOptionPane.showMessageDialog(null, "You cannot undo.", null,
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			int choice = JOptionPane.showConfirmDialog(null,
					"Do you agree to allow your opponent to undo?", null,
					JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				ChessGameFrame chessGameFrame = ChessGameFrame.getInstance();
				chessGameFrame.chessboardPanel.undoMove();
			} else {
				JOptionPane.showMessageDialog(null,
						"Your opponent declines your undo request.");
			}

		}
	}
}
