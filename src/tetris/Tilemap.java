package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

interface intToColor {
	public default Color getColor(int c) {
		switch(c) {
			case 1:
				return Color.red;
				
			case 2:
				return Color.yellow;
				
			case 3:
				return Color.green;
				
			case 4:
				return Color.MAGENTA;
				
			case 5:
				return Color.orange;
				
			case 6:
				return Color.blue;
				
			case 7:
				return Color.white;
			
		}
		return Color.red;
	}
}
	
public class Tilemap implements intToColor, Serializable{
		
	private int rows;
	private int columns;
	private int[][] tiles;
	private int score = 0;
	
	private void updateTilemap(int row) {
		for(int i = row; i >=0; --i) {
			if(i > 0)
				tiles[i] = tiles[i - 1];
			else
				for(int j = 0; j < columns; ++j)
					tiles[i][j] = 0;
		}
	}
	
	public Tilemap(int r, int c) {
		tiles = new int[r][c];
		
		rows = r;
		columns = c; 
		
		for(int i = 0; i < r; ++i) {
			for(int j = 0; j < c; ++j) {
				tiles[i][j] = 0;
			}
		}
	}
	
	public void setTile(int row, int column, int value) {
		tiles[row][column] = value;
	}
	
	public int getTile(int row, int column) {
		return tiles[row][column];
	}
		
	public void display(Graphics g, int x, int y, int tileSize) {
		
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < columns; ++j) {
				if(tiles[i][j] > 0) {
					g.setColor(getColor(tiles[i][j]));
					int x0 = x + j*tileSize;
					int y0 = y + i*tileSize;
					g.fillRect(x0, y0, tileSize, tileSize);
				}
			}
		}
	}
	
	public void reset() {
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < columns; ++j) {
				tiles[i][j] = 0;
			}
		}
		score = 0;
	}
	
	public void checkRow() {
		for(int i = 0; i < rows; ++i) {
			boolean scored = true;
			
			for(int j = 0; j < columns; ++j) {
				if(tiles[i][j] == 0)
					scored = false;
			}
			
			if(scored) {
				for(int k = 0; k < columns; ++k) {
					tiles[i][k] = 0;
				}
				
				++score;
				
				updateTilemap(i);
			}
		}
	}
	
	public boolean topReached() {
		boolean result = false;
		for(int i = 0; i < columns; ++i) {
			for(int j = 0; j < 4; ++j) {
				if(tiles[j][i] > 0)
					result = true;
			}
		}
		return result;
	}
	
	public void save() {
		try {
			FileOutputStream f = new FileOutputStream("tilemap.txt");
			ObjectOutputStream out = new ObjectOutputStream(f);
			
			out.writeObject(this);
			out.close();
		}catch(IOException e) {}
	}
	
	public int getScore() {
		return score;
	}
	
	public void setTiles(int[][] t) {
		tiles = t;
	}
	
	public int[][] getTiles() {
		return tiles;
	}
	
}
