package tetris;

import org.junit.jupiter.api.Test;

import org.junit.Before;
import org.junit.Assert;

public class TetrominoTest {
	private Tetromino tetromino;
	
	private Tetromino setup() {
		int[][] shape = {{3, 0}, {3, 3}, {0, 3}};
		return tetromino = new Tetromino(50, 50, shape);	 
	}
	
	@Test
	public void testFall() {
		tetromino = setup();
		tetromino.fall();
		int value = tetromino.getY();
		Assert.assertEquals(value, 51);
	}
	
	@Test
	public void testMoveLeft() {
		tetromino = setup();
		tetromino.moveLeft();
		int value = tetromino.getX();
		Assert.assertEquals(value, 49);
	}
	
	@Test
	public void testMoveRight() {
		tetromino = setup();
		tetromino.moveRight();
		int value = tetromino.getX();
		Assert.assertEquals(value, 51);	
	}
	
	@Test
	public void testRotation() {
		tetromino = setup();
		tetromino.setX(50);
		tetromino.setY(50);
		
		tetromino.rotate(1000, 1000);
		int[][] shape1 = tetromino.getShape();
		int[][] value1 = {{3, 3, 0}, {0, 3, 3}};
		
		tetromino.rotate(1000, 1000);
		int[][] shape2 = tetromino.getShape();
		int[][] value2 = {{0, 3}, {3, 3}, {3, 0}};
		
		tetromino.rotate(1000, 1000);
		int[][] shape3 = tetromino.getShape();
		int[][] value3 = {{0, 3, 3}, {3, 3, 0}};
		
		tetromino.rotate(1000, 1000);
		int[][] shape4 = tetromino.getShape();
		int[][] value4 = {{3, 0}, {3, 3}, {0, 3}};
		
		Assert.assertArrayEquals(shape1, value1);
		Assert.assertArrayEquals(shape2, value2);
		Assert.assertArrayEquals(shape3, value3);
		Assert.assertArrayEquals(shape4, value4);
	}
}
