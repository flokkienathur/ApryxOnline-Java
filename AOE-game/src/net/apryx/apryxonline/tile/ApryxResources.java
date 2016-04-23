package net.apryx.apryxonline.tile;

import java.io.File;

import net.apryx.graphics.texture.Sprite;
import net.apryx.graphics.texture.Texture;
import net.apryx.graphics.texture.TextureLoader;
import net.apryx.graphics.texture.TextureRegion;
import net.apryx.tiles.Tile;

public class ApryxResources {
	
	public static final Tile tileVoid = new VoidTile(0);
	public static final Tile tileGrass = new DefaultTile(1);
	public static final Tile tileStone = new DefaultTile(2);
	
	//TODO not player but entity sheet or something
	public static Texture player;
	public static Texture tileSheet;
	
	public static Sprite[] tileSprites;
	
	public static void init(){
		player = TextureLoader.loadTexture(new File("texture/player.png"));
		tileSheet = TextureLoader.loadTexture(new File("texture/tilesheet.png"));
		
		tileSprites = new Sprite[]{
			null,
			new Sprite(new TextureRegion(tileSheet, 0,0,16,16)),
			new Sprite(new TextureRegion(tileSheet, 16,0,16,16))
		};
	}
	
	public static void dispose(){
		player.dispose();
		tileSheet.dispose();
	}
	
}
