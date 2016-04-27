package net.apryx.apryxonline;

import java.io.File;
import java.util.Scanner;

import net.apryx.apryxonline.tile.ApryxTile;
import net.apryx.tiles.TileMap;

public class TileMapLoader {
	
	public static TileMap load(String s){
		Scanner scanner = null;
		try{
			scanner = new Scanner(new File(s));
			
			int width = scanner.nextInt();
			int height = scanner.nextInt();
			
			TileMap map = new TileMap(width, height);
			
			for(int y = 0; y < height; y ++){
				for(int x = 0; x < width; x++){
					int id = scanner.nextInt();
					map.setTile(x, y, ApryxTile.tiles[id]);
				}
			}
			
			return map;
		}catch(Exception e){
		}finally{
			if(scanner != null){
				scanner.close();
			}
		}
		return null;
	
	}
}
