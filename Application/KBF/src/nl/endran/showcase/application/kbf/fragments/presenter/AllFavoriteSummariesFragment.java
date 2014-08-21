package nl.endran.showcase.application.kbf.fragments.presenter;

import java.util.List;

import nl.endran.showcase.application.kbf.fragments.view.SummariesFragmentView;
import nl.endran.showcaselib.ShowcaseLibrary;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaselib.usecases.data.GetAllFavoriteSummariesAndCurrentUserRatings.GetAllFavoriteSummariesAndCurrentUserRatingsListener;
import nl.endran.showcaseui.R;
import android.content.Context;

public class AllFavoriteSummariesFragment extends BaseSummariesFragment {

	@Override
	protected void getSummaries(final ShowcaseLibrary showcaseLibrary, final SummariesFragmentView summariesFragmentView) {
		showcaseLibrary.getDataAccess().getAllFavoriteSummariesAndCurrentUserRatings(new GetAllFavoriteSummariesAndCurrentUserRatingsListener() {

			@Override
			public void onFail(final String message) {
				summariesFragmentView.showError(message);
			}

			@Override
			public void onSummariesAndRatings(final List<Summary> summaries, final List<UserBeer> userBeers) {
				AllFavoriteSummariesFragment.this.summaries = summaries;
				summariesFragmentView.setSummaries(summaries);
				summariesFragmentView.setUserBeers(userBeers);

			}
		});
	}

	@Override
	protected String getEmptyViewMessage(final Context context) {
		return context.getString(R.string.no_favorites_yet);
	}
}
