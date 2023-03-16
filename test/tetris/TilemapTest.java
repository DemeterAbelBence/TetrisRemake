package tetris;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

public class TilemapTest {
	private Tilemap tilemap;
	
	@Test
	public void testScore() {		
		int[][] tiles = new int[10][10];
		for(int i = 0; i < 10; ++i) {
			tiles[9][i] = 1;
		}
		
		tilemap = new Tilemap(10, 10);
		tilemap.setTiles(tiles);
		tilemap.checkRow();
		
		int[][] value = new int[10][10];
		
		Assert.assertArrayEquals(tilemap.getTiles(), value);
	}
	
	@Test
	public void testReachedTop(){
		int[][] tiles = new int[10][10];
		tiles[2][2] = 1;
		
		tilemap = new Tilemap(10, 10);
		tilemap.setTiles(tiles);
		
		Assert.assertTrue(tilemap.topReached());
	}
}
