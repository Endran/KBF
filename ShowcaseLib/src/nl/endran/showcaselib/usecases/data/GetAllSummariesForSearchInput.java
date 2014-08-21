package nl.endran.showcaselib.usecases.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.FilterOptions;
import nl.endran.showcaselib.datacontainers.SortOptions;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaselib.usecases.data.GetAllSummariesForSearchInput.GetAllSummariesForSearchInputListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetAllSummariesForSearchInput extends BaseThreadSeparatorCommand<GetAllSummariesForSearchInputListener> {

	private static final int SMALLER_THEN = -1;
	private static final int LARGER_AS = +1;
	private static final int EQUAL_TO = 0;

	public interface GetAllSummariesForSearchInputListener extends BaseListener {

		public void onSummariesAndRatings(List<Summary> summaries, List<UserBeer> userBeers);
	}

	private List<Summary> summaries = new ArrayList<Summary>();
	private List<UserBeer> userBeers;

	private final String searchInput;
	private final FilterOptions filterOptions;
	private final SortOptions sortOptions;

	public GetAllSummariesForSearchInput(final ShowcaseDatabase showcaseDatabase, final String searchInput, final FilterOptions filterOptions,
			final SortOptions sortOptions, final GetAllSummariesForSearchInputListener listener) {
		super(showcaseDatabase, listener);

		this.searchInput = searchInput;
		this.filterOptions = (filterOptions == null ? FilterOptions.NO_FILTER : filterOptions);
		this.sortOptions = (sortOptions == null ? SortOptions.NUMBER : sortOptions);
	}

	@Override
	public String execute() {

		final List<Summary> allSummaries = showcaseDatabase.getAllSummariesForSearchInput(searchInput);

		if (allSummaries == null) {
			return "An error occurred when retrieving all summaries";
		}

		userBeers = showcaseDatabase.getAllUserBeerRatingsForCurrentUser();
		if (userBeers == null) {
			return "An error occurred when retrieving all ratings";
		}

		switch (filterOptions) {
		case EXCLUSIVE:
			for (final Summary summary : allSummaries) {
				if (summary.getFestivalBeerAward() != null && summary.getFestivalBeerAward().length() > 0) {
					summaries.add(summary);
				}
			}
			break;
		case FESTIVAL_FIRST:
			for (final Summary summary : allSummaries) {
				if (summary.isFestivalBeerFirstTime()) {
					summaries.add(summary);
				}
			}
			break;
		case NOT_RATED:
			for (final Summary summary : allSummaries) {
				final UserBeer userBeer = getUserBeerForBeerdId(summary.getBeerId());
				if (userBeer == null || userBeer.getRating() <= 0) {
					summaries.add(summary);
				}
			}
			break;
		case RATED:
			for (final Summary summary : allSummaries) {
				final UserBeer userBeer = getUserBeerForBeerdId(summary.getBeerId());
				if (userBeer != null && userBeer.getRating() > 0) {
					summaries.add(summary);
				}
			}
			break;
		case NO_FILTER:
		default:
			summaries = allSummaries;
			break;
		}

		switch (sortOptions) {
		case ALPHABET:
			Collections.sort(summaries, new AlphabetComparator());
			break;
		case COLOR:
			Collections.sort(summaries, new ColorComparator());
			break;
		case PERCENTAGE:
			Collections.sort(summaries, new PercentageComparator());
			break;
		case PRICE:
			Collections.sort(summaries, new PriceComparator());
			break;
		case AVERAGE_RATING:
			Collections.sort(summaries, new AverageRatingComparator());
			break;
		case MY_RATING:
			Collections.sort(summaries, new MyRatingComparator());
			break;
		case NUMBER:
		default:
			break;
		}

		return null;
	}

	@Override
	public void informListener() {
		listener.onSummariesAndRatings(summaries, userBeers);
	}

	private UserBeer getUserBeerForBeerdId(final int beerId) {
		if (userBeers == null || userBeers.size() <= 0) {
			return null;
		}

		for (final UserBeer userBeer : userBeers) {
			if (userBeer.getBeerId() == beerId) {
				return userBeer;
			}
		}

		return null;
	}

	private class AlphabetComparator implements Comparator<Summary> {
		@Override
		public int compare(final Summary x, final Summary y) {
			return x.getBeerName().compareTo(y.getBeerName());
		}
	}

	private class ColorComparator implements Comparator<Summary> {
		@Override
		public int compare(final Summary x, final Summary y) {
			return x.getBeerColor().compareTo(y.getBeerColor());
		}
	}

	private class PercentageComparator implements Comparator<Summary> {
		@Override
		public int compare(final Summary x, final Summary y) {
			if (x.getBeerPercentage() > y.getBeerPercentage()) {
				return LARGER_AS;
			} else if (x.getBeerPercentage() < y.getBeerPercentage()) {
				return SMALLER_THEN;
			}

			return EQUAL_TO;
		}
	}

	private class PriceComparator implements Comparator<Summary> {
		@Override
		public int compare(final Summary x, final Summary y) {
			if (x.getFestivalBeerPrice() > y.getFestivalBeerPrice()) {
				return LARGER_AS;
			} else if (x.getFestivalBeerPrice() < y.getFestivalBeerPrice()) {
				return SMALLER_THEN;
			}

			return EQUAL_TO;
		}
	}

	private class AverageRatingComparator implements Comparator<Summary> {
		@Override
		public int compare(final Summary x, final Summary y) {
			if (x.getBeerRatingAvg() > y.getBeerRatingAvg()) {
				return SMALLER_THEN;
			} else if (x.getBeerRatingAvg() < y.getBeerRatingAvg()) {
				return LARGER_AS;
			}

			return EQUAL_TO;
		}
	}

	private class MyRatingComparator implements Comparator<Summary> {
		@Override
		public int compare(final Summary x, final Summary y) {
			final UserBeer xUserBeer = getUserBeerForBeerdId(x.getBeerId());
			final UserBeer yUserBeer = getUserBeerForBeerdId(y.getBeerId());

			if (xUserBeer != null && yUserBeer == null) {
				return SMALLER_THEN;
			} else if (xUserBeer == null && yUserBeer != null) {
				return LARGER_AS;
			} else if (xUserBeer == null && yUserBeer == null) {
				return EQUAL_TO;
			}

			if (xUserBeer.getRating() > yUserBeer.getRating()) {
				return SMALLER_THEN;
			} else if (xUserBeer.getRating() < yUserBeer.getRating()) {
				return LARGER_AS;
			}

			return EQUAL_TO;
		}
	}
}
