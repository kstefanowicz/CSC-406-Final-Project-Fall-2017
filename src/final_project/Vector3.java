package final_project;

public class Vector3 {
	float x, y, z;
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getMagnitude() {
		return (float) Math.sqrt((double) (x*x) + (y*y) + (z*z));
	}
	
	public Vector3 normalize() {
		float magnitude = getMagnitude();
		return new Vector3(x/magnitude, y/magnitude, z/magnitude);
	}
	
	public Vector3 multiply(float scalar) {
		return new Vector3(x * scalar, y * scalar, z * scalar);
	}
	
	public Vector3 minus(Vector3 other) {
		return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
	}
	
	public Vector3 plus(Vector3 other) {
		return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
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
	
	public float dot(Vector3 other) {
		return (x*other.getX()) + (y*other.getY()) + (z*other.getZ());
	}
	
	public float distance(Vector3 other) {
		float xDiff = x-other.getX();
		float yDiff = y-other.getY();
		float zDiff = z-other.getZ();
		return (float) Math.sqrt((double)((xDiff)*(xDiff) + (yDiff)*(yDiff) + (zDiff)*(zDiff)));
	}
}
