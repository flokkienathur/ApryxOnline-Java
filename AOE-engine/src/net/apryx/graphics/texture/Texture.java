package net.apryx.graphics.texture;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;

public class Texture {

	public static final int WRAP_S = GL11.GL_TEXTURE_WRAP_S;
	public static final int WRAP_T = GL11.GL_TEXTURE_WRAP_T;
	
	public static final int MIN_FILTER = GL11.GL_TEXTURE_MIN_FILTER;
	public static final int MAG_FILTER = GL11.GL_TEXTURE_MAG_FILTER;

	public static final int CLAMP = GL11.GL_CLAMP;
	public static final int REPEAT = GL11.GL_REPEAT;

	public static final int NEAREST = GL11.GL_NEAREST;
	public static final int LINEAR = GL11.GL_LINEAR;

	public static final int RGB = GL11.GL_RGB;
	public static final int RGBA = GL11.GL_RGBA;
	
	protected int width, height;
	protected int id;
	
	public Texture(){
		id = GL11.glGenTextures();
	}
	
	protected Texture(int id){
		this.id = id;
	}
	
	public void bind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void unbind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public void texParameter(int param, int value){
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, param, value);
	}

	public void setData(int width, int height, FloatBuffer data, int colorDepth){
		setWidth(width);
		setHeight(height);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, colorDepth, width, height, 0, colorDepth, GL11.GL_FLOAT, data);
	}

	public void setData(int width, int height, IntBuffer data, int colorDepth){
		setWidth(width);
		setHeight(height);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, colorDepth, width, height, 0, colorDepth, GL11.GL_UNSIGNED_INT, data);
	}
	
	public void setData(int width, int height, ByteBuffer data, int colorDepth){
		setWidth(width);
		setHeight(height);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, colorDepth, width, height, 0, colorDepth, GL11.GL_UNSIGNED_BYTE, data);
	}
	
	public void dispose(){
		GL11.glDeleteTextures(id);
	}
	
	public float getTexCoordX() {
		return 0;
	}
	
	public float getTexCoordY() {
		return 0;
	}
	
	public float getTexCoordX2() {
		return 1;
	}
	
	public float getTexCoordY2() {
		return 1;
	}

	public int getWidth() {
		return width;
	}

	private void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	private void setHeight(int height) {
		this.height = height;
	}

	public int getId() {
		return id;
	}
	
}