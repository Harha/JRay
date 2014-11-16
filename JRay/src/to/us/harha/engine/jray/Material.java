package to.us.harha.engine.jray;

import to.us.harha.engine.util.math.Vec3f;

public class Material {

	// Material diffuse reflection color
	private Vec3f	m_diffuse;
	// Material specular reflection color
	private Vec3f	m_specular;
	// Material surface roughness factor
	private float	m_roughness;
	// Material surface specular reflectivity factor
	private float	m_fresnel;
	// Material surface microfacet density factor
	private float	m_density;
	// Material surface reflectivity
	private float	m_reflectivity;
	// Material surface refractivity
	private float	m_refractivity;
	// Material surface diffuse reflectivity
	private float	m_reflectivity_diffuse;
	// Material refraction index
	private float	m_n;

	/*
	 * Constructor Material(Vec3f diffuse, Vec3f specular, float roughness, float fresnel, float density);
	 * @param diffuse: Material diffuse reflection color
	 * @param specular: Material specular reflection color
	 * @param roughness: Material surface roughness factor
	 * @param fresnel: Material surface specular reflectivity factor
	 * @param density: Material surface microfacet density factor
	 * @param m_reflectivity: Material surface reflectivity
	 * @param m_refractivity: Material surface refractivity
	 * @param m_reflectivity_diffuse: Material surface diffuse reflectivity
	 * @param n: Material refraction index
	 */
	public Material(Vec3f diffuse, Vec3f specular, float roughness, float fresnel, float density, float reflectivity, float refractivity, float reflectivity_diffuse, float n) {
		m_diffuse = diffuse;
		m_specular = specular;
		m_roughness = roughness;
		m_fresnel = fresnel;
		m_density = density;
		m_reflectivity = reflectivity;
		m_refractivity = refractivity;
		m_reflectivity_diffuse = reflectivity_diffuse;
		m_n = n;
	}

	/**
	 * @return the m_diffuse
	 */
	public Vec3f get_diffuse() {
		return m_diffuse;
	}

	/**
	 * @return the m_specular
	 */
	public Vec3f get_specular() {
		return m_specular;
	}

	/**
	 * @return the m_roughness
	 */
	public float get_roughness() {
		return m_roughness;
	}

	/**
	 * @return the m_fresnel
	 */
	public float get_fresnel() {
		return m_fresnel;
	}

	/**
	 * @return the m_density
	 */
	public float get_density() {
		return m_density;
	}

	/**
	 * @return the m_reflectivity
	 */
	public float get_reflectivity() {
		return m_reflectivity;
	}

	/**
	 * @return the m_refractivity
	 */
	public float get_refractivity() {
		return m_refractivity;
	}

	/**
	 * @return the m_reflectivity_diffuse
	 */
	public float get_reflectivity_diffuse() {
		return m_reflectivity_diffuse;
	}

	/**
	 * @return the m_n
	 */
	public float get_n() {
		return m_n;
	}

	/**
	 * @set param m_diffuse
	 */
	public void set_diffuse(Vec3f m_diffuse) {
		this.m_diffuse = m_diffuse;
	}

	/**
	 * @set param m_specular
	 */
	public void set_specular(Vec3f m_specular) {
		this.m_specular = m_specular;
	}

	/**
	 * @set param m_roughness
	 */
	public void set_roughness(float m_roughness) {
		this.m_roughness = m_roughness;
	}

	/**
	 * @set param m_fresnel
	 */
	public void set_fresnel(float m_fresnel) {
		this.m_fresnel = m_fresnel;
	}

	/**
	 * @set param m_density
	 */
	public void set_density(float m_density) {
		this.m_density = m_density;
	}

	/**
	 * @set param m_reflectivity
	 */
	public void set_reflectivity(float m_reflectivity) {
		this.m_reflectivity = m_reflectivity;
	}

	/**
	 * @set param m_refractivity
	 */
	public void set_refractivity(float m_refractivity) {
		this.m_refractivity = m_refractivity;
	}

	/**
	 * @set param m_n
	 */
	public void set_n(float m_n) {
		this.m_n = m_n;
	}

	/**
	 * @set param m_reflectivity_diffuse
	 */
	public void set_reflectivity_diffuse(float m_reflectivity_diffuse) {
		this.m_reflectivity_diffuse = m_reflectivity_diffuse;
	}

}
