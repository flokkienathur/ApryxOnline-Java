package net.apryx.tiles;


public class TileMap {
	
	private final int width, height;
	public static final int TILE_SIZE = 16;
	
	private Tile[] tiles;
	
	public TileMap(int width, int height){
		this.width = width;
		this.height = height;
		
		this.tiles = new Tile[width * height];
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
