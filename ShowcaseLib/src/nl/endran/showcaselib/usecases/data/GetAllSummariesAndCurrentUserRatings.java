package nl.endran.showcaselib.usecases.data;

import java.util.List;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaselib.usecases.data.GetAllSummariesAndCurrentUserRatings.GetAllSummariesAndCurrentUserRatingsListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetAllSummariesAndCurrentUserRatings extends BaseThreadSeparatorCommand<GetAllSummariesAndCurrentUserRatingsListener> {

	public interface GetAllSummariesAndCurrentUserRatingsListener extends BaseListener {

		public void onSummariesAndRatings(List<Summary> summaries, List<UserBeer> userBeers);
	}

	private List<Summary> summaries;
	private List<UserBeer> userBeers;

	public GetAllSummariesAndCurrentUserRatings(final ShowcaseDatabase showcaseDatabase, final GetAllSummariesAndCurrentUserRatingsListener listener) {
		super(showcaseDatabase, listener);
	}

	@Override
	public String execute() {
		summaries = showcaseDatabase.getAllSummaries();
		if (summaries == null) {
			return "An error occurred when retrieving all summaries";
		}

		userBeers = showcaseDatabase.getAllUserBeerRatingsForCurrentUser();
		if (userBeers == null) {
			return "An error occurred when retrieving all ratings";
		}

		return null;
	}

	@Override
	public void informListener() {
		listener.onSummariesAndRatings(summaries, userBeers);
	}
}
