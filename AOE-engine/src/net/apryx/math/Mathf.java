package net.apryx.math;

public class Mathf {
	
	public static final float PI = (float)Math.PI;
	public static final float EPSILON = 0.001f; //magic numbers ftw
	
	public static float abs(float x) {
		return (float) Math.abs(x);
	}
	
	public static float floor(float x){
		return (int)x;
	}

	public static float sign(float x) {
		if (x > 0)
			return 1;
		if (x < 0)
			return -1;
		else
			return 0;
	}

	public static float lerp(float a, float b, float f) {
		return a + (b - a) * f;
	}

	public static float asin(float x) {
		return (float) Math.asin(x);
	}

	public static float acos(float x) {
		return (float) Math.acos(x);
	}

	public static float atan(float x) {
		return (float) Math.atan(x);
	}

	public static float atan2(float y, float x) {
		return (float) Math.atan2(y, x);
	}

	public static float ceil(float x) {
		return (float) Math.ceil(x);
	}

	public static float cos(float x) {
		return (float) Math.cos(x);
	}

	public static float cosh(float x) {
		return (float) Math.cosh(x);
	}

	public static float sqrt(float x) {
		return (float) Math.sqrt(x);
	}

	public static float sin(float x) {
		return (float) Math.sin(x);
	}

	public static float sinh(float x) {
		return (float) Math.sinh(x);
	}

	public static float tan(float x) {
		return (float) Math.tan(x);
	}

	public static float tanh(float x) {
		return (float) Math.tanh(x);
	}

	public static float toDegrees(float x){
		return (float) Math.toDegrees(x);
	}
	
	public static float toRadians(float x){
		return (float) Math.toRadians(x);
	}
	
	public static float random(){
		return (float) Math.random();
	}

	public static float max(float a, float b){
		return (float) Math.max(a, b);
	}
	
	public static float min(float a, float b){
		return (float) Math.min(a, b);
	}

	public static boolean greaterOrEqual(float a, float b){
		return a - b > -EPSILON;
	}
	public static boolean smallerOrEqual(float a, float b){
		return a - b < EPSILON;
	}
	
	public static boolean equal(float a, float b){
		return abs(a - b) < EPSILON;
	}
}