package net.apryx.graphics;

public class Model {
	
	protected final float[] vertices;
	protected final float[] uvs;
	protected final float[] colors;
	protected final int size;
	
	public Model(int count){
		vertices = new float[count * 3];
		uvs = new float[count * 2];
		colors = new float[count * 4];
		size = count;
	}
}
