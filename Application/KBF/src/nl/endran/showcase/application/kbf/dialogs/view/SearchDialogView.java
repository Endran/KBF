package nl.endran.showcase.application.kbf.dialogs.view;

import java.util.List;

import nl.endran.showcaseui.R;
import android.app.Dialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchDialogView {

	public interface SearchDialogViewListener {
		public void onSearchEntered(String searchInput, int filterListPosition, int sortListPosition);
	}

	// private final SearchDialogViewListener listener;

	public SearchDialogView(final Dialog dialog, final List<String> filterList, final List<String> sortList, final SearchDialogViewListener listener) {
		// this.listener = listener;

		dialog.setContentView(R.layout.dialog_search);
		dialog.setTitle(R.string.menu_search);

		final EditText editTextInput = (EditText) dialog.findViewById(R.id.editTextInput);

		final Spinner spinnerFilter = (Spinner) dialog.findViewById(R.id.spinnerFilter);
		final ArrayAdapter<String> spinnerFilterArrayAdapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, filterList);
		spinnerFilterArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerFilter.setAdapter(spinnerFilterArrayAdapter);

		final Spinner spinnerSort = (Spinner) dialog.findViewById(R.id.spinnerSort);
		final ArrayAdapter<String> spinnerSortArrayAdapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, sortList);
		spinnerSortArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSort.setAdapter(spinnerSortArrayAdapter);

		final Button buttonSearch = (Button) dialog.findViewById(R.id.buttonSearch);
		buttonSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View arg0) {
				final String searchString = editTextInput.getText().toString();
				listener.onSearchEntered(searchString, spinnerFilter.getSelectedItemPosition(), spinnerSort.getSelectedItemPosition());
				dialog.dismiss();
			}
		});

		editTextInput.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					final String searchString = editTextInput.getText().toString();
					listener.onSearchEntered(searchString, spinnerFilter.getSelectedItemPosition(), spinnerSort.getSelectedItemPosition());
					dialog.dismiss();
					return true;
				}
				return false;
			}
		});
	}
}
