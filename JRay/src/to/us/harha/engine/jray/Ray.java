package to.us.harha.engine.jray;

import to.us.harha.engine.util.math.Vec3f;

public class Ray {

	// Ray origin coordinate vector
	private Vec3f	m_position;
	// Ray direction vector
	private Vec3f	m_direction;
	// To know wether we are inside a medium or not
	private boolean	m_inside;

	/*
	 * Constructor Ray(Vec3f position, Vec3f direction);
	 * @param position: Ray origin coordinate vector
	 * @param direction: Ray direction vector
	 */
	public Ray(Vec3f position, Vec3f direction) {
		m_position = position;
		m_direction = direction;
		m_inside = false;
	}

	/*
	 * Constructor Ray();
	 * @info: Blank Ray constructor
	 */
	public Ray() {
		m_position = new Vec3f();
		m_direction = new Vec3f();
	}

	/**
	 * @return the m_position
	 */
	public Vec3f get_position() {
		return m_position;
	}

	/**
	 * @return the m_direction
	 */
	public Vec3f get_direction() {
		return m_direction;
	}

	/**
	 * @return the m_inside
	 */
	public boolean get_inside() {
		return m_inside;
	}

	/**
	 * @set param m_position
	 */
	public void set_position(Vec3f m_position) {
		this.m_position = m_position;
	}

	/**
	 * @set param m_direction
	 */
	public void set_direction(Vec3f m_direction) {
		this.m_direction = m_direction;
	}

	/**
	 * @set param m_inside
	 */
	public void set_inside(boolean m_inside) {
		this.m_inside = m_inside;
	}

}
