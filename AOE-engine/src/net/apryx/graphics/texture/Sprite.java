package net.apryx.graphics.texture;

public class Sprite {
	
	private float width, height, xCenter, yCenter, xOffset, yOffset;
	private Texture texture;
	private boolean straightUp = false;
	
	public Sprite(Texture texture){
		this(texture, texture.getWidth(), texture.getHeight());
	}
	
	public Sprite(Texture texture, float width, float height){
		this(texture, width, height, 0, 0);
	}
	
	public Sprite(Texture texture, float width, float height, float xOffset, float yOffset){
		this.width = width;
		this.height = height;
		this.xCenter = xOffset;
		this.yCenter = yOffset;
		this.setTexture(texture);
	}
	
	public Sprite center(){
		xCenter = getWidth() / 2;
		yCenter = getHeight() / 2;
		return this;
	}

	public float getWidth() {
		return width;
	}

	public Sprite setWidth(float width) {
		this.width = width;
		return this;
	}
	
	public boolean isStraightUp() {
		return straightUp;
	}
	
	public Sprite setStraightUp(boolean straightUp) {
		this.straightUp = straightUp;
		return this;
	}

	public float getHeight() {
		return height;
	}

	public Sprite setHeight(float height) {
		this.height = height;
		return this;
	}

	public float getXCenter() {
		return xCenter;
	}

	public Sprite setXCenter(float xOffset) {
		this.xCenter = xOffset;
		return this;
	}

	public float getYCenter() {
		return yCenter;
	}

	public Sprite setYCenter(float yOffset) {
		this.yCenter = yOffset;
		return this;
	}
	
	public Sprite setCenter(float x, float y){
		this.xCenter = x;
		this.yCenter = y;
		return this;
	}
	public Sprite setOffset(float x, float y){
		this.xOffset = x;
		this.yOffset = y;
		return this;
	}

	public Sprite setXOffset(float xOffset) {
		this.xOffset = xOffset;
		return this;
	}
	
	public Sprite setYOffset(float yOffset) {
		this.yOffset = yOffset;
		return this;
	}

	public float getXOffset() {
		return xOffset;
	}
	
	public float getYOffset() {
		return yOffset;
	}
	
	public Texture getTexture() {
		return texture;
	}

	public Sprite setTexture(Texture texture) {
		this.texture = texture;
		return this;
	}
}