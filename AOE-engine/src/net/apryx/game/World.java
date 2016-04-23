package net.apryx.game;

import java.util.ArrayList;
import java.util.List;

import net.apryx.engine.Engine;
import net.apryx.graphics.SpriteBatch;
import net.apryx.input.Input;
import net.apryx.tiles.TileMap;

public class World {
	
	protected List<GameObject> gameObjects;
	public float viewX, viewY, viewWidth = 640, viewHeight = 360;
	public TileMap map;
	
	public World(){
		gameObjects = new ArrayList<>();
	}
	
	public void addGameObject(GameObject object){
		gameObjects.add(object);
		object.world = this;
	}
	
	public void removeGameObject(GameObject object){
		gameObjects.remove(object);
		object.world = null;
	}
	
	public void render(SpriteBatch batch){
		batch.view.setIdentity();
		batch.view.setOrthagonal(viewX, viewX + viewWidth, viewY + viewHeight, viewY, -1000, 1000);
		
		preRender(batch);
		
		for(int i = 0; i < gameObjects.size(); i++){
			gameObjects.get(i).render(batch);
		}
	}
	
	protected void preRender(SpriteBatch batch){
		
	}
	
	public void update(){
		for(int i = 0; i < gameObjects.size(); i++){
			gameObjects.get(i).update();
		}
	}
	
	public float getMouseX(){
		return Input.getMouseX() / Engine.window.getWidth() * viewWidth + viewX;
	}
	
	public float getMouseY(){
		return Input.getMouseY() / Engine.window.getHeight() * viewHeight + viewY;
	}
}
