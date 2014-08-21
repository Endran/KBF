package nl.endran.showcase.database.sqllite.implementation;

import nl.endran.showcaselib.datacontainers.ShowcaseDataObject;

public class Application implements ShowcaseDataObject {

	private static final long serialVersionUID = 3952059409290263718L;

	private int id;
	private String timestamp;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final String timestamp) {
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}
}
