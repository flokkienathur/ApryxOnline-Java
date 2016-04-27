package net.apryx.apryxonline.tile;

import net.apryx.tiles.Tile;

public class ApryxTile extends Tile {

	public static Tile[] tiles = new Tile[Short.MAX_VALUE];
	
	public static final Tile tileVoid = new VoidTile(0);
	public static final Tile tileGrass = new DefaultTile(1);
	public static final Tile tileStone = new DefaultTile(2);
	

	
}
