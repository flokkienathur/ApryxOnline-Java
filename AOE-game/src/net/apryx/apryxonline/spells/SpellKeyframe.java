package net.apryx.apryxonline.spells;

public class SpellKeyframe {
	
	private float x,y,z;
	private float xOffset, yOffset, zOffset;
	private float time;
	private boolean unmoveable = true;
	
	public SpellKeyframe(float time){
		this.time = time;
	}
	
	public SpellKeyframe setUnmoveable(boolean unmoveable) {
		this.unmoveable = unmoveable;
		return this;
	}
	
	public SpellKeyframe setPosition(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public SpellKeyframe setOffset(float x, float y, float z){
		this.xOffset = x;
		this.yOffset = y;
		this.zOffset = z;
		return this;
	}
	
	public boolean isUnmoveable() {
		return unmoveable;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	public float getTime() {
		return time;
	}
	
	public float getxOffset() {
		return xOffset;
	}
	
	public float getyOffset() {
		return yOffset;
	}
	
	public float getzOffset() {
		return zOffset;
	}
}
