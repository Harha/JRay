package to.us.harha.engine;

import javax.swing.JFrame;

import to.us.harha.engine.Engine;

public class Main {

	// Program's default title
	public static final String	TITLE	= "Raytracer";
	// Main display's drawing space width
	public static final int		width	= 1280 / 4;
	// Main display's drawing space height
	public static final int		height	= width / 16 * 9;
	// Main display's drawing space aspect ratio
	public static final int		ar		= width / height;
	// Main display's drawing space scale
	public static final int		scale	= 4;
	// Static engine object
	public static Engine		engine;
	// Static general logger object
	public static Log			logger_main = new Log("MAIN");

	public static void main(String args[]) {
		engine = new Engine();
		engine.frame.setResizable(false);
		engine.frame.setTitle(TITLE + " | Initializing...");
		engine.frame.add(engine);
		engine.frame.pack();
		engine.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		engine.frame.setLocationRelativeTo(null);
		engine.frame.setVisible(true);
		engine.start();
	}

}
