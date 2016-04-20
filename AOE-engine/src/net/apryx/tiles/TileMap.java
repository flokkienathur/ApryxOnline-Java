package net.apryx.tiles;

import net.apryx.graphics.SpriteBatch;

public class TileMap {
	
	private final int width, height;
	public static final int TILE_SIZE = 16;
	
	private Tile[] tiles;
	
	public TileMap(int width, int height){
		this.width = width;
		this.height = height;
		
		this.tiles = new Tile[width * height];
	}
	
	public void draw(SpriteBatch batch, float x, float y, float xscale, float yscale){
		//TODO viewport stuff, for performance reasons ofc
		for(int yIndex = 0; yIndex < height; yIndex++){
			for(int xIndex = 0; xIndex < width; xIndex++){
				Tile t = getTile(xIndex, yIndex);
				if(t != null){
					t.draw(batch, x + xIndex * TILE_SIZE * yscale, y + yIndex * TILE_SIZE * yscale, xscale, yscale);					
				}
			}
		}
	}
	
	public boolean isSolid(int x, int y){
		Tile t = getTile(x,y);
		return t != null && t.isSolid();
	}
	
	public Tile getTile(int x, int y){
		if(x < 0)
			return null;
		if(y < 0)
			return null;
		if(x >= width)
			return null;
		if(y >= height)
			return null;
		
		return tiles[x + y * width];
	}
	public boolean setTile(int x, int y, Tile tile){
		if(x < 0)
			return false;
		if(y < 0)
			return false;
		if(x >= width)
			return false;
		if(y >= height)
			return false;
		
		tiles[x + y * width] = tile;
		return true;
	}
	
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
}
