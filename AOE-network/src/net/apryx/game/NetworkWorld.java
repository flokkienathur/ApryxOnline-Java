package net.apryx.game;

/**
 * 
 * @author Folkert
 *
 */
public class NetworkWorld extends World{
	
	public NetworkGameObject getGameObjectByNetworkId(int id){
		for(GameObject o : gameObjects){
			if(o instanceof NetworkGameObject && ((NetworkGameObject)o).getNetworkID() == id){
				return (NetworkGameObject) o;
			}
		}
		return null;
	}
}
