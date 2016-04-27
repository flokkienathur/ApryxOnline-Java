package net.apryx.tiles;

import net.apryx.graphics.SpriteBatch;
import net.apryx.graphics.texture.Sprite;

public class TileRenderer {
	
	protected TileMap map;
	protected Sprite[] sprites;
	
	public TileRenderer(TileMap map){
		this.map = map;
		//TODO make this a setting or something
		this.sprites = new Sprite[16];
	}
	
	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}
	
	public void setSprite(Tile tile, Sprite sprite){
		setSprite(tile.getID(), sprite);
	}
	
	public void setMap(TileMap map) {
		this.map = map;
	}
	
	public TileMap getMap() {
		return map;
	}
	
	public void draw(SpriteBatch batch, float xx, float yy){
		if(map == null)
			return;
		
		for(int y = 0; y < map.height; y++){
			for(int x = 0; x < map.width; x++){
				Tile t = map.tiles[x + y * map.width];
				
				if(t == null)
					continue;
				
				Sprite sprite = getSprite(t.getID());
				
				if(sprite == null)
					continue;
				
				batch.drawSprite(sprite, xx + (x << 5), yy + (y << 5), 0);
			}
		}
	}
	
	public void setSprite(int id, Sprite sprite){
		sprites[id] = sprite;
	}
	
	public Sprite getSprite(int id){
		if(id < 0) //These checks might be removed
			return null;
		
		if(id > sprites.length)
			return null;
		
		return sprites[id];
	}
}
