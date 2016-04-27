package net.apryx.network.aoe;

import java.util.HashMap;
import java.util.Map;

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
	public static final int S_CAST = 		0x0031;

	public static final int S_CHANGELEVEL =	0xA040;
	
	//two byte integer
	private int type;
	
	//The not binary data
	private Map<String, String> data;
	
	//the actual binary data, max length is also two bytes
	private byte[] binaryData;
	
	public BMessage(int type){
		this.type = type;
		data = new HashMap<String, String>();
	}
	
	public void setBinaryData(byte[] binaryData) {
		this.binaryData = binaryData;
	}
	
	public byte[] getBinaryData() {
		return binaryData;
	}
	
	public boolean hasBinaryData(){
		return binaryData == null || binaryData.length == 0;
	}
	
	public int getBinaryDataSize(){
		return binaryData == null ? 0 : binaryData.length;
	}
	
	public Map<String, String> getData() {
		return data;
	}
	
	public String getString(String key){
		return data.get(key);
	}
	
	public int getInt(String key, int defaultValue){
		try{
			return Integer.parseInt(data.get(key));
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	public float getFloat(String key, float defaultValue){
		try{
			return Float.parseFloat(data.get(key));
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	public void set(String key, String value){
		data.put(key, value);
	}

	public void set(String key, int value){
		data.put(key, ""+value);
	}
	
	public void set(String key, float value){
		data.put(key, ""+value);
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	
	
	
}
