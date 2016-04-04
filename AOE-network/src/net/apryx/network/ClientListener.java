package net.apryx.network;

public interface ClientListener<T> {
	public void onConnect(Client<T> client);
	public void onDisconnect(Client<T> client);
	public void onMessage(Client<T> client, T message);
}
