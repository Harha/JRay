package to.us.harha.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;

import to.us.harha.engine.Main;
import to.us.harha.engine.gfx.RGB;
import to.us.harha.engine.gfx.Display;
import to.us.harha.engine.math.Intersectable;
import to.us.harha.engine.math.Intersection;
import to.us.harha.engine.math.Light;
import to.us.harha.engine.math.MathUtils;
import to.us.harha.engine.math.Ray;
import to.us.harha.engine.math.Vector3f;

public class Engine extends Canvas implements Runnable {

	// Generated serial version UID
	private static final long		serialVersionUID	= -3180700335424399722L;

	// Integers
	private final int				MAX_N				= 5;
	private static AtomicInteger	counter				= new AtomicInteger(0);

	// Floats
	private float[]					delta;
	private float[]					fps;
	private float[]					frames;
	private float[]					lastFrame;
	private float[]					lastFPS;

	// Booleans
	private boolean					running;

	// Arrays
	private int[]					pixels;

	// Java objects
	private BufferedImage			image;
	private Dimension				dimension;
	private Thread					thread;
	public JFrame					frame;
	private Font					font;
	private BufferStrategy			bs;

	// Custom objects
	private Display					display;
	private Camera					camera;
	private Input					input;
	private Level					level;
	private Log						logger_engine;

	// Multi-threading
	private final int				THREAD_COUNT;
	private final ExecutorService	e;

