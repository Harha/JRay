package to.us.harha.engine.jray;

import java.util.ArrayList;
import java.util.List;

import to.us.harha.engine.util.math.Vec3f;

public class LightPoint extends Light {

	public LightPoint(Vec3f position, Vec3f color, float intensity) {
		m_position = position;
		m_color = color;
		m_intensity = intensity;
		m_type = 1;
	}

	public static List<Light> lightBulb(Vec3f pos, Vec3f col, float intensity) {
		List<Light> temp = new ArrayList<Light>();

		temp.add(new LightPoint(new Vec3f(pos.x, pos.y, pos.z), col, intensity));

		temp.add(new LightPoint(new Vec3f(pos.x + 0.25f, pos.y, pos.z), col, intensity * 0.5f));
		temp.add(new LightPoint(new Vec3f(pos.x + 0.50f, pos.y, pos.z), col, intensity * 0.5f));
		temp.add(new LightPoint(new Vec3f(pos.x - 0.25f, pos.y, pos.z), col, intensity * 0.5f));
		temp.add(new LightPoint(new Vec3f(pos.x - 0.50f, pos.y, pos.z), col, intensity * 0.5f));

		temp.add(new LightPoint(new Vec3f(pos.x, pos.y + 0.25f, pos.z), col, intensity * 0.5f));
		temp.add(new LightPoint(new Vec3f(pos.x, pos.y + 0.50f, pos.z), col, intensity * 0.5f));
		temp.add(new LightPoint(new Vec3f(pos.x, pos.y - 0.25f, pos.z), col, intensity * 0.5f));
		temp.add(new LightPoint(new Vec3f(pos.x, pos.y - 0.50f, pos.z), col, intensity * 0.5f));

		temp.add(new LightPoint(new Vec3f(pos.x, pos.y, pos.z + 0.25f), col, intensity * 0.5f));
		temp.add(new LightPoint(new Vec3f(pos.x, pos.y, pos.z + 0.50f), col, intensity * 0.5f));
		temp.add(new LightPoint(new Vec3f(pos.x, pos.y, pos.z - 0.25f), col, intensity * 0.5f));
		temp.add(new LightPoint(new Vec3f(pos.x, pos.y, pos.z - 0.50f), col, intensity * 0.5f));

		temp.add(new LightPoint(new Vec3f(pos.x + 0.25f, pos.y + 0.25f, pos.z + 0.25f), col, intensity * 0.25f));
		temp.add(new LightPoint(new Vec3f(pos.x + 0.50f, pos.y + 0.50f, pos.z + 0.50f), col, intensity * 0.25f));
		temp.add(new LightPoint(new Vec3f(pos.x - 0.25f, pos.y - 0.25f, pos.z - 0.25f), col, intensity * 0.25f));
		temp.add(new LightPoint(new Vec3f(pos.x - 0.50f, pos.y - 0.50f, pos.z - 0.50f), col, intensity * 0.25f));

		temp.add(new LightPoint(new Vec3f(pos.x - 0.25f, pos.y + 0.25f, pos.z + 0.25f), col, intensity * 0.25f));
		temp.add(new LightPoint(new Vec3f(pos.x - 0.50f, pos.y + 0.50f, pos.z + 0.50f), col, intensity * 0.25f));
		temp.add(new LightPoint(new Vec3f(pos.x + 0.25f, pos.y - 0.25f, pos.z - 0.25f), col, intensity * 0.25f));
		temp.add(new LightPoint(new Vec3f(pos.x + 0.50f, pos.y - 0.50f, pos.z - 0.50f), col, intensity * 0.25f));

		return temp;
	}

}
