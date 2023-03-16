package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements intToColor{
	
	private int rows;
	private int columns;
	private int tileSize;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean endOfGame = false;
	private JLabel score;
	
	private Tilemap tilemap;
	private Tetromino tetromino;
	private ArrayList<int[][]> shapes;
	
	private Tetromino spawnTetromino() {
		int middle = (columns/2);
		
		Random r = new Random();
		int[][] shape = shapes.get(r.nextInt(shapes.size()));
		tetromino = new Tetromino(middle, 0, shape);
		
		return tetromino;
	}
	
	private boolean tetrominoBottomCollision() {
		int[][] shape = tetromino.getShape();

		int h = shape.length;
		int w = shape[0].length;
		int x0 = tetromino.getX();
		int y0 = tetromino.getY();
		
		if(tetromino.getY() + h + 1 > rows)
			return true;
		
		for(int  i = h - 1; i >=0; --i) {
			int[] line = shape[i];
			
			for(int j = 0; j < w; ++j) {
				if(line[j] != 0 && tilemap.getTile(y0 + i + 1, x0 + j) != 0) {
					return true;
				}		
			}
		}	
		return false;
	}
	
	private boolean tetrominoLeftSideCollision() {
		int[][] shape = tetromino.getShape();
		int h = shape.length;
		int x0 = tetromino.getX();
		int y0 = tetromino.getY();
				
		
		for(int i = 0; i < h; ++i) {
			if(x0 == 0)
				return true;
			
			if(shape[i][0] > 0 && tilemap.getTile(y0 + i, x0 - 1) > 0)
				return true;
		}

		return false;
	}
	
	private void colorTilemap(int w, int h, int[][] shape) {
		int s;
		
		int x0 = tetromino.getX();
		int y0 = tetromino.getY();
		
		for(int i = 0; i < h; ++i) {
			for(int j = 0; j < w; ++j) {
				if(shape[i][j] != 0) {
					s = shape[i][j];
					tilemap.setTile(y0 + i, x0 + j, s);
				}
			}
		}
		endOfGame = tilemap.topReached();
		tetromino = spawnTetromino();
	}

 	private boolean tetrominoRightSideCollision() {
		int[][] shape = tetromino.getShape();
		int h = shape.length;
		int w = shape[0].length;
		int x0 = tetromino.getX();
		int y0 = tetromino.getY();

		for(int i = 0; i < h; ++i) {
			if(x0 + w == columns)
				return true;
			
			if(shape[i][w - 1] > 0 && tilemap.getTile(y0 + i, x0 + w) > 0) 
				return true;
		}

		return false;
	}
	
	public GamePanel(int x, int y, int r, int c, int t){
		this.x = x;
		this.y = y;
		
		rows = r;
		columns = c;
		tileSize = t;
		
		width = c * t;
		height = r * t;
		
		tilemap = new Tilemap(r, c);
		
		shapes = new ArrayList<>();
		int[][] shape1 = {{1, 0}, {1, 0}, {1, 1}};
		shapes.add(shape1);
		int[][] shape2 = {{2, 2}, {2, 0}, {2, 0}};
		shapes.add(shape2);
		int[][] shape3 = {{3, 0}, {3, 3}, {0, 3}};
		shapes.add(shape3);
		int[][] shape4 = {{0, 4}, {4, 4}, {4, 0}};
		shapes.add(shape4);
		int[][] shape5 = {{5, 5, 5}, {0, 5, 0}};
		shapes.add(shape5);
		int[][] shape6 = {{6, 6}, {6, 6}};
		shapes.add(shape6);
		int[][] shape7 = {{7}, {7}, {7}, {7}};
		shapes.add(shape7);
 		
		tetromino = spawnTetromino();
		
		score = new JLabel("Score: " + Integer.toString(tilemap.getScore()));
		score.setVisible(true);
		score.setBounds(50, 50, 100, 50);
		this.add(score);
		this.setBounds(x, y, 400, y + height + 1);
		this.setBackground(Color.cyan);
	}
		
	public void drawTetromino(Graphics g) {
		int[][] shape = tetromino.getShape();
		int h = shape.length;
		int w = shape[0].length;
		int x0, y0;
	
		for(int i = 0; i < h; ++i) {
			y0 = y + tetromino.getY()*tileSize + i*tileSize;

			for(int j = 0; j < w; ++j) {
				if(shape[i][j] != 0) {
					x0 =  x + tetromino.getX()*tileSize + j*tileSize;
										
					g.setColor(getColor(shape[i][j]));
					g.fillRect(x0, y0,tileSize, tileSize);
				}
			}
		}
	}

	public void rotateTetromino() {
		if(!tilemap.topReached())
			tetromino.rotate(columns, rows);
	}
	
	public void moveTetrominoLeft() {
		if(!tetrominoLeftSideCollision() && !tilemap.topReached())
			tetromino.moveLeft();
	}
	
	public void moveTetrominoRight() {
		if(!tetrominoRightSideCollision() && !tilemap.topReached())
			tetromino.moveRight();
	}
	
	public void moveTetrominoDown() {
		int[][] shape = tetromino.getShape();
		int h = shape.length;
		int w = shape[0].length;
		
		if(tetrominoBottomCollision()) {
			colorTilemap(w, h, shape);
			return;
		}
		
		tetromino.fall();
		repaint();
	}
	
	public void placeTetromino() {
		int[][] shape = tetromino.getShape();
		int h = shape.length;
		int w = shape[0].length;
		
		while(!tetrominoBottomCollision())
			tetromino.fall();
		
		colorTilemap(w, h, shape);
	}
	
	public void loadTilemap(String fileName) {
		ObjectInputStream in;
		try{		
			FileInputStream f = new FileInputStream(fileName);
			in = new ObjectInputStream(f);
			tilemap = (Tilemap)in.readObject();
		}catch(IOException|ClassNotFoundException c) {}
	}
	
	public void setEndOfGame(boolean value) {
		endOfGame = value;
	}
	
	public Tilemap getTilemap() {return tilemap;}
	
	public boolean getEndOfGame() {
		return endOfGame;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		score.setBounds(50, 50, 100, 50);
		if(!endOfGame) {	
			score.setText("Score: " + Integer.toString(tilemap.getScore()));
		}
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y + 5 * tileSize, width, height - 5 * tileSize);
		
		drawTetromino(g);
		
		tilemap.display(g, x, y, tileSize);
			
		g.setColor(Color.BLACK);
		for(int i = 0; i <= width; i += tileSize) {
			g.drawLine(x + i, y + 5 * tileSize, x + i, y + height);
		}

		for(int i = 5 * tileSize; i <= height; i += tileSize) {
			g.drawLine(x , y + i, x + width, y + i);
		}
		
		g.setColor(Color.cyan);
		g.fillRect(x, y, width, 5 * tileSize);

	}
}