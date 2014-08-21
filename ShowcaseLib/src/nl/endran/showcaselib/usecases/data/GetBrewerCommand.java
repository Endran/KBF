package nl.endran.showcaselib.usecases.data;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.Brewer;
import nl.endran.showcaselib.usecases.data.GetBrewerCommand.GetBrewerListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetBrewerCommand extends BaseThreadSeparatorCommand<GetBrewerListener> {

	public interface GetBrewerListener extends BaseListener {

		public void onBrewer(Brewer brewer);
	}

	private final int brewerId;

	private Brewer brewer;

	public GetBrewerCommand(final ShowcaseDatabase showcaseDatabase, final int brewerId, final GetBrewerListener listener) {
		super(showcaseDatabase, listener);
		this.brewerId = brewerId;
	}

	@Override
	public String execute() {
		this.brewer = this.showcaseDatabase.getBrewer(this.brewerId);
		if (this.brewer == null) {
			return "An error occurred when retrieving brewer " + this.brewerId;
		}

		return null;
	}

	@Override
	public void informListener() {
		this.listener.onBrewer(this.brewer);
	}
}
