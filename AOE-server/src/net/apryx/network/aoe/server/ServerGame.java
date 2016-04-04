package net.apryx.network.aoe.server;

import java.util.ArrayList;
import java.util.List;

import net.apryx.engine.Network;
import net.apryx.game.NetworkGame;
import net.apryx.game.World;
import net.apryx.logger.Log;
import net.apryx.network.Client;
import net.apryx.network.Server;
import net.apryx.network.ServerListener;
import net.apryx.network.aoe.AOECreateMessage;
import net.apryx.network.aoe.AOEDestroyMessage;
import net.apryx.network.aoe.AOELoginMessage;
import net.apryx.network.aoe.AOEMessage;
import net.apryx.network.aoe.AOEUpdateMessage;
import net.apryx.network.serializer.AOESerializer;

/**
 * Server game handles the messages from the login and stuffs
 * @author Folkert
 *
 */
public class ServerGame extends NetworkGame implements ServerListener<AOEMessage>{
	
	private List<ServerWorld> worlds;
	
	private Server<AOEMessage> server;
	private ArrayList<GameObjectServerPlayer> characters;
	
	
	private int lastID = 1;
	

	@Override
	public void init() {
		worlds = new ArrayList<ServerWorld>();
		
		characters = new ArrayList<>();
		
		server = new Server<AOEMessage>(new AOESerializer());
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
	
	public void broadcast(AOEMessage message){
		broadcast(message, null);
	}
	
	public void broadcast(AOEMessage message, GameObjectServerPlayer exclude){
		for(int i = 0; i < characters.size(); i++){
			if(!characters.get(i).equals(exclude)){
				characters.get(i).getClient().send(message);
			}
		}
	}

	@Override
	public void onConnect(Server<AOEMessage> server, Client<AOEMessage> client) {
		Log.debug("Client connected!");
		
		//create the player object
		client.setAttribute(new GameObjectServerPlayer(0,0, client));
	}

	@Override
	public void onDisconnect(Server<AOEMessage> server, Client<AOEMessage> client) {
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
			
			AOEDestroyMessage message = new AOEDestroyMessage();
			message.networkID = character.getNetworkID();
			
			//Send everyone that this guy disconnected!
			broadcast(message);
		}
	}

	@Override
	public void onMessage(Server<AOEMessage> server, Client<AOEMessage> client, AOEMessage message) {
		GameObjectServerPlayer character = client.getAttribute(GameObjectServerPlayer.class);
		if(message instanceof AOELoginMessage){
			AOELoginMessage l = (AOELoginMessage) message;
			
			Log.debug("Login from : "+ l.name);
			
			character.setName(l.name);
			character.setLoggedIn(true);
			character.setNetworkID(lastID++);
			
			AOECreateMessage createMessage = new AOECreateMessage();
			createMessage.type = "player"; //TODO REFORMAT THIS
			createMessage.networkID = character.getNetworkID();

			//TODO add it to the right world
			worlds.get(0).addGameObject(character);
			
			broadcast(createMessage, character);
			
			for(GameObjectServerPlayer gameObject : characters){
				//Reuse create message
				createMessage.type = "player"; // TODO refactor this
				createMessage.networkID = gameObject.getNetworkID();
				createMessage.x = gameObject.x;
				createMessage.y = gameObject.y;
				
				character.getClient().send(createMessage);
				
			}
			
			
			characters.add(character);
		}
		
		//TODO make this safe and stuff
		else if(message instanceof AOEUpdateMessage){
			ServerWorld world = character.getServerWorld();
			
			//we don't want to have the server crash on us
			if(world != null)
				world.processMessage(character, (AOEUpdateMessage) message);
		}
	}
	
}
