package net.apryx.math;

public class Vector3 {
	
	public static Vector3 zero = new Vector3(0,0,0);
	public static Vector3 one = new Vector3(1,1,1);
	
	public float x, y,z;
	
	//contstructing vectors
	public Vector3(){
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector3 other){
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	//adding vectors
	public Vector3 add(Vector3 other){
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}
	
	public Vector3 add(float other){
		this.x += other;
		this.y += other;
		this.z += other;
		return this;
	}
	
	//subtracting vectors
	public Vector3 sub(Vector3 other){
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}
	
	public Vector3 sub(float other){
		this.x -= other;
		this.y -= other;
		this.z -= other;
		return this;
	}
	
	//multiplying vectors
	public Vector3 mul(Vector3 other) {
		this.x *= other.x;
		this.y *= other.y;
		this.z *= other.z;
		return this;
	}

	public Vector3 mul(float other) {
		this.x *= other;
		this.y *= other;
		this.z *= other;
		return this;
	}
	
	//dividing vectors
	public Vector3 div(Vector3 other) {
		this.x /= other.x;
		this.y /= other.y;
		this.z /= other.z;
		return this;
	}

	public Vector3 div(float other) {
		this.x /= other;
		this.y /= other;
		this.z /= other;
		return this;
	}
	
	public Vector3 scale(float other){
		return mul(other);
	}
	
	public float sqrLength(){
		return x*x + y*y + z*z;
	}
	
	public float length(){
		return Mathf.sqrt(sqrLength());
	}
	
	public boolean equals(Vector3 other){
		return other.x == x && other.y == y && z == other.z;
	}
	
	public Vector3 clone(){
		return new Vector3(this);
	}
	
	public String toString(){
		return "[" + x + ", "+ y+ ", "+z+ "]";
	}
	
	public Vector2 toVector2(){
		return new Vector2(x,y);
	}

	//STATIC METHODS
	
	//dot product for the two vectors
	public static float dot(Vector3 a, Vector3 b){
		return a.x * b.x + b.y * a.y + a.z * b.z;
	}
	
	//interpolate vectors.
	public static Vector3 lerp(Vector3 a, Vector3 b, float f){
		return new Vector3(
				Mathf.lerp(a.x, b.x, f),
				Mathf.lerp(a.y, b.y, f),
				Mathf.lerp(a.z, b.z, f)
				);
	}
	
	public static float angle(Vector3 a, Vector3 b){
		return Mathf.acos(
					dot(a,b) / (a.length() * b.length())
				);
	}
	
	//not the real cross product, its not defined for Vector3 in math.
	//returns a perpendicular vector
	public static Vector3 cross(Vector3 a, Vector3 b){
		return new Vector3(
					a.y*b.z - a.z*b.y,
					a.z*b.x - a.x*b.z,
					a.x*b.y - a.y*b.x
				);
	}
	
}