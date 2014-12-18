package nl.endran.showcase.application.kbf;

import java.util.ArrayList;
import java.util.List;

import nl.endran.showcaseui.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivityView {

    public interface LoginActivityViewListener {

        public void onOkClicked();

        public void onLogoutClicked();
    }

    private final Activity activity;

    private final Button buttonOK;
    private final Button buttonLogout;
    private final EditText editTextActivationCode;
    private final EditText editTextUserName;
    private final Spinner spinnerEmail;

    @SuppressLint("NewApi")
    public LoginActivityView(final Activity activity, final LoginActivityViewListener listener) {

        this.activity = activity;
        activity.setContentView(R.layout.activity_login);

        buttonOK = (Button) activity.findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                listener.onOkClicked();
            }
        });

        buttonLogout = (Button) activity.findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                listener.onLogoutClicked();
            }
        });

        editTextActivationCode = (EditText) activity.findViewById(R.id.editTextActivationCode);

        editTextUserName = (EditText) activity.findViewById(R.id.editTextUserName);

        spinnerEmail = (Spinner) activity.findViewById(R.id.spinnerEmail);
        final ArrayAdapter<String> spinnerCoordinatesArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item,
                new ArrayList<String>());
        spinnerCoordinatesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmail.setAdapter(spinnerCoordinatesArrayAdapter);

        buttonOK.setEnabled(!ApplicationState.isLoggedIn());
        editTextUserName.setEnabled(!ApplicationState.isLoggedIn());
        spinnerEmail.setEnabled(!ApplicationState.isLoggedIn());

        if (ApplicationState.isActivated()) {
            editTextActivationCode.setEnabled(false);
            editTextActivationCode.setText(R.string.activationCode);
            buttonLogout.setVisibility(View.VISIBLE);
        } else {
            editTextActivationCode.setEnabled(true);
            buttonLogout.setVisibility(View.GONE);
        }
    }

    public void showError(final int messageId) {
        showError(activity.getString(messageId));
    }

    public void showError(final String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void loggedIn(final String userName) {
        editTextUserName.setText(userName);
        buttonLogout.setVisibility(View.VISIBLE);
    }

    public void setEmailList(final List<String> emailList) {
        final ArrayAdapter<String> spinnerCoordinatesArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, emailList);
        spinnerCoordinatesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmail.setAdapter(spinnerCoordinatesArrayAdapter);
    }

    public String getActivationCode() {
        return editTextActivationCode.getText().toString().trim();
    }

    public String getUserName() {
        return editTextUserName.getText().toString().trim();
    }

    public void showMessage(final int messageId) {
        showMessage(activity.getString(messageId));
    }

    public void showMessage(final String message) {
        showError(message);
    }

    public String getEmail() {
        return (String) spinnerEmail.getSelectedItem();
    }
}
