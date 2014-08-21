package nl.endran.showcase.application.kbf;

import java.util.ArrayList;
import java.util.List;

import nl.endran.activity.EndranActivity;
import nl.endran.showcase.application.kbfservice.ShowcaseServer;
import nl.endran.showcase.application.kbfservice.ShowcaseServerBinder;
import nl.endran.showcaselib.ShowcaseLibrary;
import nl.endran.showcaselib.usecases.backend.SynchronizeCommand.SynchronizeAllListener;
import nl.endran.showcaselib.usecases.backend.UploadUserDataCommand.UploadUserDataCommandListener;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public abstract class BaseShowcaseActivity extends EndranActivity {

	public interface ShowcaseLibraryListener {
		public void onShowcaseLibrary(ShowcaseLibrary showcaseLibrary);
	}

	private final List<ShowcaseLibraryListener> listenerList = new ArrayList<ShowcaseLibraryListener>();;
	protected ShowcaseLibrary showcaseLibrary;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		Thread.setDefaultUncaughtExceptionHandler(new KbfDefaultUncaughtExceptionHandler(this));
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();

		bindService(getServiceIntent(), showcaseServiceServiceConnection, Service.BIND_AUTO_CREATE);
	}

	@Override
	protected void onPause() {
		super.onPause();

		unbindService(showcaseServiceServiceConnection);
	}

	private Intent getServiceIntent() {
		final Intent serviceIntent = new Intent(this, ShowcaseServer.class);
		return serviceIntent;
	}

	private final ServiceConnection showcaseServiceServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(final ComponentName name, final IBinder binder) {
			if (!(binder instanceof ShowcaseServerBinder)) {
				log.error("binder is not an instance of ShowcaseServerBinder");
				return;
			}

			showcaseLibrary = ((ShowcaseServerBinder) binder).getShowcaseLibrary();
			onShowcaseServiceConnected(showcaseLibrary);

			syncAll();
			uploadUserData();

			while (listenerList.size() > 0) {
				final ShowcaseLibraryListener listener = listenerList.remove(0);
				listener.onShowcaseLibrary(showcaseLibrary);
			}
		}

		@Override
		public void onServiceDisconnected(final ComponentName name) {
			showcaseLibrary = null;
			onShowcaseServiceDisconnected();
		}
	};

	public void retrieveShowcaseLibrary(final ShowcaseLibraryListener listener) {
		if (showcaseLibrary != null) {
			listener.onShowcaseLibrary(showcaseLibrary);
		} else {
			listenerList.add(listener);
		}
	}

	protected void syncAll() {
		showcaseLibrary.getBackendAccess().synchronizeAll(new SynchronizeAllListener() {

			@Override
			public void onFail(final String message) {
				log.error("SynchronizeAllListener onFail: " + message);
			}

			@Override
			public void onSynced() {
				log.info("SynchronizeAllListener onSynced");
			}
		});
	}

	private void uploadUserData() {
		showcaseLibrary.getBackendAccess().uploadUserDataCommand(new UploadUserDataCommandListener() {

			@Override
			public void onFail(final String message) {
				log.info("UploadUserDataCommandListener onFail " + message);
			}

			@Override
			public void onUpdated() {
				log.info("UploadUserDataCommandListener onUpdated");
			}

			@Override
			public void onNotUpdated() {
				log.info("UploadUserDataCommandListener onNotUpdated");
			}
		});
	}

	abstract void onShowcaseServiceConnected(ShowcaseLibrary showcaseLibrary);

	abstract void onShowcaseServiceDisconnected();

}
