package net.apryx.apryxonline;

import net.apryx.game.GameObject;
import net.apryx.game.NetworkGameObject;
import net.apryx.game.NetworkWorld;
import net.apryx.graphics.SpriteBatch;
import net.apryx.network.Client;
import net.apryx.network.aoe.BMessage;
import net.apryx.tiles.TileMapRenderer;

public class ClientWorld extends NetworkWorld{
	
	protected Client<BMessage> client;
	protected TileMapRenderer tileRenderer;
	
	public ClientWorld(Client<BMessage> client){
		this.client = client;
		tileRenderer = new TileMapRenderer(null);
	}
	

	@Override
	protected void renderSelf(SpriteBatch batch){
		tileRenderer.setMap(map);
		tileRenderer.draw(batch, 0, 0);
	}
	
	@Override
	public void update() {
		super.update();
		for(int i = 0; i < gameObjects.size(); i++){
			GameObject obj = gameObjects.get(i);
			if(obj instanceof NetworkGameObject){
				NetworkGameObject netObject = (NetworkGameObject) obj;
				
				//If its ours, lets send an update!
				if(netObject.isLocal() && netObject.hasChanged()){
					BMessage message = new BMessage(BMessage.C_MOVE);
					message.set("network_id", netObject.getNetworkID());
					message.set("x", netObject.x);
					message.set("y", netObject.y);
					message.set("target_x", netObject.targetX);
					message.set("target_y", netObject.targetY);
					
					client.send(message);
				}
			}
		}
	}

}

