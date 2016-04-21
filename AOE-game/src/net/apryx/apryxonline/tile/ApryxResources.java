package net.apryx.apryxonline.tile;

import java.io.File;

import net.apryx.graphics.texture.Texture;
import net.apryx.graphics.texture.TextureLoader;
import net.apryx.tiles.Tile;

public class ApryxResources {
	
	public static final Tile voidTile = new VoidTile(0);
	public static final Tile grass = new DefaultTile(1);
	
	//TODO not player but entity sheet or something
	public static Texture player;
	
	public static void init(){
		player = TextureLoader.loadTexture(new File("texture/player.png"));
	}
	
	public static void dispose(){
		player.dispose();
	}
	
}
