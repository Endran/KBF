package nl.endran.showcase.application.kbf.menu;

import nl.endran.showcaseui.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainMenu extends ShowCaseMenu {
	// private final ILogger log = LoggerFactory.GetLogger(this);

	private final Activity activity;
	private final MainMenuListener listener;

	// private Menu menu;

	public interface MainMenuListener {
		public boolean onSearchClicked();

		public boolean onSettingsClicked();

		public boolean onRateClicked();

		public boolean onAboutClicked();

		public boolean onAccountClicked();
	}

	@SuppressLint("NewApi")
	public MainMenu(final Activity activity, final MainMenuListener listener) {
		this.activity = activity;
		this.listener = listener;
	}

	@Override
	public void createOptionsMenu(final Menu menu, final MenuInflater menuInflater) {
		menuInflater.inflate(R.menu.main, menu);
	}

	@Override
	public boolean optionsItemSelected(final MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			activity.finish();
			return true;
		case R.id.action_search:
			return listener.onSearchClicked();
		case R.id.action_rate:
			return listener.onRateClicked();
		case R.id.action_about:
			return listener.onAboutClicked();
		case R.id.action_account:
			return listener.onAccountClicked();
		default:
			return false;
		}
	}
}
