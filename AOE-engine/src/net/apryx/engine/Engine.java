package net.apryx.engine;

import org.lwjgl.opengl.GL11;

import net.apryx.game.Game;
import net.apryx.graphics.Window;
import net.apryx.time.Time;

public class Engine {
	
	/**
	 * Window object
	 */
	public static Window window;
	
	/**
	 * Current game object
	 */
	public static Game game;
	
	//actually are settings
	/**
	 * Start with creating window. (if this is set, you can still set the window variable manually).
	 */
	public static boolean headless = false;
	/**
	 * Window title
	 */
	public static String title = "Apryx Engine";
	
	/**
	 * Target Framerate
	 */
	public static float frameRate = 120;
	
	/**
	 * This variable indicates whether the engine should be running or not
	 */
	public static boolean running = true;
	
	/**
	 * Starts running the given game
	 * @param game
	 */
	public static void create(Game game){
		Engine.game = game;
		create();
	}
	
	/**
	 * Starts running the current game
	 */
	public static void create(){
		if(!headless){
			window = new Window(1280,720,false);
			window.setTitle(title);

			window.setVisible(true);
			
			GL11.glEnable(GL11.GL_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		
		long previous = System.nanoTime();
		
		
		game.init();
		
		while(!game.isCloseRequested() && running){
			if(!isHeadless()){
				window.pollEvents();
			}
			long current = System.nanoTime();
			
			//nanotime to seconds
			float delta = (float) ((current - previous) / 1000000000.0d);

			Time.deltaTime = delta;
			Time.runTime += delta;
			
			previous = current;
			
			//update game
			game.update();
			
			//render the game
			game.render();
			
			if(!isHeadless()){
				window.swap();
			}
			
			//If its -1, we don't sleep at all
			if(frameRate > 0){
				try{
					Thread.sleep((long) (1000 / frameRate));					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		game.destroy();
		
		window.destroy();
	}
	
	public static void stop(){
		running = false;
	}
	
	/**
	 * Returns whether the engine is currently headless (Engine.headless != Engine.isHeadless() in some cases)
	 * @return
	 */
	public static boolean isHeadless(){
		return window == null;
	}
}
