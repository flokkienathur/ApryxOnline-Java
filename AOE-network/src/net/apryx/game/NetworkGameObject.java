package net.apryx.game;

import net.apryx.logger.Log;
import net.apryx.math.Mathf;
import net.apryx.network.aoe.BMessage;
import net.apryx.time.Time;

public abstract class NetworkGameObject extends GameObject{
	
	protected int networkID = -1;
	public float targetX = 0;
	public float targetY = 0;
	private boolean changed = false;
	private boolean local = false;
	
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
		
		if(m.getType() == BMessage.S_MOVE || m.getType() == BMessage.C_MOVE){
			x = m.getFloat("x", 0);
			y = m.getFloat("y", 0);
			targetX = m.getFloat("target_x", 0);
			targetY = m.getFloat("target_y", 0);
			
			setChanged();
		}
	}
	
	public boolean isLocal(){
		return networkID <= 0 || local;
	}
	
	public void setLocal(boolean l){
		local = l;
	}

	public int getNetworkID() {
		return networkID;
	}

	public void setChanged() {
		this.changed = true;
	}
	public void setChanged(boolean changed) {
		this.changed = false;
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public void setNetworkID(int networkID) {
		this.networkID = networkID;
	}

	public boolean moveToTarget(float speed){
		float xDir = targetX - x;
		float yDir = targetY - y;
		
		float l = Mathf.sqrt(xDir * xDir + yDir * yDir);
		if(l < 1f)
			return true;
		
		xDir /= l;
		yDir /= l;

		x += xDir * speed * Time.deltaTime;
		y += yDir * speed * Time.deltaTime;
		
		return false;
	}
}
