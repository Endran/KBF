package nl.endran.showcaselib.util.seperation;

public interface ThreadSeparatorCommand {

	/**
	 * Is called on a separate Thread. Exeutions may take some time, the UI won't be bothered since it runs on a different Thread.
	 * 
	 * @return An error message, or null when eveything is ok.
	 */
	public String execute();

	/**
	 * Is called on the same Thread as which the ThreadSeperator was created.
	 */
	public void informListener();

	/**
	 * Is called on the same Thread as which the ThreadSeperator was created.
	 * 
	 * @param message
	 *            describing the failure.
	 */
	public void failed(String message);
}
