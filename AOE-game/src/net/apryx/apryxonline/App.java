package net.apryx.apryxonline;
import net.apryx.engine.Engine;

public class App {
	public static void main(String[] args) {
		Engine.frameRate = 240;
		Engine.create(new ApryxGame());
	}
}
