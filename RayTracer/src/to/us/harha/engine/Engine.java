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
import to.us.harha.engine.gfx.RGBA;
import to.us.harha.engine.gfx.Display;
import to.us.harha.engine.math.Intersectable;
import to.us.harha.engine.math.Intersection;
import to.us.harha.engine.math.Light;
import to.us.harha.engine.math.Ray;
import to.us.harha.engine.math.Vector3f;

public class Engine extends Canvas implements Runnable {

	// Generated serial version UID
	private static final long		serialVersionUID	= -3180700335424399722L;

	// Integers
	private final int				MAX_N				= 10;
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
		camera = new Camera(level.getPlayerSpawn(), new Vector3f(0.0f, 0.0f, -1.0f));
		addKeyListener(input = new Input());

		// Initialize multithreading stuff
		THREAD_COUNT = 8;
		e = Executors.newFixedThreadPool(THREAD_COUNT);
		delta = new float[THREAD_COUNT];
		fps = new float[THREAD_COUNT];
		frames = new float[THREAD_COUNT];
		lastFrame = new float[THREAD_COUNT];
		lastFPS = new float[THREAD_COUNT];
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
		frame.setTitle(Main.TITLE + " Stopped...");
		running = false;
		try {
			System.out.println("Main worker thread stopped!");
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
		for (int i = 0; i < THREAD_COUNT; i++) {
			updateDelta(i);
			lastFPS[i] = getTime();
		}
		requestFocus();
		setupRenderThreads();
		while (running) {
			updateDelta(0);
			update(delta[0]);
			render();
		}
		stop();
	}

	/*
	 * Main update method.
	 */
	public void update(float delta) {
		/*
		 * Simple camera movement.
		 */
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

		counter.addAndGet(1);
		if (counter.get() >= 100) {
			System.out.println("FPS[0]: " + frames[0] + " FPS[1]: " + frames[1] + " FPS[2]: " + frames[2] + " FPS[3]: " + frames[3] + " FPS[4]: " + frames[4]);
			System.out.println(e.toString());
			counter.set(0);
		}
	}

