package nl.endran.showcase.backend.crispe;

import java.util.ArrayList;
import java.util.List;

import nl.endran.showcase.backend.crispe.helpers.CrashLogSenderHelper;
import nl.endran.showcase.backend.crispe.helpers.LoginHelper;
import nl.endran.showcase.backend.crispe.helpers.SyncAllHelper;
import nl.endran.showcase.backend.crispe.helpers.UserBeerHelper;
import nl.endran.showcaselib.connectors.ShowcaseBackend;
import nl.endran.showcaselib.datacontainers.CustomServerCredentials;
import nl.endran.showcaselib.datacontainers.ShowcaseDataObject;
import nl.endran.showcaselib.datacontainers.UserBeer;
import android.content.Context;

public class CrispeBackend implements ShowcaseBackend {

	private final Context context;

	public CrispeBackend(final Context context) {
		this.context = context;
	}

	@Override
	public void initalize() {
		final CrashLogSenderHelper crashLogSenderHelper = new CrashLogSenderHelper(context);
		crashLogSenderHelper.send();
	}

	@Override
	public List<ArrayList<ShowcaseDataObject>> syncAll(final String timestamp) {
		final SyncAllHelper syncAllHelper = new SyncAllHelper(context, timestamp);
		final boolean downloadSucces = syncAllHelper.download();

		if (downloadSucces) {
			return syncAllHelper.parse();
		} else {
			return null;
		}
	}

	@Override
	public CustomServerCredentials loginToCustomServer(final String userName, final String email) {
		final LoginHelper loginHelper = new LoginHelper(context, userName, email);
		return loginHelper.login();
	}

	@Override
	public boolean uploadUserData(final CustomServerCredentials customServerCredentials, final List<UserBeer> userBeerList) {
		final UserBeerHelper userBeerHelper = new UserBeerHelper(context, customServerCredentials, userBeerList);
		return userBeerHelper.send();
	}
}
