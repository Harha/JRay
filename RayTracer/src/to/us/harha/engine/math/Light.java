package to.us.harha.engine.math;

import to.us.harha.engine.gfx.RGBA;

public class Light {

	// Light position vector in the world
	public Vector3f	pos;
	// Light color
	public RGBA	col;
	// Light intesnity scalar value
	public float	intensity;

	/*
	 * Light constructor
	 */
	public Light(Vector3f pos, RGBA col, float intensity) {
		this.pos = pos;
		this.col = col;
		this.intensity = intensity;
	}

	public void translate(float x, float y, float z) {
		pos.x = x;
		pos.y = y;
		pos.z = z;
	}

}
