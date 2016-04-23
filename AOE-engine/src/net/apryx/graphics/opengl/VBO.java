package net.apryx.graphics.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

public class VBO {
	
	public static final int ARRAY_BUFFER = GL15.GL_ARRAY_BUFFER;
	public static final int ELEMENT_ARRAY_BUFFER = GL15.GL_ELEMENT_ARRAY_BUFFER;

	public static final int STREAM_DRAW = GL15.GL_STREAM_DRAW;
	public static final int STATIC_DRAW = GL15.GL_STATIC_DRAW;
	public static final int DYNAMIC_DRAW = GL15.GL_DYNAMIC_DRAW;
	
	private int id;
	private int type;
	
	public VBO(int type){
		this.type = type;
		id = GL15.glGenBuffers();
	}
	
	/**
	 * 
	 * @param buffer the buffer to read data from
	 * @param offset the offset in floats
	 * @param size the size in floats
	 */
	public void bufferSubData(FloatBuffer buffer, long offset, long size){
		//Do some nasty hacking for size limitations and stuffs (glBufferSubData does not limit the size for 
		//FloatBuffers, which impacts the performance quite a bit for almost empty buffers)
		
		//offset * 4 because size in bytes needed
		//size * 4 because size in bytes needed
		
		//TODO test this!
		//GL15.nglBufferSubData(type, offset << 2, size << 2, MemoryUtil.memAddress(buffer));
		GL15.glBufferSubData(type, offset, buffer);
	}
	
	public void bufferData(FloatBuffer buffer){
		bufferData(buffer, STATIC_DRAW);
	}
	
	public void bufferData(FloatBuffer buffer, int drawManner){
		GL15.glBufferData(type, buffer, drawManner);
	}
	
	public void bufferData(IntBuffer buffer){
		bufferData(buffer, STATIC_DRAW);
	}
	
	public void bufferData(IntBuffer buffer, int drawManner){
		GL15.glBufferData(type, buffer, drawManner);
	}
	
	public void bufferData(ByteBuffer buffer, int size, int drawManner){
		GL15.glBufferData(type, size, buffer, drawManner);
	}
	
	@Deprecated
	public ByteBuffer mapBuffer(){
		return GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY);
	}

	@Deprecated
	public void unmapBuffer(){
		GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
	}
	
	public void bind(){
		GL15.glBindBuffer(type, id);
	}
	
	public void unbind(){
		GL15.glBindBuffer(type, 0);
	}
	
	public void dispose(){
		GL15.glDeleteBuffers(id);
	}
}