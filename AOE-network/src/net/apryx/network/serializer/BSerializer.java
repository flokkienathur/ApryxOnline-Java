package net.apryx.network.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.apryx.logger.Log;
import net.apryx.network.WebSerializer;
import net.apryx.network.aoe.BMessage;
import net.apryx.network.aoe.BMessageReader;
import net.apryx.network.aoe.BMessageWriter;

public class BSerializer implements WebSerializer<BMessage>{

	@Override
	public void write(BMessage data, DataOutputStream stream) throws IOException {
		stream.writeChar(data.getType());
		stream.writeChar(data.getData().length);
		stream.write(data.getData());
	}

	@Override
	public BMessage read(DataInputStream stream) throws IOException {
		int type = stream.readChar();
		int length = stream.readChar();
		
		byte[] data = new byte[length];
		int read = stream.read(data);
		
		if(read != length){
			Log.error("Failed to read data for message");
			return null;
		}
		
		return new BMessage(type, data);
	}
	
	
	public static void main(String[] args) throws Exception{
		BSerializer s = new BSerializer();
		
		BMessageWriter writer = new BMessageWriter(BMessage.S_ERROR);
		writer.writeShort(BMessage.VERSION);
		writer.writeString("allahu");
		writer.writeString("akbar!");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream oStream = new DataOutputStream(out);
		
		s.write(writer.create(), oStream);
		
		System.out.println("Wow, thats one " + out.size());
		
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		DataInputStream iStream = new DataInputStream(in);
		
		BMessage mOut = s.read(iStream);
		
		BMessageReader reader = new BMessageReader(mOut);
		System.out.println(reader.readShort());
		System.out.println(reader.readString());
		System.out.println(reader.readString());
		
		System.out.println(BMessage.S_ERROR + " : " + mOut.getType());
		
		
	}
	
}
