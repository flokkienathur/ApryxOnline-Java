package net.apryx.graphics;

import java.nio.FloatBuffer;

import net.apryx.graphics.opengl.VBO;
import net.apryx.graphics.shader.ShaderConstants;
import net.apryx.graphics.shader.ShaderLoader;
import net.apryx.graphics.shader.ShaderProgram;
import net.apryx.graphics.texture.Sprite;
import net.apryx.graphics.texture.Texture;
import net.apryx.graphics.texture.TextureLoader;
import net.apryx.math.Matrix4;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

//TODO implement this class 
public class SpriteBatch {
	
	//Stride in bytes
	private static int FLOAT_COUNT = (3 + 2 + 4);
	private static int STRIDE = FLOAT_COUNT * 4;

	private static int VERTEX_OFFSET = 0;
	private static int VERTEX_SIZE = 3;

	private static int UV_OFFSET = 3 * 4;
	private static int UV_SIZE = 2;

	private static int COLOR_OFFSET = 3 * 4 + 2 * 4;
	private static int COLOR_SIZE = 4;
	
	
	//all attributes and stuffs :)
	protected float[] xyz = {0,0,0};
	protected float[] uv = {0,0};
	protected float[] rgba = {1,0,0,1};
	
	protected float depth = 0;
	
	//Data and vbo
	protected int size;
	protected int idx;
	protected FloatBuffer buffer;
	protected VBO vbo;
	
	//shaders
	protected ShaderProgram defaultShaderProgram;
	protected Texture defaultTexture;
	
	protected Texture currentTexture;
	protected ShaderProgram currentShaderProgram;
	public Matrix4 view;
	
	protected boolean drawing;
	
	public SpriteBatch(){
		//400 triangles
		this(12000);
	}
	
	/**
	 * Creates a new spritebatch
	 * @param size the size in vertices
	 */
	public SpriteBatch(int size){
		this.size = size;
		this.idx = 0;
		this.buffer = BufferUtils.createFloatBuffer(FLOAT_COUNT * size);
		
		vbo = new VBO(VBO.ARRAY_BUFFER);
		vbo.bind();
		vbo.bufferData(this.buffer, VBO.DYNAMIC_DRAW);

		defaultShaderProgram = ShaderLoader.createDefaultProgram();
		defaultTexture = TextureLoader.createDefaultTexture();
		
		currentTexture = defaultTexture;
		currentShaderProgram = defaultShaderProgram;
		view = Matrix4.getOrthagonalMatrix(-1, 1, 1, -1, -1, 1);
	}
	
	public void begin(){
		drawing = true;
	}
	
	public void end(){
		flush();
		drawing = false;
	}
	
	public void texture(Texture t){
		if(t == null){
			if(currentTexture != defaultTexture){
				flush();
				currentTexture = defaultTexture;
			}
		}else{
			if(t.getId() != currentTexture.getId()){
				flush();
			}
			currentTexture = t;
		}
	}
	
	public void color(float r, float g, float b){
		color(r,g,b,1);
	}
	
	public void color(float r, float g, float b, float a){
		rgba[0] = r;
		rgba[1] = g;
		rgba[2] = b;
		rgba[3] = a;
	}
	
	public void uv(float u, float v){
		uv[0] = u;
		uv[1] = v;
	}
	
	public void vertex(float x, float y){
		vertex(x,y,depth);
	}
	
	public void depth(float d){
		depth = d;
	}
	
	public void vertex(float x, float y, float z){
		xyz[0] = x;
		xyz[1] = y;
		xyz[2] = z;
		idx++;
		//but it in the buffers
		buffer.put(xyz).put(uv).put(rgba);
	}
	
	public void drawSprite(Sprite sprite, float x, float y){
		drawSprite(sprite, x, y, 1, 1);
	}
	
	public void drawSprite(Sprite sprite, float x, float y, float xscale, float yscale){
		Texture t = sprite.getTexture();
		texture(t);
		
		uv(t.getTexCoordX(), t.getTexCoordY());
		vertex(x - sprite.getxOffset() * xscale,y  - sprite.getyOffset() * yscale);
		uv(t.getTexCoordX2(), t.getTexCoordY());
		vertex(x - sprite.getxOffset() * xscale + sprite.getWidth() * xscale,y  - sprite.getyOffset() * yscale);
		uv(t.getTexCoordX2(), t.getTexCoordY2());
		vertex(x - sprite.getxOffset() * xscale + sprite.getWidth() * xscale,y  - sprite.getyOffset() * yscale + sprite.getHeight() * yscale);

		uv(t.getTexCoordX(), t.getTexCoordY());
		vertex(x - sprite.getxOffset() * xscale,y  - sprite.getyOffset() * yscale);
		uv(t.getTexCoordX2(), t.getTexCoordY2());
		vertex(x - sprite.getxOffset() * xscale + sprite.getWidth() * xscale,y  - sprite.getyOffset() * yscale + sprite.getHeight() * yscale);
		uv(t.getTexCoordX(), t.getTexCoordY2());
		vertex(x - sprite.getxOffset() * xscale,y  - sprite.getyOffset() * yscale + sprite.getHeight() * yscale);
	}
	
	public void drawRectangle(float x, float y, float width, float height){
		texture(null);
		vertex(x,y);
		vertex(x + width, y);
		vertex(x + width, y + height);
		
		vertex(x,y);
		vertex(x + width, y + height);
		vertex(x, y + height);
	}
	
	public void flush(){
		buffer.rewind();
		
		vbo.bind();
		
		//copy data to vbo
		vbo.bufferSubData(buffer, 0, idx * STRIDE);
		
		//opengl code
		GL20.glEnableVertexAttribArray(ShaderConstants.POSITION_INDEX);
		GL20.glVertexAttribPointer(ShaderConstants.POSITION_INDEX, VERTEX_SIZE, GL11.GL_FLOAT, false, STRIDE, VERTEX_OFFSET);
		
		GL20.glEnableVertexAttribArray(ShaderConstants.COLOR_INDEX);
		GL20.glVertexAttribPointer(ShaderConstants.COLOR_INDEX, COLOR_SIZE, GL11.GL_FLOAT, false, STRIDE, COLOR_OFFSET);
		
		GL20.glEnableVertexAttribArray(ShaderConstants.UV_INDEX);
		GL20.glVertexAttribPointer(ShaderConstants.UV_INDEX, UV_SIZE, GL11.GL_FLOAT, false, STRIDE, UV_OFFSET);
		
		currentTexture.bind();
		currentShaderProgram.use();
		
		currentShaderProgram.setUniformMatrixView(view);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, idx);
		
		currentTexture.unbind();
		currentShaderProgram.reset();
		
		vbo.unbind();
		idx = 0;
	}
	
	public void dispose(){
		vbo.dispose();
		defaultShaderProgram.dispose();
		defaultTexture.dispose();
	}
}
