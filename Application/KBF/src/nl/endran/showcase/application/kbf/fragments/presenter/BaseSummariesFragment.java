package nl.endran.showcase.application.kbf.fragments.presenter;

import java.util.ArrayList;
import java.util.List;

import nl.endran.activity.EndranSupportV4Fragment;
import nl.endran.showcase.application.kbf.BaseShowcaseActivity;
import nl.endran.showcase.application.kbf.BaseShowcaseActivity.ShowcaseLibraryListener;
import nl.endran.showcase.application.kbf.BeerDetailActivity;
import nl.endran.showcase.application.kbf.fragments.view.SummariesFragmentView;
import nl.endran.showcase.application.kbf.fragments.view.SummariesFragmentView.SummariesFragmentViewListener;
import nl.endran.showcaselib.ShowcaseLibrary;
import nl.endran.showcaselib.datacontainers.Summary;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseSummariesFragment extends EndranSupportV4Fragment {

	protected SummariesFragmentView summariesFragmentView;

	protected BaseShowcaseActivity activity;
	protected ShowcaseLibrary showcaseLibrary;

	protected List<Summary> summaries;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		summariesFragmentView = new SummariesFragmentView(inflater, savedInstanceState, new SummariesFragmentViewListener() {

			@Override
			public void onSummaryClicked(final int festivalBeerId) {
				showBeerDetailActivity(festivalBeerId);
			}
		});
		return summariesFragmentView.getRootView();
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final FragmentActivity fragmentActivity = getActivity();
		if (!(fragmentActivity instanceof BaseShowcaseActivity)) {
			log.error("onActivityCreated, could not cast getActivity() to BaseShowcaseActivity");
			return;
		}

		activity = (BaseShowcaseActivity) fragmentActivity;
		summariesFragmentView.setEmptyViewMessage(getEmptyViewMessage(activity));
	}

	@Override
	public void onStart() {
		super.onStart();
		activity.retrieveShowcaseLibrary(new ShowcaseLibraryListener() {
			@Override
			public void onShowcaseLibrary(final ShowcaseLibrary showcaseLibrary) {
				BaseSummariesFragment.this.showcaseLibrary = showcaseLibrary;
				getSummaries(showcaseLibrary, summariesFragmentView);
			}
		});
	}

	protected void showBeerDetailActivity(final int festivalBeerId) {
		final Intent intent = new Intent(activity, BeerDetailActivity.class);
		intent.putIntegerArrayListExtra(BeerDetailActivity.FESTIVAL_BEER_ID_LIST_KEY, getFestivalBeerIsList());
		intent.putStringArrayListExtra(BeerDetailActivity.FESTIVAL_BEER_NAME_LIST_KEY, getFesivalBeerNameList());
		intent.putExtra(BeerDetailActivity.FESTIVAL_BEER_ID_KEY, festivalBeerId);
		activity.startActivity(intent);
	}

	private ArrayList<String> getFesivalBeerNameList() {
		final ArrayList<String> result = new ArrayList<String>();
		for (final Summary summary : summaries) {
			result.add(summary.getBeerName());
		}
		return result;
	}

	private ArrayList<Integer> getFestivalBeerIsList() {
		final ArrayList<Integer> result = new ArrayList<Integer>();
		for (final Summary summary : summaries) {
			result.add(summary.getFestivalBeerID());
		}
		return result;
	}

	protected abstract String getEmptyViewMessage(Context context);

	protected abstract void getSummaries(final ShowcaseLibrary showcaseLibrary, final SummariesFragmentView summariesFragmentView);
}
