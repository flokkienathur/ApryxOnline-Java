package net.apryx.apryxonline;

import net.apryx.game.GameObject;
import net.apryx.game.NetworkGameObject;
import net.apryx.game.NetworkWorld;
import net.apryx.network.Client;
import net.apryx.network.aoe.BMessage;

public class ClientWorld extends NetworkWorld{
	
	protected Client<BMessage> client;
	
	public ClientWorld(Client<BMessage> client){
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
//					reuseableUpdate.networkID = netObject.getNetworkID();
//					reuseableUpdate.x = netObject.x;
//					reuseableUpdate.y = netObject.y;
//					reuseableUpdate.targetX = netObject.targetX;
//					reuseableUpdate.targetY = netObject.targetY;
//					
//					client.send(reuseableUpdate);
				}
			}
		}
	}

}

