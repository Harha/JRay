package to.us.harha.engine.math;

public class MathUtils {

	public MathUtils() {

	}

	/*
	 * XY Plane rotation matrix.
	 */
	public static Vector3f rotateXY(Vector3f v, Vector3f e, float angle) {
		final float theta = (float) Math.toRadians(angle);
		final float sin = (float) Math.sin(theta);
		final float cos = (float) Math.cos(theta);
		Vector3f p = new Vector3f();
		v.sub(e);
		p.x = v.x * cos + v.y * -sin;
		p.y = v.x * sin + v.y * cos;
		p.z = v.z;
		p.add(e);
		return p;
	}

	/*
	 * YZ Plane rotation matrix.
	 */
	public static Vector3f rotateYZ(Vector3f v, Vector3f e, float angle) {
		final float theta = (float) Math.toRadians(angle);
		final float sin = (float) Math.sin(theta);
		final float cos = (float) Math.cos(theta);
		Vector3f p = new Vector3f();
		v.sub(e);
		p.x = v.x;
		p.y = v.y * cos + v.z * sin;
		p.z = v.y * -sin + v.z * cos;
		p.add(e);
		return p;
	}

	/*
	 * XZ Plane rotation matrix.
	 */
	public static Vector3f rotateXZ(Vector3f v, Vector3f e, float angle) {
		final float theta = (float) Math.toRadians(angle);
		final float sin = (float) Math.sin(theta);
		final float cos = (float) Math.cos(theta);
		Vector3f p = new Vector3f();
		v.sub(e);
		p.x = v.x * cos + v.z * -sin;
		p.y = v.y;
		p.z = v.x * sin + v.z * cos;
		p.add(e);
		return p;
	}

	/*
	 * clampf
	 * Clamp a float value between two given values.
	 */
	public static float clampf(float f, float min, float max) {
		return Math.max(min, Math.min(f, max));
	}

	/*
	 * interpolatef
	 * Interpolate a float value between two values.
	 */
	public static float interpolatef(float min, float max, float gradient) {
		return min + (max - min) * clampf(gradient, 0.0f, 1.0f);
	}

}
