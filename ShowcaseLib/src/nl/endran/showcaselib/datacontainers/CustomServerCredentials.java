package nl.endran.showcaselib.datacontainers;

public class CustomServerCredentials implements ShowcaseDataObject {
	private static final long serialVersionUID = -1506688657121138868L;

	private int id;
	private boolean activated;
	private int userId;
	private String serverKey;
	public String email;
	public String userName;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(final int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(final int userId) {
		this.userId = userId;
	}

	public String getServerKey() {
		return serverKey;
	}

	public void setServerKey(final String serverKey) {
		this.serverKey = serverKey;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(final boolean activated) {
		this.activated = activated;
	}
}
