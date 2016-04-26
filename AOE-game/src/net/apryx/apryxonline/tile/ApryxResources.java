package net.apryx.apryxonline.tile;

import java.io.File;

import net.apryx.graphics.texture.Sprite;
import net.apryx.graphics.texture.Texture;
import net.apryx.graphics.texture.TextureLoader;
import net.apryx.graphics.texture.TextureRegion;

public class ApryxResources {
	
	//TODO not player but entity sheet or something
	public static Texture player;
	public static Texture tileSheet;
	
	public static Sprite[] tileSprites;
	
	public static void init(){
		player = TextureLoader.loadTexture(new File("texture/player.png"));
		tileSheet = TextureLoader.loadTexture(new File("texture/tilesheet.png"));
		
		tileSprites = new Sprite[]{
			null,
			new Sprite(new TextureRegion(tileSheet, 0,0,32,32)),
			new Sprite(new TextureRegion(tileSheet, 32,0,32,32))
		};
	}
	
	public static void dispose(){
		player.dispose();
		tileSheet.dispose();
	}
	
}
