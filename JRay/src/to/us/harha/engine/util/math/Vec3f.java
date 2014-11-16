package to.us.harha.engine.util.math;

import to.us.harha.engine.Main;

public class Vec3f {

	// Vector x-coordinate
	public float	x;
	// Vector y-coordinate
	public float	y;
	// Vector z-coordinate
	public float	z;

	/*
	 * Constructor Vec3f(float x, float y, float z);
	 * @param x: Vector x-coordinate
	 * @param y: Vector y-coordinate
	 * @param z: Vector z-coordinate
	 */
	public Vec3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/*
	 * Constructor Vec3f(float f);
	 * @@param f: Sets the initial value for all vector components
	 */
	public Vec3f(float f) {
		x = f;
		y = f;
		z = f;
	}

	/*
	 * Constructor Vec3f();
	 * @info: Blank vector constructor, all components are equal to 0.0f
	 */
	public Vec3f() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}

	public static Vec3f add(Vec3f left, Vec3f right) {
		return new Vec3f(left.x + right.x, left.y + right.y, left.z + right.z);
	}

	public static Vec3f add(Vec3f left, float f) {
		return new Vec3f(left.x + f, left.y + f, left.z + f);
	}

	public static Vec3f sub(Vec3f left, Vec3f right) {
		return new Vec3f(left.x - right.x, left.y - right.y, left.z - right.z);
	}

	public static Vec3f scale(Vec3f left, Vec3f right) {
		return new Vec3f(left.x * right.x, left.y * right.y, left.z * right.z);
	}

	public static Vec3f scale(Vec3f v, float f) {
		return new Vec3f(f * v.x, f * v.y, f * v.z);
	}

	public static Vec3f divide(Vec3f left, Vec3f right) {
		return new Vec3f(left.x / right.x, left.y / right.y, left.z / right.z);
	}

	public static Vec3f divide(Vec3f v, float f) {
		return new Vec3f(v.x / f, v.y / f, v.z / f);
	}

	public static Vec3f cross(Vec3f left, Vec3f right) {
		return new Vec3f(left.y * right.z - right.y * left.z, left.z * right.x - right.z * left.x, left.x * right.y - right.x * left.y);
	}

	public static float dot(Vec3f left, Vec3f right) {
		return left.x * right.x + left.y * right.y + left.z * right.z;
	}

	public static Vec3f normalize(Vec3f v) {
		return new Vec3f(v.x / length(v), v.y / length(v), v.z / length(v));
	}

	public static float length(Vec3f v) {
		return (float) Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
	}

	public static Vec3f negate(Vec3f v) {
		return new Vec3f(-v.x, -v.y, -v.z);
	}

	public static Vec3f reflect(Vec3f I, Vec3f N) {
		return sub(I, Vec3f.scale(scale(N, dot(N, I)), 2.0f));
	}

	public static Vec3f refract(Vec3f I, Vec3f N, float index) {
		float NdotI = Vec3f.dot(N, I);
		float n, n1, n2;

		if (NdotI > 0.0f) {
			n1 = 1.0f;
			n2 = index;
		} else {
			n1 = index;
			n2 = 1.0f;
		}

		n = n1 / n2;

		float k = (float) Math.sqrt(1.0f - n * n * (1.0f - NdotI * NdotI));

		if (k < 0.0f) // Total internal reflection
			return reflect(negate(I), N);

		Vec3f temp_a = scale(N, n * NdotI - k);
		Vec3f temp_b = scale(I, n);
		return sub(temp_a, temp_b);
	}

	public static Vec3f randomize(Vec3f v, float f) {
		return new Vec3f((float) (v.x + (f * Math.cos(Math.random()))), (float) (v.y + (f * Math.cos(Math.random()))), (float) (v.z + (f * Math.cos(Math.random()))));
	}

}
