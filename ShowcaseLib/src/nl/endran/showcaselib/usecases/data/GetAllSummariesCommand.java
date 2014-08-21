package nl.endran.showcaselib.usecases.data;

import java.util.List;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.usecases.data.GetAllSummariesCommand.GetAllSummariesListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetAllSummariesCommand extends BaseThreadSeparatorCommand<GetAllSummariesListener> {

	public interface GetAllSummariesListener extends BaseListener {

		public void onSummaries(List<Summary> summaries);
	}

	private List<Summary> summaries;

	public GetAllSummariesCommand(final ShowcaseDatabase showcaseDatabase, final GetAllSummariesListener listener) {
		super(showcaseDatabase, listener);
	}

	@Override
	public String execute() {
		summaries = showcaseDatabase.getAllSummaries();
		if (summaries == null) {
			return "An error occurred when retrieving all summaries";
		}

		return null;
	}

	@Override
	public void informListener() {
		listener.onSummaries(summaries);
	}
}
