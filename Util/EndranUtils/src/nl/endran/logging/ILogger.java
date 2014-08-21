package nl.endran.logging;

public interface ILogger
{

	public abstract void verbose(String message);

	public abstract void verbose(String message, Throwable throwable);

	public abstract void debug(String message);

	public abstract void debug(String message, Throwable throwable);

	public abstract void info(String message);

	public abstract void info(String message, Throwable throwable);

	public abstract void warn(String message);

	public abstract void warn(String message, Throwable throwable);

	public abstract void error(String message);

	public abstract void error(String message, Throwable throwable);

}