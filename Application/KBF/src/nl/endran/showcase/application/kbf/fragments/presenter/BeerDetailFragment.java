package nl.endran.showcase.application.kbf.fragments.presenter;

import java.util.List;

import nl.endran.activity.EndranSupportV4Fragment;
import nl.endran.showcase.application.kbf.ApplicationState;
import nl.endran.showcase.application.kbf.BaseShowcaseActivity;
import nl.endran.showcase.application.kbf.BaseShowcaseActivity.ShowcaseLibraryListener;
import nl.endran.showcase.application.kbf.LoginActivity;
import nl.endran.showcase.application.kbf.fragments.view.BeerDetailView;
import nl.endran.showcase.application.kbf.fragments.view.BeerDetailView.BeerDetailViewListener;
import nl.endran.showcase.application.kbf.menu.BeerDetailMenu;
import nl.endran.showcase.application.kbf.menu.BeerDetailMenu.BeerDetailMenuListener;
import nl.endran.showcaselib.ShowcaseLibrary;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaselib.usecases.backend.UploadUserDataCommand.UploadUserDataCommandListener;
import nl.endran.showcaselib.usecases.data.GetAllRatingForBeer.GetAllRatingForBeerListener;
import nl.endran.showcaselib.usecases.data.GetCompleteBeerDetails.CompleteBeerDetailsListener;
import nl.endran.showcaselib.usecases.data.SaveUserRating.SaveUserRatingListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class BeerDetailFragment extends EndranSupportV4Fragment {

	public static final String FESTIVAL_BEER_NAME_KEY = "FESTIVAL_BEER_NAME_KEY";
	public static final String FESTIVAL_BEER_ID_KEY = "FESTIVAL_BEER_ID_KEY";

	private final static String DIALOG_NOTE_KEY = "DIALOG_NOTE_KEY";
	private final static String DIALOG_RATING_KEY = "DIALOG_RATING_KEY";

	private boolean activationRequestPending = false;

	private BeerDetailView beerDetailView;
	private BeerDetailMenu beerDetailMenu;
	private BaseShowcaseActivity activity;
	private ShowcaseLibrary showcaseLibrary;
	private int savedRating;
	private String savedNote;

	private UserBeer userBeer = new UserBeer();
	private int festivalBeerId;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		beerDetailView = new BeerDetailView(inflater, savedInstanceState, new BeerDetailViewListener() {

			@Override
			public void onDialogSaveClicked(final int beerId, final int rating, final String note) {
				userBeer.setRating(rating);
				userBeer.setNote(note);
				storeUserData(userBeer);
			}

			@Override
			public boolean onIsActivated() {
				return checkActivated();
			}
		});

		setHasOptionsMenu(true);

		return beerDetailView.getRootView();
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final FragmentActivity fragmentActivity = getActivity();
		if (!(fragmentActivity instanceof BaseShowcaseActivity)) {
			log.error("onActivityCreated, could not cast getActivity() to ShowcaseActivity");
			return;
		}

		final Bundle arguments = getArguments();
		if (arguments == null) {
			log.error("onActivityCreated, getArguments() returned null");
		}

		if (savedInstanceState != null) {
			savedRating = savedInstanceState.getInt(DIALOG_RATING_KEY, -1);
			savedNote = savedInstanceState.getString(DIALOG_NOTE_KEY);
		}

		festivalBeerId = arguments.getInt(FESTIVAL_BEER_ID_KEY, -1);
		log.debug("festivalBeerId = " + festivalBeerId);
		if (festivalBeerId < 0) {
			log.error("onActivityCreated, festivalBeerId was not set in the arguments");
		}

		activity = (BaseShowcaseActivity) fragmentActivity;
		activity.retrieveShowcaseLibrary(new ShowcaseLibraryListener() {
			@Override
			public void onShowcaseLibrary(final ShowcaseLibrary showcaseLibrary) {
				BeerDetailFragment.this.showcaseLibrary = showcaseLibrary;
				getBeerDetail(festivalBeerId);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

		if (activationRequestPending && festivalBeerId >= 0) {
			getBeerDetail(festivalBeerId);
		}

		activationRequestPending = false;
	}

	@Override
	public void onSaveInstanceState(final Bundle outState) {

		if (beerDetailView != null && beerDetailView.isDialogActive()) {
			final int rating = beerDetailView.getDialogRating();
			final String note = beerDetailView.getDialogNote();
			if (rating >= 0 && note != null) {
				outState.putInt(DIALOG_RATING_KEY, rating);
				outState.putString(DIALOG_NOTE_KEY, note);
			}
		}

		super.onSaveInstanceState(outState);
	}

	private void getBeerDetail(final int festivalBeerId) {
		showcaseLibrary.getDataAccess().getCompleteBeerDetails(festivalBeerId, new CompleteBeerDetailsListener() {

			@Override
			public void onFail(final String message) {
				beerDetailView.showError(message);
			}

			@Override
			public void onDetails(final Summary summary, final UserBeer userBeer) {
				BeerDetailFragment.this.userBeer = userBeer;
				beerDetailView.onDetails(activity, summary, userBeer);

				if (savedRating >= 0 && savedNote != null) {
					beerDetailView.showDialog(savedRating, savedNote);
					savedRating = -1;
					savedNote = null;
				}

				getBeerDetailMenu().updateUserBeer(userBeer);
				getComments(summary.getBeerId());
			}
		});
	}

	protected void getComments(final int beerId) {
		showcaseLibrary.getDataAccess().getAllRatingForBeer(beerId, new GetAllRatingForBeerListener() {

			@Override
			public void onFail(final String message) {
				beerDetailView.showError(message);
			}

			@Override
			public void onRatings(final List<UserBeer> userBeerList) {
				beerDetailView.setComments(userBeerList);
			}
		});
	}

	private void storeUserData(final UserBeer userBeer) {
		showcaseLibrary.getDataAccess().saveUserRating(userBeer.getBeerId(), userBeer.getRating(), userBeer.getNote(), userBeer.getWishlist(),
				userBeer.getFavorite(), new SaveUserRatingListener() {

					@Override
					public void onFail(final String message) {
						beerDetailView.showError(message);
					}

					@Override
					public void onStored() {
						log.info("onStored");
						beerDetailView.updateUserBeer(userBeer);
						getBeerDetailMenu().updateUserBeer(userBeer);
						uploadUserData();
					}
				});
	}

	private void uploadUserData() {
		showcaseLibrary.getBackendAccess().uploadUserDataCommand(new UploadUserDataCommandListener() {

			@Override
			public void onFail(final String message) {
				log.info("UploadUserDataCommandListener onFail " + message);
			}

			@Override
			public void onUpdated() {
				log.info("UploadUserDataCommandListener onUpdated");
			}

			@Override
			public void onNotUpdated() {
				log.info("UploadUserDataCommandListener onNotUpdated");
			}
		});
	}

	// /-----------------------------
	// This is necessary, becausen super.isVisible() is buggy in pre v11 support
	// library. We are using v4
	@Override
	public void setMenuVisibility(final boolean visible) {
		super.setMenuVisibility(visible);
		getBeerDetailMenu().setMenuVisibility(visible);
	}

	@Override
	public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
		getBeerDetailMenu().createOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		return getBeerDetailMenu().optionsItemSelected(item);
	}

	private BeerDetailMenu getBeerDetailMenu() {
		if (beerDetailMenu == null) {
			beerDetailMenu = new BeerDetailMenu(new BeerDetailMenuListener() {

				@Override
				public boolean onIsActivated() {
					return checkActivated();
				}

				@Override
				public void onWishlistClicked(final boolean wishListValue) {
					userBeer.setWishlist(wishListValue);
					storeUserData(userBeer);
				}

				@Override
				public void onNoteClicked() {
					beerDetailView.showDialog();
				}

				@Override
				public void onFavoriteClicked(final boolean favoriteValue) {
					userBeer.setFavorite(favoriteValue);
					storeUserData(userBeer);
				}
			});
		}
		return beerDetailMenu;
	}

	private boolean checkActivated() {
		final boolean isActivated = ApplicationState.isActivated();
		if (!isActivated) {
			activationRequestPending = true;
			startActivity(new Intent(activity, LoginActivity.class));
		}
		return isActivated;
	}
}
