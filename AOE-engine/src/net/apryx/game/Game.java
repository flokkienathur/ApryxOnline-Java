package net.apryx.game;

import net.apryx.engine.Engine;

public abstract class Game {
	
	public abstract void init();
	public abstract void update();
	public abstract void render();
	public abstract void destroy();
	
	public boolean isCloseRequested(){
		return Engine.window.isCloseRequested();
	}
	
}