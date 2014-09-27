package to.us.harha.engine.math;

import to.us.harha.engine.gfx.RGBA;

public class Sphere extends Intersectable {

	// Sphere position vector in the world
	public Vector3f	pos;
	// Sphere radius
	public float	r;

	/*
	 * Sphere constructor
	 */
	public Sphere(Vector3f pos, RGBA hue, float r, int type_1) {
		this.pos = pos;
		this.r = r;
		this.hue = hue;
		this.type_1 = type_1;
		this.density = 1.52f;
	}

	/*
	 * Ray -> Sphere intersection check method
	 */
	public Intersection intersect(Ray ray, int type) {
		Vector3f S;
		float squaredDistance;
		float b, d, t1, t2;

		S = ray.pos._sub(pos);
		squaredDistance = S.dotP(S);
		if (squaredDistance <= r)
			return null;
		b = -S.dotP(ray.dir);
		d = b * b - S.dotP(S) + r * r;
		if (d < 0.0f)
			return null;
		d = (float) Math.sqrt(d);
		t1 = b - d;
		t2 = b + d;
		if (t1 <= 1e-3)
			return null;
		Intersection x = new Intersection();
		x.pos = ray.pos._add(ray.dir._scale(t1));
		x.pos_back = ray.pos._add(ray.dir._scale(t2));
		x.norm = x.pos._sub(pos)._unitV();
		x.dist = t1;
		x.dist_back = t2;
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

	public RGBA getHue() {
		return hue;
	}

	public int getType_1() {
		return type_1;
	}

	public int getType_2() {
		return type_2;
	}

	public float getDensity() {
		return density;
	}

}
