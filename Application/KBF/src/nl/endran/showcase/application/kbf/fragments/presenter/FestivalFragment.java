package nl.endran.showcase.application.kbf.fragments.presenter;

import nl.endran.activity.EndranSupportV4Fragment;
import nl.endran.showcase.application.kbf.BaseShowcaseActivity.ShowcaseLibraryListener;
import nl.endran.showcase.application.kbf.MainActivity;
import nl.endran.showcase.application.kbf.fragments.view.FestivalFragmentView;
import nl.endran.showcaselib.ShowcaseLibrary;
import nl.endran.showcaselib.datacontainers.Festival;
import nl.endran.showcaselib.usecases.data.GetFestivalCommand.GetFestivalListener;
import nl.endran.showcaseui.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FestivalFragment extends EndranSupportV4Fragment {

	FestivalFragmentView festivalFragmentView;

	MainActivity activity;
	private ShowcaseLibrary showcaseLibrary;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		festivalFragmentView = new FestivalFragmentView(inflater, savedInstanceState);
		return festivalFragmentView.getRootView();
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
				FestivalFragment.this.showcaseLibrary = showcaseLibrary;
				getFestival();
			}
		});
	}

	private void getFestival() {
		showcaseLibrary.getDataAccess().getFestival(getResources().getInteger(R.integer.festivalId), new GetFestivalListener() {

			@Override
			public void onFail(final String message) {
				festivalFragmentView.showError(message);
			}

			@Override
			public void onFestival(final Festival festival) {
				festivalFragmentView.showFestival(festival);
			}
		});
	}
}
