package net.apryx.network.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import net.apryx.logger.Log;
import net.apryx.network.WebSerializer;
import net.apryx.network.aoe.BMessage;

public class BSerializer implements WebSerializer<BMessage>{

	@Override
	public void write(BMessage data, DataOutputStream stream) throws IOException {
		stream.writeChar(data.getType());
		
		Map<String, String> map = data.getData();
		
		stream.writeChar(map.size());
		
		for(String key : map.keySet()){
			writeString(stream, key);
			writeString(stream, map.get(key));
		}
		
		stream.writeChar(data.getBinaryDataSize());
		
		if(data.getBinaryDataSize() == 0){
			stream.write(data.getBinaryData());
		}
	}

	@Override
	public BMessage read(DataInputStream stream) throws IOException {
		int type = stream.readChar();
		int count = stream.readChar();
		
		BMessage message = new BMessage(type);
		
		for(int i = 0; i < count; i++){
			String key = readString(stream);
			String value = readString(stream);
			message.set(key, value);
		}
		
		int length = stream.readChar();
		
		byte[] b = new byte[length];
		stream.readFully(b);
		
		message.setBinaryData(b);
		
		return message;
	}
	
	public String readString(DataInputStream stream) throws IOException{
		int length = stream.readChar();
		
		byte[] bytes = new byte[length];
		int read = stream.read(bytes);
		
		if(read != length){
			Log.error("Invalid String length!");
		}
		
		return new String(bytes, StandardCharsets.US_ASCII);
	}
	
	public void writeString(DataOutputStream stream, String s) throws IOException{
		stream.writeChar(s.length());
		stream.write(s.getBytes(StandardCharsets.US_ASCII));
	}
	
	/*
	public static void main(String[] args) throws Exception{
		BSerializer s = new BSerializer();
		
		BMessage message = new BMessage(BMessage.S_MOVE);
		
		message.setString("Hans", "plast");
		message.setFloat("float", 123.1f);
		message.setInt("int", 12);
		
		
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(outStream);
		s.write(message, out);
		
		ByteArrayInputStream inStream = new ByteArrayInputStream(outStream.toByteArray());
		DataInputStream in = new DataInputStream(inStream);
		BMessage outMessage = s.read(in);
		
		System.out.println(outMessage.getType());
		System.out.println(outMessage.getString("Hans"));
		System.out.println(outMessage.getFloat("float", -1));
		System.out.println(outMessage.getInt("int", -1));
		
	}*/
	
}
