package nl.endran.showcaselib.usecases.data;

import java.util.ArrayList;
import java.util.List;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaselib.usecases.data.GetAllFavoriteSummariesAndCurrentUserRatings.GetAllFavoriteSummariesAndCurrentUserRatingsListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetAllFavoriteSummariesAndCurrentUserRatings extends BaseThreadSeparatorCommand<GetAllFavoriteSummariesAndCurrentUserRatingsListener> {

	public interface GetAllFavoriteSummariesAndCurrentUserRatingsListener extends BaseListener {

		public void onSummariesAndRatings(List<Summary> summaries, List<UserBeer> userBeers);
	}

	private List<Summary> summaries;
	private List<UserBeer> userBeers;

	public GetAllFavoriteSummariesAndCurrentUserRatings(final ShowcaseDatabase showcaseDatabase,
			final GetAllFavoriteSummariesAndCurrentUserRatingsListener listener) {
		super(showcaseDatabase, listener);
	}

	@Override
	public String execute() {
		userBeers = showcaseDatabase.getAllUserBeerRatingsForCurrentUser();
		if (userBeers == null) {
			return "An error occurred when retrieving all ratings";
		}

		final List<Integer> beerIdList = new ArrayList<Integer>();
		for (final UserBeer userBeer : userBeers) {
			if (userBeer.getFavorite()) {
				beerIdList.add(userBeer.getBeerId());
			}
		}

		summaries = showcaseDatabase.getSummaryByBeerIdList(beerIdList);

		return null;
	}

	@Override
	public void informListener() {
		listener.onSummariesAndRatings(summaries, userBeers);
	}
}
