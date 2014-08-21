package nl.endran.showcase.backend.crispe.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import nl.endran.showcase.backend.crispe.util.CrispeServerCodes;
import nl.endran.showcasebackend.R;
import nl.endran.showcaselib.datacontainers.CustomServerCredentials;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class LoginHelper {
	ILogger log = LoggerFactory.GetLogger(this);

	private final Context context;
	private final String userName;
	private final String email;

	public LoginHelper(final Context context, final String userName, final String email) {
		this.context = context;
		this.userName = userName;
		this.email = email;
	}

	public CustomServerCredentials login() {
		CustomServerCredentials customServerCredentials = null;
		try {
			if (userName == null || userName.length() <= 4 || email == null || !email.contains("@")) {
				log.error("Invalid credentials userName:" + userName + " email:" + email);
				return null;
			}

			final String serverUrl = (String) context.getText(R.string.server_url);
			final String user = (String) context.getText(R.string.server_user);

			final HttpClient httpclient = new DefaultHttpClient();
			final HttpPost httppost = new HttpPost(serverUrl + user);

			HttpResponse response = null;

			final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("username", userName));
			nameValuePairs.add(new BasicNameValuePair("password", email));
			nameValuePairs.add(new BasicNameValuePair("email", email));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			response = httpclient.execute(httppost);

			final int statusCode = ServerStatusCodeHelper.getStatusCode(response);
			switch (statusCode) {
			case CrispeServerCodes.SUCCES_CODE:
				final String responseString = ServerStatusCodeHelper.parseResponse(response);
				final String[] splittedResponse = responseString.split(" ");
				if (splittedResponse.length != 2) {
					log.warn("Unexpeced response content, splittedResponse.length= " + splittedResponse.length);
					return null;
				}

				customServerCredentials = new CustomServerCredentials();
				customServerCredentials.setUserId(Integer.parseInt(splittedResponse[0]));
				customServerCredentials.setServerKey(splittedResponse[1]);
				customServerCredentials.setEmail(email);
				customServerCredentials.setUserName(userName);
				log.info("Login succes");
				return customServerCredentials;
			case CrispeServerCodes.FAIL_CODE:
				log.info("Received CrispeServerCodes.FAIL_CODE");
				return null;
			default:
				log.warn("Received unkown code " + response.getStatusLine().getStatusCode());
				return null;
			}

		} catch (final IllegalStateException e) {
			log.error("Exception in login", e);
			return null;
		} catch (final IOException e) {
			log.error("Exception in login", e);
			return null;
		}
	}

}
