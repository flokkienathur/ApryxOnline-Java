package net.apryx.apryxonline.tile;

import net.apryx.tiles.Tile;

public class DefaultTile extends Tile{
	
	protected int id;
	
	public DefaultTile(int id){
		this.id = id;
	}
	
	
	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
}
