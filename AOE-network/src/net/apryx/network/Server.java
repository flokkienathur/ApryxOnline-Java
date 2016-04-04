package net.apryx.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server<T> implements ClientListener<T>{
	
	private int port;
	private WebSerializer<T> serializer;
	private ArrayList<Client<T>> connectedClients;
	private ArrayList<ServerListener<T>> listeners;
	private ServerSocket serverSocket;
	private boolean running = true;
	
	public Server(WebSerializer<T> serializer){
		connectedClients = new ArrayList<Client<T>>();
		listeners = new ArrayList<>();
		this.serializer = serializer;
	}
	
	public void setSerializer(WebSerializer<T> serializer) {
		this.serializer = serializer;
	}
	
	public WebSerializer<T> getSerializer() {
		return serializer;
	}
	
	public Server<T> setPort(int port){
		this.port = port;
		return this;
	}
	
	public void addListener(ServerListener<T> listener){
		listeners.add(listener);
	}
	
	public int getPort() {
		return port;
	}

	public Server<T> listen(){
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
			return this;
		}
		
		while(running){
			try{
				Socket socket = serverSocket.accept();

				Client<T> client = new Client<T>(serializer);
				client.addClientListener(this);
				client.setSocket(socket);
				client.async();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return this;
	}

	public Server<T> listen(int port){
		setPort(port);
		return listen();
	}
	
	public Server<T> async(int port){
		setPort(port);
		return async();
	}

	public Server<T> broadcast(T message){
		for (int i = 0; i < connectedClients.size(); i++) {
			connectedClients.get(i).send(message);
		}
		return this;
	}
	public Server<T> broadcast(T message, Client<T> exclude){
		for (int i = 0; i < connectedClients.size(); i++) {
			if(!connectedClients.get(i).equals(exclude)){
				connectedClients.get(i).send(message);				
			}
		}
		return this;
	}
	
	public Server<T> async(){
		new Thread("Server Thread"){
			public void run(){
				listen();
			}
		}.start();
		
		return this;
	}
	
	public ArrayList<Client<T>> getConnectedClients() {
		return connectedClients;
	}

	@Override
	public void onConnect(Client<T> client) {
		connectedClients.add(client);
		
		invokeOnConnect(client);
	}

	@Override
	public void onDisconnect(Client<T> client) {
		connectedClients.remove(client);
		
		//remove the listener
		client.removeClientListener(this);
		
		invokeOnDisconnect(client);
	}

	@Override
	public void onMessage(Client<T> client, T message) {
		invokeOnMessage(client, message);
	}
	
	private void invokeOnConnect(Client<T> client){
		for(int i = 0; i < listeners.size() ;i++){
			ServerListener<T> l = listeners.get(i);
			l.onConnect(this, client);
		}
	}
	
	private void invokeOnDisconnect(Client<T> client){
		for(int i = 0; i < listeners.size() ;i++){
			ServerListener<T> l = listeners.get(i);
			l.onDisconnect(this, client);
		}
	}
	
	private void invokeOnMessage(Client<T> client, T msg){
		for(int i = 0; i < listeners.size() ;i++){
			ServerListener<T> l = listeners.get(i);
			l.onMessage(this, client, msg);
		}
	}
	
}
