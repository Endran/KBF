package nl.endran.showcase.application.kbf.fragments.view;

import java.util.List;

import nl.endran.showcase.application.kbf.ApplicationState;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;
import nl.endran.showcaseui.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BeerDetailView {

	public interface BeerDetailViewListener {
		/**
		 * @return true if activated, false otherwise.
		 */
		public boolean onIsActivated();

		public void onDialogSaveClicked(int beerId, int rating, String note);
	}

	private final LayoutInflater inflater;
	private final BeerDetailViewListener listener;

	private final View rootView;
	private final TextView textViewName;
	private final TextView textViewBrewer;
	private final TextView textViewNumber;
	private final TextView textViewPayment;
	private final TextView textViewMethod;
	private final TextView textViewPercentage;
	private final TextView textViewCity;
	private final TextView textViewColor;
	private final RelativeLayout relativeLayoutRating;
	private final TextView textViewAverageRating;
	private final RatingBar ratingBarAverage;
	private final RatingBar ratingBarMyRating;
	private final LinearLayout linearLayoutCheck;
	private final LinearLayout linearLayoutRecommended;
	private final LinearLayout linearLayoutNew;
	private final LinearLayout linearLayoutAward;
	private final TextView textViewAward;
	private final LinearLayout linearLayoutLastBottle;
	private final LinearLayout linearLayoutSoldOut;
	private final TextView textViewContent;
	private final RelativeLayout relativeLayoutActivated;
	private final RelativeLayout relativeLayoutNotActivated;
	private final Button buttonActivate;
	private final LinearLayout linearLayoutComment;

	private Activity activity;
	private Summary summary;
	private UserBeer userBeer;

	CommentViewHolder myCommentViewHolder;

	private Dialog dialog;

	public BeerDetailView(final LayoutInflater inflater, final Bundle savedInstanceState, final BeerDetailViewListener listener) {
		this.inflater = inflater;
		this.listener = listener;

		rootView = inflater.inflate(R.layout.fragment_beer_detail, null);

		textViewName = (TextView) rootView.findViewById(R.id.textViewName);
		textViewBrewer = (TextView) rootView.findViewById(R.id.textViewBrewer);
		textViewNumber = (TextView) rootView.findViewById(R.id.textViewNumber);
		textViewPayment = (TextView) rootView.findViewById(R.id.textViewPayment);
		textViewMethod = (TextView) rootView.findViewById(R.id.textViewMethod);
		textViewPercentage = (TextView) rootView.findViewById(R.id.textViewPercentage);
		textViewCity = (TextView) rootView.findViewById(R.id.textViewCity);
		textViewColor = (TextView) rootView.findViewById(R.id.textViewColor);
		relativeLayoutRating = (RelativeLayout) rootView.findViewById(R.id.relativeLayoutRating);
		textViewAverageRating = (TextView) rootView.findViewById(R.id.textViewAverageRating);
		ratingBarAverage = (RatingBar) rootView.findViewById(R.id.ratingBarAverage);
		ratingBarMyRating = (RatingBar) rootView.findViewById(R.id.ratingBarMyRating);
		linearLayoutCheck = (LinearLayout) rootView.findViewById(R.id.linearLayoutCheck);
		linearLayoutRecommended = (LinearLayout) rootView.findViewById(R.id.linearLayoutRecommended);
		linearLayoutNew = (LinearLayout) rootView.findViewById(R.id.linearLayoutNew);
		linearLayoutAward = (LinearLayout) rootView.findViewById(R.id.linearLayoutAward);
		textViewAward = (TextView) rootView.findViewById(R.id.textViewAward);
		linearLayoutLastBottle = (LinearLayout) rootView.findViewById(R.id.linearLayoutLastBottle);
		linearLayoutSoldOut = (LinearLayout) rootView.findViewById(R.id.linearLayoutSoldOut);
		textViewContent = (TextView) rootView.findViewById(R.id.textViewContent);

		relativeLayoutNotActivated = (RelativeLayout) rootView.findViewById(R.id.relativeLayoutNotActivated);
		relativeLayoutActivated = (RelativeLayout) rootView.findViewById(R.id.relativeLayoutActivated);
		buttonActivate = (Button) rootView.findViewById(R.id.buttonActivate);
		linearLayoutComment = (LinearLayout) rootView.findViewById(R.id.linearLayoutComment);

		relativeLayoutRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				// listener.onRatingClicked();
				showDialog();
			}
		});
	}

	public View getRootView() {
		return rootView;
	}

	public void showError(final String message) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}

	public void onDetails(final Activity activity, final Summary summary, final UserBeer userBeer) {
		this.activity = activity;
		this.summary = summary;
		this.userBeer = userBeer;

		textViewName.setText(summary.getBeerName());

		textViewBrewer.setText(summary.getBrewerName());
		textViewNumber.setText("" + summary.getFestivalBeerNumber());
		textViewPayment.setText("" + summary.getFestivalBeerPrice());
		textViewMethod.setText(summary.getMethodName());
		textViewPercentage.setText("" + summary.getBeerPercentage());
		textViewCity.setText(summary.getBrewerLocation());
		textViewColor.setText(summary.getBeerColor());

		if (!ApplicationState.isActivated()) {
			relativeLayoutRating.setVisibility(View.GONE);
			relativeLayoutActivated.setVisibility(View.GONE);
			relativeLayoutNotActivated.setVisibility(View.VISIBLE);

			buttonActivate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View arg0) {
					listener.onIsActivated();
				}
			});

			return;
		}

		relativeLayoutRating.setVisibility(View.VISIBLE);
		relativeLayoutActivated.setVisibility(View.VISIBLE);
		relativeLayoutNotActivated.setVisibility(View.GONE);

		textViewContent.setText(summary.getBeerDiscription());

		textViewAverageRating.setText(String.format(activity.getString(R.string.averageRating), summary.getBeerRatingCount()));
		ratingBarAverage.setRating((float) summary.getBeerRatingAvg());

		ratingBarMyRating.setRating(userBeer.getRating());

		if (userBeer.getRating() > 0) {
			linearLayoutCheck.setVisibility(View.VISIBLE);
		} else {
			linearLayoutCheck.setVisibility(View.GONE);
		}

		// if (beerDetails.recommend) {
		// linearLayoutRecommended.setVisibility(View.VISIBLE);
		// } else {
		linearLayoutRecommended.setVisibility(View.GONE);
		// }

		if (summary.isFestivalBeerFirstTime()) {
			linearLayoutNew.setVisibility(View.VISIBLE);
		} else {
			linearLayoutNew.setVisibility(View.GONE);
		}

		if (summary.getFestivalBeerAward() != null && summary.getFestivalBeerAward().length() > 0) {
			linearLayoutAward.setVisibility(View.VISIBLE);
			textViewAward.setText(summary.getFestivalBeerAward());

		} else {
			linearLayoutAward.setVisibility(View.GONE);
		}

		if (summary.getStockName().equals("Last")) {
			linearLayoutLastBottle.setVisibility(View.VISIBLE);
			linearLayoutSoldOut.setVisibility(View.GONE);
		} else if (summary.getStockName().equals("Empty")) {
			linearLayoutLastBottle.setVisibility(View.GONE);
			linearLayoutSoldOut.setVisibility(View.VISIBLE);
		} else {
			linearLayoutLastBottle.setVisibility(View.GONE);
			linearLayoutSoldOut.setVisibility(View.GONE);
		}
	}

	public void showDialog() {
		showDialog(-1, null);
	}

	public void showDialog(int rating, String note) {

		final boolean isActivated = listener.onIsActivated();
		if (!isActivated) {
			return;
		}

		dialog = new Dialog(activity, android.R.style.Theme_Dialog);
		dialog.setContentView(R.layout.dialog_rating);
		dialog.setTitle(summary.getBeerName());
		dialog.show();

		if (note == null) {
			note = userBeer.getNote();
		}

		if (rating < 0) {
			rating = userBeer.getRating();
		}

		if (note == null || note.length() <= 0 || note.equals("null")) {
			note = "";
		}

		final EditText dialogEditTextNote = (EditText) dialog.findViewById(R.id.editTextNote);
		dialogEditTextNote.setText(note);

		final RatingBar dialogRatingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
		dialogRatingBar.setRating(rating);

		final Button dialogButtonClear = (Button) dialog.findViewById(R.id.buttonClear);
		dialogButtonClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				dialogRatingBar.setRating(0f);
			}
		});

		final Button dialogButtonOk = (Button) dialog.findViewById(R.id.buttonOk);
		dialogButtonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				listener.onDialogSaveClicked(summary.getBeerId(), (int) dialogRatingBar.getRating(), dialogEditTextNote.getText().toString());
				dialog.dismiss();
			}
		});

		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(final DialogInterface arg0) {
				dialog = null;
			}
		});
	}

	public boolean isDialogActive() {
		return dialog != null;
	}

	public int getDialogRating() {
		if (dialog == null) {
			return -1;
		}

		final RatingBar dialogRatingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
		if (dialogRatingBar == null) {
			return -1;
		}

		return (int) dialogRatingBar.getRating();
	}

	public String getDialogNote() {
		if (dialog == null) {
			return null;
		}

		final EditText dialogEditTextNote = (EditText) dialog.findViewById(R.id.editTextNote);
		if (dialogEditTextNote == null) {
			return null;
		}

		return dialogEditTextNote.getText().toString();
	}

	public void updateUserBeer(final UserBeer userBeer) {
		this.userBeer = userBeer;

		if (userBeer.getRating() > 0) {
			linearLayoutCheck.setVisibility(View.VISIBLE);
		} else {
			linearLayoutCheck.setVisibility(View.GONE);
		}

		ratingBarMyRating.setRating(userBeer.getRating());

		setMyComment();
	}

	static class CommentViewHolder {
		TextView beerNameText;
		TextView userNameText;
		RatingBar ratingBar;
		TextView commentText;
		TextView commentDate;
		RelativeLayout commentArea;
		RelativeLayout noCommentArea;
		LinearLayout px1layout;
	}

	@SuppressLint("CutPasteId")
	public void setComments(final List<UserBeer> comments) {
		linearLayoutComment.removeAllViews();

		if (!ApplicationState.isActivated()) {
			return;
		}

		activity.getLayoutInflater();

		View commentItem = inflater.inflate(R.layout.list_item_comment, null);
		myCommentViewHolder = new CommentViewHolder();
		myCommentViewHolder.commentArea = (RelativeLayout) commentItem.findViewById(R.id.commentArea);
		myCommentViewHolder.noCommentArea = (RelativeLayout) commentItem.findViewById(R.id.noCommentArea);
		myCommentViewHolder.beerNameText = (TextView) commentItem.findViewById(R.id.beerNameText);
		myCommentViewHolder.userNameText = (TextView) commentItem.findViewById(R.id.userNameText);
		myCommentViewHolder.ratingBar = (RatingBar) commentItem.findViewById(R.id.ratingBar);
		myCommentViewHolder.commentText = (TextView) commentItem.findViewById(R.id.commentText);
		myCommentViewHolder.commentDate = (TextView) commentItem.findViewById(R.id.commentDate);
		myCommentViewHolder.px1layout = (LinearLayout) commentItem.findViewById(R.id.px1layout);

		myCommentViewHolder.commentArea.setClickable(true);
		myCommentViewHolder.commentArea.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				showDialog();
			}
		});

		myCommentViewHolder.noCommentArea.setClickable(true);
		myCommentViewHolder.noCommentArea.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				showDialog();
			}
		});

		myCommentViewHolder.px1layout.setVisibility(View.INVISIBLE);

		setMyComment();

		linearLayoutComment.addView(commentItem);

		for (final UserBeer userBeer : comments) {
			if (myCommentViewHolder != null && userBeer != null) {
				if (userBeer.getRating() <= 0 || userBeer.getUserId() <= 0 || userBeer.getUserId() == ApplicationState.userId) {
					continue;
				}
				commentItem = inflater.inflate(R.layout.list_item_comment, null);
				final CommentViewHolder holder = new CommentViewHolder();
				holder.beerNameText = (TextView) commentItem.findViewById(R.id.beerNameText);
				holder.userNameText = (TextView) commentItem.findViewById(R.id.userNameText);
				holder.ratingBar = (RatingBar) commentItem.findViewById(R.id.ratingBar);
				holder.commentText = (TextView) commentItem.findViewById(R.id.commentText);
				holder.commentDate = (TextView) commentItem.findViewById(R.id.commentDate);

				setForHolder(holder, userBeer);
				linearLayoutComment.addView(commentItem);
			}
		}
	}

	private void setMyComment() {
		if (myCommentViewHolder != null) {
			final boolean hasUserRated = userBeer.getRating() > 0;
			if (!hasUserRated) {
				myCommentViewHolder.commentArea.setVisibility(View.INVISIBLE);
				myCommentViewHolder.noCommentArea.setVisibility(View.VISIBLE);
			} else {
				myCommentViewHolder.commentArea.setVisibility(View.VISIBLE);
				myCommentViewHolder.noCommentArea.setVisibility(View.INVISIBLE);
			}

			setForHolder(myCommentViewHolder, userBeer);
			myCommentViewHolder.userNameText.setText(activity.getText(R.string.beerDetailYourRating));
		}
	}

	private void setForHolder(final CommentViewHolder holder, final UserBeer userBeer) {

		holder.beerNameText.setVisibility(View.GONE);

		String userName = userBeer.getUserName();
		if (userBeer.getUserName() == null || userBeer.getUserName().length() <= 0 || userBeer.getUserName().equals("null")) {
			userName = activity.getString(R.string.anonymous);
		}

		holder.userNameText.setText(String.format(activity.getString(R.string.ratingOfuser), userName));

		holder.ratingBar.setRating(userBeer.getRating());
		if (userBeer.getNote() != null && !userBeer.getNote().equals("") && !userBeer.getNote().equals("null")) {
			holder.commentText.setText(activity.getText(R.string.beerDetailNotes) + ": " + userBeer.getNote());
		} else {
			holder.commentText.setText("");
			holder.commentDate.setVisibility(View.GONE);
			holder.commentDate = holder.commentText;
		}

		if (userBeer.getDate() != null && !userBeer.getDate().equals("") && !userBeer.getDate().equals("null")) {
			holder.commentDate.setText(transformDate(userBeer.getDate()));
			holder.commentDate.setVisibility(View.VISIBLE);
		} else {
			holder.commentDate.setVisibility(View.GONE);
		}
	}

	private CharSequence transformDate(final String date) {
		// TODO make into humand readable date
		return date;
	}
}
