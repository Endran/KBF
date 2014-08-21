package nl.endran.showcaselib.usecases.backend;

import java.util.ArrayList;
import java.util.List;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import nl.endran.showcaselib.connectors.ShowcaseBackend;
import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.ShowcaseDataObject;
import nl.endran.showcaselib.usecases.backend.SynchronizeCommand.SynchronizeAllListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class SynchronizeCommand extends BaseThreadSeparatorCommand<SynchronizeAllListener> {
	ILogger log = LoggerFactory.GetLogger(this);

	public interface SynchronizeAllListener extends BaseListener {
		public void onSynced();
	}

	private final ShowcaseBackend showcaseBackend;

	public SynchronizeCommand(final ShowcaseBackend showcaseBackend, final ShowcaseDatabase showcaseDatabase, final SynchronizeAllListener listener) {
		super(showcaseDatabase, listener);
		this.showcaseBackend = showcaseBackend;
	}

	@Override
	public String execute() {
		final String timestamp = this.showcaseDatabase.getLatestUpdateTimestamp();

		// // final TODO Only for debug
		// if (timestamp != null && timestamp.length() > 0) {
		// log.error("skipping sync for test purposes!");
		// return null;
		// }

		final List<ArrayList<ShowcaseDataObject>> syncedShowcaseDataObjectList = this.showcaseBackend.syncAll(timestamp);

		if (syncedShowcaseDataObjectList == null) {
			return "Error while syncing the Database";
		}

		final boolean stored = this.showcaseDatabase.storeShowcaseDataObjectListList(syncedShowcaseDataObjectList);

		if (!stored) {
			return "Error while storing the Database";
		}

		return null;
	}

	@Override
	public void informListener() {
		this.listener.onSynced();
	}
}
