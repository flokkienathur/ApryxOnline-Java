package net.apryx.network.aoe;

public class BMessage {

	//Version number
	public static final int VERSION =		1;
	
	//Client messages
	public static final int C_HANDSHAKE = 	0x0001;	
	public static final int C_MOVE = 		0x0011;
	public static final int C_CAST = 		0x0031;
	
	//Server messages
	public static final int S_HANDSHAKE = 	0xA001;
	public static final int S_ERROR =	 	0xA002;
	public static final int S_MOVE = 		0xA011;
	public static final int S_CREATE = 		0xA021;
	public static final int S_DESTROY =		0xA022;
	
	//two byte integer
	private final int type;
	
	//the actual binary data, max length is also two bytes
	private final byte[] buffer;
	
	public BMessage(int type, byte[] data){
		this.type = type;
		this.buffer = data;
	}
	
	public int getType() {
		return type;
	}
	
	public byte[] getData() {
		return buffer;
	}
	
	
}
