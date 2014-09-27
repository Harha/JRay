package to.us.harha.engine.math;

public class Vector4f {

	public float	x;
	public float	y;
	public float	z;
	public float	w;

	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector4f() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		w = 0.0f;
	}

	public void add(Vector4f v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		this.w += v.w;
	}

	public void sub(Vector4f v) {
		this.x -= v.x;
		this.y -= v.y;
		this.z -= v.z;
		this.w -= v.w;
	}

	public void scale(float f) {
		this.x *= f;
		this.y *= f;
		this.z *= f;
		this.w *= f;
	}

	public void unitV() {
		x /= length();
		y /= length();
		z /= length();
		w /= length();
	}

	public void negate() {
		x -= x;
		y -= y;
		z -= z;
		w -= w;
	}

	public float length() {
		return (float) (Math.sqrt(x * x + y * y + z * z + w * w));
	}

	public float dotP(Vector4f v) {
		return (x * v.x) + (y * v.y) + (z * v.z) + (w * v.w);
	}

}
