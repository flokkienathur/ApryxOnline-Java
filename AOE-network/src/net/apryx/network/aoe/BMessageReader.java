package net.apryx.network.aoe;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class BMessageReader {
	
	private BMessage message;
	private ByteArrayInputStream out;
	private DataInputStream stream;
	
	public BMessageReader(BMessage message){
		setMessage(message);
	}
	
	public String readString(){
		try{
			int length = stream.readShort();
			byte[] array = new byte[length];
			stream.read(array);
			return new String(array);
		}catch(Exception e){}
		return null;
	}
	
	public int readInt() {
		try{
			return stream.readInt();
		}catch(Exception e){}
		return -1;
	}
	
	public int readByte() {
		try{
			return stream.read();
		}catch(Exception e){}
		return -1;
	}
	
	public int readShort() {
		try{
			return stream.readShort();
		}catch(Exception e){}
		return -1;
	}
	
	public float readFloat(){
		try{
			return stream.readFloat();
		}catch(Exception e){}
		return -1;
	}
	
	public void setMessage(BMessage message) {
		this.message = message;
		out = new ByteArrayInputStream(message.getData());
		stream = new DataInputStream(out);
	}
	
	public BMessage getMessage() {
		return message;
	}
}
