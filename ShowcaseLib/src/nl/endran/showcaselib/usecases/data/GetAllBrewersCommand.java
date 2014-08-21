package nl.endran.showcaselib.usecases.data;

import java.util.List;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.Brewer;
import nl.endran.showcaselib.usecases.data.GetAllBrewersCommand.GetAllBrewersListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetAllBrewersCommand extends BaseThreadSeparatorCommand<GetAllBrewersListener> {

	public interface GetAllBrewersListener extends BaseListener {

		public void onBrewers(List<Brewer> brewers);
	}

	private List<Brewer> brewers;

	public GetAllBrewersCommand(final ShowcaseDatabase showcaseDatabase, final GetAllBrewersListener listener) {
		super(showcaseDatabase, listener);
	}

	@Override
	public String execute() {
		brewers = showcaseDatabase.getAllBrewers();
		if (brewers == null) {
			return "An error occurred when retrieving all brewers";
		}

		return null;
	}

	@Override
	public void informListener() {
		listener.onBrewers(brewers);
	}
}
