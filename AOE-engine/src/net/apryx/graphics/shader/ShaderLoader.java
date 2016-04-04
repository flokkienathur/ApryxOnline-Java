package net.apryx.graphics.shader;

import java.io.File;

public class ShaderLoader {
	
	public ShaderLoader(){
	}
	
	public static Shader createVertexShader(File file){
		Shader shader = new Shader(Shader.VERTEX_SHADER);
		shader.source(file);
		shader.compile();
		
		//TODO check errors
		//GL.checkError();
		//if the compile worked
		if(shader.isCompiled()){
			return shader;
		}else{
			shader.dispose();
			return null;
		}
	}
	
	public static Shader createFragmentShader(File file){
		Shader shader = new Shader(Shader.FRAGMENT_SHADER);
		shader.source(file);
		shader.compile();
		
		//TODO check errors
		//GL.checkError();
		//if the compile worked
		if(shader.isCompiled()){
			return shader;
		}else{
			shader.dispose();
			return null;
		}
	}
	
	public static Shader createVertexShader(String source){
		Shader shader = new Shader(Shader.VERTEX_SHADER);
		shader.source(source);
		shader.compile();
		
		//TODO check errors
		//GL.checkError();
		//if the compile worked
		if(shader.isCompiled()){
			return shader;
		}else{
			shader.dispose();
			return null;
		}
	}
	
	public static Shader createFragmentShader(String source){
		Shader shader = new Shader(Shader.FRAGMENT_SHADER);
		shader.source(source);
		shader.compile();
		
		//TODO check errors
		//GL.checkError();
		//if the compile worked
		if(shader.isCompiled()){
			return shader;
		}else{
			shader.dispose();
			return null;
		}
	}
	
	public static ShaderProgram createProgram(File vertex, File fragment){
		return createProgram(
				createVertexShader(vertex),
				createFragmentShader(fragment)
				);
	}

	public static ShaderProgram createProgram(String vertex, String fragment){
		return createProgram(
				createVertexShader(vertex),
				createFragmentShader(fragment)
				);
	}
	public static ShaderProgram createDefaultProgram(){
		return createProgram(
				createVertexShader(ShaderConstants.defaultVertex),
				createFragmentShader(ShaderConstants.defaultFragment)
				);
	}

	public static ShaderProgram createProgram(Shader vertex, Shader fragment){
		ShaderProgram program = new ShaderProgram();
		program.attach(vertex);
		program.attach(fragment);
		
		program.bindAttributeLocation(ShaderConstants.POSITION_INDEX, ShaderConstants.POSITION_ATTR);
		program.bindAttributeLocation(ShaderConstants.COLOR_INDEX, ShaderConstants.COLOR_ATTR);
		program.bindAttributeLocation(ShaderConstants.UV_INDEX, ShaderConstants.UV_ATTR);
		program.bindAttributeLocation(ShaderConstants.NORMAL_INDEX, ShaderConstants.NORMAL_ATTR);
		
		program.link();
		
		return program;
	}
	
	
}

