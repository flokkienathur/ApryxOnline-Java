package net.apryx.apryxonline;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import net.apryx.engine.Engine;
import net.apryx.engine.Network;
import net.apryx.game.Game;
import net.apryx.game.NetworkGameObject;
import net.apryx.game.NetworkWorld;
import net.apryx.graphics.SpriteBatch;
import net.apryx.logger.Log;
import net.apryx.network.Client;
import net.apryx.network.ClientListener;
import net.apryx.network.aoe.BMessage;
import net.apryx.network.serializer.BSerializer;

import org.lwjgl.opengl.GL11;

public class ApryxGame extends Game implements ClientListener<BMessage>{
	
	private SpriteBatch batch;
	private NetworkWorld world;
	
	//TODO refactor to different class probably
	private Client<BMessage> client;
	private Queue<BMessage> messageQueue;
	
	@Override
	public void init() {
		batch = new SpriteBatch();
		
		//AARGGH LINKED LIST D: (Java has no non synchronized, not sorted list)
		messageQueue = new LinkedList<BMessage>();
		
		client = new Client<BMessage>(new BSerializer());
		client.addClientListener(this);
		
		try {
			client.connect("localhost", Network.DEFAULT_PORT).async();
		} catch (IOException e) {
			Log.error("Unable to connect to server!");
			Log.error(e.getMessage());
			
			Engine.stop();
			return;
		}

		world = new ClientWorld(client);
		world.addGameObject(new GameObjectPlayer(0,0));
	}

	@Override
	public void update() {
		while(!messageQueue.isEmpty()){
			handleMessage(messageQueue.poll());
		}
		world.update();
	}

	@Override
	public void render() {
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		batch.begin();
		
		world.render(batch);
		
		batch.end();
	}

	@Override
	public void destroy() {
		batch.dispose();
		client.disconnect("Game ended");
	}

	@Override
	public void onConnect(Client<BMessage> client) {
		Log.debug("Connected!");
		
		BMessage message = new BMessage(BMessage.C_HANDSHAKE);
		message.set("version", BMessage.VERSION);
		message.set("username", "JustF");
		message.set("password", "PasswordHansaplast");
		
		client.send(message);
	}

	@Override
	public void onDisconnect(Client<BMessage> client) {
		Log.debug("Not connected ): !");
	}

	@Override
	public void onMessage(Client<BMessage> client, BMessage message) {
		messageQueue.add(message);
	}
	
	public void handleMessage(BMessage message){
		if(message == null)
			Log.error("Message is null!");
		
		if(message.getType() == BMessage.S_HANDSHAKE){
			Log.debug("Received new handshake :D");
		}
		
		else if(message.getType() == BMessage.S_CREATE){
			int networkID = message.getInt("network_id", -3);
			
			String name = message.getString("game_object");
			
			//TODO move this to another class or something
			if(name.equals("player")){
				GameObjectPlayer player = new GameObjectPlayer(0, 0);
				player.setNetworkID(networkID);
				player.initNetwork(message);
				
				world.addGameObject(player);
				Log.debug("Created " + networkID);
			}
						
		}
		else if(message.getType() == BMessage.S_MOVE){
			int networkID = message.getInt("network_id", -3);
			
			NetworkGameObject gameObject = world.getGameObjectByNetworkId(networkID);
			if(gameObject == null){
				Log.error("Uncreated gameobject with id " + networkID);
				return;
			}
			
			//update this GameObject
			gameObject.process(message);
		}
		else if(message.getType() == BMessage.S_DESTROY){
			int networkID = message.getInt("network_id", -3);
			
			NetworkGameObject gameObject = world.getGameObjectByNetworkId(networkID);
			world.removeGameObject(gameObject);
			
			Log.debug("Destroy " + networkID);
		}
	}
	
	@Override
	public boolean isCloseRequested() {
		return super.isCloseRequested() || !client.isConnected();
	}

}
