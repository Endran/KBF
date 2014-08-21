package nl.endran.showcaselib.usecases.data;

import java.util.List;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaselib.usecases.data.GetAllRatingForBeer.GetAllRatingForBeerListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetAllRatingForBeer extends BaseThreadSeparatorCommand<GetAllRatingForBeerListener> {

	public interface GetAllRatingForBeerListener extends BaseListener {

		public void onRatings(final List<UserBeer> userBeerList);
	}

	private final int beerId;

	private List<UserBeer> userBeerList;

	public GetAllRatingForBeer(final ShowcaseDatabase showcaseDatabase, final int beerId, final GetAllRatingForBeerListener listener) {
		super(showcaseDatabase, listener);
		this.beerId = beerId;
	}

	@Override
	public String execute() {
		userBeerList = showcaseDatabase.getAllUserBeersForBeer(beerId);
		if (userBeerList == null) {
			return "An error occurred when retrieving all ratings";
		}

		return null;
	}

	@Override
	public void informListener() {
		listener.onRatings(userBeerList);
	}
}
