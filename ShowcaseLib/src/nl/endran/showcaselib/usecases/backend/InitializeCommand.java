package nl.endran.showcaselib.usecases.backend;

import nl.endran.showcaselib.connectors.ShowcaseBackend;
import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class InitializeCommand extends BaseThreadSeparatorCommand<BaseListener> {

	private final ShowcaseBackend showcaseBackend;

	public InitializeCommand(final ShowcaseBackend showcaseBackend, final ShowcaseDatabase showcaseDatabase) {
		super(showcaseDatabase, new BaseListener() {
			@Override
			public void onFail(final String message) {
				// not used
			}
		});
		this.showcaseBackend = showcaseBackend;
	}

	@Override
	public String execute() {
		showcaseBackend.initalize();
		return null;
	}

	@Override
	public void informListener() {
		// not used
	}

}
