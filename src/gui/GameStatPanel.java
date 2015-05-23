package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import management.GameManager;
import management.Player;

/**
 * The class for creating game statistic panel
 * 
 * @author Xuefeng Zhu
 *
 */
public class GameStatPanel extends JPanel {
	protected PlayerPanel playerWPanel;
	protected PlayerPanel playerBPanel;
	protected PlayerPanel currentPlayerPanel;

	/**
	 * Constructor for GameStatPanel
	 */
	public GameStatPanel() {
		super(new GridLayout(2, 1));

		GameManager gameManager = GameManager.getInstance();
		Player playerW = gameManager.getCurrentPlayer();
		playerWPanel = new PlayerPanel(playerW);
		this.add(playerWPanel);

		Player playerB = gameManager.getOpponent(playerW);
		playerBPanel = new PlayerPanel(playerB);
		this.add(playerBPanel);

		// highlight current player statics border
		currentPlayerPanel = playerWPanel;
		currentPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.blue,
				5));
	}

	/**
	 * Helper function for performing restart
	 */
	protected void restart() {
		currentPlayerPanel.setBorder(null);
		currentPlayerPanel = playerWPanel;
		currentPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.blue,
				5));
	}

	/**
	 * Set two players' name
	 */
	protected void setName() {
		playerWPanel.setName();
		playerBPanel.setName();
	}

	/**
	 * Increase the score of the specific player
	 * 
	 * @param player
	 */
	protected void increasePlayerScore(Player player) {
		playerBPanel.increaseScore(player);
		playerWPanel.increaseScore(player);
	}

	/**
	 * Switch to another player
	 */
	protected void switchPlayer() {
		currentPlayerPanel.setBorder(null);

		if (currentPlayerPanel == playerBPanel) {
			currentPlayerPanel = playerWPanel;
		} else {
			currentPlayerPanel = playerBPanel;
		}
		currentPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.blue,
				5));
	}

	/**
	 * The class for create player statistic panel
	 * 
	 * @author Xuefeng Zhu
	 *
	 */
	private class PlayerPanel extends JPanel {
		private JLabel nameLabel;
		private JLabel scoreLabel;
		private Player player;

		/**
		 * Constructor for PlayerPanel
		 * 
		 * @param player
		 */
		public PlayerPanel(Player player) {
			super(new GridLayout(5, 1));

			this.player = player;
			JLabel playerLabel = new JLabel("player " + player.getColor());
			this.add(playerLabel);

			nameLabel = new JLabel("name: " + player.name);
			this.add(nameLabel);

			scoreLabel = new JLabel("score: " + player.score);
			this.add(scoreLabel);
		}

		/**
		 * Set the name of the player
		 */
		protected void setName() {
			player.name = JOptionPane
					.showInputDialog("Please enter the name of player "
							+ player.getColor());
			nameLabel.setText("name: " + player.name);
		}

		/**
		 * Increase the score of the player
		 * 
		 * @param player
		 */
		protected void increaseScore(Player player) {
			if (this.player == player) {
				player.score += 1;
				scoreLabel.setText("score: " + player.score);
			}
		}
	}

}
