package nl.endran.activity;

import java.util.ArrayList;

import android.view.Menu;
import android.view.MenuItem;

public class OptionsMenuMonitor {

	ArrayList<OptionsMenuListener> listeners = new ArrayList<OptionsMenuMonitor.OptionsMenuListener>();

	public interface OptionsMenuListener {
		public boolean onCreateOptionsMenu(final Menu menu);

		public boolean onOptionsItemSelected(final MenuItem item);
	}

	public void addListener(final OptionsMenuListener listener) {
		listeners.add(listener);
	}

	public void removeListener(final OptionsMenuListener listener) {
		listeners.remove(listener);
	}

	public boolean onCreateOptionsMenu(final Menu menu) {
		for (final OptionsMenuListener listener : listeners) {
			if (listener.onCreateOptionsMenu(menu)) {
				return true;
			}
		}
		return false;
	}

	public boolean onOptionsItemSelected(final MenuItem item) {
		for (final OptionsMenuListener listener : listeners) {
			if (listener.onOptionsItemSelected(item)) {
				return true;
			}
		}
		return false;
	}

}
