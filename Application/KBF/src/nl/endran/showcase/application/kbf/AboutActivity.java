package nl.endran.showcase.application.kbf;

import nl.endran.activity.EndranActivity;
import nl.endran.logging.LogLevel;
import nl.endran.logging.LoggerFactory;
import nl.endran.showcaseui.R;
import android.os.Bundle;

public class AboutActivity extends EndranActivity {
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	@Override
	protected void initializeLogger() {
		LoggerFactory.setLogLevel(LogLevel.ERROR);
		LoggerFactory.setLogTag(getString(R.string.app_name));
	}
}
