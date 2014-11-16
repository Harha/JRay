package to.us.harha.engine;

import to.us.harha.engine.util.Logger;

public class Main {

	// Constant variables below
	public static final float	EPSILON			= 1e-2f;

	// Program's title
	public static final String	TITLE			= "JRay";
	// Main window's width
	public static int			width			= 1280 / 1;
	// Main window's height
	public static int			height			= width / 16 * 9;
	// Main window's scale
	public static int			scale			= 1;
	// Main window's aspect ratio
	public static float			aspect_ratio	= (float) width / (float) height;
	// Is the main window resizable or not?
	public static boolean		isResizable		= false;
	// Main Engine object
	public static Engine		engine;
	// Main Logger object
	public static Logger		log_main		= new Logger(Main.class.getName());

	/*
	 * static void main(String args[]);
	 * @param args[]: Program startup arguments
	 */
	public static void main(String args[]) {
		engine = new Engine(width, height, scale, isResizable);
		engine.engine_start();
	}

}
