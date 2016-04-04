package net.apryx.graphics.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;

public class TextureLoader {
	
	public TextureLoader(){
	}
	
	public static Texture loadTexture(File file){
		try{
			BufferedImage image = ImageIO.read(file);
			
			ByteBuffer bb = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
			
			for(int y = 0; y < image.getHeight(); y++){
				for(int x = 0; x < image.getWidth(); x++){
					int argb = image.getRGB(x, y);
					
					byte r = (byte) ((argb >> 16) & 0xFF);
	                byte g = (byte) ((argb >> 8) & 0xFF);
	                byte b = (byte) ((argb) & 0xFF);
	                byte a = (byte) ((argb >> 24) & 0xFF);
	                 
	                bb.put(r).put(g).put(b).put(a);
				}
			}
			
			bb.flip();

			Texture texture = new Texture();
			texture.bind();
			texture.texParameter(Texture.WRAP_S, Texture.CLAMP);
			texture.texParameter(Texture.WRAP_T, Texture.CLAMP);
			texture.texParameter(Texture.MIN_FILTER, Texture.NEAREST);
			texture.texParameter(Texture.MAG_FILTER, Texture.NEAREST);
			texture.setData(image.getWidth(), image.getHeight(), bb, Texture.RGBA);
			texture.unbind();
			
			return texture;
		}catch(Exception e){
			System.err.println("Error reading : " + file.getAbsolutePath());
			e.printStackTrace();
			return null;
		}
	}
	
	public static Texture loadTexture(int width, int height, int colorDepth){
		Texture texture = new Texture();
		texture.bind();
		
		texture.texParameter(Texture.WRAP_S, Texture.CLAMP);
		texture.texParameter(Texture.WRAP_T, Texture.CLAMP);
		texture.texParameter(Texture.MIN_FILTER, Texture.NEAREST);
		texture.texParameter(Texture.MAG_FILTER, Texture.NEAREST);
		
		texture.setData(width, height, BufferUtils.createFloatBuffer(width * height * 3), Texture.RGB);

		texture.unbind();
		return texture;
	}
	
	public static Texture loadTexture(int width, int height, FloatBuffer colors, int colorDepth){
		Texture texture = new Texture();
		texture.bind();
		
		texture.texParameter(Texture.WRAP_S, Texture.CLAMP);
		texture.texParameter(Texture.WRAP_T, Texture.CLAMP);
		texture.texParameter(Texture.MIN_FILTER, Texture.NEAREST);
		texture.texParameter(Texture.MAG_FILTER, Texture.NEAREST);
		
		texture.setData(width, height, colors, colorDepth);
		texture.unbind();
		
		return texture;
	}
	
	public static Texture createDefaultTexture(){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
		buffer.put(1).put(1).put(1).put(1).flip();
		return loadTexture(1,1,buffer, Texture.RGBA);
	}
	
}