package net.apryx.math;

public class Vector2 {

	public static Vector2 one = new Vector2(1,1);
	public static Vector2 zero = new Vector2(0,0);
	
	public float x, y;
	
	//contstructing vectors
	public Vector2(){
		x = 0;
		y = 0;
	}
	
	public Vector2(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector2 other){
		this.x = other.x;
		this.y = other.y;
	}

	//adding vectors
	public Vector2 add(Vector2 other){
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vector2 add(float other){
		this.x += other;
		this.y += other;
		return this;
	}
	
	//subtracting vectors
	public Vector2 sub(Vector2 other){
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}
	
	public Vector2 sub(float other){
		this.x -= other;
		this.y -= other;
		return this;
	}
	
	//multiplying vectors
	public Vector2 mul(Vector2 other) {
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}

	public Vector2 mul(float other) {
		this.x *= other;
		this.y *= other;
		return this;
	}
	
	//dividing vectors
	public Vector2 div(Vector2 other) {
		this.x /= other.x;
		this.y /= other.y;
		return this;
	}

	public Vector2 div(float other) {
		this.x /= other;
		this.y /= other;
		return this;
	}
	
	public Vector2 scale(float other){
		return mul(other);
	}
	
	public float sqrLength(){
		return x*x + y*y;
	}
	
	public float length(){
		return Mathf.sqrt(sqrLength());
	}
	
	public boolean equals(Vector2 other){
		return other.x == x && other.y == y;
	}
	
	public Vector2 clone(){
		return new Vector2(this);
	}
	
	public String toString(){
		return "[" + x + ", "+ y+ "]";
	}
	
	public Vector3 toVector3(){
		return new Vector3(x,y,0);
	}

	//STATIC METHODS
	
	//dot product for the two vectors
	public static float dot(Vector2 a, Vector2 b){
		return a.x * b.x + b.y * a.y;
	}
	
	//interpolate vectors.
	public static Vector2 lerp(Vector2 a, Vector2 b, float f){
		return new Vector2(
				Mathf.lerp(a.x, b.x, f),
				Mathf.lerp(a.y, b.y, f)
				);
	}

	public static float angle(Vector2 a){
		return Mathf.toDegrees(Mathf.tanh(a.y/a.x));
	}
	
	public static float angle(Vector2 a, Vector2 b){
		return Mathf.acos(
					dot(a,b) / (a.length() * b.length())
				);
	}
	
	//not the real cross product, its not defined for Vector2 in math.
	//returns a perpendicular vector
	public static Vector2 cross(Vector2 a){
		return new Vector2(a.y,-a.x);
	}
	
}