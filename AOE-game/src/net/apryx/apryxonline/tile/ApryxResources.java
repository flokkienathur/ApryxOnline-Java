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
		
		tileSprites = new Sprite[256];
		tileSprites[1] = new Sprite(new TextureRegion(tileSheet, 0,0,32,32));
		tileSprites[2] = new Sprite(new TextureRegion(tileSheet, 32,0,32,32)).setCenter(0,32).setOffset(0, 32).setStraightUp(true);
		tileSprites[3] = new Sprite(new TextureRegion(tileSheet, 32,32,32,32));
		
		int index = 4;
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 3; x++){
				//Create new tile, it registers itself :)
				new DefaultTile(index);
				tileSprites[index] = new Sprite(new TextureRegion(tileSheet, 64 + x * 32, y * 32, 32, 32));
				
				index++;
			}
		}
	}
	
	public static void dispose(){
		player.dispose();
		tileSheet.dispose();
	}
	
}
