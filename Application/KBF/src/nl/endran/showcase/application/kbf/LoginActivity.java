package nl.endran.showcase.application.kbf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import nl.endran.showcase.application.kbf.LoginActivityView.LoginActivityViewListener;
import nl.endran.showcaselib.ShowcaseLibrary;
import nl.endran.showcaselib.datacontainers.CustomServerCredentials;
import nl.endran.showcaselib.usecases.backend.TryLoginCommand.TryLoginCommandListener;
import nl.endran.showcaselib.usecases.data.GetUserCredentials.UserCredentialsListener;
import nl.endran.showcaselib.usecases.data.Logout.LogoutListener;
import nl.endran.showcaseui.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.util.Patterns;

public class LoginActivity extends BaseShowcaseActivity {

	private LoginActivityView loginActivityView;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loginActivityView = new LoginActivityView(this, new LoginActivityViewListener() {

			@Override
			public void onOkClicked() {
				tryLogin(loginActivityView.getActivationCode(), loginActivityView.getUserName(), loginActivityView.getEmail());
			}

			@Override
			public void onLogoutClicked() {
				logout();
			}
		});

		final Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		final Account[] accounts = AccountManager.get(this).getAccounts();

		final List<String> emailList = new ArrayList<String>();

		for (final Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				emailList.add(account.name);
			}
		}

		loginActivityView.setEmailList(emailList);

	}

	protected void tryLogin(final String activationCode, final String userName, final String email) {
		if (userName == null || userName.length() <= 0) {
			loginActivityView.showError(R.string.login_toast_noUserName);
		} else if (userName.length() <= 4) {
			loginActivityView.showError(R.string.login_toast_toShortUserName);
		} else if (activationCode == null || !getString(R.string.activationCode).toLowerCase().equals(activationCode.toLowerCase())) {
			loginActivityView.showError(R.string.login_toast_invalidActivation);
		} else {
			ApplicationState.activated = true;
			loginActivityView.showMessage(R.string.login_toast_activated);
			showcaseLibrary.getBackendAccess().tryLogin(true, userName, email, new TryLoginCommandListener() {

				@Override
				public void onFail(final String message) {
					loginActivityView.showError(message);
				}

				@Override
				public void onCustomServerCredentials(final CustomServerCredentials customServerCredentials) {
					if (customServerCredentials.getUserId() > 0) {
						ApplicationState.userId = customServerCredentials.getUserId();
						loginActivityView.showMessage(R.string.login_toast_loggedin);
					}
				}
			});

			LoginActivity.this.finish();
		}
	}

	private void logout() {
		showcaseLibrary.getDataAccess().Logout(new LogoutListener() {

			@Override
			public void onFail(final String message) {
				loginActivityView.showError(message);
			}

			@Override
			public void onLoggedOut() {
				ApplicationState.activated = false;
				ApplicationState.userId = -1;
			}
		});
		finish();
	}

	@Override
	protected void initializeLogger() {
	}

	private void getUserCredentials() {

		showcaseLibrary.getDataAccess().getUserCredentials(new UserCredentialsListener() {

			@Override
			public void onFail(final String message) {
				loginActivityView.showError(message);
			}

			@Override
			public void onCredentials(final CustomServerCredentials customServerCredentials) {
				if (customServerCredentials.getUserId() > 0) {
					loginActivityView.loggedIn(customServerCredentials.getUserName());
				}
			}
		});
	}

	@Override
	void onShowcaseServiceConnected(final ShowcaseLibrary showcaseLibrary) {
		getUserCredentials();
	}

	@Override
	void onShowcaseServiceDisconnected() {
	}
}
