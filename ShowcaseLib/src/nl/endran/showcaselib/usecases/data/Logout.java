package nl.endran.showcaselib.usecases.data;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.usecases.data.Logout.LogoutListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class Logout extends BaseThreadSeparatorCommand<LogoutListener> {

	public interface LogoutListener extends BaseListener {
		public void onLoggedOut();
	}

	public Logout(final ShowcaseDatabase showcaseDatabase, final LogoutListener listener) {
		super(showcaseDatabase, listener);
	}

	@Override
	public String execute() {
		showcaseDatabase.resetCustomServerCredentials();
		return null;
	}

	@Override
	public void informListener() {
		listener.onLoggedOut();
	}
}