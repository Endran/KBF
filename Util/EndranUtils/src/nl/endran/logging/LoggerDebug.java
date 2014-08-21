package nl.endran.logging;

import android.util.Log;

public class LoggerDebug implements ILogger
{
	String tag;
	String name;

	public LoggerDebug(String tag, String name)
	{
		this.tag = tag;
		this.name = name;
	}

	public void verbose(String message)
	{
	}

	public void verbose(String message, Throwable throwable)
	{
	}

	public void debug(String message)
	{
		Log.d(tag, name + " :: " + message);
	}

	public void debug(String message, Throwable throwable)
	{
		Log.d(tag, name + " :: " + message, throwable);
	}

	public void info(String message)
	{
		Log.i(tag, name + " :: " + message);
	}

	public void info(String message, Throwable throwable)
	{
		Log.i(tag, name + " :: " + message, throwable);
	}

	public void warn(String message)
	{
		Log.w(tag, name + " :: " + message);
	}

	public void warn(String message, Throwable throwable)
	{
		Log.w(tag, name + " :: " + message, throwable);
	}

	public void error(String message)
	{
		Log.e(tag, name + " :: " + message);
	}

	public void error(String message, Throwable throwable)
	{
		Log.e(tag, name + " :: " + message, throwable);
	}
}
