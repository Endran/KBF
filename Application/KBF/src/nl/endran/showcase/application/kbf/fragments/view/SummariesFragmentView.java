package nl.endran.showcase.application.kbf.fragments.view;

import java.util.ArrayList;
import java.util.List;

import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaseui.R;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SummariesFragmentView {

	public interface SummariesFragmentViewListener {
		public void onSummaryClicked(int festivalBeerId);
	}

	private final View rootView;

	private final ListView listViewSummaries;
	private final TextView textViewEmptyText;

	private final SummariesAdapter summariesAdapter;

	public SummariesFragmentView(final LayoutInflater inflater, final Bundle savedInstanceState, final SummariesFragmentViewListener listener) {
		rootView = inflater.inflate(R.layout.fragment_summaries, null);

		listViewSummaries = (ListView) rootView.findViewById(R.id.listViewSummaries);

		textViewEmptyText = (TextView) rootView.findViewById(R.id.textViewEmptyText);
		listViewSummaries.setEmptyView(textViewEmptyText);

		summariesAdapter = new SummariesAdapter(inflater);
		listViewSummaries.setAdapter(summariesAdapter);

		listViewSummaries.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
				listener.onSummaryClicked((int) id);
			}
		});
	}

	public View getRootView() {
		return rootView;
	}

	public void setEmptyViewMessage(final String emptyViewMessage) {
		textViewEmptyText.setText(emptyViewMessage);
	}

	public void showError(final String message) {
		Toast.makeText(rootView.getContext(), message, Toast.LENGTH_SHORT).show();
	}

	public void setSummaries(final List<Summary> summaries) {
		summariesAdapter.setSummaries(summaries);
	}

	private class SummariesAdapter extends BaseAdapter {

		private final LayoutInflater inflater;
		private List<Summary> summaries = new ArrayList<Summary>();
		private final SparseArray<UserBeer> userBeerArray = new SparseArray<UserBeer>();
		private boolean summariesIsSet;
		private boolean userBeersIsSet;

		public SummariesAdapter(final LayoutInflater inflater) {
			this.inflater = inflater;
		}

		public void setSummaries(final List<Summary> summaries) {
			this.summaries = summaries;
			summariesIsSet = true;
		}

		public void setUserBeers(final List<UserBeer> userBeers) {
			userBeerArray.clear();
			for (final UserBeer userBeer : userBeers) {
				userBeerArray.put(userBeer.getBeerId(), userBeer);
			}

			userBeersIsSet = true;
			checkDataComplete();
		}

		private void checkDataComplete() {
			if (summariesIsSet && userBeersIsSet) {
				summariesAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public int getCount() {
			return summaries.size();
		}

		@Override
		public Object getItem(final int position) {
			return summaries.get(position);
		}

		@Override
		public long getItemId(final int position) {
			return summaries.get(position).getFestivalBeerID();
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null || convertView.getTag() == null || !(convertView.getTag() instanceof ViewHolder)) {
				convertView = inflater.inflate(R.layout.list_item_beer, null);
				viewHolder = new ViewHolder();

				viewHolder.imageViewLogo = (ImageView) convertView.findViewById(R.id.imageViewLogo);

				viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
				viewHolder.textViewSubTitle = (TextView) convertView.findViewById(R.id.textViewSubTitle);

				viewHolder.imageViewRecommended = (ImageView) convertView.findViewById(R.id.imageViewRecommended);
				viewHolder.imageViewCheck = (ImageView) convertView.findViewById(R.id.imageViewCheck);
				viewHolder.imageViewWhishlist = (ImageView) convertView.findViewById(R.id.imageViewWhishlist);
				viewHolder.imageViewFavorite = (ImageView) convertView.findViewById(R.id.imageViewFavorite);

				viewHolder.imageViewAward = (ImageView) convertView.findViewById(R.id.imageViewAward);
				viewHolder.imageViewNew = (ImageView) convertView.findViewById(R.id.imageViewNew);
				viewHolder.imageViewStock = (ImageView) convertView.findViewById(R.id.imageViewStock);
				viewHolder.imageViewPrice = (ImageView) convertView.findViewById(R.id.imageViewPrice);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			final Summary summary = summaries.get(position);

			final UserBeer userBeer = userBeerArray.get(summary.getBeerId());

			// Set the titles
			viewHolder.textViewTitle.setText(summary.getFestivalBeerNumber() + " - " + summary.getBeerName());
			viewHolder.textViewSubTitle.setText(summary.getBrewerName());

			// Set award
			if (summary.getFestivalBeerAward() == null || summary.getFestivalBeerAward().length() <= 0) {
				viewHolder.imageViewAward.setVisibility(View.GONE);
			} else {
				viewHolder.imageViewAward.setVisibility(View.VISIBLE);
			}

			// Set first time
			if (summary.isFestivalBeerFirstTime()) {
				viewHolder.imageViewNew.setVisibility(View.VISIBLE);
			} else {
				viewHolder.imageViewNew.setVisibility(View.GONE);
			}

			// Set stock
			if (summary.getStockName() != null && summary.getStockName().equals("Last")) {
				viewHolder.imageViewStock.setImageResource(R.drawable.warn);
				viewHolder.imageViewStock.setVisibility(View.VISIBLE);
			} else if (summary.getStockName() != null && summary.getStockName().equals("Empty")) {
				viewHolder.imageViewStock.setImageResource(R.drawable.deny);
				viewHolder.imageViewStock.setVisibility(View.VISIBLE);
			} else {
				viewHolder.imageViewStock.setVisibility(View.GONE);
			}

			// Set price
			if (summary.getFestivalBeerPrice() >= 4) {
				viewHolder.imageViewPrice.setImageResource(R.drawable.four);
				viewHolder.imageViewPrice.setVisibility(View.VISIBLE);
			} else if (summary.getFestivalBeerPrice() >= 3) {
				viewHolder.imageViewPrice.setImageResource(R.drawable.three);
				viewHolder.imageViewPrice.setVisibility(View.VISIBLE);
			} else if (summary.getFestivalBeerPrice() >= 2) {
				viewHolder.imageViewPrice.setImageResource(R.drawable.two);
				viewHolder.imageViewPrice.setVisibility(View.VISIBLE);
			} else if (summary.getFestivalBeerPrice() >= 1) {
				viewHolder.imageViewPrice.setImageResource(R.drawable.one);
				viewHolder.imageViewPrice.setVisibility(View.VISIBLE);
			} else {
				viewHolder.imageViewPrice.setVisibility(View.GONE);
			}

			if (userBeer != null) {

				// Set rated
				if (userBeer.getRating() > 0) {
					viewHolder.imageViewCheck.setVisibility(View.VISIBLE);
				} else {
					viewHolder.imageViewCheck.setVisibility(View.GONE);
				}

				// Set wishlist
				if (userBeer.getWishlist()) {
					viewHolder.imageViewWhishlist.setVisibility(View.VISIBLE);
				} else {
					viewHolder.imageViewWhishlist.setVisibility(View.GONE);
				}

				// Set favorite
				if (userBeer.getFavorite()) {
					viewHolder.imageViewFavorite.setVisibility(View.VISIBLE);
				} else {
					viewHolder.imageViewFavorite.setVisibility(View.GONE);
				}

			} else {

				// Set rated
				viewHolder.imageViewCheck.setVisibility(View.GONE);

				// Set wishlist
				viewHolder.imageViewWhishlist.setVisibility(View.GONE);

				// Set favorite
				viewHolder.imageViewFavorite.setVisibility(View.GONE);
			}

			// Set recommendation
			viewHolder.imageViewRecommended.setVisibility(View.GONE);

			return convertView;
		}
	}

	public static class ViewHolder {
		ImageView imageViewLogo;
		TextView textViewTitle;
		TextView textViewSubTitle;
		ImageView imageViewRecommended;
		ImageView imageViewCheck;
		ImageView imageViewWhishlist;
		ImageView imageViewFavorite;
		ImageView imageViewAward;
		ImageView imageViewNew;
		ImageView imageViewStock;
		ImageView imageViewPrice;
	}

	public void setUserBeers(final List<UserBeer> userBeers) {
		summariesAdapter.setUserBeers(userBeers);
	}
}
