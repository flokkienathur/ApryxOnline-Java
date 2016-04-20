package net.apryx.tiles;

import net.apryx.graphics.SpriteBatch;

public class Tile {
	
	//Should this tile be drawn by the tilemap? Tile renderer? idk, because the server should have tiles too
	public void draw(SpriteBatch batch, float x, float y, float xscale, float yscale){
		//TODO draw this stuff
	}
	
	public boolean isSolid(){
		return false;
	}
}
