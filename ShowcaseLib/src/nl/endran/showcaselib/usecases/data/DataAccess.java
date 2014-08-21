package nl.endran.showcaselib.usecases.data;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.FilterOptions;
import nl.endran.showcaselib.datacontainers.SortOptions;
import nl.endran.showcaselib.usecases.data.GetAllBrewersCommand.GetAllBrewersListener;
import nl.endran.showcaselib.usecases.data.GetAllFavoriteSummariesAndCurrentUserRatings.GetAllFavoriteSummariesAndCurrentUserRatingsListener;
import nl.endran.showcaselib.usecases.data.GetAllRatingForBeer.GetAllRatingForBeerListener;
import nl.endran.showcaselib.usecases.data.GetAllSummariesAndCurrentUserRatings.GetAllSummariesAndCurrentUserRatingsListener;
import nl.endran.showcaselib.usecases.data.GetAllSummariesCommand.GetAllSummariesListener;
import nl.endran.showcaselib.usecases.data.GetAllSummariesForSearchInput.GetAllSummariesForSearchInputListener;
import nl.endran.showcaselib.usecases.data.GetAllUserBeerRatingsForCurrentUser.GetAlluserBeerRatingsForCurrentUserListener;
import nl.endran.showcaselib.usecases.data.GetAllWhisListSummariesAndCurrentUserRatings.GetAllWhisListSummariesAndCurrentUserRatingsListener;
import nl.endran.showcaselib.usecases.data.GetBrewerCommand.GetBrewerListener;
import nl.endran.showcaselib.usecases.data.GetCompleteBeerDetails.CompleteBeerDetailsListener;
import nl.endran.showcaselib.usecases.data.GetFestivalBeerNamesAndIds.GetFestivalBeerNamesAndIdsListener;
import nl.endran.showcaselib.usecases.data.GetFestivalCommand.GetFestivalListener;
import nl.endran.showcaselib.usecases.data.GetSummaryCommand.GetSummaryListener;
import nl.endran.showcaselib.usecases.data.GetUserCredentials.UserCredentialsListener;
import nl.endran.showcaselib.usecases.data.IsInitializedCommand.IsInitializedListener;
import nl.endran.showcaselib.usecases.data.Logout.LogoutListener;
import nl.endran.showcaselib.usecases.data.SaveUserRating.SaveUserRatingListener;
import nl.endran.showcaselib.util.seperation.ThreadSeparator;

public class DataAccess {

	private final ShowcaseDatabase showcaseDatabase;
	private final ThreadSeparator threadSeparator;

	public DataAccess(final ShowcaseDatabase showcaseDatabase) {
		this.showcaseDatabase = showcaseDatabase;

		threadSeparator = new ThreadSeparator();
	}

	public void getAllSummaries(final GetAllSummariesListener listener) {
		threadSeparator.enqueue(new GetAllSummariesCommand(showcaseDatabase, listener));
	}

	public void getSummary(final int festivalBeerId, final GetSummaryListener listener) {
		threadSeparator.enqueue(new GetSummaryCommand(showcaseDatabase, festivalBeerId, listener));
	}

	public void getAllBrewers(final GetAllBrewersListener listener) {
		threadSeparator.enqueue(new GetAllBrewersCommand(showcaseDatabase, listener));
	}

	public void getBrewer(final int brewerId, final GetBrewerListener listener) {
		threadSeparator.enqueue(new GetBrewerCommand(showcaseDatabase, brewerId, listener));
	}

	public void getFestival(final int festivalId, final GetFestivalListener listener) {
		threadSeparator.enqueue(new GetFestivalCommand(showcaseDatabase, festivalId, listener));
	}

	public void isInitialized(final IsInitializedListener listener) {
		threadSeparator.enqueue(new IsInitializedCommand(showcaseDatabase, listener));
	}

	public void getCompleteBeerDetails(final int festivalBeerId, final CompleteBeerDetailsListener listener) {
		threadSeparator.enqueue(new GetCompleteBeerDetails(showcaseDatabase, festivalBeerId, listener));
	}

	public void getFestivalBeerNamesAndIds(final GetFestivalBeerNamesAndIdsListener listener) {
		threadSeparator.enqueue(new GetFestivalBeerNamesAndIds(showcaseDatabase, listener));
	}

	public void getUserCredentials(final UserCredentialsListener listener) {
		threadSeparator.enqueue(new GetUserCredentials(showcaseDatabase, listener));
	}

	public void saveUserRating(final int beerId, final int rating, final String note, final boolean wishlist, final boolean favorite,
			final SaveUserRatingListener listener) {
		threadSeparator.enqueue(new SaveUserRating(showcaseDatabase, beerId, rating, note, wishlist, favorite, listener));
	}

	public void getAllRatingForCurrentUser(final GetAlluserBeerRatingsForCurrentUserListener listener) {
		threadSeparator.enqueue(new GetAllUserBeerRatingsForCurrentUser(showcaseDatabase, listener));

	}

	public void getAllRatingForBeer(final int beerID, final GetAllRatingForBeerListener listener) {
		threadSeparator.enqueue(new GetAllRatingForBeer(showcaseDatabase, beerID, listener));

	}

	public void getAllSummariesAndCurrentUserRatings(final GetAllSummariesAndCurrentUserRatingsListener listener) {
		threadSeparator.enqueue(new GetAllSummariesAndCurrentUserRatings(showcaseDatabase, listener));
	}

	public void getAllFavoriteSummariesAndCurrentUserRatings(final GetAllFavoriteSummariesAndCurrentUserRatingsListener listener) {
		threadSeparator.enqueue(new GetAllFavoriteSummariesAndCurrentUserRatings(showcaseDatabase, listener));
	}

	public void getAllWhisListSummariesAndCurrentUserRatings(final GetAllWhisListSummariesAndCurrentUserRatingsListener listener) {
		threadSeparator.enqueue(new GetAllWhisListSummariesAndCurrentUserRatings(showcaseDatabase, listener));

	}

	public void getAllSummariesForSearchInput(final String searchInput, final FilterOptions filterOptions, final SortOptions sortOptions,
			final GetAllSummariesForSearchInputListener listener) {
		threadSeparator.enqueue(new GetAllSummariesForSearchInput(showcaseDatabase, searchInput, filterOptions, sortOptions, listener));
	}

	public void Logout(final LogoutListener listener) {
		threadSeparator.enqueue(new Logout(showcaseDatabase, listener));
	}
}
