package nl.endran.showcaselib.util.seperation;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;

public abstract class BaseThreadSeparatorCommand<T extends BaseListener> implements ThreadSeparatorCommand {

	protected final ShowcaseDatabase showcaseDatabase;
	protected final T listener;

	public BaseThreadSeparatorCommand(final ShowcaseDatabase showcaseDatabase, final T listener) {
		this.showcaseDatabase = showcaseDatabase;
		this.listener = listener;
	}

	@Override
	public void failed(final String message) {
		this.listener.onFail(message);
	}
}
