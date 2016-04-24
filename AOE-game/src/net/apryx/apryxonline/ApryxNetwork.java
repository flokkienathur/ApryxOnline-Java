package net.apryx.apryxonline;

import java.util.LinkedList;
import java.util.Queue;

import net.apryx.apryxonline.tile.ApryxResources;
import net.apryx.game.NetworkGameObject;
import net.apryx.game.NetworkWorld;
import net.apryx.logger.Log;
import net.apryx.network.Client;
import net.apryx.network.ClientListener;
import net.apryx.network.aoe.BMessage;
import net.apryx.tiles.TileMap;

public class ApryxNetwork implements ClientListener<BMessage>{
	

	private Client<BMessage> client;
	private Queue<BMessage> messageQueue;
	
	public NetworkWorld world;
	public int playerID = -1;
	
	public ApryxNetwork(Client<BMessage> client){
		messageQueue = new LinkedList<BMessage>();
		this.client = client;
	}
	
	public Client<BMessage> getClient() {
		return client;
	}
	
	public void setClient(Client<BMessage> client) {
		this.client = client;
	}
	
	public void update(){
		while(!messageQueue.isEmpty()){
			try{
				handleMessage(messageQueue.poll());				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
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
			Log.debug("Handshake done!");
			playerID = message.getInt("player_id", -1);
		}
		
		else if(message.getType() == BMessage.S_CHANGELEVEL){
			Log.debug("Change level!");
			world = new ApryxWorld(client);
			//TODO load the world
			
			world.map = new TileMap(16, 16);
			
			for(int y = 0; y < 16; y ++){
				for(int x = 0; x < 16; x++){
					world.map.setTile(x, y, ApryxResources.tileGrass);
				}
			}
			
		}
		
		else if(message.getType() == BMessage.S_CREATE){
			int networkID = message.getInt("network_id", -3);
			
			String name = message.getString("game_object");
			
			//TODO move this to another class or something
			if(name.equals("player")){
				GameObjectPlayer player = new GameObjectPlayer(0, 0);
				player.setNetworkID(networkID);
				player.initNetwork(message);
				
				if(player.getNetworkID() == playerID){
					player.setLocal(true);
				}
				
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
}
