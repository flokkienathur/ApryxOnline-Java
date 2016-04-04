package net.apryx.network;

public interface ServerListener<T> {
	public void onConnect(Server<T> server, Client<T> client);
	public void onDisconnect(Server<T> server, Client<T> client);
	public void onMessage(Server<T> server, Client<T> client, T message);
}
