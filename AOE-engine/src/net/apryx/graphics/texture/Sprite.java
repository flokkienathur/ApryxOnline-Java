package net.apryx.graphics.texture;

public class Sprite {
	
	private float width, height, xOffset, yOffset;
	private Texture texture;
	
	public Sprite(Texture texture){
		this(texture, texture.getWidth(), texture.getHeight());
	}
	
	public Sprite(Texture texture, float width, float height){
		this(texture, width, height, 0, 0);
	}
	
	public Sprite(Texture texture, float width, float height, float xOffset, float yOffset){
		this.width = width;
		this.height = height;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.setTexture(texture);
	}
	
	public Sprite center(){
		xOffset = getWidth() / 2;
		yOffset = getHeight() / 2;
		return this;
	}

	public float getWidth() {
		return width;
	}

	public Sprite setWidth(float width) {
		this.width = width;
		return this;
	}

	public float getHeight() {
		return height;
	}

	public Sprite setHeight(float height) {
		this.height = height;
		return this;
	}

	public float getxOffset() {
		return xOffset;
	}

	public Sprite setxOffset(float xOffset) {
		this.xOffset = xOffset;
		return this;
	}

	public float getyOffset() {
		return yOffset;
	}

	public Sprite setyOffset(float yOffset) {
		this.yOffset = yOffset;
		return this;
	}

	public Texture getTexture() {
		return texture;
	}

	public Sprite setTexture(Texture texture) {
		this.texture = texture;
		return this;
	}
}