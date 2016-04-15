package net.apryx.network.aoe.server;

import net.apryx.game.GameObject;
import net.apryx.game.NetworkWorld;
import net.apryx.network.Server;
import net.apryx.network.aoe.BMessage;

public class ServerWorld extends NetworkWorld{
	
	protected Server<BMessage> server;
	
	public ServerWorld(Server<BMessage> server){
		this.server = server;
	}
	
	@Override
	public void update() {
		super.update();
		
		for(int i = 0; i < gameObjects.size(); i++){
			GameObject obj = gameObjects.get(i);
			if(obj instanceof GameObjectServerPlayer){
				GameObjectServerPlayer netObject = (GameObjectServerPlayer) obj;
				
				//TODO add changed or something
				
				//If its NOT local, so its clients
				if(!netObject.isLocal()){
					BMessage message = new BMessage(BMessage.S_MOVE);

					message.set("network_id", netObject.getNetworkID());
					message.set("x", netObject.x);
					message.set("y", netObject.y);
					message.set("target_x", netObject.targetX);
					message.set("target_y", netObject.targetY);
					
					server.broadcast(message, netObject.getClient());
				}
			}
		}
	}

	public void processMessage(GameObjectServerPlayer sender, BMessage message) {
		sender.process(message);
	}
}