	/*
	 * Start the rendering threads for each core.
	 * Each thread renders a small portion of the screen.
	 */
	public void setupRenderThreads() {
		e.execute(new Runnable() {
			public void run() {
				while (running) {
					for (int y = 0; y < THREAD_COUNT / 2; y++) {
						for (int x = 0; x < THREAD_COUNT / 2; x++) {
							renderTrace(x, y, 1);
						}
					}
				}
			}
		});
		e.execute(new Runnable() {
			public void run() {
				while (running) {
					for (int y = 0; y < THREAD_COUNT / 2; y++) {
						for (int x = THREAD_COUNT / 2; x < THREAD_COUNT; x++) {
							renderTrace(x, y, 2);
						}
					}
				}
			}
		});
		e.execute(new Runnable() {
			public void run() {
				while (running) {
					for (int y = THREAD_COUNT / 2; y < THREAD_COUNT; y++) {
						for (int x = 0; x < THREAD_COUNT / 2; x++) {
							renderTrace(x, y, 3);
						}
					}
				}
			}
		});
		e.execute(new Runnable() {
			public void run() {
				while (running) {
					for (int y = THREAD_COUNT / 2; y < THREAD_COUNT; y++) {
						for (int x = THREAD_COUNT / 2; x < THREAD_COUNT; x++) {
							renderTrace(x, y, 4);
						}
					}
				}
			}
		});
		System.out.println("Started all render threads succesfully!");
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
		try {
			e.awaitTermination(10, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
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
		updateFPS(0);
	}

	/*
	 * Render a portion of the screen at i,j
	 */
	public void renderTrace(int i, int j, int thread) {
		Ray primaryRay = new Ray();
		int width = display.getWidth();
		int height = display.getHeight();
		int yOffset = (height / THREAD_COUNT) * j;
		for (int y = yOffset; y < (height / THREAD_COUNT) + yOffset; y++) {
			int xOffset = (width / THREAD_COUNT) * i;
			for (int x = xOffset; x < (width / THREAD_COUNT) + xOffset; x++) {
				primaryRay.primaryCast(x, y, display.getWidth(), display.getHeight(), camera);
				RGBA color = trace(primaryRay, 0);
				if (color != null) {
					display.drawPixel(x, y, color);
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
	private RGBA trace(Ray ray, int n) {
		if (n >= MAX_N) {
			return null;
		}
		Intersection intersection = null;
		Intersection intersection_final = null;
		Intersection intersection_light = null;
		Intersectable object = null;
		RGBA color_diffuse = new RGBA();
		RGBA color_specular = new RGBA();
		RGBA color_reflection = new RGBA();
		RGBA color_refraction = new RGBA();
		RGBA color_final = new RGBA();
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
			float c1 = intersection_final.norm.dotP(ray.dir);
			Vector3f reflectionVector = intersection_final.norm._scale(2.0f)._scale(c1)._sub(ray.dir);
			Ray reflectionRay = new Ray(intersection_final.pos, reflectionVector._negate());
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
					refractionVector = ray.dir._scale(nIn)._add(intersection_final.norm._scale(nIn * c1 - c2));
				} else {
					float nIn = object.getDensity() / 1.0f;
					float c2 = (float) Math.sqrt(1.0f - (nIn * nIn) * (1.0f - (c1 * c1)));
					refractionVector = ray.dir._scale(nIn)._add(intersection_final.norm._scale(nIn * c1 - c2));
				}
				Ray refractionRay = new Ray(intersection_final.pos, refractionVector);
				color_refraction = trace(refractionRay, n + 1);
			}
		}
		/*
		 * Case 2: Object is also a diffuse object
		 * Compute illumination (Diffuse and specular colors)
		 */
		float dotProduct = 0.0f;
		float diffuseFactor = 0.0f;
		float attenuationFactorDiff = 1.0f;
		float attenuationFactorSpec = 1.0f;
		float specularFactor = 0.0f;
		for (Light l : level.getLights()) {
			Vector3f lightVector = l.pos._sub(intersection_final.pos);
			Ray shadowRay = new Ray(intersection_final.pos, lightVector);
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
			dotProduct = intersection_final.norm.dotP(lightVector._unitV());
			diffuseFactor = dotProduct;
			specularFactor = (float) Math.pow(dotProduct, 20);
			attenuationFactorDiff = l.intensity / lightVector.length();
			attenuationFactorSpec = (l.intensity) / (lightVector.length());
			if (dotProduct > 0.0f && inShadow == false) {
				color_diffuse.add(l.col.scaleR(diffuseFactor).scaleR(attenuationFactorDiff));
				color_specular._add(specularFactor * attenuationFactorSpec);
				/*
				 * Checkerboard texture calculation
				 */
				if (object.getType_2() == 1) {
					int square_xz = (int) (Math.floor(intersection_final.pos.x) + Math.floor(intersection_final.pos.z));
					if ((square_xz % 2) == 0) {
						color_diffuse.scale(new RGBA(0.0f, 0.0f, 0.0f, 0.0f));
						color_specular.scale(new RGBA(0.0f, 0.0f, 0.0f, 0.0f));
					} else {
						color_diffuse.scale(new RGBA(0.25f, 0.0f, 1.0f, 0.0f));
						color_specular.scale(new RGBA(0.25f, 0.0f, 1.0f, 0.0f));
					}
				}
				if (object.getType_2() == 2) {
					int square_yz = (int) (Math.floor(intersection_final.pos.y) + Math.floor(intersection_final.pos.z));
					if ((square_yz % 2) == 0) {
						color_diffuse.scale(new RGBA(0.0f, 0.0f, 0.0f, 0.0f));
						color_specular.scale(new RGBA(0.0f, 0.0f, 0.0f, 0.0f));
					} else {
						color_diffuse.scale(new RGBA(0.5f, 1.0f, 0.0f, 0.0f));
						color_specular.scale(new RGBA(0.5f, 1.0f, 0.0f, 0.0f));
					}
				}
				if (object.getType_2() == 3) {
					int square_xy = (int) (Math.floor(intersection_final.pos.x) + Math.floor(intersection_final.pos.y));
					if ((square_xy % 2) == 0) {
						color_diffuse.scale(new RGBA(0.0f, 0.0f, 0.0f, 0.0f));
						color_specular.scale(new RGBA(0.0f, 0.0f, 0.0f, 0.0f));
					} else {
						color_diffuse.scale(new RGBA(0.5f, 1.0f, 0.0f, 0.0f));
						color_specular.scale(new RGBA(0.5f, 1.0f, 0.0f, 0.0f));
					}
				}
				if (behindGlass) {
					color_diffuse.scale(0.95f);
				}
			}

		}
		if (object.getType_1() == 0) {
			color_final.add(color_diffuse);
		}
		if (object.getType_1() == 1 && color_reflection != null) {
			color_final.add(color_reflection);
		}
		if (object.getType_1() == 2 && color_refraction != null) {
			color_final.add(color_refraction);
			if (color_reflection != null)
				color_final.add(color_reflection.scaleR(0.20f));
		}
		if (object.getType_1() == 0)
			color_final.add(color_specular);
		else
			color_final.add(color_specular.scaleR(0.5f));
		color_final.add(level.getLightAmbient());
		color_final.scale(object.getHue());
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
