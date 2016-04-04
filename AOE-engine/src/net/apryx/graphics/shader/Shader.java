package net.apryx.graphics.shader;

import java.io.File;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import net.apryx.utils.IOUtils;

public class Shader {

	public static int VERTEX_SHADER = GL20.GL_VERTEX_SHADER;
	public static int FRAGMENT_SHADER = GL20.GL_FRAGMENT_SHADER;

	int type = 0;
	int id = 0;
	boolean compiled = false;
	
	public Shader(int type){
		init(type);
	}
	
	public Shader(int type, String source, boolean autoCompile){
		init(type);
		source(source);
		
		if(autoCompile)
			compile();
	}
	
	public Shader(int type, File source, boolean autoCompile){
		this(type, IOUtils.fileToString(source), autoCompile);
	}
	
	private void init(int type){
		this.type = type;
		id = GL20.glCreateShader(type);
	}
	
	public void source(String source){
		GL20.glShaderSource(id, source);
	}
	
	public void source(File file){
		if(!file.exists()){
			System.err.println("Shader sourcefile does not exist : " + file.toString());
			return;
		}
		source(IOUtils.fileToString(file));
	}
	
	public void compile(){
		GL20.glCompileShader(id);

		if(GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
			
			System.err.println("Shader compiling failed!");
			System.err.println(GL20.glGetShaderInfoLog(id));
			
			compiled = false;
		}else{
			compiled = true;
		}
	}
	
	public int getId(){
		return id;
	}
	
	public int getType(){
		return type;
	}
	
	public boolean isCompiled(){
		return compiled;
	}
	
	public void dispose(){
		GL20.glDeleteShader(id);
	}
}