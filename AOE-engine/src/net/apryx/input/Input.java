package net.apryx.input;

import net.apryx.engine.Engine;

public class Input {
	
	public static void setMouseGrabbed(boolean b){
		Engine.window.setCursorHidden(b);
	}
	
	public static float getMouseX(){
		return Engine.window.getMouseX();
	}
	
	public static float getMouseY(){
		return Engine.window.getMouseY();
	}
	
	public static float getMouseDX(){
		return Engine.window.getMouseDX();
	}
	
	public static float getMouseDY(){
		return Engine.window.getMouseDY();
	}

	public static boolean isKeyPressed(int keycode){
		return Engine.window.isKeyPressed(keycode);
	}
	
	public static boolean isKeyDown(int keycode){
		return Engine.window.isKeyDown(keycode);
	}
	
	public static boolean isKeyReleased(int keycode){
		return Engine.window.isKeyReleased(keycode);
	}

	public static boolean isMouseButtonPressed(int keycode){
		return Engine.window.isMouseButtonPressed(keycode);
	}
	
	public static boolean isMouseButtonDown(int keycode){
		return Engine.window.isMouseButtonDown(keycode);
	}
	
	public static boolean isMouseButtonReleased(int keycode){
		return Engine.window.isMouseButtonReleased(keycode);
	}
}