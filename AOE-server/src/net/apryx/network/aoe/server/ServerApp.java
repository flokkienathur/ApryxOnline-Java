package net.apryx.network.aoe.server;

import net.apryx.engine.Engine;
import net.apryx.logger.Log;
import net.apryx.logger.LogGui;

public class ServerApp {
	
	public static void main(String[] args) {
		Engine.headless = true;
		
		Engine.frameRate = 15; //15 fps
		
		LogGui gui = new LogGui();
		Log.err = gui;
		Log.out = gui;
		
		Engine.create(new ServerGame());
	}
	
}
