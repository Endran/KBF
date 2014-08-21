package nl.endran.showcase.application.kbf.menu;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract class ShowCaseMenu {
	ILogger log = LoggerFactory.GetLogger(this);

	public abstract void createOptionsMenu(Menu menu, MenuInflater menuInflater);

	public abstract boolean optionsItemSelected(MenuItem item);

}