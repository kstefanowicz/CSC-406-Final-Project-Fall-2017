package final_project;

/**
 * Vector3 used for 3d vector math
 * @author devingould
 *
 */
public class Vector3 {
	/**
	 * x y and z values of vector
	 */
	float x, y, z;
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Get the magnitude of the vector
	 * @return magnitude - float
	 */
	public float getMagnitude() {
		return (float) Math.sqrt((double) (x*x) + (y*y) + (z*z));
	}
	
	/**
	 * normalize the vector
	 * @return Vector3 - normalized
	 */
	public Vector3 normalize() {
		float magnitude = getMagnitude();
		return new Vector3(x/magnitude, y/magnitude, z/magnitude);
	}
	
	/**
	 * multiply vector by scalar
	 * @param scalar
	 * @return Vector3
	 */
	public Vector3 multiply(float scalar) {
		return new Vector3(x * scalar, y * scalar, z * scalar);
	}
	
	/**
	 * subtract one vector from other
	 * @param other
	 * @return Vector3
	 */
	public Vector3 minus(Vector3 other) {
		return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
	}
	
	/**
	 * Add Vector3s together
	 * @param other
	 * @return Vector3
	 */
	public Vector3 plus(Vector3 other) {
		return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
	}
	
	/**
	 * get x value
	 * @return x value
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * get y value
	 * @return y value
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * get z value
	 * @return z value
	 */
	public float getZ() {
		return z;
	}
	
	/**
	 * get the dot product of two vectors
	 * @param other
	 * @return float - dot product
	 */
	public float dot(Vector3 other) {
		return (x*other.getX()) + (y*other.getY()) + (z*other.getZ());
	}
	
	/**
	 * get the distance between two vectors
	 * @param other
	 * @return distance
	 */
	public float distance(Vector3 other) {
		float xDiff = x-other.getX();
		float yDiff = y-other.getY();
		float zDiff = z-other.getZ();
		return (float) Math.sqrt((double)((xDiff)*(xDiff) + (yDiff)*(yDiff) + (zDiff)*(zDiff)));
	}
}
