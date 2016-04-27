package net.apryx.apryxonline.tile;

import net.apryx.tiles.TileMap;
import net.apryx.tiles.TileRenderer;

public class ApryxTileRenderer extends TileRenderer{

	public ApryxTileRenderer(TileMap map) {
		super(map);
		this.sprites = ApryxResources.tileSprites;
	}
	
}
