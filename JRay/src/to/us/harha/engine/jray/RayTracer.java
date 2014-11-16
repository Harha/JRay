package to.us.harha.engine.jray;

import java.util.ArrayList;
import java.util.List;

import to.us.harha.engine.Main;
import to.us.harha.engine.gfx.Display;
import to.us.harha.engine.jray.Camera;
import to.us.harha.engine.jray.Intersection;
import to.us.harha.engine.jray.Ray;
import to.us.harha.engine.jray.Scene;
import to.us.harha.engine.jray.primitives.Plane;
import to.us.harha.engine.jray.primitives.Primitive;
import to.us.harha.engine.jray.primitives.Sphere;
import to.us.harha.engine.util.Logger;
import to.us.harha.engine.util.MathUtils;
import to.us.harha.engine.util.math.Vec3f;

public class RayTracer {

	// Maximum recursion level which shall not be passed
	private int		MAX_ITERATIONS;
	// The main camera we are looking from
	private Camera	m_camera;
	// The scene we are rendering
	private Scene	m_scene;
	// RayTracer logger object
	private Logger	m_log;

	public RayTracer(int MAX_ITERATIONS) {
		this.MAX_ITERATIONS = MAX_ITERATIONS;
		m_camera = new Camera(new Vec3f(0.0f, 2.5f, 17.5f));
		List<Primitive> primitives = new ArrayList<Primitive>();
		List<Light> lights = new ArrayList<Light>();

		Material mat_white_diffuse = new Material(new Vec3f(1.0f), new Vec3f(1.0f), 0.25f, 0.50f, 0.90f, 0.0f, 0.0f, 0.1f, 1.0f);
		Material mat_red_diffuse = new Material(new Vec3f(1.0f, 0.0f, 0.0f), new Vec3f(1.0f), 0.25f, 0.50f, 0.90f, 0.0f, 0.0f, 0.12f, 1.0f);
		Material mat_green_diffuse = new Material(new Vec3f(0.0f, 1.0f, 0.0f), new Vec3f(1.0f), 0.25f, 0.50f, 0.90f, 0.0f, 0.0f, 0.12f, 1.0f);
		Material mat_blue_diffuse = new Material(new Vec3f(0.0f, 0.0f, 1.0f), new Vec3f(1.0f), 0.25f, 0.50f, 0.90f, 0.0f, 0.0f, 0.20f, 1.0f);

		Material mat_bronze = new Material(new Vec3f(0.647f, 0.443f, 0.392f), new Vec3f(1.0f), 0.37f, 1.0f, 0.90f, 0.1f, 0.0f, 0.25f, 1.0f);
		Material mat_silver = new Material(new Vec3f(0.458823f), new Vec3f(1.0f), 0.25f, 1.0f, 0.90f, 0.1f, 0.0f, 0.25f, 1.0f);
		Material mat_white_diffuse_reflective = new Material(new Vec3f(1.0f), new Vec3f(1.0f), 0.05f, 1.0f, 0.90f, 0.1f, 0.0f, 0.25f, 1.0f);

		Material mat_mirror = new Material(new Vec3f(1.0f), new Vec3f(1.0f), 0.37f, 0.1f, 0.1f, 1.0f, 0.0f, 0.0f, 1.0f);

		Material mat_white_reflective = new Material(new Vec3f(1.0f), new Vec3f(1.0f), 0.15f, 1.0f, 0.25f, 1.0f, 0.0f, 0.0f, 1.0f);
		Material mat_red_reflective = new Material(new Vec3f(1.0f, 0.0f, 0.0f), new Vec3f(1.0f), 0.17f, 1.0f, 0.37f, 1.0f, 0.0f, 0.0f, 1.0f);
		Material mat_green_reflective = new Material(new Vec3f(0.0f, 1.0f, 0.0f), new Vec3f(1.0f), 0.20f, 0.75f, 0.20f, 1.0f, 0.0f, 0.0f, 1.0f);
		Material mat_blue_reflective = new Material(new Vec3f(0.0f, 0.0f, 1.0f), new Vec3f(1.0f), 0.10f, 0.90f, 0.15f, 1.0f, 0.0f, 0.0f, 1.0f);

		Material mat_white_refractive = new Material(new Vec3f(1.0f), new Vec3f(1.0f), 0.15f, 1.0f, 0.25f, 0.0f, 1.0f, 0.0f, 1.52f);
		Material mat_red_refractive = new Material(new Vec3f(1.0f, 0.0f, 0.0f), new Vec3f(1.0f), 0.17f, 1.0f, 0.37f, 0.0f, 1.0f, 0.0f, 1.52f);
		Material mat_green_refractive = new Material(new Vec3f(0.0f, 1.0f, 0.0f), new Vec3f(1.0f), 0.20f, 0.75f, 0.20f, 0.0f, 1.0f, 0.0f, 1.52f);
		Material mat_blue_refractive = new Material(new Vec3f(0.0f, 0.0f, 1.0f), new Vec3f(1.0f), 0.10f, 0.90f, 0.15f, 0.0f, 1.0f, 0.0f, 1.52f);

		primitives.add(new Sphere(new Vec3f(0.0f, 1.0f, 0.0f), 2.0f, mat_white_diffuse_reflective));
		primitives.add(new Sphere(new Vec3f(5.0f, 0.0f, 5.0f), 1.0f, mat_blue_reflective));
		primitives.add(new Sphere(new Vec3f(-5.0f, 2.0f, 2.5f), 1.0f, mat_red_reflective));
		primitives.add(new Sphere(new Vec3f(-5.0f, 0.0f, 5.0f), 1.0f, mat_red_refractive));
		primitives.add(new Sphere(new Vec3f(3.0f, 1.0f, 8.5f), 1.0f, mat_white_refractive));
		primitives.add(new Sphere(new Vec3f(-2.0f, 1.5f, 7.5f), 1.0f, mat_blue_refractive));
		primitives.add(new Sphere(new Vec3f(-2.0f, 1.5f, 7.5f), 0.37f, mat_blue_refractive));
		primitives.add(new Sphere(new Vec3f(-7.5f, 7.5f, -17.5f), 4.0f, mat_bronze));
		primitives.add(new Sphere(new Vec3f(7.5f, 7.5f, -17.5f), 4.0f, mat_silver));

		primitives.add(new Plane(new Vec3f(0.0f, -1.0f, 0.0f), new Vec3f(0.0f, 1.0f, 0.0f), mat_white_diffuse));
		primitives.add(new Plane(new Vec3f(0.0f, 10.0f, 0.0f), new Vec3f(0.0f, -1.0f, 0.0f), mat_blue_diffuse));
		primitives.add(new Plane(new Vec3f(0.0f, 0.0f, -20.0f), new Vec3f(0.0f, 0.0f, 1.0f), mat_white_diffuse));
		primitives.add(new Plane(new Vec3f(0.0f, 0.0f, 20.0f), new Vec3f(0.0f, 0.0f, -1.0f), mat_white_diffuse));
		primitives.add(new Plane(new Vec3f(-10.0f, 0.0f, 0.0f), new Vec3f(1.0f, 0.0f, 0.0f), mat_white_diffuse));
		primitives.add(new Plane(new Vec3f(10.0f, 0.0f, 0.0f), new Vec3f(-1.0f, 0.0f, 0.0f), mat_bronze));

		lights.addAll(LightPoint.lightBulb(new Vec3f(0.0f, 5.0f, 0.0f), new Vec3f(1.0f), 0.125f));
		lights.addAll(LightPoint.lightBulb(new Vec3f(0.0f, 7.5f, -12.5f), new Vec3f(1.0f), 0.62f));
		lights.addAll(LightPoint.lightBulb(new Vec3f(7.5f, 2.5f, 10.0f), new Vec3f(1.0f, 0.5f, 0.25f), 0.125f));
		lights.addAll(LightPoint.lightBulb(new Vec3f(-7.5f, 1.0f, 5.0f), new Vec3f(0.25f, 0.5f, 1.0f), 0.125f));
		lights.addAll(LightPoint.lightBulb(new Vec3f(2.5f, 1.0f, 2.5f), new Vec3f(0.5f, 1.0f, 0.25f), 0.125f));
		lights.addAll(LightPoint.lightBulb(new Vec3f(-2.5f, 1.0f, 6.0f), new Vec3f(0.0f, 0.0f, 1.0f), 0.2f));

		m_scene = new Scene(primitives, lights);
		m_log = new Logger(this.getClass().getName());
	}

