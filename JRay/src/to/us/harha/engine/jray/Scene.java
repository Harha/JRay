package to.us.harha.engine.jray;

import java.util.ArrayList;
import java.util.List;

import to.us.harha.engine.jray.primitives.Primitive;
import to.us.harha.engine.util.math.Vec3f;

public class Scene {

	// Contains all the intersectable objects in the scene
	private List<Primitive>	m_primitives;
	private List<Light>		m_lights;
	private Vec3f			m_light_ambient;

	/*
	 * Constructor Scene(List<Primitive> primitives);
	 * @param primitives: Contains all the intersectable objects in the scene
	 */
	public Scene(List<Primitive> primitives, List<Light> lights) {
		m_primitives = new ArrayList<Primitive>(primitives);
		m_lights = new ArrayList<Light>(lights);
		m_light_ambient = new Vec3f(0.025f);
	}

	/**
	 * @return the m_primitives
	 */
	public List<Primitive> get_primitives() {
		return m_primitives;
	}

	/**
	 * @return the m_lights
	 */
	public List<Light> get_lights() {
		return m_lights;
	}

	/**
	 * @return the m_light_ambient
	 */
	public Vec3f get_light_ambient() {
		return m_light_ambient;
	}

	/**
	 * @set param m_primitives
	 */
	public void set_primitives(List<Primitive> m_primitives) {
		this.m_primitives = m_primitives;
	}

	/**
	 * @set param m_lights
	 */
	public void set_lights(List<Light> m_lights) {
		this.m_lights = m_lights;
	}

	/**
	 * @set param m_light_ambient
	 */
	public void set_light_ambient(Vec3f m_light_ambient) {
		this.m_light_ambient = m_light_ambient;
	}

}
