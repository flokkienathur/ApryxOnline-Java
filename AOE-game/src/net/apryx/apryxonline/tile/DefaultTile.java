package net.apryx.apryxonline.tile;

public class DefaultTile extends ApryxTile{
	
	protected int id;
	
	public DefaultTile(int id){
		ApryxTile.tiles[id] = this;
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