	public void render(Display display) {
		float width = display.get_width();
		float height = display.get_height();
		Ray ray_primary = new Ray(m_camera.get_position(), m_camera.get_eye());
		ray_primary.set_inside(false);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Calculate primary ray direction and then intersect
				float x_norm = (x - width * 0.5f) / width * Main.aspect_ratio;
				float y_norm = (height * 0.5f - y) / height;
				Vec3f ray_dir = Vec3f.normalize(new Vec3f(x_norm, y_norm, -1.0f));
				ray_primary.set_direction(ray_dir);
				display.drawPixelVec3f(x, y, rayTrace(ray_primary, 0, false));
			}
		}
	}

	/*
	 * Vec3f rayTrace(Ray ray, int n);
	 * @param ray: The input ray object to trace in the scene
	 * @param n: The current recursion level as integer
	 */
	public Vec3f rayTrace(Ray ray, int n, boolean diffusiveReflection) {
		// Return if maximum recursion level is exceeded
		if (n > MAX_ITERATIONS)
			return new Vec3f(0.0f);

		// Initialize some intermediate objects & variables
		Intersection iSection = null;
		Intersection iSectionFinal = null;
		Intersection iSectionLight = null;
		Vec3f color_final = new Vec3f();
		float t_value = Float.MAX_VALUE;

		// Trace the ray initially against all objects in the scene
		for (Primitive p : m_scene.get_primitives()) {
			iSection = p.intersect(ray);
			if (iSection != null) {
				if (iSection.get_t() < t_value) {
					iSectionFinal = iSection;
					t_value = iSection.get_t();
				}
			}
		}

		// Return if no intersection happened
		if (iSectionFinal == null) {
			return new Vec3f(0.0f);
		}

		Vec3f RO = ray.get_position();
		Vec3f RD = ray.get_direction();
		Vec3f V = iSectionFinal.get_position();
		Vec3f N = iSectionFinal.get_normal();
		Primitive P = iSectionFinal.get_primitive();
		Material M = iSectionFinal.get_material();

		// We hit a surface, shade the surface color against each light source
		for (Light l : m_scene.get_lights()) {
			boolean shadow = false;
			for (Primitive p : m_scene.get_primitives()) {
				if (!p.equals(P)) {
					Vec3f L = Vec3f.sub(l.get_position(), V);
					Ray shadowRay = new Ray(iSectionFinal.get_position(), Vec3f.normalize(L));
					iSectionLight = p.intersect(shadowRay);
					if (iSectionLight != null && iSectionLight.get_t() < Vec3f.length(L))
						shadow = true;
				}
			}
			if (!shadow)
				color_final = Vec3f.add(color_final, shadeCookTorrace(ray, l, iSectionFinal));
		}

		// Check whether or not the ray is a diffusive reflection ray
		if (!diffusiveReflection) {
			// We hit a diffuse surface, reflect the ray and randomize it's direction a bit (Diffusive reflection)
			if (M.get_reflectivity_diffuse() > (Main.EPSILON)) {
				Ray reflectedRay = new Ray(V, Vec3f.normalize(Vec3f.randomize(Vec3f.reflect(RD, N), 0.25f * M.get_roughness())));
				color_final = Vec3f.add(color_final, Vec3f.scale(rayTrace(reflectedRay, n + 1, true), M.get_reflectivity_diffuse()));
			}

			// We hit a reflective surface, reflect the ray
			if (M.get_reflectivity() > (0.0f + Main.EPSILON)) {
				Ray refletedRay = new Ray(V, Vec3f.normalize(Vec3f.reflect(RD, N)));
				color_final = Vec3f.add(color_final, Vec3f.scale(rayTrace(refletedRay, n + 1, false), M.get_reflectivity()));
			}

			// We hit a refractive surface, refract the ray
			if (M.get_refractivity() > (0.0f + Main.EPSILON)) {
				Ray refractedRay = new Ray();
				refractedRay.set_position(V);
				refractedRay.set_direction(Vec3f.normalize(Vec3f.refract(Vec3f.negate(RD), N, M.get_n())));
				color_final = Vec3f.add(color_final, Vec3f.scale(rayTrace(refractedRay, n + 1, false), M.get_refractivity()));
			}
		}

		return MathUtils.smoothstep(Vec3f.add(color_final, Vec3f.scale(m_scene.get_light_ambient(), M.get_diffuse())), 0.0f, 1.0f);
	}

	/*
	 * Vec3f shadeCookTorrance(Ray ray, Light light, Intersection x);
	 * @param ray: The input ray that is to be shaded
	 * @param light: The input light source
	 * @param x: The input surface intersection data
	 */
	public Vec3f shadeCookTorrace(Ray ray, Light light, Intersection x) {
		// Calculate light direction, color & attenuation factor
		Vec3f L = Vec3f.sub(light.get_position(), x.get_position());
		float A = light.get_intensity() / Vec3f.length(L);
		L = Vec3f.normalize(L);

		// Get the surface properties & light color
		Vec3f MDIFF = x.get_material().get_diffuse();
		Vec3f MSPEC = x.get_material().get_specular();
		Vec3f LCOL = light.get_color();
		float R = x.get_material().get_roughness();
		float r_sq = R * R;
		float F = x.get_material().get_fresnel();
		float K = x.get_material().get_density();

		// Calculate any aliases and intermediary values
		Vec3f N = x.get_normal();
		Vec3f V = Vec3f.negate(ray.get_direction());
		Vec3f H = Vec3f.normalize(Vec3f.add(L, V));
		float NdotL = Math.max(Vec3f.dot(N, L), 0.0f);
		float NdotH = Math.max(Vec3f.dot(N, H), 0.0f);
		float NdotV = Math.max(Vec3f.dot(N, V), 0.0f);
		float VdotH = Math.max(Vec3f.dot(V, H), 0.0f);

		// Return black if light and normal vectors are facing in opposite directions
		if (NdotL < Main.EPSILON)
			return new Vec3f();

		// Evaluate the geometric term
		float geo_numerator = 2.0f * NdotH;
		float geo_denominator = VdotH;
		float geo_a = (geo_numerator * NdotV) / geo_denominator;
		float geo_b = (geo_numerator * NdotL) / geo_denominator;
		float geo = Math.min(1.0f, Math.min(geo_a, geo_b));

		// Evaluate the roughness term
		float roughness_a = (float) (1.0 / (4.0 * r_sq * Math.pow(NdotH, 4.0)));
		float roughness_b = NdotH * NdotH - 1.0f;
		float roughness_c = r_sq * NdotH * NdotH;
		float roughness = (float) (roughness_a * Math.exp(roughness_b / roughness_c));

		// Evaluate the fresnel term
		float fresnel = (float) Math.pow(1.0 - VdotH, 5.0);
		fresnel *= 1.0f - F;
		fresnel += F;

		// Put all the terms together for computation
		float Rs = (fresnel * geo * roughness) / (NdotV * NdotL);

		// Calculate the final color which will be returned
		Vec3f final_a = Vec3f.scale(Vec3f.scale(LCOL, NdotL), A);
		Vec3f final_c = Vec3f.add(Vec3f.scale(MDIFF, K), Vec3f.scale(MSPEC, Rs * (1.0f - K)));
		return Vec3f.scale(final_a, final_c);
	}

}
