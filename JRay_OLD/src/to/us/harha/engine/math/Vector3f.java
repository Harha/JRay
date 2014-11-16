package to.us.harha.engine.math;

public class Vector3f {

	public float	x;
	public float	y;
	public float	z;

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}

	public void add(Vector3f v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
	}

	public Vector3f _add(Vector3f v) {
		return new Vector3f(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector3f _add(float f) {
		return new Vector3f(x + f, y + f, z + f);
	}

	public void sub(Vector3f v) {
		this.x -= v.x;
		this.y -= v.y;
		this.z -= v.z;
	}

	public Vector3f _sub(Vector3f v) {
		return new Vector3f(x - v.x, y - v.y, z - v.z);
	}

	public Vector3f _sub(float f) {
		return new Vector3f(x - f, y - f, z - f);
	}

	public void scale(float f) {
		this.x *= f;
		this.y *= f;
		this.z *= f;
	}

	public Vector3f _scale(float f) {
		return new Vector3f(x * f, y * f, z * f);
	}

	public Vector3f _divide(Vector3f v) {
		return new Vector3f(x / v.x, y / v.y, z / v.z);
	}
	
	public Vector3f cross(Vector3f v) {
		return new Vector3f(y * v.z - v.y * z, z * v.x - v.z * x, x * v.y - v.x * y);
	}

	public void unitV() {
		x /= length();
		y /= length();
		z /= length();
	}

	public Vector3f _unitV() {
		return new Vector3f(x / length(), y / length(), z / length());
	}

	public void negate() {
		x = -x;
		y = -y;
		z = -z;
	}

	public Vector3f _negate() {
		return new Vector3f(-x, -y, -z);
	}

	public float length() {
		return (float) (Math.sqrt(x * x + y * y + z * z));
	}

	public float dotP(Vector3f v) {
		return x * v.x + y * v.y + z * v.z;
	}

	public String toString() {
		return "x: " + x + " y:" + y + " z:" + z;
	}

}
