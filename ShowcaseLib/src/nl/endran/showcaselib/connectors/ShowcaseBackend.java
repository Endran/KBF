package nl.endran.showcaselib.connectors;

import java.util.ArrayList;
import java.util.List;

import nl.endran.showcaselib.datacontainers.CustomServerCredentials;
import nl.endran.showcaselib.datacontainers.ShowcaseDataObject;
import nl.endran.showcaselib.datacontainers.UserBeer;

public interface ShowcaseBackend {

	public void initalize();

	/**
	 * Synchronize all data, given the latest timestamp. If the timestamp is omitted (left null or empty), then all data completely refreshed.
	 * 
	 * @param timestamp
	 *            to sync to. Leave null or empty to sync the entire database.
	 * @return Synchronized data from the server, or null when an error occurred.
	 */
	public List<ArrayList<ShowcaseDataObject>> syncAll(String timestamp);

	public CustomServerCredentials loginToCustomServer(String userName, String email);

	public boolean uploadUserData(final CustomServerCredentials customServerCredentials, final List<UserBeer> userBeerList);
}
