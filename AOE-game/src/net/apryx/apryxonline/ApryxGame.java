package net.apryx.apryxonline;

import java.io.IOException;

import net.apryx.apryxonline.tile.ApryxResources;
import net.apryx.engine.Engine;
import net.apryx.game.Game;
import net.apryx.graphics.SpriteBatch;
import net.apryx.logger.Log;
import net.apryx.network.Client;
import net.apryx.network.aoe.BMessage;
import net.apryx.network.serializer.BSerializer;

import org.lwjgl.opengl.GL11;

public class ApryxGame extends Game {
	
	private SpriteBatch batch;
	
	//TODO refactor to different class probably
	private Client<BMessage> client;
	public ApryxNetwork network;
	
	public static ApryxGame instance;
	
	@Override
	public void init() {
		instance = this;
		
		ApryxResources.init();
		
		batch = new SpriteBatch();
		
		client = new Client<BMessage>(new BSerializer());
		network = new ApryxNetwork(client);
		client.addClientListener(network);
		
		try {
			client.connect("localhost", Network.DEFAULT_PORT).async();
		} catch (IOException e) {
			Log.error("Unable to connect to server!");
			Log.error(e.getMessage());
			
			Engine.stop();
			return;
		}
	}

	@Override
	public void update() {
		network.update();
		if(network.world != null){
			network.world.update();			
		}
	}

	@Override
	public void render() {
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		if(network.world != null){
			batch.begin();
			
			network.world.render(batch);
			
			batch.end();
		}
		
	}

	@Override
	public void destroy() {
		batch.dispose();
		client.disconnect("Game ended");
		
		ApryxResources.dispose();
		
	}
	@Override
	public boolean isCloseRequested() {
		return super.isCloseRequested() || !client.isConnected();
	}

}
