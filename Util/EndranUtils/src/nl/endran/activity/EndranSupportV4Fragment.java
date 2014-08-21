package nl.endran.activity;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EndranSupportV4Fragment extends Fragment {
	protected ILogger log = LoggerFactory.GetLogger(this);
	protected String fragmentName;
	protected boolean trackFragments = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		log.verbose("onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		log.verbose("onAttach");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		log.verbose("onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		log.verbose("onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		log.verbose("onResume");
		super.onResume();
		if (trackFragments) {
			try {
				if (fragmentName == null || fragmentName.equals("")) {
					fragmentName = this.getClass().getSimpleName();
				}
				EndranTracking.getInstance().trackView("/" + fragmentName);
			} catch (Exception ex) {
				log.warn("onResume Exception in EndranTracking.getInstance().trackView(" + fragmentName + ");", ex);
			}
		}
	}

	@Override
	public void onStart() {
		log.verbose("onStart");
		super.onStart();
	}

	@Override
	public void onPause() {
		log.verbose("onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		log.verbose("onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		log.verbose("onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		log.verbose("onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		log.verbose("onDetach");
		super.onDetach();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		log.verbose("onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}
}
