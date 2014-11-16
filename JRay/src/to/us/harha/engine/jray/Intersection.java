package to.us.harha.engine.jray;

import to.us.harha.engine.jray.primitives.Primitive;
import to.us.harha.engine.util.math.Vec3f;

public class Intersection {

	// Intersection incident coordinate
	private Vec3f		m_position;
	// Intersection incident surface normal
	private Vec3f		m_normal;
	// Intersection distance from ray origin
	private float		m_t;
	// Intersection surface material
	private Material	m_material;
	// Intersection object
	private Primitive	m_primitive;

	/*
	 * Constructor Intersection(Vec3f position, Vec3f normal);
	 * @param position: Intersection incident coordinate
	 * @param normal: Intersection incident surface normal
	 */
	public Intersection(Vec3f position, Vec3f normal, float t, Material material, Primitive primitive) {
		m_position = position;
		m_normal = normal;
		m_t = t;
		m_material = material;
		m_primitive = primitive;
	}

	/*
	 * Constructor Intersection();
	 * @info: Blank constructor
	 */
	public Intersection() {
		m_position = new Vec3f();
		m_normal = new Vec3f();
	}

	/**
	 * @return the m_position
	 */
	public Vec3f get_position() {
		return m_position;
	}

	/**
	 * @return the m_normal
	 */
	public Vec3f get_normal() {
		return m_normal;
	}

	/**
	 * @return the m_t
	 */
	public float get_t() {
		return m_t;
	}

	/**
	 * @return the m_material
	 */
	public Material get_material() {
		return m_material;
	}

	/**
	 * @return the m_primitive
	 */
	public Primitive get_primitive() {
		return m_primitive;
	}

	/**
	 * @pset aram m_position
	 */
	public void set_position(Vec3f m_position) {
		this.m_position = m_position;
	}

	/**
	 * @set param m_normal
	 */
	public void set_normal(Vec3f m_normal) {
		this.m_normal = m_normal;
	}

	/**
	 * @set param m_t
	 */
	public void set_t(float m_t) {
		this.m_t = m_t;
	}

	/**
	 * @set param m_material
	 */
	public void set_material(Material m_material) {
		this.m_material = m_material;
	}

	/**
	 * @set param m_primitive
	 */
	public void set_primitive(Primitive m_primitive) {
		this.m_primitive = m_primitive;
	}

}
