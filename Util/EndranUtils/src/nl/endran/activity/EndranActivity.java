package nl.endran.activity;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public abstract class EndranActivity extends FragmentActivity {
	protected ILogger log;

	/**
	 * Boolean indicating that the activity is running in the foreground. It is
	 * in between the onResume and onPause lifecycle steps.
	 */
	private boolean isInFront = true;

	private final ActivityLifecycleMonitor activityLifecycleMonitor = new ActivityLifecycleMonitor();
	private final OptionsMenuMonitor optionsMenuMonitor = new OptionsMenuMonitor();

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		initializeLogger();
		log = LoggerFactory.GetLogger(this);
		log.verbose("Logger acquired in EndranFragmentActivity");

		log.verbose("onCreate");
		super.onCreate(savedInstanceState);

		activityLifecycleMonitor.onCreate(savedInstanceState);
	}

	/**
	 * Override this in the first/main activity. Initialize the logger,
	 * otherwise the loglevel is set to VERBOSE and the logtag to
	 * UninitializedLogTag. <br>
	 * <br>
	 * Example implementation: <br>
	 * LoggerFactory.setLogLevel(LogLevel.VERBOSE); <br>
	 * LoggerFactory.setLogTag(MyLogTag); <br>
	 * LoggerFactory.setLogLevelForClass(SomeClass.class.getSimpleName(),
	 * LogLevel.ERROR); <br>
	 */
	protected abstract void initializeLogger();

	@Override
	protected void onPostCreate(final Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		log.verbose("onPostCreate");
		activityLifecycleMonitor.onPostCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		log.verbose("onResume");
		isInFront = true;
		super.onResume();
		activityLifecycleMonitor.onResume();
	}

	@Override
	protected void onPause() {
		log.verbose("onPause");
		isInFront = false;
		super.onPause();
		activityLifecycleMonitor.onPause();
	}

	@Override
	protected void onStart() {
		log.verbose("onStart");
		super.onStart();

		try {
			log.verbose("EasyTracker activityStart");
			EndranTracking.getInstance().activityStart(this);
		} catch (final Exception ex) {
			log.warn("onStart Exception in EndranTracking.getInstance().activityStart(this)", ex);
		}

		activityLifecycleMonitor.onStart();
	}

	@Override
	protected void onRestart() {
		log.verbose("onRestart");
		super.onRestart();
		activityLifecycleMonitor.onRestart();
	}

	@Override
	protected void onStop() {
		log.verbose("onStop");
		super.onStop();

		try {
			log.verbose("EasyTracker activityStop");
			EndranTracking.getInstance().activityStop(this);
		} catch (final Exception ex) {
			log.warn("onStop Exception in EndranTracking.getInstance().activityStart(this)", ex);
		}

		activityLifecycleMonitor.onStop();
	}

	@Override
	protected void onDestroy() {
		log.verbose("onDestroy");
		super.onDestroy();
		activityLifecycleMonitor.onDestroy();
	}

	@Override
	public void onBackPressed() {
		log.verbose("onBackPressed");
		super.onBackPressed();
		activityLifecycleMonitor.onBackPressed();
	}

	@Override
	protected void onSaveInstanceState(final Bundle savedInstanceState) {
		log.verbose("onSaveInstanceState");
		super.onSaveInstanceState(savedInstanceState);
		activityLifecycleMonitor.onSaveInstanceState(savedInstanceState);
	}

	@Override
	protected void onRestoreInstanceState(final Bundle savedInstanceState) {
		log.verbose("onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
		activityLifecycleMonitor.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		log.verbose("onActivityResult(requestCode=" + requestCode + ", resultCode=" + resultCode + ", data=" + data
				+ ")");
		super.onActivityResult(requestCode, resultCode, data);
		activityLifecycleMonitor.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onConfigurationChanged(final Configuration newConfig) {
		log.verbose("onConfigurationChanged");
		super.onConfigurationChanged(newConfig);
		activityLifecycleMonitor.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		log.verbose("onCreateOptionsMenu");
		if (optionsMenuMonitor.onCreateOptionsMenu(menu)) {
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		log.verbose("onOptionsItemSelected");
		if (optionsMenuMonitor.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public ActivityLifecycleMonitor getActivityLifecycleMonitor() {
		return activityLifecycleMonitor;
	};

	public OptionsMenuMonitor getOptionsMenuMonitor() {
		return optionsMenuMonitor;
	}

	/**
	 * Boolean indicating that the activity is running in the foreground. It is
	 * in between the onResume and onPause lifecycle steps.
	 */
	public boolean isInFront() {
		return isInFront;
	}
}
