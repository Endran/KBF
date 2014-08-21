package nl.endran.showcaselib.connectors;

import java.util.ArrayList;
import java.util.List;

import nl.endran.showcaselib.datacontainers.Beer;
import nl.endran.showcaselib.datacontainers.Brewer;
import nl.endran.showcaselib.datacontainers.CustomServerCredentials;
import nl.endran.showcaselib.datacontainers.Festival;
import nl.endran.showcaselib.datacontainers.FestivalBeer;
import nl.endran.showcaselib.datacontainers.ShowcaseDataObject;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;

public interface ShowcaseDatabase {

	/**
	 * Get the timestamp to the most recent update of the database, with fresh data from the server.
	 * 
	 * @return timestamp of latest fresh data, or null if the database has not been filled.
	 */
	public String getLatestUpdateTimestamp();

	/**
	 * Store a List of a List of ShowcaseDataObjects.
	 * 
	 * @param showcaseDataObjectListList
	 *            to store.
	 * @return true when the list is successfully stored, false otherwise.
	 */
	public boolean storeShowcaseDataObjectListList(List<ArrayList<ShowcaseDataObject>> showcaseDataObjectListList);

	/**
	 * Store a List of ShowcaseDataObjects.
	 * 
	 * @param showcaseDataObjectList
	 *            to store.
	 * @return true when the list is successfully stored, false otherwise.
	 */
	public boolean storeShowcaseDataObjectList(List<ShowcaseDataObject> showcaseDataObjectList);

	/**
	 * Store a ShowcaseDataObject.
	 * 
	 * @param showcaseDataObject
	 *            to store.
	 * @return true when the ShowcaseDataObject is successfully stored, false otherwise.
	 */
	public boolean storeShowcaseDataObject(ShowcaseDataObject showcaseDataObject);

	/**
	 * Get a List of all Summaries.
	 * 
	 * @return List of all Summaries.
	 */
	public List<Summary> getAllSummaries();

	public Summary getSummary(int festivalBeerId);

	public List<Beer> getAllBeers();

	public Beer getBeer(int beerId);

	public List<Brewer> getAllBrewers();

	public Brewer getBrewer(int brewerId);

	public List<Festival> getAllFestivals();

	public Festival getFestival(int festivalId);

	public List<FestivalBeer> getAllFestivalBeers();

	public FestivalBeer getFestivalBeer(int festivalBeerId);

	public List<UserBeer> getAllUserBeersForBeer(int beerId);

	/**
	 * Retrieves the User beer for the current userId
	 */
	public UserBeer getUserBeer(int beerId);

	public void setCustomServerCredentials(CustomServerCredentials CustomServerCredentials);

	public CustomServerCredentials getCustomServerCredentials();

	public boolean storeUserRating(final int beerId, final int rating, final String note, final boolean wishlist, final boolean favorite);

	/**
	 * Retrieves the User beer for the given userId
	 */
	UserBeer getUserBeer(int beerId, int userId);

	public List<UserBeer> getAllUserBeerRatingsForCurrentUser();

	public List<Summary> getSummaryByBeerIdList(List<Integer> beerIdList);

	public List<UserBeer> getNotServerUpdatedUserBeers();

	public boolean setServerUpdatedUserBeers(final List<UserBeer> userBeerList);

	public List<Summary> getAllSummariesForSearchInput(String searchInput);

	void resetCustomServerCredentials();

}
