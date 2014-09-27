package to.us.harha.engine;

import to.us.harha.engine.math.Vector3f;

public class Camera {

	// Camera position vector in the world coordinates
	public Vector3f	pos;
	// Camera up vector, to keep it's orientation in control
	public Vector3f	up;
	// Camera right vector = cross(view, up)
	public Vector3f	right;
	// Camera view vector
	public Vector3f	view;
	// Camera eye position vector in the world coordinates
	public Vector3f	eye;
	// Camera yaw
	public float	yaw;
	// Camera pitch
	public float	pitch;
	// Camera movement speed
	public float	speed;

	/*
	 * Camera constructor
	 */
	public Camera(Vector3f pos, Vector3f eye) {
		this.pos = pos;
		this.eye = eye;
		view = new Vector3f();
		up = new Vector3f(0.0f, 1.0f, 0.0f);
		right = new Vector3f();
		yaw = -90.0f;
		pitch = 0.0f;
		speed = 0.01f;
		recalc();
	}

	/*
	 * Camera position/orientation recalculation method
	 */
	public void recalc() {
		// Yaw
		eye.x = (float) Math.cos(Math.toRadians(yaw));
		eye.z = (float) Math.sin(Math.toRadians(yaw));
		// Pitch
		eye.y = (float) Math.sin(Math.toRadians(pitch));
		// Normalize the up vector
		up.unitV();
		// Calculate the view direction vector
		view = eye;
		view.unitV();
		// Calculate the right vector based on up and view vectors
		right = view.cross(up);
		right.unitV();
	}

	public void moveForward(float f) {
		pos.x += f * (float) Math.cos(Math.toRadians(yaw));
		pos.z += f * (float) Math.sin(Math.toRadians(yaw));
	}

	public void moveBackward(float f) {
		pos.x -= f * (float) Math.cos(Math.toRadians(yaw));
		pos.z -= f * (float) Math.sin(Math.toRadians(yaw));
	}

	public void moveLeft(float f) {
		pos.x -= f * (float) Math.cos(Math.toRadians(yaw + 90.0f));
		pos.z -= f * (float) Math.sin(Math.toRadians(yaw + 90.0f));
	}

	public void moveRight(float f) {
		pos.x += f * (float) Math.cos(Math.toRadians(yaw + 90.0f));
		pos.z += f * (float) Math.sin(Math.toRadians(yaw + 90.0f));
	}

}
