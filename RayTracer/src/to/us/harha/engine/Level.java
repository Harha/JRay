package to.us.harha.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import to.us.harha.engine.gfx.RGBA;
import to.us.harha.engine.math.Intersectable;
import to.us.harha.engine.math.Light;
import to.us.harha.engine.math.Plane;
import to.us.harha.engine.math.Sphere;
import to.us.harha.engine.math.Triangle;
import to.us.harha.engine.math.Vector3f;

public class Level {

	// m_intersectables contains all Intersectable objects in the current loaded level regardless of their type
	private ArrayList<Intersectable>	m_intersectables;
	// m_lights contains all Light objects in the current loaded level
	private ArrayList<Light>			m_lights;
	// m_light_ambient contains the level ambient light value
	private RGBA						m_light_ambient;
	// m_player_spawn contains player spawn location
	private Vector3f					m_player_spawn;

	/*
	 * Level(String path)
	 * Level.java main constructor
	 * Loads the level from the input path into memory
	 */
	public Level(String path_1, String path_2) {
		m_intersectables = new ArrayList<Intersectable>();
		m_lights = new ArrayList<Light>();
		loadLevelFromFile(path_1);
		loadObjFromFile(path_2);
	}

	/*
	 * Level.java main update method
	 */
	public void update(float delta) {

	}

	/*
	 * loadLevelFromFile(String path)
	 * Loads a level from the input path
	 * Level file structure:
	 * plane	xp yp zp xn yn zn r g b a type_1 type_2
	 * sphere	xp yp zp r g b a r type
	 * triangle vx1 vy1 vz1 vx2 vy2 vz2 vx3 vy3 vz3 r g b a type_1 type_2
	 * light	xp yp zp r g b a intensity
	 * ambient	r g b a
	 * spawn	xp yp zp
	 */
	public void loadLevelFromFile(String path) {
		String workingDir = System.getProperty("user.dir");
		String finalPath = workingDir + "\\" + path;
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(finalPath));
			long startTime = System.nanoTime();
			while ((line = reader.readLine()) != null) {
				String[] values = line.trim().split(" ");
				if (line.startsWith("plane ")) {
					Vector3f plane_p = new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
					Vector3f plane_n = new Vector3f(Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6]));
					RGBA plane_c = new RGBA(Float.parseFloat(values[7]), Float.parseFloat(values[8]), Float.parseFloat(values[9]), Float.parseFloat(values[10]));
					int plane_t1 = Integer.parseInt(values[11]);
					int plane_t2 = Integer.parseInt(values[12]);
					m_intersectables.add(new Plane(plane_p, plane_n, plane_c, plane_t1, plane_t2));
				} else if (line.startsWith("sphere ")) {
					Vector3f sphere_p = new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
					RGBA sphere_c = new RGBA(Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6]), Float.parseFloat(values[7]));
					float sphere_r = Float.parseFloat(values[8]);
					int sphere_t = Integer.parseInt(values[9]);
					m_intersectables.add(new Sphere(sphere_p, sphere_c, sphere_r, sphere_t));
				} else if (line.startsWith("light ")) {
					Vector3f light_p = new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
					RGBA light_c = new RGBA(Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6]), Float.parseFloat(values[7]));
					float light_i = Float.parseFloat(values[8]);
					m_lights.add(new Light(light_p, light_c, light_i));
				} else if (line.startsWith("ambient ")) {
					RGBA ambient_c = new RGBA(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]));
					m_light_ambient = ambient_c;
				} else if (line.startsWith("spawn ")) {
					Vector3f spawn_p = new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
					m_player_spawn = spawn_p;
				}
			}
			reader.close();
			long endTime = System.nanoTime();
			System.out.println("Map: " + finalPath + " loaded successfully!");
			System.out.println("Total time taken to load the map: " + (endTime - startTime) / 1000000000.0 + "seconds");
		} catch (Exception e) {
			System.err.println("Error loading the level " + finalPath);
			e.printStackTrace();
		}
	}

	/*
	 * .obj File structure
	 * v = vertex
	 * f = face
	 * 
	 * vertex xyz @ face pointer id can be found with this calculation from a 1-dimensional array:
	 * (x or y or z, in other words 0 or 1 or 2) + f * 3 = Vertex line coordinate
	 * so 1 + 2 * 3 would get the y coordinate of vertex found @ face id of 2
	 * 
	 * I just wrote that down so that I don't forget now that I worked it out by myself. :D Quite easy though, this took me like 1 hour or so.
	 * Had no previous experience about this. Normal support will be easy to add, but I'll do that tomorrow.
	 */
	public void loadObjFromFile(String path) {
		String workingDir = System.getProperty("user.dir");
		String finalPath = workingDir + "\\" + path;
		String line;

		// .obj file format stuff
		List<Float> vertices = new ArrayList<Float>();
		List<Integer> faces = new ArrayList<Integer>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(finalPath));
			while ((line = reader.readLine()) != null) {
				String[] values = line.trim().split(" ");
				if (line.startsWith("v ")) {
					vertices.add((float) Double.parseDouble(values[1]));
					vertices.add((float) Double.parseDouble(values[2]));
					vertices.add((float) Double.parseDouble(values[3]));
				} else if (line.startsWith("f ")) {
					faces.add(Integer.parseInt(values[1]));
					faces.add(Integer.parseInt(values[2]));
					faces.add(Integer.parseInt(values[3]));
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < faces.size(); i += 3) {
			int index_0 = faces.get(i) - 1;
			int index_1 = faces.get(i + 1) - 1;
			int index_2 = faces.get(i + 2) - 1;
			Vector3f vertex_1 = new Vector3f(vertices.get(0 + index_0 * 3), vertices.get(1 + index_0 * 3), vertices.get(2 + index_0 * 3));
			Vector3f vertex_2 = new Vector3f(vertices.get(0 + index_1 * 3), vertices.get(1 + index_1 * 3), vertices.get(2 + index_1 * 3));
			Vector3f vertex_3 = new Vector3f(vertices.get(0 + index_2 * 3), vertices.get(1 + index_2 * 3), vertices.get(2 + index_2 * 3));
			Vector3f[] verts = new Vector3f[] { vertex_1, vertex_2, vertex_3 };
			m_intersectables.add(new Triangle(verts, new RGBA(1.0f, 1.0f, 1.0f, 1.0f), 0, 0));
			System.out.println(vertex_1 + " " + vertex_2 + " " + vertex_3);
		}

	}

	/*
	 * getIntersectables()
	 * returns m_intersectables
	 */
	public ArrayList<Intersectable> getIntersectables() {
		return m_intersectables;
	}

	/*
	 * getIntersectable(int i)
	 * returns object @ index i from m_intersectables
	 */
	public Intersectable getIntersectable(int i) {
		return m_intersectables.get(i);
	}

	/*
	 * getLights()
	 * returns m_lights
	 */
	public ArrayList<Light> getLights() {
		return m_lights;
	}

	/*
	 * getLights(int i)
	 * returns object @ index i from m_lights
	 */
	public Light getLight(int i) {
		return m_lights.get(i);
	}

	/*
	 * getLightAmbient()
	 * returns m_light_ambient
	 */
	public RGBA getLightAmbient() {
		return m_light_ambient;
	}

	/*
	 * getPlayerSpawn()
	 * returns m_player_spawn
	 */
	public Vector3f getPlayerSpawn() {
		return m_player_spawn;
	}

}
