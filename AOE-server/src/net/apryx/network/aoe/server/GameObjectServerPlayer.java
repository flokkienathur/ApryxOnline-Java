package net.apryx.network.aoe.server;

import net.apryx.game.NetworkGameObject;
import net.apryx.network.Client;
import net.apryx.network.aoe.BMessage;

public class GameObjectServerPlayer extends NetworkGameObject{
	
	private boolean loggedIn = false;
	private String name = "";
	private Client<BMessage> client;
	
	public GameObjectServerPlayer(float x, float y, Client<BMessage> client) {
		super(x, y);
		this.client = client;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public Client<BMessage> getClient() {
		return client;
	}
	
	public ServerWorld getServerWorld(){
		if(this.world != null && this.world instanceof ServerWorld)
			return (ServerWorld) world;
		return null;
	}
	
	public void setClient(Client<BMessage> client) {
		this.client = client;
	}

}
