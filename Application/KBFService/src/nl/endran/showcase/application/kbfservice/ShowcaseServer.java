package nl.endran.showcase.application.kbfservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import nl.endran.showcase.backend.crispe.CrispeBackend;
import nl.endran.showcase.database.sqllite.SqlLiteDatabse;
import nl.endran.showcaselib.ShowcaseLibrary;
import nl.endran.showcaselib.connectors.ShowcaseBackend;
import nl.endran.showcaselib.connectors.ShowcaseDatabase;

public class ShowcaseServer extends Service {

	private ShowcaseLibrary showcaseLibrary;

	private final IBinder localBinder = new LocalBinder();

	@Override
	public IBinder onBind(final Intent intent) {
		lazyInitializeShowcaseLibrary();
		return localBinder;
	}

	@Override
	public void onDestroy() {
		showcaseLibrary.onDestroy();
		super.onDestroy();
	}

	private class LocalBinder extends Binder implements ShowcaseServerBinder {
		public ShowcaseLibrary getShowcaseLibrary() {
			return showcaseLibrary;
		}
	};

	private void lazyInitializeShowcaseLibrary() {
		if (showcaseLibrary == null) {
			final ShowcaseDatabase showcaseDatabase = new SqlLiteDatabse(this);
			final ShowcaseBackend showcaseBackend = new CrispeBackend(this);

			showcaseLibrary = new ShowcaseLibrary(showcaseDatabase, showcaseBackend);
		}
	}
}
