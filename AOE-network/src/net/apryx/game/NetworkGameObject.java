package net.apryx.game;

import net.apryx.logger.Log;
import net.apryx.network.aoe.BMessage;

public abstract class NetworkGameObject extends GameObject{
	
	protected int networkID = -1;
	public float targetX = 0;
	public float targetY = 0;
	private boolean changed = false;
	
	public NetworkGameObject(){
		super(0,0);
	}
	
	public NetworkGameObject(float x, float y){
		super(x,y);
	}
	
	public void initNetwork(BMessage m){
		x = m.getFloat("x", 0);
		y = m.getFloat("y", 0);
	}
	
	public void process(BMessage m){
		//-1 is local
		int id = m.getInt("network_id", -2);
		
		if(id != this.networkID){
			Log.error("Incorrect network ID : " + id);
			return;
		}
		
		x = m.getFloat("x", 0);
		y = m.getFloat("y", 0);
		targetX = m.getFloat("target_x", 0);
		targetY = m.getFloat("target_y", 0);
	}
	
	public boolean isLocal(){
		return networkID <= 0;
	}

	public int getNetworkID() {
		return networkID;
	}
	
	public void setChanged() {
		this.changed = true;
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public void setNetworkID(int networkID) {
		this.networkID = networkID;
	}
}
