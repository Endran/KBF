package nl.endran.showcase.application.kbf;

import nl.endran.logging.LogLevel;
import nl.endran.logging.LoggerFactory;
import nl.endran.showcase.application.kbf.MainActivityView.MainActivityViewListener;
import nl.endran.showcase.application.kbf.fragments.presenter.AllSearchResultSummariesFragment;
import nl.endran.showcase.application.kbf.menu.MainMenu;
import nl.endran.showcase.application.kbf.menu.MainMenu.MainMenuListener;
import nl.endran.showcase.application.kbf.menu.ShowCaseMenu;
import nl.endran.showcaselib.ShowcaseLibrary;
import nl.endran.showcaselib.datacontainers.FilterOptions;
import nl.endran.showcaselib.datacontainers.SortOptions;
import nl.endran.showcaseui.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;

public class AdvancedSearchActivity extends BaseShowcaseActivity {

	public static String SEARCH_INPUT = "SEARCH_INPUT";
	public String searchInput = "";

	public static String FILTER_INPUT = "FILTER_INPUT";
	public FilterOptions filterOptions = FilterOptions.NO_FILTER;

	public static String SORT_INPUT = "SORT_INPUT";
	public SortOptions sortOptions = SortOptions.NUMBER;

	private static final String CURRENT_ITEM = "CURRENT_ITEM";
	private static final int DEFAULT_CURRENT_ITEM = 1;
	private int currentItem = DEFAULT_CURRENT_ITEM;

	private MainActivityView mainActivityView;
	private ShowCaseMenu mainMenu;

	private final SparseArray<Pair<Fragment, String>> fragmentArray = new SparseArray<Pair<Fragment, String>>();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LoggerFactory.GetLogger(this);

		if (savedInstanceState != null) {
			currentItem = savedInstanceState.getInt(CURRENT_ITEM, DEFAULT_CURRENT_ITEM);
		}

		searchInput = getIntent().getStringExtra(SEARCH_INPUT);
		final int filterOptionsValue = getIntent().getIntExtra(FILTER_INPUT, 0);
		final int searchInputValue = getIntent().getIntExtra(SORT_INPUT, 0);

		filterOptions = FilterOptions.getByValue(filterOptionsValue);
		sortOptions = SortOptions.getByValue(searchInputValue);

		initializeFragments();

		if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setHomeButtonEnabled(true);
		}

		mainActivityView = new MainActivityView(this, new MainActivityViewListener() {

			@Override
			public void onSearchEntered(final String searchString, final FilterOptions filterOptions, final SortOptions sortOptions) {
				// TODO Auto-generated method stub

			}
		});

		mainMenu = new MainMenu(this, new MainMenuListener() {

			@Override
			public boolean onSettingsClicked() {
				return false;
			}

			@Override
			public boolean onRateClicked() {

				final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
				try {
				    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
				} catch (android.content.ActivityNotFoundException anfe) {
				    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
				}
				
				return true;
			}


			@Override
			public boolean onSearchClicked() {
				return false;
			}

			@Override
			public boolean onAboutClicked() {
				startActivity(new Intent(AdvancedSearchActivity.this, AboutActivity.class));
				return true;
			}

			@Override
			public boolean onAccountClicked() {
				startActivity(new Intent(AdvancedSearchActivity.this, LoginActivity.class));
				return true;
			}
		});
	}

	@Override
	protected void initializeLogger() {
		LoggerFactory.setLogLevel(LogLevel.VERBOSE);
		LoggerFactory.setLogTag(getString(R.string.app_name));
		LoggerFactory.setLogLevelForClass("XmlParser", LogLevel.ERROR);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final boolean result = mainMenu.optionsItemSelected(item);

		if (result) {
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onSaveInstanceState(final Bundle savedInstanceState) {
		currentItem = mainActivityView.getCurrentItem();
		savedInstanceState.putInt(CURRENT_ITEM, currentItem);
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	void onShowcaseServiceConnected(final ShowcaseLibrary showcaseLibrary) {
		mainActivityView.show(fragmentArray, currentItem);
	}

	@Override
	void onShowcaseServiceDisconnected() {
	}

	private void initializeFragments() {

		fragmentArray.clear();

		final AllSearchResultSummariesFragment allSearchResultSummariesFragment = new AllSearchResultSummariesFragment();
		final Bundle bundle = new Bundle();
		bundle.putString(AllSearchResultSummariesFragment.SEARCH_INPUT, searchInput);
		bundle.putInt(AdvancedSearchActivity.FILTER_INPUT, filterOptions.getValue());
		bundle.putInt(AdvancedSearchActivity.SORT_INPUT, sortOptions.getValue());

		final String title = (searchInput == null || searchInput.length() <= 0 ? "- " : searchInput) + ", "
				+ getString(AllSearchResultSummariesFragment.FILTER_OPTION_MAP.get(filterOptions.getValue())) + ", "
				+ getString(AllSearchResultSummariesFragment.SORT_OPTION_MAP.get(sortOptions.getValue()));

		allSearchResultSummariesFragment.setArguments(bundle);
		fragmentArray.put(0, new Pair<Fragment, String>(allSearchResultSummariesFragment, title));

	}
}