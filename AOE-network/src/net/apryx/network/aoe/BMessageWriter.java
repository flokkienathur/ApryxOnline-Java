package net.apryx.network.aoe;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class BMessageWriter {
	
	private int type = -1;
	private ByteArrayOutputStream out;
	private DataOutputStream stream;
	
	public BMessageWriter(int type){
		this.type = type;
		out = new ByteArrayOutputStream();
		stream = new DataOutputStream(out);
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void writeString(String s) {
		try{
			stream.writeChar(s.length());	
			stream.write(s.getBytes());
		}catch(Exception e){}
	}
	
	public void writeInt(int i) {
		try{
			stream.writeInt(i);			
		}catch(Exception e){}
	}
	
	public void writeByte(int i) {
		try{
			stream.write(i);			
		}catch(Exception e){}
	}

	public void writeShort(int i) {
		try{
			stream.writeShort(i);			
		}catch(Exception e){}
	}
	
	public void writeChar(int i) {
		try{
			stream.writeChar(i);			
		}catch(Exception e){}
	}
	
	public void writeFloat(float f) {
		try{
			stream.writeFloat(f);			
		}catch(Exception e){}
	}
	
	public void reset(){
		out.reset();
	}
	
	public BMessage create(){
		return new BMessage(type, out.toByteArray());
	}
	
}
