package nl.endran.activity;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import com.google.analytics.tracking.android.EasyTracker;

public class EndranTracking {
	private static final EndranTracking instance = new EndranTracking();

	protected ILogger log = LoggerFactory.GetLogger(this);

	public static final String ALLOW_STAT_KEY = "ALLOW_STAT_KEY";

	private boolean initialized = false;
	private boolean allowTracking = false;

	private EndranTracking() {
	}

	public static EndranTracking getInstance() {
		return instance;
	}

	// public void setAllowTracking(final Activity activity, boolean
	// allowTracking) {
	// storeContext(activity.getApplicationContext());
	//
	// if (initialized && this.allowTracking != allowTracking && allowTracking
	// == false) {
	// EasyTracker.getInstance().activityStop(activity);
	// } else if (initialized && this.allowTracking != allowTracking &&
	// allowTracking == true) {
	// EasyTracker.getInstance().activityStart(activity);
	// }
	// this.allowTracking = allowTracking;
	// }

	private void lazyInitialize(Context applicationContext) {
		if (!initialized) {
			initialized = true;
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
			allowTracking = sharedPrefs.getBoolean(ALLOW_STAT_KEY, false);

			sharedPrefs.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
				public void onSharedPreferenceChanged(SharedPreferences sharedPrefs, String key) {
					log.verbose("onSharedPreferenceChanged with key: " + key);
					if (ALLOW_STAT_KEY == key) {
						allowTracking = sharedPrefs.getBoolean(ALLOW_STAT_KEY, true);
					}
				}
			});
		}
	}

	public void activityStart(final Activity activity) {
		lazyInitialize(activity.getApplicationContext());
		if (allowTracking) {
			try {
				EasyTracker.getInstance().activityStart(activity);
			} catch (Exception ex) {
				log.error("Exception occurred in activityStart", ex);
			}
		}
	}

	public void activityStop(final Activity activity) {
		if (allowTracking) {
			try {
				EasyTracker.getInstance().activityStop(activity);
			} catch (Exception ex) {
				log.error("Exception occurred in activityStop", ex);
			}
		}
	}

	public void trackView(final String view) {
		if (allowTracking) {
			try {
				EasyTracker.getTracker().trackView(view);
			} catch (Exception ex) {
				log.error("Exception occurred in trackView", ex);
			}
		}
	}

}
