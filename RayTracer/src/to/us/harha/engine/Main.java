package to.us.harha.engine;

import javax.swing.JFrame;

import to.us.harha.engine.Engine;

public class Main {

	// Program's default title
	public static final String	TITLE		= "Raytracer";
	// Main display's drawing space width
	public static int			width		= 1280 / 1;
	// Main display's drawing space height
	public static int			height		= width / 16 * 9;
	// Main display's drawing space aspect ratio
	public static int			ar			= width / height;
	// Main display's drawing space scale
	public static int			scale		= 1;
	// Static engine object
	public static Engine		engine;
	// Static general logger object
	public static Log			logger_main	= new Log("MAIN");

	public static void main(String args[]) {
		if (args.length > 0) {
			try {
				width = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("Argument " + args[0] + " must be an integer...");
			}
			try {
				height = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.err.println("Argument " + args[1] + " must be an integer...");
			}
			try {
				scale = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				System.err.println("Argument " + args[2] + " must be an integer...");
			}
			ar = width / height;
		}
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
