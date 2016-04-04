package net.apryx.network;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public interface WebSerializer<T> {
	
	public void write(T data, DataOutputStream stream) throws IOException;
	public T read(DataInputStream stream) throws IOException;
}
