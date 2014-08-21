package nl.endran.showcaselib.usecases.data;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.usecases.data.SaveUserRating.SaveUserRatingListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class SaveUserRating extends BaseThreadSeparatorCommand<SaveUserRatingListener> {

	public interface SaveUserRatingListener extends BaseListener {

		public void onStored();
	}

	private final int beerId;
	private final int rating;
	private final String note;
	private final boolean wishlist;
	private final boolean favorite;

	public SaveUserRating(final ShowcaseDatabase showcaseDatabase, final int beerId, final int rating, final String note, final boolean wishlist,
			final boolean favorite, final SaveUserRatingListener listener) {
		super(showcaseDatabase, listener);

		this.beerId = beerId;
		this.rating = rating;
		this.note = note;
		this.wishlist = wishlist;
		this.favorite = favorite;
	}

	@Override
	public String execute() {

		final boolean succes = showcaseDatabase.storeUserRating(beerId, rating, note, wishlist, favorite);

		if (succes) {
			return null;
		} else {
			return "Could not save rating";
		}
	}

	@Override
	public void informListener() {
		listener.onStored();
	}
}
