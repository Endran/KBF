package nl.endran.showcase.application.kbf;

import nl.endran.activity.EndranActivity;
import nl.endran.showcase.application.kbf.dialogs.presenter.SearchDialog;
import nl.endran.showcase.application.kbf.dialogs.presenter.SearchDialog.SearchDialogListener;
import nl.endran.showcaselib.datacontainers.FilterOptions;
import nl.endran.showcaselib.datacontainers.SortOptions;
import nl.endran.showcaseui.R;
import android.app.Dialog;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityView {

	public interface MainActivityViewListener {
		public void onSearchEntered(String searchString, FilterOptions filterOptions, SortOptions sortOptions);
	}

	private final EndranActivity activity;
	private final MainActivityViewListener listener;

	private SparseArray<Pair<Fragment, String>> fragmentArray = new SparseArray<Pair<Fragment, String>>();

	private final ViewPager viewPager;
	private final LinearLayout linearLayoutLoading;

	private final Handler loadingHandler;
	private final Runnable loadingRunnable;

	Dialog searchDialog;
	private final ProgressBar progressBarLoading;
	private final TextView textViewLoading;

	public MainActivityView(final EndranActivity activity, final MainActivityViewListener listener) {
		this.activity = activity;
		this.listener = listener;
		activity.setContentView(R.layout.activity_main);

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

		linearLayoutLoading = (LinearLayout) activity.findViewById(R.id.linearLayoutLoading);
		progressBarLoading = (ProgressBar) activity.findViewById(R.id.progressBarLoading);
		textViewLoading = (TextView) activity.findViewById(R.id.textViewLoading);

		loadingRunnable = new Runnable() {

			@Override
			public void run() {
				linearLayoutLoading.setVisibility(View.VISIBLE);
			}
		};
		loadingHandler = new Handler();
		loadingHandler.postDelayed(loadingRunnable, 500);
	}

	public void show(final SparseArray<Pair<Fragment, String>> fragmentArray, final int currentItem) {
		loadingHandler.removeCallbacks(loadingRunnable);
		linearLayoutLoading.setVisibility(View.GONE);

		this.fragmentArray = fragmentArray;
		viewPager.getAdapter().notifyDataSetChanged();

		if (currentItem < fragmentArray.size()) {
			viewPager.setCurrentItem(currentItem);
		}
	}

	public void showError(final int messageId) {
		showError(activity.getString(messageId));
	}

	public void showError(final String message) {
		progressBarLoading.setVisibility(View.GONE);
		textViewLoading.setText(message);
	}

	private class PagerTestAdapter extends FragmentPagerAdapter {

		public PagerTestAdapter(final FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(final int position) {
			return fragmentArray.get(position).first;
		}

		@Override
		public int getCount() {
			return fragmentArray.size();
		}

		@Override
		public CharSequence getPageTitle(final int position) {
			return fragmentArray.get(position).second;
		}
	}

	public int getCurrentItem() {
		final int currentItem = viewPager.getCurrentItem();
		return currentItem;
	}

	public void showSearchDialog() {
		if (searchDialog != null && searchDialog.isShowing()) {
			searchDialog.dismiss();
			searchDialog = null;
		}

		searchDialog = new SearchDialog(activity, new SearchDialogListener() {

			@Override
			public void onSearchEntered(final String searchInput, final FilterOptions filterOptions, final SortOptions sortOptions) {
				listener.onSearchEntered(searchInput, filterOptions, sortOptions);
			}

			@Override
			public void onDismiss() {
				searchDialog = null;

			}
		});
		searchDialog.show();
	}

	public void showMessage(final int messageId) {
		showMessage(activity.getString(messageId));
	}

	public void showMessage(final String message) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}
}
