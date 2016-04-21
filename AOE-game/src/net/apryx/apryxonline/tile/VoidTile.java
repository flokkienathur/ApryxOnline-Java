package net.apryx.apryxonline.tile;

public class VoidTile extends DefaultTile{

	public VoidTile(int id) {
		super(id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
