package net.apryx.graphics.shader;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import net.apryx.math.Matrix4;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderProgram {
	
	int id = 0;
	
	private int flag;
	
	//matrix locations
	private int uniformModelMatrixLocation;
	private int uniformViewMatrixLocation;
	private int uniformProjectionMatrixLocation;
	private int uniformBlendColorLocation;
	
	//temp matrix buffers
	private FloatBuffer modelMatrix, viewMatrix, projectionMatrix;
	
	private ArrayList<Shader> attachedShaders;
	
	public ShaderProgram(){
		id = GL20.glCreateProgram();
		attachedShaders = new ArrayList<Shader>();

		modelMatrix = BufferUtils.createFloatBuffer(16);
		viewMatrix = BufferUtils.createFloatBuffer(16);
		projectionMatrix = BufferUtils.createFloatBuffer(16);
	}
	
	public void attach(Shader shader){
		if(!shader.isCompiled())
			throw new IllegalArgumentException("Shader is not compiled");
		GL20.glAttachShader(id, shader.getId());
		attachedShaders.add(shader);
	}
	
	public void detach(Shader shader){
		GL20.glDetachShader(id, shader.getId());
		attachedShaders.remove(shader);
	}
	
	public void link(){
		GL20.glLinkProgram(id);
		
		uniformModelMatrixLocation = getUniformLocation(ShaderConstants.UNIFORM_MATRIX_MODEL);
		uniformViewMatrixLocation = getUniformLocation(ShaderConstants.UNIFORM_MATRIX_VIEW);
		uniformProjectionMatrixLocation = getUniformLocation(ShaderConstants.UNIFORM_MATRIX_PROJECTION);
		uniformBlendColorLocation = getUniformLocation(ShaderConstants.UNIFORM_BLEND);
	}
	
	public void use(){
		GL20.glUseProgram(id);
	}
	
	public void reset(){
		GL20.glUseProgram(0);
	}
	
	public int getId(){
		return id;
	}
	
	public boolean isLinked(){
		return GL20.glGetProgrami(id, GL20.GL_LINK_STATUS) == GL11.GL_TRUE;
	}
	
	public void bindAttributeLocation(int index, String name){
		GL20.glBindAttribLocation(id, index, name);
	}
	
	public int getUniformLocation(String name){
		return GL20.glGetUniformLocation(id, name);
	}
	
	public void setUniformMatrixModel(Matrix4 matrix){
		Matrix4.toFloatBuffer(matrix, modelMatrix);
		modelMatrix.position(0);
		setUniformMatrix4(uniformModelMatrixLocation, modelMatrix);
	}
	
	public void setUniformMatrixView(Matrix4 matrix){
		Matrix4.toFloatBuffer(matrix, viewMatrix);
		viewMatrix.position(0);
		setUniformMatrix4(uniformViewMatrixLocation, viewMatrix);
	}
	
	public void setUniformMatrixProjection(Matrix4 matrix){
		Matrix4.toFloatBuffer(matrix, projectionMatrix);
		projectionMatrix.position(0);
		setUniformMatrix4(uniformProjectionMatrixLocation, projectionMatrix);
	}
	
	public void setUniformBlend(float r, float g, float b){
		GL20.glUniform4f(uniformBlendColorLocation, r, g, b, 1);
	}
	
	public void setUniformBlend(float r, float g, float b, float a){
		GL20.glUniform4f(uniformBlendColorLocation, r, g, b, a);
	}
	
	public void setUniformVec4(int location, float a, float b, float c, float d){
		GL20.glUniform4f(uniformBlendColorLocation, a,b,c,d);
	}
	
	public void setUniformMatrix4(int location, FloatBuffer matrix){
		GL20.glUniformMatrix4fv(location, true, matrix);
	}
	
	public int getAttribLocation(String name){
		return GL20.glGetAttribLocation(id, name);
	}
	
	@Deprecated
	public void enableVertexAttribArray(int attributeLocation){
		if(attributeLocation < 0)
			return;
		GL20.glEnableVertexAttribArray(attributeLocation);
	}

	@Deprecated
	public void disableVertexAttribArray(int attributeLocation){
		if(attributeLocation < 0)
			return;
		GL20.glDisableVertexAttribArray(attributeLocation);
	}

	@Deprecated
	public void vertexAttribPointer(int attribLocation, int numVertices, int stride, int offset){
		vertexAttribPointer(attribLocation, numVertices, GL11.GL_FLOAT, false, stride, offset);
	}

	@Deprecated
	public void vertexAttribPointer(int attributeLocation, int size, int type, boolean normalized, int stride, long offset){
		GL20.glVertexAttribPointer(attributeLocation, size, type, normalized, stride, offset);
	}
	
	public void dispose(){
		GL20.glDeleteProgram(id);
		for(Shader s : attachedShaders){
			s.dispose();
		}
	}
	
	public int getBatchFlags(){
		return flag;
	}
}