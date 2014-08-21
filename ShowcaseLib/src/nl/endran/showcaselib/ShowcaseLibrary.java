package nl.endran.showcaselib;

import nl.endran.showcaselib.connectors.ShowcaseBackend;
import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.usecases.backend.BackendAccess;
import nl.endran.showcaselib.usecases.data.DataAccess;

public class ShowcaseLibrary {
	private final DataAccess dataAccess;
	private final BackendAccess backendAccess;

	public ShowcaseLibrary(final ShowcaseDatabase showcaseDatabase, final ShowcaseBackend showcaseBackend) {
		dataAccess = new DataAccess(showcaseDatabase);
		backendAccess = new BackendAccess(showcaseBackend, showcaseDatabase);
	}

	public void onDestroy() {
	}

	public DataAccess getDataAccess() {
		return dataAccess;
	}

	public BackendAccess getBackendAccess() {
		return backendAccess;
	}
}
