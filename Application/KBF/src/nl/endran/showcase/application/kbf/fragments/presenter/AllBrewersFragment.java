package nl.endran.showcase.application.kbf.fragments.presenter;

import java.util.List;

import nl.endran.activity.EndranSupportV4Fragment;
import nl.endran.showcase.application.kbf.BaseShowcaseActivity.ShowcaseLibraryListener;
import nl.endran.showcase.application.kbf.MainActivity;
import nl.endran.showcase.application.kbf.fragments.view.BrewersFragmentView;
import nl.endran.showcaselib.ShowcaseLibrary;
import nl.endran.showcaselib.datacontainers.Brewer;
import nl.endran.showcaselib.usecases.data.GetAllBrewersCommand.GetAllBrewersListener;
import nl.endran.showcaseui.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AllBrewersFragment extends EndranSupportV4Fragment {

	BrewersFragmentView brewersFragmentView;

	MainActivity activity;
	private ShowcaseLibrary showcaseLibrary;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		brewersFragmentView = new BrewersFragmentView(inflater, savedInstanceState);
		return brewersFragmentView.getRootView();
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final FragmentActivity fragmentActivity = getActivity();
		if (!(fragmentActivity instanceof MainActivity)) {
			log.error("onActivityCreated, could not cast getActivity() to MainActivity");
			return;
		}

		activity = (MainActivity) fragmentActivity;
		activity.retrieveShowcaseLibrary(new ShowcaseLibraryListener() {
			@Override
			public void onShowcaseLibrary(final ShowcaseLibrary showcaseLibrary) {
				AllBrewersFragment.this.showcaseLibrary = showcaseLibrary;
				getBrewers();
			}
		});
	}

	private void getBrewers() {
		showcaseLibrary.getDataAccess().getAllBrewers(new GetAllBrewersListener() {

			@Override
			public void onFail(final String message) {
				brewersFragmentView.showError(message);
			}

			@Override
			public void onBrewers(final List<Brewer> brewers) {
				brewersFragmentView.setBrewers(brewers);
			}
		});
	}

	@Override
	public String toString() {
		return getString(R.string.allBrewersFragmentName);
	}
}
