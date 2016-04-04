package net.apryx.graphics.shader;

public class ShaderConstants {
	public static String defaultVertex = "#version 150\n"+
			"\n"+
			"//get this from somewhere over the rainbow ~\n"+
			"in vec3 position;\n"+
			"in vec4 color;\n"+
			"in vec2 uv;\n"+
			"\n"+
			"//pass this to the fragment shader\n"+
			"out vec4 v_Position;\n"+
			"out vec4 v_Color;\n"+
			"out vec2 v_Uv;\n"+
			"\n"+
			"uniform mat4 u_MatrixView;\n"+
			"\n"+
			"void main()\n"+
			"{\n"+
			" v_Uv = uv;\n"+
			" v_Color = color;\n"+
			" v_Position = u_MatrixView * vec4(position, 1.0);\n"+
			" gl_Position = v_Position;\n"+
			"}";
	
	public static String defaultFragment = "#version 150\n"+
			"\n"+
			"uniform sampler2D sampler;\n"+
			"\n"+
			"in vec4 v_Position;\n"+
			"in vec4 v_Color;\n"+
			"in vec2 v_Uv;\n"+
			"\n"+
			"void main()\n"+
			"{\n"+
			" gl_FragColor = v_Color * texture(sampler, v_Uv);\n"+
			"}";;
	
	public static final int POSITION_INDEX = 0;
	public static final int COLOR_INDEX = 1;
	public static final int UV_INDEX = 2;
	public static final int NORMAL_INDEX = 3;
	
	public static final String POSITION_ATTR = "position";
	public static final String COLOR_ATTR = "color";
	public static final String UV_ATTR = "uv";
	public static final String NORMAL_ATTR = "normal";
	
	public static final String UNIFORM_MATRIX_MODEL = "u_MatrixModel";
	public static final String UNIFORM_MATRIX_VIEW = "u_MatrixView";
	public static final String UNIFORM_MATRIX_PROJECTION = "u_MatrixProjection";
	public static final String UNIFORM_BLEND = "u_Blend";
}
