package net.apryx.graphics.texture;

public class TextureRegion extends Texture{
	
	private float xCoordStart, yCoordStart, xCoordEnd, yCoordEnd;
	
	public TextureRegion(Texture texture, int xStart, int yStart, int width, int height){
		super(texture.getId());
		
		this.width = width;
		this.height = height;
		
		//TODO get the right xStart and stuff
		xCoordStart = xStart / ((float)texture.getWidth());
		yCoordStart = yStart / ((float)texture.getHeight());
		xCoordEnd = (xStart + width) / ((float)texture.getWidth());
		yCoordEnd = (yStart + height) / ((float)texture.getHeight());
	}
	
	public float getTexCoordX() {
		return xCoordStart;
	}
	
	public float getTexCoordY() {
		return yCoordStart;
	}
	
	public float getTexCoordX2() {
		return xCoordEnd;
	}
	
	public float getTexCoordY2() {
		return yCoordEnd;
	}
}
