package nl.endran.logging;

import java.util.HashMap;

public class LoggerFactory {
	private static LogLevel _loglevel = LogLevel.VERBOSE;
	private static String _tag = "UninitializedLogTag";
	private static HashMap<String, LogLevel> loglevelMap = new HashMap<String, LogLevel>();

	public static void setLogLevel(final LogLevel logLevel) {
		_loglevel = logLevel;
	}

	public static void setLogLevelForClass(final String classSimpleName, final LogLevel logLevel) {
		loglevelMap.put(classSimpleName, logLevel);
	}

	public static void setLogTag(final String tag) {
		_tag = tag;
	}

	public static String getLogTag() {
		return _tag;
	}

	/**
	 * Create a ILogger instance for a class. Usually object is a callers
	 * <b>this</b>
	 * 
	 * @param object
	 *            A callers <b>this</b>
	 * @return ILogger instance
	 */
	public static ILogger GetLogger(final Object object) {
		return GetLoggerWithString(object.getClass().getSimpleName());
	}

	/**
	 * Create a ILogger instance for a class. Usually object is a callers
	 * simpleName
	 * 
	 * @param object
	 *            A name
	 * @return ILogger instance
	 */
	public static ILogger GetLoggerWithString(final String string) {
		LogLevel logLevel = loglevelMap.get(string);
		if (logLevel == null) {
			logLevel = _loglevel;
		}

		switch (logLevel) {
		case VERBOSE:
			return new LoggerVerbose(_tag, string);
		case DEBUG:
			return new LoggerDebug(_tag, string);
		case INFO:
			return new LoggerInfo(_tag, string);
		case WARN:
			return new LoggerWarn(_tag, string);
		case ERROR:
			return new LoggerError(_tag, string);
		default:
			return new LoggerOff();
		}
	}
}
