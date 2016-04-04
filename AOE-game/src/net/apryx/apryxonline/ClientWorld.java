package net.apryx.apryxonline;

import net.apryx.game.GameObject;
import net.apryx.game.NetworkGameObject;
import net.apryx.game.NetworkWorld;
import net.apryx.logger.Log;
import net.apryx.network.Client;
import net.apryx.network.aoe.AOEMessage;
import net.apryx.network.aoe.AOEUpdateMessage;

public class ClientWorld extends NetworkWorld{
	
	protected Client<AOEMessage> client;
	private AOEUpdateMessage reuseableUpdate = new AOEUpdateMessage();
	
	public ClientWorld(Client<AOEMessage> client){
		this.client = client;
	}
	
	@Override
	public void update() {
		super.update();
		for(int i = 0; i < gameObjects.size(); i++){
			GameObject obj = gameObjects.get(i);
			if(obj instanceof NetworkGameObject){
				NetworkGameObject netObject = (NetworkGameObject) obj;
				//If its ours, lets send an update!
				if(netObject.isLocal()){
					reuseableUpdate.networkID = netObject.getNetworkID();
					reuseableUpdate.x = netObject.x;
					reuseableUpdate.y = netObject.y;
					reuseableUpdate.targetX = netObject.targetX;
					reuseableUpdate.targetY = netObject.targetY;
					
					client.send(reuseableUpdate);
				}
			}
		}
	}

	public void processMessage(AOEUpdateMessage message) {
		//TODO make this safe lol
		if(message.networkID != -1){
			NetworkGameObject object = this.getGameObjectByNetworkId(message.networkID);
			if(object == null){
				Log.error("Unknown network object : " + message.networkID);
				return;
			}
			object.process(message);
			
		}
	}
}

