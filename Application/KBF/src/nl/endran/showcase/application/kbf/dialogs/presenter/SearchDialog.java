package nl.endran.showcase.application.kbf.dialogs.presenter;

import java.util.ArrayList;
import java.util.List;

import nl.endran.showcase.application.kbf.dialogs.view.SearchDialogView;
import nl.endran.showcase.application.kbf.dialogs.view.SearchDialogView.SearchDialogViewListener;
import nl.endran.showcase.application.kbf.fragments.presenter.AllSearchResultSummariesFragment;
import nl.endran.showcaselib.datacontainers.FilterOptions;
import nl.endran.showcaselib.datacontainers.SortOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class SearchDialog extends Dialog {

	public interface SearchDialogListener {
		public void onDismiss();

		public void onSearchEntered(String searchInput, FilterOptions filterOptions, SortOptions sortOptions);
	}

	// private final SearchDialogListener listener;

	public SearchDialog(final Context context, final SearchDialogListener listener) {
		super(context, android.R.style.Theme_Dialog);

		final List<String> filterList = new ArrayList<String>();
		filterList.add(context.getString(AllSearchResultSummariesFragment.FILTER_OPTION_MAP.get(FilterOptions.NO_FILTER.getValue())));
		filterList.add(context.getString(AllSearchResultSummariesFragment.FILTER_OPTION_MAP.get(FilterOptions.RATED.getValue())));
		filterList.add(context.getString(AllSearchResultSummariesFragment.FILTER_OPTION_MAP.get(FilterOptions.NOT_RATED.getValue())));
		filterList.add(context.getString(AllSearchResultSummariesFragment.FILTER_OPTION_MAP.get(FilterOptions.FESTIVAL_FIRST.getValue())));
		filterList.add(context.getString(AllSearchResultSummariesFragment.FILTER_OPTION_MAP.get(FilterOptions.EXCLUSIVE.getValue())));

		final List<String> sortList = new ArrayList<String>();
		sortList.add(context.getString(AllSearchResultSummariesFragment.SORT_OPTION_MAP.get(SortOptions.NUMBER.getValue())));
		sortList.add(context.getString(AllSearchResultSummariesFragment.SORT_OPTION_MAP.get(SortOptions.ALPHABET.getValue())));
		sortList.add(context.getString(AllSearchResultSummariesFragment.SORT_OPTION_MAP.get(SortOptions.MY_RATING.getValue())));
		sortList.add(context.getString(AllSearchResultSummariesFragment.SORT_OPTION_MAP.get(SortOptions.AVERAGE_RATING.getValue())));
		sortList.add(context.getString(AllSearchResultSummariesFragment.SORT_OPTION_MAP.get(SortOptions.COLOR.getValue())));
		sortList.add(context.getString(AllSearchResultSummariesFragment.SORT_OPTION_MAP.get(SortOptions.PRICE.getValue())));
		sortList.add(context.getString(AllSearchResultSummariesFragment.SORT_OPTION_MAP.get(SortOptions.PERCENTAGE.getValue())));

		new SearchDialogView(this, filterList, sortList, new SearchDialogViewListener() {

			@Override
			public void onSearchEntered(final String searchInput, final int filterListPosition, final int sortListPosition) {
				listener.onSearchEntered(searchInput, getFilterOptionsForString(filterListPosition), getSortOptionsForString(sortListPosition));
			}
		});

		setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(final DialogInterface dialog) {
				listener.onDismiss();
			}
		});
	}

	private FilterOptions getFilterOptionsForString(final int filterListPosition) {
		switch (filterListPosition) {
		case 1:
			return FilterOptions.RATED;
		case 2:
			return FilterOptions.NOT_RATED;
		case 3:
			return FilterOptions.FESTIVAL_FIRST;
		case 4:
			return FilterOptions.EXCLUSIVE;
		case 0:
		default:
			return FilterOptions.NO_FILTER;
		}
	}

	private SortOptions getSortOptionsForString(final int sortListPosition) {
		switch (sortListPosition) {
		case 1:
			return SortOptions.ALPHABET;
		case 2:
			return SortOptions.MY_RATING;
		case 3:
			return SortOptions.AVERAGE_RATING;
		case 4:
			return SortOptions.COLOR;
		case 5:
			return SortOptions.PRICE;
		case 6:
			return SortOptions.PERCENTAGE;
		case 0:
		default:
			return SortOptions.NUMBER;
		}
	}
}
