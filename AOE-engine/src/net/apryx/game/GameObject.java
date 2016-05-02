package net.apryx.game;

import net.apryx.graphics.SpriteBatch;
import net.apryx.graphics.texture.Sprite;

public abstract class GameObject {
	
	protected World world;
	
	protected int layer;
	protected Sprite sprite;
	public float x, y;
	protected float hspeed, vspeed;
	protected float xScale = 1, yScale = 1;
	
	public GameObject(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void update(){
		x += hspeed;
		y += vspeed;
	}
	
	public void render(SpriteBatch batch){
		
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public float getLeft(){
		return x - sprite.getXCenter();
	}
	public float getRight(){
		return x - sprite.getXCenter() + sprite.getWidth();
	}
	public float getTop(){
		return y - sprite.getYCenter();
	}
	public float getBottom(){
		return y - sprite.getYCenter() + sprite.getHeight();
	}
	
	public int getLayer() {
		return layer;
	}
	
	public static float getDepthByPosition(float y){
		return y / 100.0f;
	}

}
