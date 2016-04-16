package net.apryx.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import net.apryx.logger.Log;

public class Client<T> {
	
	private Socket socket;
	private WebSerializer<T> serializer;
	private ArrayList<ClientListener<T>> listeners;
	
	private HashMap<String, Object> attributes;
	
	private DataInputStream input;
	private DataOutputStream output;
	
	private boolean connected = false;
	
	public Client(WebSerializer<T> serializer){
		setSerializer(serializer);
		listeners = new ArrayList<ClientListener<T>>();
		attributes = new HashMap<String, Object>();
	}
	
	public void addClientListener(ClientListener<T> listener){
		listeners.add(listener);
	}
	
	public void removeClientListener(ClientListener<T> listener){
		listeners.remove(listener);
	}
	
	public WebSerializer<T> getSerializer() {
		return serializer;
	}
	
	public void setSerializer(WebSerializer<T> serializer) {
		this.serializer = serializer;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) throws IOException{
		this.socket = socket;
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());			
		connected = true;
		invokeOnConnect();
	}
	
	public synchronized void disconnect(String reason) {
		Log.debug("Disconnecting because '" + reason + "'");
		if(socket != null && !socket.isClosed()){
			try{
				output.flush();
			}catch(Exception e){
				Log.error(e.getMessage());
			}
			try{
				socket.close();
			}catch(Exception e){
				Log.error(e.getMessage());
			}
		}
		
		if(connected){
			connected = false;
			invokeOnDisconnect();
		}
	}
	
	public Client<T> connect(String address, int port) throws IOException{
		InetAddress addr = InetAddress.getByName(address);
		Socket socket = new Socket(addr, port);
		setSocket(socket);
		return this;
	}
	
	public synchronized Client<T> send(T message){
		try{
			serializer.write(message, output);
			output.flush();
		}catch(Exception e){
			e.printStackTrace();
			disconnect("Failed to send message.");
		}
		return this;
	}
	
	public Client<T> listen() {
		while(connected){
			try{
				T t = serializer.read(input);
				if(t == null){
					Log.error("Message is null!");
					disconnect("Serializer doesn't know message");
				}else{
					invokeOnMessage(t);
				}
			}catch(Exception e){
				e.printStackTrace();
				Log.error(e.getMessage());
				break;
			}
		}
		
		disconnect("Not connected anymore");
		return this;
	}
	
	public Client<T> async(){
		new Thread("Client Thread"){
			public void run(){
				try{
					listen();					
				}catch(Exception e){
					//Does this even happen?
					Log.error("Client async, no arguments, can this even happen?");
					e.printStackTrace();
				}
			}
		}.start();
		
		return this;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	private void invokeOnConnect(){
		for(int i = 0; i < listeners.size() ;i++){
			ClientListener<T> l = listeners.get(i);
			l.onConnect(this);
		}
	}
	
	private void invokeOnDisconnect(){
		for(int i = 0; i < listeners.size() ;i++){
			ClientListener<T> l = listeners.get(i);
			l.onDisconnect(this);
		}
	}
	
	private void invokeOnMessage(T msg){
		for(int i = 0; i < listeners.size() ;i++){
			ClientListener<T> l = listeners.get(i);
			l.onMessage(this, msg);
		}
	}

	public void setAttribute(String name, Object value){
		this.attributes.put(name, value);
	}
	public void setAttribute(Object value){
		this.attributes.put(value.getClass().toGenericString(), value);
	}
	
	@SuppressWarnings("unchecked")
	public <Z> Z getAttribute(Class<Z> cls){
		return (Z) this.attributes.get(cls.toGenericString());
	}
	public Object getAttribute(String name){
		return this.attributes.get(name);
	}

}
