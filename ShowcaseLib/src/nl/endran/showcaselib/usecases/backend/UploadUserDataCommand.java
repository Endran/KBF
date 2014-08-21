package nl.endran.showcaselib.usecases.backend;

import java.util.List;

import nl.endran.showcaselib.connectors.ShowcaseBackend;
import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.CustomServerCredentials;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaselib.usecases.backend.UploadUserDataCommand.UploadUserDataCommandListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class UploadUserDataCommand extends BaseThreadSeparatorCommand<UploadUserDataCommandListener> {

	public interface UploadUserDataCommandListener extends BaseListener {
		void onUpdated();

		void onNotUpdated();
	}

	private final ShowcaseBackend showcaseBackend;

	private boolean updated;

	public UploadUserDataCommand(final ShowcaseBackend showcaseBackend, final ShowcaseDatabase showcaseDatabase, final UploadUserDataCommandListener listener) {
		super(showcaseDatabase, listener);
		this.showcaseBackend = showcaseBackend;
	}

	@Override
	public String execute() {
		final CustomServerCredentials customServerCredentials = showcaseDatabase.getCustomServerCredentials();
		final List<UserBeer> userBeerList = showcaseDatabase.getNotServerUpdatedUserBeers();
		updated = showcaseBackend.uploadUserData(customServerCredentials, userBeerList);

		if (updated) {
			updated &= showcaseDatabase.setServerUpdatedUserBeers(userBeerList);
		}

		return null;
	}

	@Override
	public void informListener() {
		if (updated) {
			listener.onUpdated();
		} else {
			listener.onNotUpdated();
		}
	}
}
