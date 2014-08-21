package nl.endran.showcaselib.usecases.data;

import java.util.List;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaselib.usecases.data.GetAllUserBeerRatingsForCurrentUser.GetAlluserBeerRatingsForCurrentUserListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetAllUserBeerRatingsForCurrentUser extends BaseThreadSeparatorCommand<GetAlluserBeerRatingsForCurrentUserListener> {

	public interface GetAlluserBeerRatingsForCurrentUserListener extends BaseListener {

		public void onRatings(final List<UserBeer> userBeerList);
	}

	private List<UserBeer> userBeerList;

	public GetAllUserBeerRatingsForCurrentUser(final ShowcaseDatabase showcaseDatabase, final GetAlluserBeerRatingsForCurrentUserListener listener) {
		super(showcaseDatabase, listener);
	}

	@Override
	public String execute() {
		userBeerList = showcaseDatabase.getAllUserBeerRatingsForCurrentUser();
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
