package to.us.harha.engine.jray.primitives;

import to.us.harha.engine.jray.Intersection;
import to.us.harha.engine.jray.Material;
import to.us.harha.engine.jray.Ray;
import to.us.harha.engine.util.math.Vec3f;

public abstract class Primitive {

	// Primitive coordinate vector
	protected Vec3f		m_position;
	// Primitive surface material information
	protected Material	m_material;

	public abstract Intersection intersect(Ray ray);

	/**
	 * @return the m_position
	 */
	public Vec3f get_position() {
		return m_position;
	}

	/**
	 * @return the m_material
	 */
	public Material get_material() {
		return m_material;
	}

	/**
	 * @set param m_position
	 */
	public void set_position(Vec3f m_position) {
		this.m_position = m_position;
	}

	/**
	 * @set param m_material
	 */
	public void set_material(Material m_material) {
		this.m_material = m_material;
	}

}
