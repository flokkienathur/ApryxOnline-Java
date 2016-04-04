package net.apryx.network.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.apryx.logger.Log;
import net.apryx.network.WebSerializer;
import net.apryx.network.aoe.AOECreateMessage;
import net.apryx.network.aoe.AOEDestroyMessage;
import net.apryx.network.aoe.AOELoginMessage;
import net.apryx.network.aoe.AOEMessage;
import net.apryx.network.aoe.AOEUpdateMessage;

public class AOESerializer implements WebSerializer<AOEMessage>{
	
	public static final int 
			CREATE = 0,
			DESTROY = 1,
			UPDATE = 2,
			LOGIN = 3;

	@Override
	public void write(AOEMessage data, DataOutputStream stream)
			throws IOException {
		
		if(data instanceof AOECreateMessage){
			AOECreateMessage m = (AOECreateMessage) data;
			
			stream.write(CREATE);
			stream.writeInt(m.networkID);
			stream.writeFloat(m.x);
			stream.writeFloat(m.y);
		}
		
		else if(data instanceof AOEDestroyMessage){
			AOEDestroyMessage m = (AOEDestroyMessage) data;
			stream.write(DESTROY);
			stream.writeInt(m.networkID);
		}

		else if(data instanceof AOEUpdateMessage){
			AOEUpdateMessage m = (AOEUpdateMessage) data;
			stream.write(UPDATE);
			stream.writeInt(m.networkID);
			stream.writeFloat(m.x);
			stream.writeFloat(m.y);
			stream.writeFloat(m.targetX);
			stream.writeFloat(m.targetY);
		}

		else if(data instanceof AOELoginMessage){
			AOELoginMessage m = (AOELoginMessage) data;
			stream.write(LOGIN);
			writeString(stream, m.name);
			writeString(stream, m.password);
		}
		
		else{
			//nope
			Log.error("Unknown class " + data.getClass());
		}
	}

	@Override
	public AOEMessage read(DataInputStream stream) throws IOException {
		int message = stream.readByte();
		
		//TODO add creating different stuffs
		if(message == CREATE){
			AOECreateMessage m = new AOECreateMessage();
			
			m.networkID = stream.readInt();
			m.x = stream.readFloat();
			m.y = stream.readFloat();
			
			return m;
		}
		
		else if(message == DESTROY){
			AOEDestroyMessage m = new AOEDestroyMessage();
			m.networkID = stream.readInt();
			return m;
		}

		else if(message == UPDATE){
			AOEUpdateMessage m = new AOEUpdateMessage();
			m.networkID = stream.readInt();
			m.x = stream.readFloat();
			m.y = stream.readFloat();
			m.targetX = stream.readFloat();
			m.targetY = stream.readFloat();
			return m;
		}
		
		else if(message == LOGIN){
			AOELoginMessage m = new AOELoginMessage();
			m.name = readString(stream);
			m.password = readString(stream);
			return m;
		}
		
		else{
			//nope
			Log.error("Unknown message " + message);
		}
		
		return null;
	}

	private String readString(DataInputStream stream) throws IOException{
		int length = stream.read();
		byte[] d = new byte[length];
		
		stream.read(d);
		
		return new String(d);
		
	}

	private void writeString(DataOutputStream stream, String string) throws IOException{
		stream.write(string.length());
		stream.write(string.getBytes());
		
	}
	
}
