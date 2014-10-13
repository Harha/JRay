package to.us.harha.engine.math;

import to.us.harha.engine.gfx.RGB;

public class Light {

	// Light position vector in the world
	public Vector3f	pos;
	// Light diffuse color
	public RGB		col_diff;
	// Light specular color
	public RGB		col_spec;
	// Light diffuse intensity scalar value
	public float	intensity_diff;
	// Light specular intensity scalar value
	public float	intensity_spec;

	/*
	 * Light constructor
	 */
	public Light(Vector3f pos, RGB col_diff, RGB col_spec, float intensity_diff, float intensity_spec) {
		this.pos = pos;
		this.col_diff = col_diff;
		this.col_spec = col_spec;
		this.intensity_diff = intensity_diff;
		this.intensity_spec = intensity_spec;
	}

	public void translate(float x, float y, float z) {
		pos.x = x;
		pos.y = y;
		pos.z = z;
	}

}
