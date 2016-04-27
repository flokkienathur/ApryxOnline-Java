package net.apryx.apryxonline;

import net.apryx.apryxonline.tile.ApryxTileRenderer;
import net.apryx.game.GameObject;
import net.apryx.game.NetworkGameObject;
import net.apryx.game.NetworkWorld;
import net.apryx.graphics.SpriteBatch;
import net.apryx.logger.Log;
import net.apryx.math.Matrix4;
import net.apryx.network.Client;
import net.apryx.network.aoe.BMessage;
import net.apryx.tiles.TileRenderer;

public class ApryxWorld extends NetworkWorld{
	
	protected Client<BMessage> client;
	protected TileRenderer tileRenderer;
	
	public ApryxWorld(Client<BMessage> client){
		this.client = client;
		tileRenderer = new ApryxTileRenderer(null);
		viewWidth = 640 / 2;
		viewHeight = 360 / 2;
	}
	

	@Override
	protected void preRender(SpriteBatch batch){
		//[ 0, 1, 2, 3]
		//[ 4, 5, 6, 7]
		//[ 8, 9,10,11]
		//[12,13,14,15]

		//[ 1, 0, 0, 0]
		//[ 0, 1, 1, 0]
		//[ 0, 1, 1, 0]
		//[ 0, 0, 0, 1]
		
		Matrix4 current = batch.view;
		Matrix4 n = new Matrix4(new float[]
				{
				1,0,0,0,
				0,1,1,0,
				0,1,-1,0,
				0,0,0,1
				}
		);
		
		Matrix4 out = new Matrix4();
		Matrix4.multiply(out, current, n);
		batch.view = out;
		
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
					
					netObject.setChanged(false);
					
					client.send(message);
				}
			}
		}
	}

}

