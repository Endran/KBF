package nl.endran.showcaselib.usecases.data;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.usecases.data.GetSummaryCommand.GetSummaryListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetSummaryCommand extends BaseThreadSeparatorCommand<GetSummaryListener> {

	public interface GetSummaryListener extends BaseListener {

		public void onSummary(Summary summary);
	}

	private final int festivalBeerId;

	private Summary summary;

	public GetSummaryCommand(final ShowcaseDatabase showcaseDatabase, final int festivalBeerId, final GetSummaryListener listener) {
		super(showcaseDatabase, listener);
		this.festivalBeerId = festivalBeerId;
	}

	@Override
	public String execute() {
		this.summary = this.showcaseDatabase.getSummary(this.festivalBeerId);
		if (this.summary == null) {
			return "An error occurred when retrieving summary " + this.festivalBeerId;
		}

		return null;
	}

	@Override
	public void informListener() {
		this.listener.onSummary(this.summary);
	}
}
