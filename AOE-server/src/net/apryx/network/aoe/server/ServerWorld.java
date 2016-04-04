package net.apryx.network.aoe.server;

import net.apryx.game.GameObject;
import net.apryx.game.NetworkWorld;
import net.apryx.network.Server;
import net.apryx.network.aoe.AOEMessage;
import net.apryx.network.aoe.AOEUpdateMessage;

public class ServerWorld extends NetworkWorld{
	
	protected Server<AOEMessage> server;
	private AOEUpdateMessage reuseableUpdate = new AOEUpdateMessage();
	
	public ServerWorld(Server<AOEMessage> server){
		this.server = server;
	}
	
	@Override
	public void update() {
		super.update();
		
		for(int i = 0; i < gameObjects.size(); i++){
			GameObject obj = gameObjects.get(i);
			if(obj instanceof GameObjectServerPlayer){
				GameObjectServerPlayer netObject = (GameObjectServerPlayer) obj;
				//If its NOT local, so its clients
				if(!netObject.isLocal()){
					reuseableUpdate.networkID = netObject.getNetworkID();
					reuseableUpdate.x = netObject.x;
					reuseableUpdate.y = netObject.y;
					reuseableUpdate.targetX = netObject.targetX;
					reuseableUpdate.targetY = netObject.targetY;
					
					//TODO make game send this and not the server or something
					server.broadcast(reuseableUpdate, netObject.getClient());
				}
			}
		}
	}

	public void processMessage(GameObjectServerPlayer sender, AOEUpdateMessage message) {
		//TODO make this safe lol
		sender.process(message);	
	}
}
