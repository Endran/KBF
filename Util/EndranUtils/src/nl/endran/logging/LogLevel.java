package nl.endran.logging;

public enum LogLevel
{
	VERBOSE(0), DEBUG(1), INFO(2), WARN(3), ERROR(4), OFF(5);

	private int level;

	private LogLevel(int level)
	{
		this.level = level;
	}

	public int toInt()
	{
		return level;
	}
}