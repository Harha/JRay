package to.us.harha.engine.math;

import to.us.harha.engine.Camera;

public class Ray {

	// Ray position vector in the world
	public Vector3f	pos;
	// Normalized ray direction vector
	public Vector3f	dir;

	/*
	 * Ray constructor
	 */
	public Ray(Vector3f pos, Vector3f dir) {
		this.pos = pos;
		this.dir = dir._unitV();
	}

	/*
	 * Blank ray constructor
	 */
	public Ray() {
		this.pos = new Vector3f();
		this.dir = new Vector3f();
	}

	/*
	 * Ray primaryCast method
	 * Used to calculate the initial direction when shooting they ray from the eye
	 */
	public void primaryCast(int x, int y, int w, int h, Camera camera) {
		pos = camera.pos;
		float x_norm = (x - w / 2.0f) / w;
		float y_norm = (h / 2.0f - y) / h;
		Vector3f ray_direction = camera.right._scale(x_norm)._add(camera.up._scale(y_norm))._add(pos)._add(camera.view);
		dir = ray_direction._sub(pos)._unitV();
	}

	public String toString() {
		return "Ray - POS: " + pos.toString() + " | DIR: " + dir.toString();
	}

}
