package to.us.harha.engine.jray.primitives;

import to.us.harha.engine.Main;
import to.us.harha.engine.jray.Intersection;
import to.us.harha.engine.jray.Material;
import to.us.harha.engine.jray.Ray;
import to.us.harha.engine.util.math.Vec3f;

public class Plane extends Primitive {

	// Plane surface normal vector
	private Vec3f	m_normal;

	/*
	 * Constructor Plane(Vec3f position, Vec3f normal, Material material);
	 * @param position: Plane coordinate in 3D-space
	 * @param normal: Plane surface normal vector
	 * @param material: Plane surface material
	 */
	public Plane(Vec3f position, Vec3f normal, Material material) {
		m_position = position;
		m_normal = normal;
		m_material = material;
	}

	@Override
	public Intersection intersect(Ray ray) {
		Vec3f P;
		float d, t;

		P = Vec3f.sub(m_position, ray.get_position());
		d = Vec3f.dot(m_normal, ray.get_direction());
		t = Vec3f.dot(P, m_normal) / d;

		if (t < Main.EPSILON)
			return null;

		Intersection x = new Intersection();

		x.set_position(Vec3f.add(Vec3f.scale(ray.get_direction(), t), ray.get_position()));
		x.set_normal(Vec3f.normalize(m_normal));
		x.set_t(t);
		x.set_material(m_material);
		x.set_primitive(this);

		return x;
	}

}