	/*
	 * Engine.java
	 * Engine class constructor
	 */
	public Engine() {
		image = new BufferedImage(Main.width, Main.height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		dimension = new Dimension(Main.width * Main.scale, Main.height * Main.scale);
		setPreferredSize(dimension);

		// Initialize java objects
		frame = new JFrame();
		font = new Font("Terminus", Font.BOLD, 12);

		// Initialize engine objects
		display = new Display(Main.width, Main.height);
		level = new Level("test.rtmap");
		camera = new Camera(level.getPlayerSpawn());
		addKeyListener(input = new Input());
		logger_engine = new Log("ENGINE");

		// Initialize multithreading stuff
		THREAD_COUNT = 4;
		e = Executors.newFixedThreadPool(THREAD_COUNT);
		delta = new float[THREAD_COUNT + 1];
		fps = new float[THREAD_COUNT + 1];
		frames = new float[THREAD_COUNT + 1];
		lastFrame = new float[THREAD_COUNT + 1];
		lastFPS = new float[THREAD_COUNT + 1];
	}

	/*
	 * Start the main thread.
	 */
	public synchronized void start() {
		frame.setTitle(Main.TITLE + " Running...");
		running = true;
		thread = new Thread(this, "Core");
		thread.start();
	}

	/*
	 * Stop the main thread.
	 */
	public synchronized void stop() {
		running = false;
		frame.setTitle(Main.TITLE + " Stopped...");
		logger_engine.printMsg("Main worker thread stopped!");
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Main run method.
	 * Handles the update and render methods.
	 */
	public void run() {
		for (int i = 0; i <= THREAD_COUNT; i++) {
			updateDelta(i);
			lastFPS[i] = getTime();
		}
		requestFocus();
		while (running) {
			updateDelta(THREAD_COUNT);
			updateFPS(THREAD_COUNT);
			update(delta[THREAD_COUNT]);
			runRenderThreads();
			try {
				e.awaitTermination(10, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			render();
		}
		stop();
	}

	/*
	 * Main update method.
	 */
	public void update(float delta) {
		// Simple camera movement and look
		if (input.w)
			camera.moveForward(camera.speed * delta);
		if (input.s)
			camera.moveBackward(camera.speed * delta);
		if (input.a)
			camera.moveLeft(camera.speed * delta);
		if (input.d)
			camera.moveRight(camera.speed * delta);
		if (input.r)
			camera.pos.y += 0.01f * delta;
		if (input.f)
			camera.pos.y -= 0.01f * delta;
		if (input.up) {
			camera.pitch += 0.1f * delta;
		}
		if (input.down) {
			camera.pitch -= 0.1f * delta;
		}
		if (input.right) {
			camera.yaw += 0.1f * delta;
		}
		if (input.left) {
			camera.yaw -= 0.1f * delta;
		}
		camera.recalc();

		// Quit the program if esc is pressed down
		if (input.esc)
			running = false;

		// Simple counter to print out info at chosen interval
		counter.addAndGet(1);
		if (counter.get() >= 1000) {
			logger_engine.printMsg("FPS: " + frames[4] + " THREAD INFO: " + e.toString());
			counter.set(0);
		}
	}

	/*
	 * Start the rendering threads for each core.
	 * Each thread renders a small portion of the screen.
	 */
	public void runRenderThreads() {
		e.execute(new Runnable() {
			public void run() {
				for (int y = 0; y < THREAD_COUNT / 2; y++) {
					for (int x = 0; x < THREAD_COUNT / 2; x++) {
						renderTrace(x, y, 0);
					}
				}
			}
		});
		e.execute(new Runnable() {
			public void run() {
				for (int y = 0; y < THREAD_COUNT / 2; y++) {
					for (int x = THREAD_COUNT / 2; x < THREAD_COUNT; x++) {
						renderTrace(x, y, 1);
					}
				}
			}
		});
		e.execute(new Runnable() {
			public void run() {
				for (int y = THREAD_COUNT / 2; y < THREAD_COUNT; y++) {
					for (int x = 0; x < THREAD_COUNT / 2; x++) {
						renderTrace(x, y, 2);
					}
				}
			}
		});
		e.execute(new Runnable() {
			public void run() {
				for (int y = THREAD_COUNT / 2; y < THREAD_COUNT; y++) {
					for (int x = THREAD_COUNT / 2; x < THREAD_COUNT; x++) {
						renderTrace(x, y, 3);
					}
				}
			}
		});
	}

	/*
	 * Main render method.
	 * Multi-trheading partially implemented.
	 */
	public void render() {
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = display.getPixel(i);
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		// Render debug information on the screen
		g.setColor(Color.WHITE);
		g.setFont(font);
		DecimalFormat df = new DecimalFormat("0.00");
		g.drawString("Camera Vec3 pos | x:" + df.format(camera.pos.x) + " y:" + df.format(camera.pos.y) + " z:" + df.format(camera.pos.z), 16, 16);
		g.drawString("Camera Vec3 eye | x:" + df.format(camera.eye.x) + " y:" + df.format(camera.eye.y) + " z:" + df.format(camera.eye.z), 16, 32);

		g.dispose();
		bs.show();
	}

	/*
	 * Render a portion of the screen at i,j
	 */
	public void renderTrace(int i, int j, int thread) {
		Ray primaryRay = new Ray();
		RGB nullColor = new RGB(0.0f, 0.0f, 0.0f);
		int width = display.getWidth();
		int height = display.getHeight();
		int yOffset = (height / THREAD_COUNT) * j;
		for (int y = yOffset; y < (height / THREAD_COUNT) + yOffset; y++) {
			int xOffset = (width / THREAD_COUNT) * i;
			for (int x = xOffset; x < (width / THREAD_COUNT) + xOffset; x++) {
				primaryRay.primaryCast(x, y, display.getWidth(), display.getHeight(), camera);
				RGB color = trace(primaryRay, 0);
				if (color != null) {
					display.drawPixel(x, y, color);
				} else {
					display.drawPixel(x, y, nullColor);
				}
			}
		}
		updateDelta(thread);
		updateFPS(thread);
	}

	/*
	 * Trace(Ray ray, int n)
	 * n = recursive level
	 */
	private RGB trace(Ray ray, int n) {
		if (n >= MAX_N) {
			return null;
		}
		Intersection intersection = null;
		Intersection intersection_final = null;
		Intersection intersection_light = null;
		Intersectable object = null;
		RGB color_diffuse = new RGB();
		RGB color_specular = new RGB();
		RGB color_reflection = new RGB();
		RGB color_refraction = new RGB();
		RGB color_final = new RGB();
		float finalDistance = Float.MAX_VALUE;
		/*
		 * Cast the initial ray and test if it intersects with anything
		 */
		for (Intersectable i : level.getIntersectables()) {
			intersection = i.intersect(ray, 0);
			if (intersection != null) {
				if (intersection.dist < finalDistance) {
					finalDistance = intersection.dist;
					intersection_final = intersection;
					object = i;
				}
			}
		}
		if (intersection_final == null)
			return null;

		Vector3f V = ray.dir;
		Vector3f P = intersection_final.pos;
		Vector3f N = intersection_final.norm;

		/*
		 * Case 1: Object is made of glass or is a mirror
		 * Split the ray into a reflection and refraction ray based on the object type
		 */
		if (object.getType_1() == 1 || object.getType_1() == 2) {
			/*
			 * Case 1.0 Ray is reflective or reflective and refractive.
			 * Calculate reflection first.
			 * V = ray.dir
			 * Reflection: 2.0f * N * dot(N,V) - V
			 */
			float c1 = N.dotP(V);
			Vector3f reflectionVector = N._scale(2.0f)._scale(c1)._sub(V);
			Ray reflectionRay = new Ray(P, reflectionVector._negate());
			color_reflection = trace(reflectionRay, n + 1);
			if (object.getType_1() == 2) {
				/*
				 * Case 1.1 Ray is going into the medium
				 * V.N < 0.0f
				 * 
				 * Case 1.2 Ray is coming out from the medium
				 * V.N > 0.0f
				 */
				Vector3f refractionVector = null;
				if (c1 < 0.0f) {
					float nIn = 1.0f / object.getDensity();
					float c2 = (float) Math.sqrt(1.0f - (nIn * nIn) * (1.0f - (c1 * c1)));
					refractionVector = V._scale(nIn)._add(N._scale(nIn * c1 - c2));
				} else {
					float nIn = object.getDensity() / 1.0f;
					float c2 = (float) Math.sqrt(1.0f - (nIn * nIn) * (1.0f - (c1 * c1)));
					refractionVector = V._scale(nIn)._add(N._scale(nIn * c1 - c2));
				}
				Ray refractionRay = new Ray(P, refractionVector);
				color_refraction = trace(refractionRay, n + 1);
			}
		}
		/*
		 * Case 2: Object is also a diffuse object
		 * Compute illumination (Diffuse and specular colors)
		 */
		Vector3f lightVector;
		float lightVLength;
		float NdotL = 0.0f;
		float diffuseFactor = 0.0f;
		float specularFactor = 0.0f;
		float diffuseFactorAtt = 1.0f;
		float specularFactorAtt = 1.0f;
		for (Light l : level.getLights()) {
			lightVector = l.pos._sub(P);
			Ray shadowRay = new Ray(P, lightVector);
			boolean inShadow = false;
			boolean behindGlass = false;
			for (Intersectable i : level.getIntersectables()) {
				if (!i.equals(object)) {
					intersection_light = i.intersect(shadowRay, 0);
					if (intersection_light != null) {
						if (intersection_light.dist < lightVector.length()) {
							if (i.getType_1() != 2) {
								inShadow = true;
							} else {
								behindGlass = true;
							}
						}
					}
				}
			}
			// Calculate the lightvector's length and then normalize it
			lightVLength = lightVector.length();
			lightVector.unitV();
			// Calculate the intensity of the diffuse light
			NdotL = N.dotP(lightVector);
			diffuseFactor = MathUtils.clampf(NdotL, 0.0f, 1.0f);
			diffuseFactorAtt = l.intensity_diff / lightVLength;
			// Calculate the specular reflection
			// This needs some fixing, can't figure it out atm
			Vector3f LhalfV = lightVector._sub(V)._unitV();
			float NdotH = N.dotP(LhalfV);
			specularFactor = (float) Math.pow(MathUtils.clampf(NdotH, 0.0f, 1.0f), 25);
			specularFactorAtt = l.intensity_spec / lightVLength;
			if (NdotL > 0.0f && !inShadow) {
				if (object.getType_1() == 0)
					color_diffuse.add(l.col_diff.scaleR(diffuseFactor * diffuseFactorAtt));
				if (NdotH > 0.0f)
					color_specular.add(l.col_spec.scaleR(specularFactor * specularFactorAtt));
				if (behindGlass)
					color_diffuse.scale(0.75f);
			}
		}
		color_final.add(level.getLightAmbient());
		color_final.add(color_diffuse);
		color_final.scale(object.getHue());
		if (object.getType_1() == 1 && color_reflection != null) {
			color_final.add(color_reflection);
		}
		if (object.getType_1() == 2 && color_refraction != null) {
			color_final.add(color_refraction);
			if (color_reflection != null)
				color_final.add(color_reflection.scaleR(0.10f));
		}
		if (object.getType_1() == 0)
			color_final.add(color_specular);
		else
			color_final.add(color_specular.scaleR(0.25f));
		return color_final;
	}

	/*
	 * long getTime();
	 * returns system time in nanoseconds.
	 */
	public float getTime() {
		return System.nanoTime() / 1000000.0f;
	}

	/*
	 * void updateDelta();
	 * returns the chosen deltaTime in nanoseconds as integer.
	 */
	public void updateDelta(int i) {
		float time = getTime();
		delta[i] = (float) (time - lastFrame[i]);
		lastFrame[i] = time;
	}

	/*
	 * void updateFPS();
	 * updates the chosen FPS counter.
	 */
	public void updateFPS(int i) {
		if (getTime() - lastFPS[i] > 1000) {
			frames[i] = fps[i];
			fps[i] = 0;
			lastFPS[i] += 1000;
		}
		fps[i]++;
	}

}
