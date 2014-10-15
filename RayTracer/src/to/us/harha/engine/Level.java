package to.us.harha.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import to.us.harha.engine.gfx.RGB;
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
	private RGB					m_light_ambient;
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
	 * plane	xp yp zp xn yn zn r g b material
	 * sphere	xp yp zp r g b r type
	 * model	xp yp zp scale material reflectivity
	 * light	xp yp zp r g b r g b intensity_diff intensity_spec
	 * ambient	r g b
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
					RGB plane_c = new RGB(Float.parseFloat(values[7]), Float.parseFloat(values[8]), Float.parseFloat(values[9]));
					int plane_m = Integer.parseInt(values[10]);
					float plane_m_r = Float.parseFloat(values[11]);
					m_intersectables.add(new Plane(plane_p, plane_n, plane_c, plane_m, plane_m_r));
				} else if (line.startsWith("sphere ")) {
					Vector3f sphere_p = new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
					RGB sphere_c = new RGB(Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6]));
					float sphere_r = Float.parseFloat(values[7]);
					int sphere_m = Integer.parseInt(values[8]);
					float sphere_m_r = Float.parseFloat(values[9]);
					m_intersectables.add(new Sphere(sphere_p, sphere_c, sphere_r, sphere_m, sphere_m_r));
				} else if (line.startsWith("light ")) {
					Vector3f light_p = new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
					RGB light_c_d = new RGB(Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6]));
					RGB light_c_s = new RGB(Float.parseFloat(values[7]), Float.parseFloat(values[8]), Float.parseFloat(values[9]));
					float light_i_d = Float.parseFloat(values[10]);
					float light_i_s = Float.parseFloat(values[11]);
					m_lights.add(new Light(light_p, light_c_d, light_c_s, light_i_d, light_i_s));
				} else if (line.startsWith("model_obj ")) {
					String model_obj_path = values[1];
					Vector3f model_obj_p = new Vector3f(Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]));
					float model_obj_s = Float.parseFloat(values[5]);
					int model_obj_m = Integer.parseInt(values[6]);
					float model_obj_m_r = Float.parseFloat(values[7]);
					m_models_obj.add(new Model(model_obj_path, model_obj_p, model_obj_s, model_obj_m, model_obj_m_r));
				} else if (line.startsWith("ambient ")) {
					RGB ambient_c = new RGB(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
					m_light_ambient = ambient_c;
				} else if (line.startsWith("spawn ")) {
					Vector3f spawn_p = new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
					m_player_spawn = spawn_p;
				}
			}
			reader.close();
		} catch (Exception e) {
			Main.logger_main.printErr("Error loading the level " + finalPath);
			e.printStackTrace();
		}
		for (int i = 0; i < m_models_obj.size(); i++) {
			for (int j = 0; j < m_models_obj.get(i).getFaces().size(); j++) {
				m_intersectables.add(m_models_obj.get(i).getFace(j));
			}
		}
		long endTime = System.nanoTime();
		Main.logger_main.printMsg("Map: " + finalPath + " loaded successfully!");
		Main.logger_main.printMsg("Map info - intersectables: " + m_intersectables.size() + " lights: " + m_lights.size() + " .obj models: " + m_models_obj.size());
		Main.logger_main.printMsg("Total time taken to load the map: " + (endTime - startTime) / 1000000000.0 + "seconds");
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
	 * getModelsObj()
	 * returns m_models_obj
	 */
	public List<Model> getModelsObj() {
		return m_models_obj;
	}

	/*
	 * getModelObj(int i)
	 * returns object @ index i from m_models_obj
	 */
	public Model getModelObj(int i) {
		return m_models_obj.get(i);
	}

	/*
	 * getLightAmbient()
	 * returns m_light_ambient
	 */
	public RGB getLightAmbient() {
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
