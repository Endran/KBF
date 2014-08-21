package nl.endran.showcaselib.datacontainers;


public class Brewer implements ShowcaseDataObject {
	private static final long serialVersionUID = -3709017551194292327L;

	private String name;
	private String location;
	private String url;
	private String description;
	private int id;

	private boolean nameSet;
	private boolean locationSet;
	private boolean urlSet;
	private boolean descriptionSet;

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public void setName(final String name) {
		this.name = name;
		this.nameSet = true;
	}

	public void setLocation(final String location) {
		this.location = location;
		this.locationSet = true;
	}

	public void setUrl(final String url) {
		this.url = url;
		this.urlSet = true;
	}

	public void setDescription(final String description) {
		this.description = description;
		this.descriptionSet = true;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public boolean isNameSet() {
		return nameSet;
	}

	public boolean isLocationSet() {
		return locationSet;
	}

	public boolean isUrlSet() {
		return urlSet;
	}

	public boolean isDescriptionSet() {
		return descriptionSet;
	}
}
