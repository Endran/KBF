package nl.endran.showcase.backend.crispe.helpers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import nl.endran.security.Hash;
import nl.endran.showcase.backend.crispe.util.CrispeServerCodes;
import nl.endran.showcasebackend.R;
import nl.endran.showcaselib.datacontainers.CustomServerCredentials;
import nl.endran.showcaselib.datacontainers.UserBeer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class UserBeerHelper {
	ILogger log = LoggerFactory.GetLogger(this);

	private final Context context;
	private final CustomServerCredentials customServerCredentials;
	private final List<UserBeer> userBeerList;

	public UserBeerHelper(final Context context, final CustomServerCredentials customServerCredentials, final List<UserBeer> userBeerList) {
		this.context = context;
		this.customServerCredentials = customServerCredentials;
		this.userBeerList = userBeerList;
	}

	public boolean send() {
		boolean succes = false;

		try {
			if (!(customServerCredentials.getUserId() > 0)) {
				log.info("User not logged in");
				return succes;
			}

			final String serverUrl = (String) context.getText(R.string.server_url);
			final String rate = (String) context.getText(R.string.server_rate);

			// Create a new HttpClient and Post Header
			final HttpClient httpclient = new DefaultHttpClient();
			final HttpPost httppost = new HttpPost(serverUrl + rate);

			HttpResponse response = null;

			if (userBeerList.size() <= 0) {
				log.info("No userBeers to send");
				return succes;
			}

			final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			for (int i = 0; i < userBeerList.size(); i++) {
				final UserBeer userBeer = userBeerList.get(i);
				final int beerId = userBeer.getBeerId();
				nameValuePairs.add(new BasicNameValuePair("rating[" + beerId + "]", "" + userBeer.getRating()));
				nameValuePairs.add(new BasicNameValuePair("notition[" + beerId + "]", "" + userBeer.getNote()));
				nameValuePairs.add(new BasicNameValuePair("favorite[" + beerId + "]", "" + userBeer.getFavorite()));
				nameValuePairs.add(new BasicNameValuePair("wishlist[" + beerId + "]", "" + userBeer.getWishlist()));
				nameValuePairs.add(new BasicNameValuePair("hash[" + beerId + "]", ""
						+ Hash.SHA1("" + beerId + userBeer.getRating() + customServerCredentials.getServerKey())));
			}

			// Add your data
			nameValuePairs.add(new BasicNameValuePair("user", "" + customServerCredentials.getUserId()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			response = httpclient.execute(httppost);
			final int statusCode = ServerStatusCodeHelper.getStatusCode(response);

			switch (statusCode) {
			case CrispeServerCodes.SUCCES_CODE:
				succes = true;
				break;
			case CrispeServerCodes.FAIL_CODE:
				final String responseString = ServerStatusCodeHelper.parseResponse(response);
				log.info("Received CrispeServerCodes.FAIL_CODE: " + responseString);
				break;
			default:
				log.warn("Received unkown code " + response.getStatusLine().getStatusCode());
				break;
			}
		} catch (final ClientProtocolException ex) {
			log.error("postData ClientProtocolException occurred", ex);
		} catch (final IOException ex) {
			log.error("postData IOException occurred", ex);
		} catch (final NoSuchAlgorithmException ex) {
			log.error("postData NoSuchAlgorithmException occurred", ex);
		} finally {
			log.verbose("postData done");
		}

		return succes;
	}
}
