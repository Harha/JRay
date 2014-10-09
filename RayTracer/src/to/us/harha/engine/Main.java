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
	// Main display's drawing space scale
	public static final int		scale	= 4;
	// Static engine object
	private static Engine		engine;

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
