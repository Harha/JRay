package to.us.harha.engine.jray.primitives;

import to.us.harha.engine.Main;
import to.us.harha.engine.jray.Intersection;
import to.us.harha.engine.jray.Material;
import to.us.harha.engine.jray.Ray;
import to.us.harha.engine.util.math.Vec3f;

public class Sphere extends Primitive {

	// Sphere radius
	private float	m_radius;

	/*
	 * Constructor Sphere(Vec3f position, float radius, Material material);
	 * @param position: Sphere coordinate in 3D-space
	 * @param radius: Sphere radius
	 * @param material: Sphere surface material
	 */
	public Sphere(Vec3f position, float radius, Material material) {
		m_position = position;
		m_radius = radius;
		m_material = material;
	}

	@Override
	public Intersection intersect(Ray ray) {
		Vec3f S;
		float dist_sq, b, d, t, t1, t2;

		S = Vec3f.sub(ray.get_position(), m_position);
		dist_sq = Vec3f.dot(S, S);

		if (dist_sq <= m_radius)
			return null;

		b = Vec3f.dot(Vec3f.negate(S), ray.get_direction());
		d = (b * b) - dist_sq + (m_radius * m_radius);

		if (d < 0.0f)
			return null;

		d = (float) Math.sqrt(d);
		t1 = b - d;
		t2 = b + d;

		if (t1 > Main.EPSILON && t2 > Main.EPSILON)
			t = t1;
		else if (t1 < 0.0f && t2 < Main.EPSILON)
			return null;
		else if (t1 < 0.0f && t2 > Main.EPSILON)
			t = t2;
		else if (t1 == t2)
			t = t1;
		else
			return null;

		/*
		if (t1 > Main.EPSILON && t2 > Main.EPSILON)
			t = t1;
		else if (t1 == t2)
			t = t1;
		else if (t1 <= 0.0f && t2 >= Main.EPSILON)
			t = t2;
		else if (t1 < 0.0f && t2 < 0.0f)
			return null;
		else
			return null;
		*/

		Intersection x = new Intersection();

		x.set_position(Vec3f.add(Vec3f.scale(ray.get_direction(), t), ray.get_position()));
		x.set_normal(Vec3f.divide(Vec3f.sub(x.get_position(), m_position), m_radius));
		x.set_t(t);
		x.set_material(m_material);
		x.set_primitive(this);

		return x;
	}

}
