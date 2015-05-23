package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import management.GameManager;
import board.Chessboard;

/**
 * The class will create a window frame showing chessboard and game info panel
 * 
 * @author Xuefeng Zhu
 *
 */
public class ChessGameFrame extends JFrame {
	private static ChessGameFrame chessGameFrame = null;

	protected ChessBoardPanel chessboardPanel;
	protected JPanel gamePanel;
	protected GameControlPanel gameControlPanel;
	protected GameStatPanel gameStatPanel;

	/**
	 * constructor for ChessGameFrame, will generate a frame with chessboard and
	 * game panel
	 * 
	 * @param title
	 */
	protected ChessGameFrame(String title) {
		super(title);

		// add chessboard
		Chessboard chessBoard = Chessboard.getInstance();
		chessboardPanel = new ChessBoardPanel(chessBoard.getHeight(),
				chessBoard.getWidth());
		this.add(chessboardPanel, BorderLayout.EAST);

		// add game panel
		gamePanel = new JPanel(new BorderLayout());
		gameControlPanel = new GameControlPanel();
		gamePanel.add(gameControlPanel, BorderLayout.NORTH);
		gameStatPanel = new GameStatPanel();
		gamePanel.add(gameStatPanel, BorderLayout.CENTER);
		this.add(gamePanel, BorderLayout.WEST);

		// add menu bar
		JMenuBar gameMenuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("New Game");
		gameMenuBar.add(gameMenu);
		this.setJMenuBar(gameMenuBar);

		JMenuItem classicGame = new JMenuItem(GameManager.CLASSIC);
		classicGame.addActionListener(new NewGameAction());
		gameMenu.add(classicGame);

		JMenuItem customGame = new JMenuItem(GameManager.CUSTOM);
		customGame.addActionListener(new NewGameAction());
		gameMenu.add(customGame);

		// configure the setting of the frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Get the static instance of ChessGameFrame
	 * 
	 * @return chessGameFrame
	 */
	public static ChessGameFrame getInstance() {
		if (chessGameFrame == null) {
			chessGameFrame = new ChessGameFrame("Chess Game");
		}
		return chessGameFrame;
	}

	/**
	 * Start a new game with specific game mode
	 * 
	 * @param mode
	 *            game mode
	 */
	protected void newGame(String mode) {
		// initialize a new game module
		GameManager gameManager = GameManager.initInstance();
		gameManager.setMode(mode);
		gameManager.initPieces();

		// initialize a new ChessboardPanel
		this.remove(chessboardPanel);
		Chessboard chessBoard = Chessboard.getInstance();
		chessboardPanel = new ChessBoardPanel(chessBoard.getHeight(),
				chessBoard.getWidth());
		this.add(chessboardPanel, BorderLayout.EAST);

		// initialize a new gameStatPanel
		gamePanel.remove(gameStatPanel);
		gameStatPanel = new GameStatPanel();
		gamePanel.add(gameStatPanel, BorderLayout.CENTER);

		this.revalidate();
		this.repaint();

		gameStatPanel.setName();
	}

	/**
	 * Restart another game
	 */
	protected void restart() {
		GameManager gameManager = GameManager.getInstance();
		gameManager.restart();
		gameStatPanel.restart();

		// initialize a new ChessboardPanel
		this.remove(chessboardPanel);
		Chessboard chessBoard = Chessboard.getInstance();
		chessboardPanel = new ChessBoardPanel(chessBoard.getHeight(),
				chessBoard.getWidth());
		this.add(chessboardPanel, BorderLayout.EAST);

		this.revalidate();
		this.repaint();
	}

	/**
	 * Action Listener used to performance new game operation
	 * 
	 * @author Xuefeng Zhu
	 *
	 */
	private class NewGameAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem jMenuItem = (JMenuItem) e.getSource();
			chessGameFrame.newGame(jMenuItem.getText());
		}
	}

	public static void main(String[] args) {
		GameManager gameMnager = GameManager.initInstance();
		gameMnager.initPieces();
		ChessGameFrame chessGameFrame = ChessGameFrame.getInstance();
		chessGameFrame.gameStatPanel.setName();
	}
}
