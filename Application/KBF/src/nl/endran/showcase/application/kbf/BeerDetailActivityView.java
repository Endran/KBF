package nl.endran.showcase.application.kbf;

import java.util.ArrayList;
import java.util.List;

import nl.endran.activity.ActivityLifecycleMonitor;
import nl.endran.activity.EndranActivity;
import nl.endran.activity.OptionsMenuMonitor.OptionsMenuListener;
import nl.endran.showcase.application.kbf.fragments.presenter.BeerDetailFragment;
import nl.endran.showcaseui.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class BeerDetailActivityView {

	private final EndranActivity activity;

	private final ViewPager viewPager;

	private final ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

	@SuppressLint("NewApi")
	public BeerDetailActivityView(final EndranActivity activity) {
		this.activity = activity;
		activity.setContentView(R.layout.activity_beer_detail);

		viewPager = (ViewPager) activity.findViewById(R.id.viewPager);
		viewPager.setAdapter(new PagerTestAdapter(activity.getSupportFragmentManager()));

		// Bind invisible next button
		activity.findViewById(R.id.viewLeftTop).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (viewPager != null && viewPager.getAdapter() != null) {
					final int targetItem = viewPager.getCurrentItem() - 1;
					final int max = viewPager.getAdapter().getCount();

					if (targetItem >= 0 && targetItem < max) {
						viewPager.setCurrentItem(targetItem);
					}
				}
			}
		});

		// Bind invisible previous button
		activity.findViewById(R.id.viewRightTop).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (viewPager != null && viewPager.getAdapter() != null) {
					final int targetItem = viewPager.getCurrentItem() + 1;
					final int max = viewPager.getAdapter().getCount();

					if (targetItem >= 0 && targetItem < max) {
						viewPager.setCurrentItem(targetItem);
					}
				}
			}
		});

		activity.getActivityLifecycleMonitor().addListener(activityLifecycleListener);
	}

	public void show(final List<Pair<String, Integer>> festivalBeerNamesAndIdsList, final int currentfestivalBeerId) {

		int currentItem = 0;
		fragmentList.clear();

		for (final Pair<String, Integer> festivalBeerNameAndId : festivalBeerNamesAndIdsList) {
			final Bundle arguments = new Bundle();
			arguments.putString(BeerDetailFragment.FESTIVAL_BEER_NAME_KEY, festivalBeerNameAndId.first);

			final int festivalBeerId = festivalBeerNameAndId.second.intValue();
			arguments.putInt(BeerDetailFragment.FESTIVAL_BEER_ID_KEY, festivalBeerId);

			if (festivalBeerId == currentfestivalBeerId) {
				currentItem = festivalBeerNamesAndIdsList.indexOf(festivalBeerNameAndId);
			}

			final BeerDetailFragment beerDetailFragment = new BeerDetailFragment();
			beerDetailFragment.setArguments(arguments);

			fragmentList.add(beerDetailFragment);
		}

		viewPager.getAdapter().notifyDataSetChanged();
		viewPager.setCurrentItem(currentItem);
	}

	public void show(final List<String> festivalBeerNamesList, final List<Integer> festivalBeerIdList, final int currentfestivalBeerId) {

		int currentItem = 0;
		fragmentList.clear();

		for (int i = 0; i < festivalBeerIdList.size(); i++) {
			final Bundle arguments = new Bundle();
			arguments.putString(BeerDetailFragment.FESTIVAL_BEER_NAME_KEY, festivalBeerNamesList.get(i));

			final int festivalBeerId = festivalBeerIdList.get(i);
			arguments.putInt(BeerDetailFragment.FESTIVAL_BEER_ID_KEY, festivalBeerId);

			if (festivalBeerId == currentfestivalBeerId) {
				currentItem = i;
			}

			final BeerDetailFragment beerDetailFragment = new BeerDetailFragment();
			beerDetailFragment.setArguments(arguments);

			fragmentList.add(beerDetailFragment);
		}

		viewPager.getAdapter().notifyDataSetChanged();
		viewPager.setCurrentItem(currentItem);
	}

	private class PagerTestAdapter extends FragmentPagerAdapter {

		public PagerTestAdapter(final FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(final int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(final int position) {
			return fragmentList.get(position).getArguments().getString(BeerDetailFragment.FESTIVAL_BEER_NAME_KEY);
		}
	}

	// / ----------------------------

	ActivityLifecycleMonitor.ActivityLifecycleListener activityLifecycleListener = new ActivityLifecycleMonitor.ActivityLifecycleListener() {

		@Override
		public void onStop() {
			// Not used
		}

		@Override
		public void onStart() {
			// Not used
		}

		@Override
		public void onSaveInstanceState(final Bundle savedInstanceState) {
			// Not used
		}

		@Override
		public void onResume() {
			activity.getOptionsMenuMonitor().addListener(optionsMenuListener);

		}

		@Override
		public void onRestoreInstanceState(final Bundle savedInstanceState) {
			// Not used
		}

		@Override
		public void onRestart() {
			// Not used
		}

		@Override
		public void onPostCreate(final Bundle savedInstanceState) {
			// Not used
		}

		@Override
		public void onPause() {
			activity.getOptionsMenuMonitor().removeListener(optionsMenuListener);
		}

		@Override
		public void onDestroy() {
			// Not used
		}

		@Override
		public void onCreate(final Bundle savedInstanceState) {
			// Not used
		}

		@Override
		public void onBackPressed() {
			// Not used
		}

		@Override
		public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
			// Not used
		}

		@Override
		public void onConfigurationChanged(final Configuration newConfig) {
			// Not used
		}
	};

	OptionsMenuListener optionsMenuListener = new OptionsMenuListener() {

		@Override
		public boolean onOptionsItemSelected(final MenuItem item) {
			switch (item.getItemId()) {
			case android.R.id.home:
				activity.finish();
				return true;
			}
			return false;
		}

		@Override
		public boolean onCreateOptionsMenu(final Menu menu) {
			// Not used
			return false;
		}
	};

	public void showError(final String message) {
		// TODO Auto-generated method stub

	}

	public int getCurrentItem() {
		final int currentItem = viewPager.getCurrentItem();
		if (fragmentList.size() <= currentItem) {
			return 0;
		}
		return fragmentList.get(currentItem).getArguments().getInt(BeerDetailFragment.FESTIVAL_BEER_ID_KEY);
	}
}
