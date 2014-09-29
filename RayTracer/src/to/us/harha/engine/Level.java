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
import to.us.harha.engine.math.Vector3f;

public class Level {

	// m_intersectables contains all Intersectable objects in the current loaded level regardless of their type
	private List<Intersectable>	m_intersectables;
	// m_lights contains all Light objects in the current loaded level
	private List<Light>			m_lights;
	// m_models_obj contains all models in the chosen level
	private List<Model>			m_models_obj;
	// m_light_ambient contains the level ambient light value
	private RGBA				m_light_ambient;
	// m_player_spawn contains player spawn location
	private Vector3f			m_player_spawn;

	/*
	 * Level(String path)
	 * Level.java main constructor
	 * Loads the level from the input path into memory
	 */
	public Level(String path_1) {
		m_intersectables = new ArrayList<Intersectable>();
		m_lights = new ArrayList<Light>();
		m_models_obj = new ArrayList<Model>();
		loadLevelFromFile(path_1);
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
		long startTime = System.nanoTime();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(finalPath));
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
				} else if (line.startsWith("model_obj ")) {
					String model_obj_path = values[1];
					Vector3f model_obj_p = new Vector3f(Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]));
					float model_obj_s = Float.parseFloat(values[5]);
					int model_obj_t1 = Integer.parseInt(values[6]);
					m_models_obj.add(new Model(model_obj_path, model_obj_p, model_obj_s, model_obj_t1));
				} else if (line.startsWith("ambient ")) {
					RGBA ambient_c = new RGBA(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]));
					m_light_ambient = ambient_c;
				} else if (line.startsWith("spawn ")) {
					Vector3f spawn_p = new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
					m_player_spawn = spawn_p;
				}
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Error loading the level " + finalPath);
			e.printStackTrace();
		}
		for (int i = 0; i < m_models_obj.size(); i++) {
			for (int j = 0; j < m_models_obj.get(i).getFaces().size(); j++) {
				m_intersectables.add(m_models_obj.get(i).getFace(j));
			}
		}
		long endTime = System.nanoTime();
		System.out.println("Map: " + finalPath + " loaded successfully!");
		System.out.println("Map info - intersectables: " + m_intersectables.size() + " lights: " + m_lights.size() + " .obj models: " + m_models_obj.size());
		System.out.println("Total time taken to load the map: " + (endTime - startTime) / 1000000000.0 + "seconds");
	}

	/*
	 * getIntersectables()
	 * returns m_intersectables
	 */
	public List<Intersectable> getIntersectables() {
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
	public List<Light> getLights() {
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
