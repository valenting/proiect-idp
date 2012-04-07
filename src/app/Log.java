package app;


import org.apache.log4j.*;


/**
 * System log. To be used for debugging and other tasks
 */
public class Log {

	private static Logger logger = Logger.getLogger(Log.class);

	public static void init() {
		PropertyConfigurator.configure("src/log4j.properties");
	}

	public static void setLogFile(String filename) {
		((FileAppender)Logger.getRootLogger().getAppender("A2")).setFile(filename);
		((FileAppender)Logger.getRootLogger().getAppender("A2")).activateOptions();
	}

	public static void debug(String message) {
		Throwable t = new Throwable();
		StackTraceElement el[] = t.getStackTrace();
		logger.debug(el[1].getClassName()+" "+el[1].getMethodName() + " - " + message);
	}
	
	public static void error(String message) {
		Throwable t = new Throwable();
		StackTraceElement el[] = t.getStackTrace();
		logger.error(el[1].getClassName()+" "+el[1].getMethodName() + " - " + message);
	}
	
	public static void info(String message) {
		Throwable t = new Throwable();
		StackTraceElement el[] = t.getStackTrace();
		logger.info(el[1].getClassName()+" "+el[1].getMethodName() + " - " + message);
	}
	
	public static void fatal(String message) {
		Throwable t = new Throwable();
		StackTraceElement el[] = t.getStackTrace();
		logger.fatal(el[1].getClassName()+" "+el[1].getMethodName() + " - " + message);
	}
	
	public static void warn(String message) {
		Throwable t = new Throwable();
		StackTraceElement el[] = t.getStackTrace();
		logger.warn(el[1].getClassName()+" "+el[1].getMethodName() + " - " + message);
	}

}
