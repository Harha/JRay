package to.us.harha.engine.math;

import to.us.harha.engine.gfx.RGB;

public abstract class Intersectable {

	// Intersectable object's hue
	protected RGB	hue;
	// Intersectable object's material type
	protected int	material;
	// Intersectable object's material reflectivity
	protected float	reflectivity;
	// Intersectable object's material density
	protected float	density;

	/*
	 * Intersection intersect(Ray ray);
	 * Method to check if a chosen ray has hit the object
	 */
	public abstract Intersection intersect(Ray ray, int type);

	public abstract void translate(float x, float y, float z);

	public abstract Vector3f getPos();

	public abstract RGB getHue();

	public abstract int getMaterial();
	
	public abstract float getReflectivity();

	public abstract float getDensity();

}
