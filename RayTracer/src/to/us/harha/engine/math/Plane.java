package to.us.harha.engine.math;

import to.us.harha.engine.gfx.RGB;

public class Plane extends Intersectable {

	// Plane position vector in the world
	public Vector3f	pos;
	// Plane normal vector
	public Vector3f	norm;

	/*
	 * Plane constructor
	 */
	public Plane(Vector3f pos, Vector3f norm, RGB hue, int material, float reflectivity) {
		this.pos = pos;
		this.norm = norm._unitV();
		this.hue = hue;
		this.material = material;
		this.reflectivity = reflectivity;
		this.density = 1.00f;
	}

	/*
	 * Ray -> Plane intersection check method
	 */
	public Intersection intersect(Ray ray, int type) {
		float denom = norm.dotP(ray.dir);
		Vector3f p0l0 = pos._sub(ray.pos);
		float t = p0l0.dotP(norm) / (denom);
		if (t <= 1e-3)
			return null;
		Intersection x = new Intersection();
		x.pos = ray.pos._add(ray.dir._scale(t));
		x.pos_back = x.pos;
		x.norm = norm;
		x.dist = t;
		x.dist_back = t;
		return x;
	}

	public void translate(float x, float y, float z) {
		pos.x += x;
		pos.y += y;
		pos.z += z;
	}

	public Vector3f getPos() {
		return pos;
	}

	public RGB getHue() {
		return hue;
	}

	public int getMaterial() {
		return material;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public float getDensity() {
		return density;
	}

}
