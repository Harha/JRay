package to.us.harha.engine.jray;

import to.us.harha.engine.util.math.Vec3f;

public class Camera {

	private Vec3f	m_position;
	private Vec3f	m_eye;
	private Vec3f	m_up;
	private Vec3f	m_right;

	public Camera(Vec3f position) {
		m_position = position;
		m_eye = new Vec3f(0.0f, 0.0f, -1.0f);
		m_up = new Vec3f(0.0f, 1.0f, 0.0f);
		m_right = Vec3f.cross(m_eye, m_up);
		m_right = Vec3f.normalize(m_right);
	}

	/**
	 * @return the m_position
	 */
	public Vec3f get_position() {
		return m_position;
	}

	/**
	 * @return the m_eye
	 */
	public Vec3f get_eye() {
		return m_eye;
	}

	/**
	 * @return the m_up
	 */
	public Vec3f get_up() {
		return m_up;
	}

	/**
	 * @return the m_right
	 */
	public Vec3f get_right() {
		return m_right;
	}

}
