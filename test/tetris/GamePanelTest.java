package tetris;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

public class GamePanelTest {

	@Test
	public void TestEndOfGame() {
		GamePanel game = new GamePanel(50, 0, 35, 15, 20);
		boolean result = false;
		int i = 0;
		while(1000 > i++) {
			game.moveTetrominoDown();
			if(game.getEndOfGame()) {
				result = true;
			}
		}
		
		Assert.assertTrue(result);
	}
	
	@Test
	public void TestMoveLeft() {
		GamePanel game = new GamePanel(50, 0, 35, 15, 20);
		
		int i = 0;
		while(100 > i++) {
			game.moveTetrominoLeft();
			game.moveTetrominoDown();
		}
		
		
		int value1 = game.getTilemap().getTile(34, 0);
		int value2 = game.getTilemap().getTile(33, 0);
		int value3 = game.getTilemap().getTile(32, 0);

		
		boolean result = value1 > 0 || value2 > 0 || value3 > 0;
		
		Assert.assertTrue(result);
	}
	
	@Test
	public void TestMoveRight() {
		GamePanel game = new GamePanel(50, 0, 35, 15, 20);
		
		int i = 0;
		while(100 > i++) {
			game.moveTetrominoRight();
			game.moveTetrominoDown();
		}
		
		int value1 = game.getTilemap().getTile(34, 14);
		int value2 = game.getTilemap().getTile(33, 14);
		int value3 = game.getTilemap().getTile(32, 14);

		
		boolean result = value1 > 0 || value2 > 0 || value3 > 0;
		
		Assert.assertTrue(result);
	}
	
	@Test
	public void TestFileHandling() {
		GamePanel game = new GamePanel(50, 0, 20, 10, 20);
		
		int i = 0;
		while(50 > i++) {
			game.moveTetrominoRight();
			game.moveTetrominoDown();
		}
		
		int j = 0;
		while(50 > j++) {
			game.moveTetrominoLeft();
			game.moveTetrominoDown();
		}
		
		Tilemap tilemap = game.getTilemap();
		game.getTilemap().save();
		game.loadTilemap("tilemap.txt");
		
		Assert.assertArrayEquals(game.getTilemap().getTiles(), tilemap.getTiles());
	}
}
