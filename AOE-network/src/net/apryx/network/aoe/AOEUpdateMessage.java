package net.apryx.network.aoe;

@Deprecated
public class AOEUpdateMessage extends AOEMessage{
	
	public float x;
	public float y;
	
	public float targetX; // where this guy is going
	public float targetY; // where this guy is going
	
	public int networkID;

	@Override
	public String toString() {
		return "AOEUpdateMessage [x=" + x + ", y=" + y + ", targetX=" + targetX
				+ ", targetY=" + targetY + ", networkID=" + networkID + "]";
	}

	
}
