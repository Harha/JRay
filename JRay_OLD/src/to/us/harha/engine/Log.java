package to.us.harha.engine;

public class Log {

	private String	prefix;

	public Log(String prefix) {
		this.prefix = "[LOG_" + prefix + "]";
		printMsg("Logger started!");
	}

	public void printMsg(String msg) {
		System.out.println(prefix + ": " + msg);
	}

	public void printErr(String msg) {
		System.err.println(prefix + " ERROR: " + msg);
	}

}
