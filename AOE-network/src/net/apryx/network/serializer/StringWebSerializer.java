package net.apryx.network.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.apryx.network.WebSerializer;

public class StringWebSerializer implements WebSerializer<String>{

	@Override
	public void write(String data, DataOutputStream stream) throws IOException {
		stream.writeShort(data.length());
		stream.write(data.getBytes());
	}

	@Override
	public String read(DataInputStream stream) throws IOException{
		int length = stream.readShort();
		
		byte[] bytes = new byte[length];
		
		int nRead = stream.read(bytes);
		
		if(nRead != length)
			return null;
		
		return new String(bytes);
	}

}
