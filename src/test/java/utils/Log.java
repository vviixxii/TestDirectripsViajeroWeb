package utils;

import org.apache.log4j.Logger;

/**
 * Clase para el manejo de la bitácora
 * 
 * @author Ricardo Romero
 * @version 1.0.0
 * @date 18/12/18
 */
public class Log {

	private static Logger Log = Logger.getLogger(Log.class.getName());

	public static void startTestCase(String testCaseName) {
		Log.info("startTestCase -> " + Utils.extraerTexto(testCaseName));
	}

	public static void endTestCase(String testCaseName) {
		Log.info("endTestCase -> " + Utils.extraerTexto(testCaseName));
	}

	public static void info(String message) {
		Log.info(message);
	}

	public static void warn(String message) {
		Log.warn(message);
	}

	public static void error(String message) {
		Log.error(message);
	}

	public static void fatal(String message) {
		Log.fatal(message);
	}

	public static void debug(String message) {
		Log.debug(message);
	}
}
