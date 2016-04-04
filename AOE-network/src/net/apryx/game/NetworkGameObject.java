package net.apryx.game;

import net.apryx.network.aoe.AOECreateMessage;
import net.apryx.network.aoe.AOEUpdateMessage;

public abstract class NetworkGameObject extends GameObject{
	
	protected int networkID = -1;
	public float targetX = 0;
	public float targetY = 0;
	
	public NetworkGameObject(){
		super(0,0);
	}
	
	public NetworkGameObject(float x, float y){
		super(x,y);
	}
	
	public void initNetwork(AOECreateMessage m){
		x = m.x;
		y = m.y;
	}
	
	public void process(AOEUpdateMessage m){
		x = m.x;
		y = m.y;
		targetX = m.targetX;
		targetY = m.targetY;
	}
	
	public boolean isLocal(){
		return networkID <= 0;
	}

	public int getNetworkID() {
		return networkID;
	}
	
	public void setNetworkID(int networkID) {
		this.networkID = networkID;
	}
}
