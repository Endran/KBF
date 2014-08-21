package nl.endran.showcaselib.usecases.data;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.CustomServerCredentials;
import nl.endran.showcaselib.usecases.data.GetUserCredentials.UserCredentialsListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetUserCredentials extends BaseThreadSeparatorCommand<UserCredentialsListener> {

	int userId;
	String userName;
	private CustomServerCredentials customServerCredentials;

	public interface UserCredentialsListener extends BaseListener {
		public void onCredentials(CustomServerCredentials customServerCredentials);
	}

	public GetUserCredentials(final ShowcaseDatabase showcaseDatabase, final UserCredentialsListener listener) {
		super(showcaseDatabase, listener);
	}

	@Override
	public String execute() {
		customServerCredentials = showcaseDatabase.getCustomServerCredentials();

		if (customServerCredentials == null) {
			return "Could not retrieve data";
		}
		return null;
	}

	@Override
	public void informListener() {
		listener.onCredentials(customServerCredentials);
	}
}
