package to.us.harha.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import to.us.harha.engine.gfx.RGBA;
import to.us.harha.engine.math.Triangle;
import to.us.harha.engine.math.Vector3f;

public class Model {

	private Vector3f		m_pos;
	private float			m_scale;
	private int				m_type_1;
	private List<Triangle>	m_faces;

	public Model(String path, Vector3f pos, float scale, int type_1) {
		m_faces = new ArrayList<Triangle>();
		m_pos = pos;
		m_scale = scale;
		m_type_1 = type_1;
		loadObjFromFile(path);
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
	private void loadObjFromFile(String path) {
		String workingDir = System.getProperty("user.dir");
		String finalPath = workingDir + "\\" + path;
		String line;
		long startTime = System.nanoTime();
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
			Vector3f[] verts = new Vector3f[] { vertex_1._scale(m_scale)._add(m_pos), vertex_2._scale(m_scale)._add(m_pos), vertex_3._scale(m_scale)._add(m_pos) };
			m_faces.add(new Triangle(verts, new RGBA(1.0f, 1.0f, 1.0f, 1.0f), m_type_1, 0));
		}
		long endTime = System.nanoTime();
		System.out.println("Model: " + finalPath + " loaded successfully!");
		System.out.println("Total time taken to load the model: " + (endTime - startTime) / 1000000000.0 + "seconds");
	}

	/*
	 * getFaces()
	 * returns m_faces
	 */
	public List<Triangle> getFaces() {
		return m_faces;
	}

	/*
	 * getFace(int i)
	 * returns object @ index i from m_faces
	 */
	public Triangle getFace(int i) {
		return m_faces.get(i);
	}

}
