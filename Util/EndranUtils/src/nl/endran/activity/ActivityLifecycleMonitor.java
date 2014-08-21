package nl.endran.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class ActivityLifecycleMonitor {

	ArrayList<ActivityLifecycleListener> listeners = new ArrayList<ActivityLifecycleMonitor.ActivityLifecycleListener>();

	public interface ActivityLifecycleListener {
		public void onCreate(final Bundle savedInstanceState);

		public void onPostCreate(final Bundle savedInstanceState);

		public void onResume();

		public void onPause();

		public void onStart();

		public void onRestart();

		public void onStop();

		public void onDestroy();

		public void onBackPressed();

		public void onSaveInstanceState(final Bundle savedInstanceState);

		public void onRestoreInstanceState(final Bundle savedInstanceState);

		public void onActivityResult(final int requestCode, final int resultCode, final Intent data);

		public void onConfigurationChanged(Configuration newConfig);
	}

	public void addListener(final ActivityLifecycleListener listener) {
		listeners.add(listener);
	}

	public void removeListener(final ActivityLifecycleListener listener) {
		listeners.remove(listener);
	}

	void onCreate(final Bundle savedInstanceState) {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onCreate(savedInstanceState);
		}
	}

	void onPostCreate(final Bundle savedInstanceState) {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onPostCreate(savedInstanceState);
		}
	}

	void onResume() {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onResume();
		}
	}

	void onPause() {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onPause();
		}
	}

	void onStart() {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onStart();
		}
	}

	void onRestart() {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onRestart();
		}
	}

	void onStop() {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onStop();
		}
	}

	void onDestroy() {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onDestroy();
		}
	}

	void onBackPressed() {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onBackPressed();
		}
	}

	void onSaveInstanceState(final Bundle savedInstanceState) {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onSaveInstanceState(savedInstanceState);
		}
	}

	void onRestoreInstanceState(final Bundle savedInstanceState) {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onRestoreInstanceState(savedInstanceState);
		}
	}

	void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onActivityResult(requestCode, resultCode, data);
		}
	}

	public void onConfigurationChanged(final Configuration newConfig) {
		for (final ActivityLifecycleListener listener : listeners) {
			listener.onConfigurationChanged(newConfig);
		}
	}
}
