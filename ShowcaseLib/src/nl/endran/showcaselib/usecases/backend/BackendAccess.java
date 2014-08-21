package nl.endran.showcaselib.usecases.backend;

import nl.endran.showcaselib.connectors.ShowcaseBackend;
import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.usecases.backend.SynchronizeCommand.SynchronizeAllListener;
import nl.endran.showcaselib.usecases.backend.TryLoginCommand.TryLoginCommandListener;
import nl.endran.showcaselib.usecases.backend.UploadUserDataCommand.UploadUserDataCommandListener;
import nl.endran.showcaselib.util.seperation.ThreadSeparator;

public class BackendAccess {
	private final ShowcaseBackend showcaseBackend;
	private final ShowcaseDatabase showcaseDatabase;
	private final ThreadSeparator threadSeparator;

	public BackendAccess(final ShowcaseBackend showcaseBackend, final ShowcaseDatabase showcaseDatabase) {
		this.showcaseBackend = showcaseBackend;
		this.showcaseDatabase = showcaseDatabase;
		threadSeparator = new ThreadSeparator();

		threadSeparator.enqueue(new InitializeCommand(this.showcaseBackend, this.showcaseDatabase));
	}

	public void synchronizeAll(final SynchronizeAllListener listener) {
		threadSeparator.enqueue(new SynchronizeCommand(showcaseBackend, showcaseDatabase, listener));
	}

	public void tryLogin(final boolean activated, final String userName, final String email, final TryLoginCommandListener listener) {
		threadSeparator.enqueue(new TryLoginCommand(showcaseBackend, showcaseDatabase, activated, userName, email, listener));
	}

	public void uploadUserDataCommand(final UploadUserDataCommandListener listener) {
		threadSeparator.enqueue(new UploadUserDataCommand(showcaseBackend, showcaseDatabase, listener));
	}
}
