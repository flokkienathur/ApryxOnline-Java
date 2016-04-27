package net.apryx.network.aoe.server;

import java.util.ArrayList;
import java.util.List;

import net.apryx.apryxonline.Network;
import net.apryx.game.NetworkGame;
import net.apryx.game.World;
import net.apryx.logger.Log;
import net.apryx.network.Client;
import net.apryx.network.Server;
import net.apryx.network.ServerListener;
import net.apryx.network.aoe.BMessage;
import net.apryx.network.serializer.BSerializer;

/**
 * Server game handles the messages from the login and stuffs
 * @author Folkert
 *
 */
public class ServerGame extends NetworkGame implements ServerListener<BMessage>{
	
	private List<ServerWorld> worlds;
	
	private Server<BMessage> server;
	private ArrayList<GameObjectServerPlayer> characters;
	
	private int lastID = 1;
	

	@Override
	public void init() {
		worlds = new ArrayList<ServerWorld>();
		
		characters = new ArrayList<>();
		
		server = new Server<BMessage>(new BSerializer());
		server.addListener(this);
		server.async(Network.DEFAULT_PORT);
		
		Log.debug("Server started!");
		
		//TODO init all worlds
		worlds.add(new ServerWorld(server));
	}

	@Override
	public void update() {
		for(World world : worlds){
			world.update();
		}
	}

	@Override
	public void render() {
		//Do nothing, because server is headless
	}

	@Override
	public void destroy() {
		
	}
	
	public boolean isCloseRequested(){
		//TODO socket closed stuff
		return false;
	}
	
	public void broadcast(BMessage message){
		broadcast(message, null);
	}
	
	public void broadcast(BMessage message, GameObjectServerPlayer exclude){
		for(int i = 0; i < characters.size(); i++){
			if(!characters.get(i).equals(exclude)){
				characters.get(i).getClient().send(message);
			}
		}
	}

	@Override
	public void onConnect(Server<BMessage> server, Client<BMessage> client) {
		Log.debug("Client connected!");
		
		//create the player object
		client.setAttribute(new GameObjectServerPlayer(0,0, client));
	}

	@Override
	public void onDisconnect(Server<BMessage> server, Client<BMessage> client) {
		Log.debug("Client disconnected!");
		
		GameObjectServerPlayer character = client.getAttribute(GameObjectServerPlayer.class);
		
		//TODO remove client from all sorts of lists and stuffs
		
		//If the character was logged in
		if(character.isLoggedIn()){
			//Remove character from connected list
			//Also remove it from its world
			
			ServerWorld world = character.getServerWorld();
			if(world != null)
				world.removeGameObject(character);
			
			characters.remove(character);
			
			BMessage message = new BMessage(BMessage.S_DESTROY);
			message.set("network_id", character.getNetworkID());
			
			//Send everyone that this guy disconnected!
			broadcast(message);
		}
	}

	@Override
	public void onMessage(Server<BMessage> server, Client<BMessage> client, BMessage message) {
		GameObjectServerPlayer character = client.getAttribute(GameObjectServerPlayer.class);
		
		if(message.getType() == BMessage.C_HANDSHAKE){
			Log.debug("Login from : "+ message.getString("username"));
			
			character.setName(message.getString("username"));
			character.setLoggedIn(true);
			character.setNetworkID(lastID++);
			
			//Add to current characters
			characters.add(character);
			
			//Add the character to the world
			worlds.get(0).addGameObject(character);
			
			//Return the handshake
			BMessage handshake = new BMessage(BMessage.S_HANDSHAKE);
			handshake.set("player_id", character.getNetworkID());
			
			//Give them the level change
			BMessage levelChange = new BMessage(BMessage.S_CHANGELEVEL);
			levelChange.set("world_name", "green_swamp");
			
			//Send the handshake and level change
			client.send(handshake);
			client.send(levelChange);	
			
			//Give them the create message :)
			BMessage createMessage = new BMessage(BMessage.S_CREATE);
			createMessage.set("game_object", "player");
			createMessage.set("network_id", character.getNetworkID());
			createMessage.set("x", 0);
			createMessage.set("y", 0);

			//Broadcast to everyone
			broadcast(createMessage, character);
			
			//Send all the current characters
			for(GameObjectServerPlayer gameObject : characters){
				//Reuse create message
				createMessage.set("game_object", "player"); // TODO refactor this
				createMessage.set("network_id", gameObject.getNetworkID());
				createMessage.set("x", gameObject.x);
				createMessage.set("y", gameObject.y);
				
				character.getClient().send(createMessage);
			}
			
			
		}
		
		//TODO make this safe and stuff
		else if(message.getType() == BMessage.C_MOVE){
			ServerWorld world = character.getServerWorld();
			
			//Set the network_id to this character, because its probably -1 for the player, TODO fix this because handshake
			message.set("network_id", character.getNetworkID());
			
			//we don't want to have the server crash on us
			if(world != null)
				world.processMessage(character,  message);
		}
		
		else if(message.getType() == BMessage.C_CAST){
			BMessage forward = new BMessage(BMessage.S_CAST);
			forward.set("network_id", character.getNetworkID());
			forward.set("spell", message.getString("spell"));
			forward.set("x", message.getString("x"));
			forward.set("y", message.getString("y"));
			
			broadcast(forward);
		}
	
	}
	
}
