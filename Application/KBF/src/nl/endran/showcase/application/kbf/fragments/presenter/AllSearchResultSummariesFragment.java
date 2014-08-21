package nl.endran.showcase.application.kbf.fragments.presenter;

import java.util.List;

import nl.endran.showcase.application.kbf.fragments.view.SummariesFragmentView;
import nl.endran.showcaselib.ShowcaseLibrary;
import nl.endran.showcaselib.datacontainers.FilterOptions;
import nl.endran.showcaselib.datacontainers.SortOptions;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaselib.usecases.data.GetAllSummariesForSearchInput.GetAllSummariesForSearchInputListener;
import nl.endran.showcaseui.R;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseIntArray;

public class AllSearchResultSummariesFragment extends BaseSummariesFragment {

	public static String SEARCH_INPUT = "SEARCH_INPUT";
	public static String FILTER_INPUT = "FILTER_INPUT";
	public static String SORT_INPUT = "SORT_INPUT";

	public static SparseIntArray FILTER_OPTION_MAP = new SparseIntArray();
	static {
		FILTER_OPTION_MAP.put(FilterOptions.NO_FILTER.getValue(), R.string.no_filter);
		FILTER_OPTION_MAP.put(FilterOptions.RATED.getValue(), R.string.already_rated);
		FILTER_OPTION_MAP.put(FilterOptions.NOT_RATED.getValue(), R.string.not_yet_rated);
		FILTER_OPTION_MAP.put(FilterOptions.EXCLUSIVE.getValue(), R.string.exclusive);
		FILTER_OPTION_MAP.put(FilterOptions.FESTIVAL_FIRST.getValue(), R.string.festivalFirst);
	}

	public static SparseIntArray SORT_OPTION_MAP = new SparseIntArray();
	static {
		SORT_OPTION_MAP.put(SortOptions.NUMBER.getValue(), R.string.number);
		SORT_OPTION_MAP.put(SortOptions.ALPHABET.getValue(), R.string.alphabet);
		SORT_OPTION_MAP.put(SortOptions.MY_RATING.getValue(), R.string.my_rating);
		SORT_OPTION_MAP.put(SortOptions.AVERAGE_RATING.getValue(), R.string.average_rating);
		SORT_OPTION_MAP.put(SortOptions.COLOR.getValue(), R.string.color);
		SORT_OPTION_MAP.put(SortOptions.PERCENTAGE.getValue(), R.string.percentage);
		SORT_OPTION_MAP.put(SortOptions.PRICE.getValue(), R.string.price);
	}

	private FilterOptions filterOptions;
	private SortOptions sortOptions;

	@Override
	protected void getSummaries(final ShowcaseLibrary showcaseLibrary, final SummariesFragmentView summariesFragmentView) {
		final Bundle arguments = getArguments();
		if (arguments == null) {
			return;
		}

		final String searchInput = arguments.getString(SEARCH_INPUT);
		final int filterOptionsValue = arguments.getInt(FILTER_INPUT, 0);
		final int sortOptionsValue = arguments.getInt(SORT_INPUT, 0);

		filterOptions = FilterOptions.getByValue(filterOptionsValue);
		sortOptions = SortOptions.getByValue(sortOptionsValue);

		log.info("Busy searching.. Filter=" + sortOptions + " Sort=" + sortOptions);

		showcaseLibrary.getDataAccess().getAllSummariesForSearchInput(searchInput, filterOptions, sortOptions, new GetAllSummariesForSearchInputListener() {

			@Override
			public void onFail(final String message) {
				summariesFragmentView.showError(message);
			}

			@Override
			public void onSummariesAndRatings(final List<Summary> summaries, final List<UserBeer> userBeers) {
				AllSearchResultSummariesFragment.this.summaries = summaries;
				summariesFragmentView.setSummaries(summaries);
				summariesFragmentView.setUserBeers(userBeers);

				summariesFragmentView.setEmptyViewMessage(getString(R.string.no_search_results));
			}
		});
	}

	@Override
	protected String getEmptyViewMessage(final Context context) {
		return getString(R.string.busy_searching);
	}
}
