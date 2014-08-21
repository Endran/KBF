package nl.endran.showcase.application.kbf.fragments.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import nl.endran.showcaselib.datacontainers.Brewer;
import nl.endran.showcaseui.R;

public class BrewersFragmentView {

	private final View rootView;

	private final ListView listViewBrewers;

	private final BrewersAdapter brewersAdapter;

	public BrewersFragmentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_brewers, null);

		listViewBrewers = (ListView) rootView.findViewById(R.id.listViewBrewers);
		brewersAdapter = new BrewersAdapter(inflater);
		listViewBrewers.setAdapter(brewersAdapter);
	}

	public View getRootView() {
		return rootView;
	}

	public void showError(final String message) {
		// TODO Auto-generated method stub
	}

	public void setBrewers(final List<Brewer> brewers) {
		brewersAdapter.setBrewers(brewers);
		brewersAdapter.notifyDataSetChanged();
	}

	private class BrewersAdapter extends BaseAdapter {

		private final LayoutInflater inflater;
		private List<Brewer> brewers = new ArrayList<Brewer>();

		public BrewersAdapter(final LayoutInflater inflater) {
			this.inflater = inflater;
		}

		public void setBrewers(final List<Brewer> brewers) {
			this.brewers = brewers;
		}

		public int getCount() {
			return brewers.size();
		}

		public Object getItem(final int position) {
			return brewers.get(position);
		}

		public long getItemId(final int position) {
			return position;
		}

		public View getView(final int position, View convertView, final ViewGroup parent) {
			convertView = inflater.inflate(android.R.layout.two_line_list_item, null);

			final TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
			final TextView text2 = (TextView) convertView.findViewById(android.R.id.text2);

			final Brewer brewer = brewers.get(position);

			text1.setText(brewer.getName());
			text2.setText(brewer.getLocation());

			return convertView;
		}
	}
}
