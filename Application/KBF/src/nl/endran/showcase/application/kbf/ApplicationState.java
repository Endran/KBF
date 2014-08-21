package nl.endran.showcase.application.kbf;

public class ApplicationState {
	public static int userId = -1;
	public static boolean activated;

	public static boolean isLoggedIn() {
		return userId >= 0;
	}

	public static boolean isActivated() {
		return activated;
	}
}
