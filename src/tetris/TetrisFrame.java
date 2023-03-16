package tetris;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class TetrisFrame extends JFrame{
	private GamePanel game;
	private Button newGameButton;
	private Button loadGameButton; 
	private Button saveAndQuitButton;
	private Button quitGameButton;
	private JLabel title;
	private JLabel lostGameLabel;
	private Timer timer;

	private int width = 600;
	private int height = 800;
	private int gameSpeed = 300;
	
	private class GameLoop implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(game != null) {
				game.moveTetrominoDown();
				game.getTilemap().checkRow();
				if(game.getEndOfGame()) {
					timer.stop();
					game.getTilemap().reset();
					displayLostGame();
				}
			}
		}
	}

	private class Listener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			
			if(!game.getEndOfGame()) {
				if (code == 37)
					game.moveTetrominoLeft();
				
				if(code == 38)
					game.rotateTetromino();
				
				if(code == 39)
					game.moveTetrominoRight();
				
				if(code == 32)
					game.placeTetromino();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

	private class newGameButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
        	setUpTilemap();
        }  
	}
	
	private class loadGameButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			loadTilemap();
		}
		
	}
	
	private class saveAndQuitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			saveTilemap();
		}		
	}
	
	private class quitGameButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	private void loadTilemap() {
		setUpTilemap();
		game.loadTilemap("tilemap.txt");
	}
	
	private void setUpTilemap() {
		this.remove(newGameButton);
		this.remove(loadGameButton);
		this.remove(quitGameButton);
		this.remove(title);
		
		game = new GamePanel(50, 0, 30, 10, 25);
		game.setVisible(true);
		this.add(game);

		game.addKeyListener(new Listener());		
		game.setFocusable(true);
		game.grabFocus();
		
		this.add(saveAndQuitButton);
		saveAndQuitButton.setVisible(true);
	}
	
	private void saveTilemap() {
		game.getTilemap().save();
		game.setEndOfGame(true);
		System.exit(0);  
	}
	
	private void displayLostGame() {
		lostGameLabel.setVisible(true);
	}
	
	private void initComponents() {
		title = new JLabel("Tetris");
		title.setBounds(width / 2 - 80, 200, 200, 70);
		title.setVisible(true);
		title.setFont(new Font("Calibri", Font.BOLD, 70));
		
		lostGameLabel = new JLabel("Game Lost");
		lostGameLabel.setBounds(width / 2, 50, 100, 50);
		lostGameLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		lostGameLabel.setForeground(Color.red);
		lostGameLabel.setVisible(false);
		
		newGameButton = new Button("New Game");
		newGameButtonListener buttonListener1 = new newGameButtonListener();
		newGameButton.setBounds(width / 2 - 50, height / 2, 100, 50);
		newGameButton.setBackground(Color.LIGHT_GRAY);
		newGameButton.addActionListener(buttonListener1);
		
		loadGameButton = new Button("Load Game");
		loadGameButtonListener buttonListener2 = new loadGameButtonListener();
		loadGameButton.setBounds(width / 2 - 50, height / 2 + 100, 100, 50);
		loadGameButton.setBackground(Color.LIGHT_GRAY);
		loadGameButton.addActionListener(buttonListener2);
		
		quitGameButton = new Button("Quit");
		quitGameButtonListener buttonListener3 = new quitGameButtonListener();
		quitGameButton.setBounds(width / 2 - 50, height / 2 + 200, 100, 50);
		quitGameButton.setBackground(Color.LIGHT_GRAY);
		quitGameButton.addActionListener(buttonListener3);
		
		saveAndQuitButton = new Button("Save And Quit"); 
		saveAndQuitButton.setBounds(width - 150, 50, 100, 50);
		saveAndQuitButtonListener buttonListener4 = new saveAndQuitButtonListener();
		saveAndQuitButton.setBackground(Color.LIGHT_GRAY);
		saveAndQuitButton.setVisible(false);
		saveAndQuitButton.addActionListener(buttonListener4);
		
		this.add(loadGameButton);
		this.add(newGameButton);
		this.add(quitGameButton);
		this.add(title);
		this.add(lostGameLabel);
 	}
	
	public TetrisFrame() {
		super("Tetris");
		this.setSize(width, height);
		this.setResizable(false);
		this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground( Color.cyan);
		this.setLayout(null);

		initComponents();
	}
	
	public void startGame() {
		timer = new Timer(gameSpeed, new GameLoop());
		timer.start();
	}
}
