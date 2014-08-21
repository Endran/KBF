package nl.endran.share;

import android.content.Context;
import android.content.Intent;

public class Share {
	public static void SimpleIntent(Context context, String shareVia, String subject, String body) {
		if (context != null && shareVia != null && subject != null && body != null) {
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
			context.startActivity(Intent.createChooser(sharingIntent, shareVia));
		}
	}
}
