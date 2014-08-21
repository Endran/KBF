package nl.endran.showcaselib.usecases.data;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.usecases.data.IsInitializedCommand.IsInitializedListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class IsInitializedCommand extends BaseThreadSeparatorCommand<IsInitializedListener> {

	public interface IsInitializedListener extends BaseListener {

		public void onIsInitialized(boolean initialized);
	}

	private boolean initialized;

	public IsInitializedCommand(final ShowcaseDatabase showcaseDatabase, final IsInitializedListener listener) {
		super(showcaseDatabase, listener);
	}

	@Override
	public String execute() {
		final String timestamp = showcaseDatabase.getLatestUpdateTimestamp();
		initialized = (timestamp != null && timestamp.length() > 0);

		return null;
	}

	@Override
	public void informListener() {
		listener.onIsInitialized(initialized);
	}
}
