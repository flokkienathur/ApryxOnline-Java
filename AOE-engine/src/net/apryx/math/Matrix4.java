package net.apryx.math;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class Matrix4 {
	
	//actual data
	private float[] data;
	
	//[ 0, 1, 2, 3]
	//[ 4, 5, 6, 7]
	//[ 8, 9,10,11]
	//[12,13,14,15]

	public Matrix4(float[] data){
		this.data = data;
	}
	
	public Matrix4(){
		this.data = new float[16];
		setIdentity();
	}
	
	public void setIdentity(){
		Arrays.fill(this.data, 0);
		setScale(1,1,1);
	}
	
	public void set(int x, int y, float value){
		data[x + y << 2] = value;
	}
	
	public void set(int index, float value){
		data[index] = value;
	}
	
	public void setTranslate(float x, float y, float z){
		data[3] = x;
		data[7] = y;
		data[11] = z;
	}
	
	public void setScale(float sx, float sy, float sz){
		data[0] = sx;
		data[5] = sy;
		data[10] = sz;
		data[15] = 1;
	}
	
	public Matrix4 clone(){
		float[] newData = new float[16];
		System.arraycopy(data, 0, newData, 0, 16);
		return new Matrix4(newData);
	}
	
	public String toString(){
		StringBuilder out = new StringBuilder();
		
		for(int i = 0; i < 4; i++){
			out.append("[");
			out.append(String.format("%.2g", data[i * 4]));
			out.append(", ");
			out.append(String.format("%.2g", data[i * 4 + 1]));
			out.append(", ");
			out.append(String.format("%.2g", data[i * 4 + 2]));
			out.append(", ");
			out.append(String.format("%.2g", data[i * 4 + 3]));
			out.append("]");
			
			if(i != 3)
				out.append("\n");
		}
		
		return out.toString();
	}
	
	public void rotateX(float angle){
		//optimized matrix matrix product with rotation matrix

		//[ 0, 1, 2, 3]
		//[ 4, c,-s, 7]
		//[ 8, s, c,11]
		//[12,13,14,15]
		
		float c = Mathf.cos(angle);
		float s = Mathf.sin(angle);
		
		//prevent data loss
		float d1, d2;
		
		for(int row = 0; row < 4; row++){
			//row start index
			int i = row * 4;
			d1 = data[i + 1];
			d2 = data[i + 2];
			data[i + 1] = d1 * c  + d2 * s;
			data[i + 2] = d1 * -s + d2 * c;
		}
	}
	
	public void rotateY(float angle){
		//optimized matrix matrix product with rotation matrix

		//[ c, 1, s, 3]
		//[ 4, 5, 6, 7]
		//[-s, 9, c,11]
		//[12,13,14,15]
		
		float c = Mathf.cos(angle);
		float s = Mathf.sin(angle);
		
		//prevent data loss
		float d1, d2;
		
		for(int row = 0; row < 4; row++){
			//row start index
			int i = row * 4;
			d1 = data[i];
			d2 = data[i + 2];
			data[i + 0] = d1 * c + d2 * -s;
			data[i + 2] = d1 * s + d2 * c ;
		}
	}
	
	public void rotateZ(float angle){
		//optimized matrix matrix product with rotation matrix
		
		//[ c,-s, 2, 3]
		//[ s, c, 6, 7]
		//[ 8, 9,10,11]
		//[12,13,14,15]
		
		float c = Mathf.cos(angle);
		float s = Mathf.sin(angle);
		
		//prevent data loss
		float d1, d2;
		
		for(int row = 0; row < 4; row++){
			//row start index
			int i = row * 4;
			
			d1 = data[i];
			d2 = data[i + 1];
			
			data[i + 0] = d1 * c + d2 * s;
			data[i + 1] = d1 * -s + d2 * c;
		}
	}

	public void translate(float x, float y, float z){
		//translate this matrix, optimized translation
		for(int row = 0; row < 4; row++){
			//row start index
			int i = row * 4;
			data[i + 3] = data[i] * x + data[i + 1] * y + data[i + 2] * z + data[i + 3];
		}
	}
	

	public void scale(float x, float y, float z){
		//scale this matrix up, optimized multiply
		for(int row = 0; row < 4; row++){
			//row start index
			int i = row * 4;
			data[i + 0] = data[i] * x;
			data[i + 1] = data[i + 1] * y;
			data[i + 2] = data[i + 2] * z;
		}
	}
	
	public static void toFloatBuffer(Matrix4 mat, FloatBuffer outputBuffer){
		outputBuffer.put(mat.data);
	}
	
	public static void multiply(Matrix4 result, Matrix4 a, Matrix4 b){
		multiply(result.data, a.data, b.data);
	}
	
	public static void multiply(float[] result, float[] a, float[] b){
		//full matrix matrix multiplication
		for(int row = 0; row < 4; row++){
			//row start index
			int i = row * 4;
			result[i + 0] = a[i] * b[0] + a[i + 1] * b[4] + a[i + 2] * b[8 ] + a[i + 3] * b[12];
			result[i + 1] = a[i] * b[1] + a[i + 1] * b[5] + a[i + 2] * b[9 ] + a[i + 3] * b[13];
			result[i + 2] = a[i] * b[2] + a[i + 1] * b[6] + a[i + 2] * b[10] + a[i + 3] * b[14];
			result[i + 3] = a[i] * b[3] + a[i + 1] * b[7] + a[i + 2] * b[11] + a[i + 3] * b[15];
		}
	}
	
	public void setOrthagonal(float left, float right, float bottom, float top, float near, float far){
		data[0 * 4 + 0] = 2f / (right - left);
		data[0 * 4 + 3] = -(right + left)/(right - left);

		data[1 * 4 + 1] = 2f / (top - bottom);
		data[1 * 4 + 3] = -(top + bottom)/(top - bottom);
		
		data[2 * 4 + 2] = (-2f) / (far - near);
		data[2 * 4 + 3] = -(far + near) / (far - near);
	}
	
	public static Matrix4 getOrthagonalMatrix(float left, float right, float bottom, float top, float near, float far){
		return new Matrix4(new float[]{
				2f / (right - left), 0, 0, -(right + left)/(right - left),
				0, 2f / (top - bottom), 0, -(top + bottom)/(top - bottom),
				0,0, (-2f) / (far - near), -(far + near) / (far - near),
				0,0,0,1
		});
	}
	
	public void setProjection(float fov, float asp, float n,
            float f){

        float uh = 1f / Mathf.tan(Mathf.toRadians(fov / 2));
        float uw = uh;
        
        data[0 * 4 + 0] = uh/asp;
        data[1 * 4 + 1] = uw;
        data[2 * 4 + 2] = (n+f)/(n-f);
        data[2 * 4 + 3] = (2*n*f)/(n-f);
        data[3 * 4 + 2] = -1;
        data[3 * 4 + 3] = 0;
        
        /*new float[]{
        		uh/asp, 0, 0, 0,
                0, uw, 0, 0,
                0, 0, (n+f)/(n-f), (2*n*f)/(n-f),
                0, 0, -1, 0
                 
        }*/
	}
	
	public static Matrix4 getProjectionMatrix(float fov, float asp, float n,
            float f) {
		
        float uh = 1f / Mathf.tan(Mathf.toRadians(fov / 2));
        float uw = uh;
         
        /*return new Matrix4(new float[]{
                -uh / asp, 0, 0, 0,
                0, uw , 0, 0,
                0 , 0, (f + n) / (f - n), -2.0f * f * n / (f - n),
                0, 0, 1, 0
                 
        });*/
        
        return new Matrix4(new float[]{
        		uh/asp, 0, 0, 0,
                0, uw, 0, 0,
                0, 0, (n+f)/(n-f), (2*n*f)/(n-f),
                0, 0, -1, 0
                 
        });
    }
}