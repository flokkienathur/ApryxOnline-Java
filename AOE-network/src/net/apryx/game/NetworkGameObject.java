package net.apryx.game;

import net.apryx.logger.Log;
import net.apryx.network.aoe.BMessage;
import net.apryx.network.aoe.BMessageReader;

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
	
	public void initNetwork(BMessageReader m){
		m.readString();
		x = m.readFloat();
		y = m.readFloat();
	}
	
	public void process(BMessageReader m){
		//Consume network id
		int id = m.readInt();
		if(id != this.networkID){
			Log.error("Incorrect network ID : " + id);
			return;
		}
		
		x = m.readFloat();
		y = m.readFloat();
		targetX = m.readFloat();
		targetY = m.readFloat();
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
