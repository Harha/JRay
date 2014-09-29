package to.us.harha.engine.math;

import to.us.harha.engine.gfx.RGBA;

public class Triangle extends Intersectable {

	// Triangle position vector in the world
	Vector3f	pos;
	// Triangle normal vector
	Vector3f	norm;
	// Triangle edge vectors
	Vector3f	edge_1;
	Vector3f	edge_2;
	// Triangle vertex vectors
	Vector3f[]	vert;

	/*
	 * Triangle constructor
	 */
	public Triangle(Vector3f[] vert, RGBA hue, int type_1, int type_2) {
		this.vert = vert;
		// Calculate triangle edge vectors and their direction is based on boolean n
		edge_1 = vert[1]._sub(vert[0]);
		edge_2 = vert[2]._sub(vert[0]);
		// calculate triangle normal vector
		norm = edge_1.cross(edge_2)._unitV();
		this.hue = hue;
		this.type_1 = type_1;
		this.type_2 = type_2;
		this.density = 1.25f;
	}

	/*
	 * Ray -> Triangle intersection code
	 */
	public Intersection intersect(Ray ray, int type) {
		Vector3f P, Q, T;
		float d, inv_d, u, v, t;

		P = ray.dir.cross(edge_2);
		// Beging calculating determinant
		d = edge_1.dotP(P);
		// If determinant is near zero, ray lies in the same plane as the triangle
		if (d < 1e-3)
			return null;
		// Final determinant
		inv_d = 1.0f / d;
		// Calculate distance from vert[0] to ray origin
		T = ray.pos._sub(vert[0]);
		// Calculate parameter and test bound
		u = T.dotP(P) * inv_d;
		// If the intersection lies outside the triangle, return null
		if (u < 0.0f || u > 1.0f)
			return null;
		// Prepare to test v parameter
		Q = T.cross(edge_1);
		// Calculate v paramter and test bound
		v = ray.dir.dotP(Q) * inv_d;
		// The intersection lies outside the triangle
		if (v < 0.0f || u + v > 1.0f)
			return null;
		t = edge_2.dotP(Q) * inv_d;
		if (t <= 1e-3)
			return null;
		// Intersection happened, return it
		Intersection x = new Intersection();
		x.pos = ray.pos._add(ray.dir._scale(t));
		x.pos_back = x.pos;
		x.norm = norm;
		x.dist = t;
		x.dist_back = t;
		return x;
	}

	public void translate(float x, float y, float z) {
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
