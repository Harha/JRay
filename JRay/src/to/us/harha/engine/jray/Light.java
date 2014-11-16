package to.us.harha.engine.jray;

import to.us.harha.engine.util.math.Vec3f;

public abstract class Light {

	// Light source's position vector
	protected Vec3f	m_position;
	// Light source's color
	protected Vec3f	m_color;
	// Light source's intensity
	protected float	m_intensity;
	// Light source type
	protected int	m_type;
	// Spherical light source radius
	protected float	m_radius;

	/**
	 * @return the m_position
	 */
	public Vec3f get_position() {
		return m_position;
	}

	/**
	 * @return the m_color
	 */
	public Vec3f get_color() {
		return m_color;
	}

	/**
	 * @return the m_intensity
	 */
	public float get_intensity() {
		return m_intensity;
	}

	/**
	 * @return the m_type
	 */
	public int get_type() {
		return m_type;
	}

	/**
	 * @return the m_radius
	 */
	public float get_radius() {
		return m_radius;
	}

	/**
	 * @set param m_position
	 */
	public void set_position(Vec3f m_position) {
		this.m_position = m_position;
	}

	/**
	 * @set param m_color
	 */
	public void set_color(Vec3f m_color) {
		this.m_color = m_color;
	}

	/**
	 * @set param m_intensity
	 */
	public void set_intensity(float m_intensity) {
		this.m_intensity = m_intensity;
	}

	/**
	 * @pset aram m_type
	 */
	public void set_type(int m_type) {
		this.m_type = m_type;
	}

	/**
	 * @set param m_radius
	 */
	public void set_radius(float m_radius) {
		this.m_radius = m_radius;
	}

}
