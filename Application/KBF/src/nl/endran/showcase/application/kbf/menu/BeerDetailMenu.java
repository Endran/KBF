package nl.endran.showcase.application.kbf.menu;

import nl.endran.showcase.application.kbf.ApplicationState;
import nl.endran.showcase.application.kbf.BaseShowcaseActivity;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaseui.R;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BeerDetailMenu extends ShowCaseMenu {
	private UserBeer userBeer;
	private final BeerDetailMenuListener listener;

	private Menu menu;

	private boolean visible;

	public interface BeerDetailMenuListener {
		public void onFavoriteClicked(boolean favoriteValue);

		public void onWishlistClicked(boolean wishListValue);

		public void onNoteClicked();

		public boolean onIsActivated();
	}

	public BeerDetailMenu(final BeerDetailMenuListener listener) {
		this.listener = listener;
	}

	public void onDetails(final BaseShowcaseActivity activity, final UserBeer userBeer) {
		this.userBeer = userBeer;

		setWishListValue();
		setFavoriteValue();
	}

	public void updateUserBeer(final UserBeer userBeer) {
		this.userBeer = userBeer;

		setWishListValue();
		setFavoriteValue();
	}

	@Override
	public void createOptionsMenu(final Menu menu, final MenuInflater menuInflater) {
		// SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity
		// .getApplicationContext());
		// boolean isAdmin = sharedPrefs.getBoolean(ActivityLogin.USER_IS_ADMIN_KEY, false);
		// if (isAdmin) {
		// inflater.inflate(R.menu.beer_details_admin, menu);
		// } else {
		menuInflater.inflate(R.menu.beer_details, menu);
		// }
		this.menu = menu;

		setWishListValue();
		setFavoriteValue();

		// setActionBarIcons();
	}

	@Override
	public boolean optionsItemSelected(final MenuItem item) {
		boolean favoriteValue;
		boolean wishListValue;

		if (userBeer == null) {
			favoriteValue = false;
			wishListValue = false;
		} else {
			favoriteValue = userBeer.getFavorite();
			wishListValue = userBeer.getWishlist();
		}

		final boolean isActivated;
		switch (item.getItemId()) {
		case R.id.menu_favorite:
			isActivated = listener.onIsActivated();
			if (!isActivated) {
				return true;
			}

			favoriteValue = !favoriteValue;
			listener.onFavoriteClicked(favoriteValue);
			break;
		case R.id.menu_cart:
			isActivated = listener.onIsActivated();
			if (!isActivated) {
				return true;
			}

			wishListValue = !wishListValue;
			listener.onWishlistClicked(wishListValue);
			break;
		case R.id.menu_note:
			isActivated = listener.onIsActivated();
			if (!isActivated) {
				return true;
			}

			listener.onNoteClicked();
			break;
		default:
			return false;
		}

		return true;
	}

	public void setMenuVisibility(final boolean visible) {
		this.visible = visible;
		setWishListValue();
		setFavoriteValue();
	}

	private void setWishListValue() {

		if (userBeer == null) {
			return;
		}

		if ((userBeer.getUserId() > 0 && userBeer.getUserId() != ApplicationState.userId)) {
			return;
		}

		if (userBeer.getUserId() < 0 && ApplicationState.isLoggedIn()) {
			return;
		}

		if (menu == null) {
			return;
		}

		if (!visible) {
			return;
		}

		final MenuItem itemCart = menu.findItem(R.id.menu_cart);
		if (itemCart == null) {
			return;
		}

		if (userBeer.getWishlist()) {
			itemCart.setIcon(R.drawable.ic_action_cart_active);
		} else {
			itemCart.setIcon(R.drawable.ic_action_cart);
		}
	}

	private void setFavoriteValue() {

		if (userBeer == null) {
			return;
		}

		if ((userBeer.getUserId() > 0 && userBeer.getUserId() != ApplicationState.userId)) {
			return;
		}

		if (userBeer.getUserId() < 0 && ApplicationState.isLoggedIn()) {
			return;
		}

		if (menu == null) {
			return;
		}

		if (!visible) {
			return;
		}

		final MenuItem itemFavorite = menu.findItem(R.id.menu_favorite);
		if (itemFavorite == null) {
			return;
		}

		if (userBeer.getFavorite()) {
			itemFavorite.setIcon(R.drawable.ic_action_favorite_active);
		} else {
			itemFavorite.setIcon(R.drawable.ic_action_favorite);
		}
	}
}
