package tetris;

import java.awt.Color;

public class Tetromino {
	
	private int x;
	private int y;
	private int[][] shape;
	private int rotationalPhase = 0;
	
	public Tetromino(int x, int y, int[][] shape) {
		this.x = x;
		this.y = y;
		this.shape = shape;
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public int[][] getShape(){return shape;}
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	
	public void fall() { ++y;}
	public void moveLeft() {--x;}
	public void moveRight() {++x;}

	public void rotate(int tilemapWidth, int tilemapHeight) {
		int h = shape.length;
		int w = shape[0].length;
		
		int[][] newShape = new int[w][h];
		
		if(x + h < tilemapWidth && y + w < tilemapHeight) {
			if(rotationalPhase == 0) {
				for(int i = 0; i < w; ++i) {
					for(int j = 0; j < h; ++j) {
						newShape[i][j] = shape[j][i];
					}
				}
				++rotationalPhase;
				shape = newShape;
				return;
			}
			
			if(rotationalPhase == 1) {
				for(int i = 0; i < w; ++i) {
					for(int j = 0; j < h; ++j) {
						newShape[w - 1 - i][j] = shape[j][i];
					}
				}
				--rotationalPhase;
			}
			shape = newShape;
		}
	}
}
