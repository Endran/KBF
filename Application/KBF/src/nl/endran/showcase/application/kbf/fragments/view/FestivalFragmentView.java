package nl.endran.showcase.application.kbf.fragments.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import nl.endran.showcaselib.datacontainers.Festival;
import nl.endran.showcaseui.R;

public class FestivalFragmentView {

	private final View rootView;

	private final TextView textViewFestivalName;
	private final TextView textViewLocation;
	private final TextView textViewDescription;

	private final View relativeLayoutContent;

	public FestivalFragmentView(final LayoutInflater inflater, final Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_festival, null);

		relativeLayoutContent = rootView.findViewById(R.id.relativeLayoutContent);
		relativeLayoutContent.setVisibility(View.GONE);

		textViewFestivalName = (TextView) rootView.findViewById(R.id.textViewFestivalName);
		textViewLocation = (TextView) rootView.findViewById(R.id.textViewLocation);
		textViewDescription = (TextView) rootView.findViewById(R.id.textViewDescription);
	}

	public View getRootView() {
		return rootView;
	}

	public void showError(final int stringResourceId) {
		showError(rootView.getContext().getString(stringResourceId));
	}

	public void showError(final String message) {
	}

	public void showFestival(final Festival festival) {
		if (festival == null) {
			showError(R.string.errorWhileRetrievingResults);
		}

		relativeLayoutContent.setVisibility(View.VISIBLE);
		textViewFestivalName.setText(festival.getName());
		textViewLocation.setText(festival.getLocation());
		textViewDescription.setText(festival.getDescription());
	}
}
