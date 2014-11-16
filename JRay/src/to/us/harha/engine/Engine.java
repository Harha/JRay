package to.us.harha.engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import to.us.harha.engine.gfx.Display;
import to.us.harha.engine.jray.RayTracer;
import to.us.harha.engine.util.Logger;

public class Engine extends Canvas implements Runnable {

	// Generated serial version UID
	private static final long	serialVersionUID	= 6026256166632077690L;

	// Engine variables
	private boolean				engine_running;

	// Engine objects
	private Logger				engine_log;
	private Display				engine_display;
	private RayTracer			engine_raytracer;

	// Java objects
	private Dimension			engine_dimension;
	private JFrame				engine_jframe;
	private Thread				engine_thread;

	/*
	 * Constructor Engine(int width, int height, int scale, boolean isResizable);
	 * @param width: Window width in pixels
	 * @param height: Window height in pixels
	 * @param scale: Window size scaling factor
	 * @param isResizable: Is the window resizable or not?
	 */
	public Engine(int width, int height, int scale, boolean isResizable) {
		// Initialize the main display objects
		engine_display = new Display(width, height);
		engine_dimension = new Dimension(width * scale, height * scale);
		setPreferredSize(engine_dimension);

		// Create a JFrame
		engine_jframe = new JFrame();
		engine_jframe.setResizable(isResizable);
		engine_jframe.setTitle(Main.TITLE);
		engine_jframe.add(this);
		engine_jframe.pack();
		engine_jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		engine_jframe.setLocationRelativeTo(null);
		engine_jframe.setVisible(true);

		// Initialize the remaining Engine objects
		engine_log = new Logger(this.getClass().getName());
		engine_raytracer = new RayTracer(8);
		engine_running = false;
	}

	/*
	 * synchronized void engine_start();
	 * @info: Starts the engine thread
	 */
	public synchronized void engine_start() {
		engine_running = true;
		engine_thread = new Thread(this, Main.TITLE);
		engine_thread.start();
	}

	/*
	 * synchronized void engine_stop();
	 * @info: Stops the engine thread and shuts down the program
	 */
	public synchronized void engine_stop() {
		System.exit(0);
	}

	/*
	 * void run();
	 * @see java.lang.Runnable#run();
	 */
	@Override
	public void run() {
		float loopTime = 0.0f;
		requestFocus();
		while (engine_running) {
			loopTime = System.nanoTime();
			render();
			String loopTimeEnd = String.format("%.2f", (System.nanoTime() - loopTime) / 1000000.0f);
			engine_log.printMsg("Main thread execution time: " + loopTimeEnd + "ms");
		}
		engine_stop();
	}

	/*
	 * void render();
	 * @info: Main render loop
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		engine_display.clear();

		engine_raytracer.render(engine_display);

		Graphics g = bs.getDrawGraphics();
		g.drawImage(engine_display.get_image(), 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

}
