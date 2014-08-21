package nl.endran.showcaselib.usecases.backend;

import nl.endran.showcaselib.connectors.ShowcaseBackend;
import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.CustomServerCredentials;
import nl.endran.showcaselib.usecases.backend.TryLoginCommand.TryLoginCommandListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class TryLoginCommand extends BaseThreadSeparatorCommand<TryLoginCommandListener> {

	public interface TryLoginCommandListener extends BaseListener {
		void onCustomServerCredentials(CustomServerCredentials customServerCredentials);
	}

	private final ShowcaseBackend showcaseBackend;

	private final boolean activated;
	private final String userName;
	private final String email;

	private CustomServerCredentials customServerCredentials;

	public TryLoginCommand(final ShowcaseBackend showcaseBackend, final ShowcaseDatabase showcaseDatabase, final boolean activated, final String userName,
			final String email, final TryLoginCommandListener listener) {
		super(showcaseDatabase, listener);
		this.showcaseBackend = showcaseBackend;

		this.activated = activated;
		this.userName = userName;
		this.email = email;
	}

	@Override
	public String execute() {
		customServerCredentials = showcaseDatabase.getCustomServerCredentials();
		final boolean isActivted = activated || customServerCredentials.isActivated();
		customServerCredentials.setActivated(isActivted);
		customServerCredentials.setEmail(email);
		customServerCredentials.setUserName(userName);
		showcaseDatabase.setCustomServerCredentials(customServerCredentials);

		if (customServerCredentials == null || customServerCredentials.getUserId() < 0) {
			final CustomServerCredentials tempCustomServerCredentials = showcaseBackend.loginToCustomServer(userName, email);
			if (tempCustomServerCredentials != null) {
				customServerCredentials = tempCustomServerCredentials;
				customServerCredentials.setActivated(isActivted);
			}
		}

		showcaseDatabase.setCustomServerCredentials(customServerCredentials);
		return null;
	}

	@Override
	public void informListener() {
		listener.onCustomServerCredentials(customServerCredentials);
	}
}
