package nl.endran.showcase.application.kbf;

import java.util.ArrayList;

import nl.endran.showcaselib.ShowcaseLibrary;
import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;

public class BeerDetailActivity extends BaseShowcaseActivity {

	public static final String FESTIVAL_BEER_ID_LIST_KEY = "FESTIVAL_BEER_LIST_ID_KEY";
	public static final String FESTIVAL_BEER_NAME_LIST_KEY = "FESTIVAL_BEER_LIST_NAME_KEY";
	public static final String FESTIVAL_BEER_ID_KEY = "FESTIVAL_BEER_ID_KEY";

	private BeerDetailActivityView beerDetailActivityView;

	private int currentItem;
	private ArrayList<Integer> festivalBeerIdList;
	private ArrayList<String> festivalBeerStringList;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setHomeButtonEnabled(true);
		}

		beerDetailActivityView = new BeerDetailActivityView(this);

		if (savedInstanceState != null) {
			currentItem = savedInstanceState.getInt(FESTIVAL_BEER_ID_KEY, 0);
		} else {
			currentItem = getIntent().getIntExtra(FESTIVAL_BEER_ID_KEY, 0);
		}

		festivalBeerIdList = getIntent().getIntegerArrayListExtra(FESTIVAL_BEER_ID_LIST_KEY);
		if (festivalBeerIdList == null) {
			(new Handler()).post(new Runnable() {

				@Override
				public void run() {
					log.warn("Finishing this activty since festivalBeerIdList is null");
					finish();
				}
			});
		}

		festivalBeerStringList = getIntent().getStringArrayListExtra(FESTIVAL_BEER_NAME_LIST_KEY);
		if (festivalBeerStringList == null) {
			(new Handler()).post(new Runnable() {

				@Override
				public void run() {
					log.warn("Finishing this activty since festivalBeerStringList is null");
					finish();
				}
			});
		}
	}

	@Override
	protected void initializeLogger() {

	}

	@Override
	protected void onSaveInstanceState(final Bundle savedInstanceState) {
		currentItem = beerDetailActivityView.getCurrentItem();
		savedInstanceState.putInt(FESTIVAL_BEER_ID_KEY, currentItem);
		super.onSaveInstanceState(savedInstanceState);
	}

	// private void getFestivalBeerNamesAndIds() {
	//
	// showcaseLibrary.getDataAccess().getFestivalBeerNames(festivalBeerIdList, new GetFestivalBeerNamesAndIdsListener() {
	//
	// @Override
	// public void onFail(final String message) {
	// beerDetailActivityView.showError(message);
	// }
	//
	// @Override
	// public void onGetFestivalBeerNamesAndIds(final List<Pair<String, Integer>> festivalBeerNamesAndIdsList) {
	// beerDetailActivityView.show(festivalBeerNamesAndIdsList, currentItem);
	// }
	// });
	// }

	private void setFestivalBeerNamesAndIds() {
		beerDetailActivityView.show(festivalBeerStringList, festivalBeerIdList, currentItem);
	}

	@Override
	void onShowcaseServiceConnected(final ShowcaseLibrary showcaseLibrary) {
		setFestivalBeerNamesAndIds();
	}

	@Override
	void onShowcaseServiceDisconnected() {
	}
}
