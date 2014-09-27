package to.us.harha.engine.math;

public class Intersection {

	// Intersection position vector in world coordinates
	public Vector3f	pos;
	// Intersection position vector at the back of the object if it has depth
	public Vector3f	pos_back;
	// Intersection normal vector
	public Vector3f	norm;
	// Intersection distance scalar
	public float	dist;
	// Intersection back distance scalar
	public float	dist_back;

	/*
	 * Intersection constructor
	 */
	public Intersection(Vector3f pos, Vector3f norm, float dist, float distBack) {
		this.pos = pos;
		this.norm = norm;
		this.dist = dist;
		this.dist_back = dist;
	}

	/*
	 * Intersection blank constructor
	 */
	public Intersection() {
		pos = new Vector3f();
		norm = new Vector3f();
	}

}
