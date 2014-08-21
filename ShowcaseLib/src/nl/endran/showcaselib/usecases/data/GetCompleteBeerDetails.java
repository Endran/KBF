package nl.endran.showcaselib.usecases.data;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaselib.usecases.data.GetCompleteBeerDetails.CompleteBeerDetailsListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetCompleteBeerDetails extends BaseThreadSeparatorCommand<CompleteBeerDetailsListener> {

	public interface CompleteBeerDetailsListener extends BaseListener {

		public void onDetails(Summary summary, UserBeer userBeer);
	}

	private final int festivalBeerId;

	private Summary summary;

	private UserBeer userBeer;

	public GetCompleteBeerDetails(final ShowcaseDatabase showcaseDatabase, final int festivalBeerId, final CompleteBeerDetailsListener listener) {
		super(showcaseDatabase, listener);
		this.festivalBeerId = festivalBeerId;
	}

	@Override
	public String execute() {

		summary = showcaseDatabase.getSummary(festivalBeerId);
		if (summary == null) {
			return "Could not find a sumamry for festivalBeerId " + festivalBeerId;
		}

		userBeer = showcaseDatabase.getUserBeer(summary.getBeerId());
		if (userBeer == null) {
			userBeer = new UserBeer();
			userBeer.setBeerId(summary.getBeerId());
		}

		return null;
	}

	@Override
	public void informListener() {
		listener.onDetails(summary, userBeer);
	}
}
