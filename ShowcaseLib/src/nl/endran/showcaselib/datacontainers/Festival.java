package nl.endran.showcaselib.datacontainers;

public class Festival implements ShowcaseDataObject {
	private static final long serialVersionUID = -6760539360774783253L;

	private String name;
	private String date;
	private String location;
	private String description;
	private int id;

	private boolean nameSet;
	private boolean dateSet;
	private boolean locationSet;
	private boolean descriptionSet;

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}

	public String getLocation() {
		return location;
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

	public void setDate(final String date) {
		this.date = date;
		this.dateSet = true;
	}

	public void setLocation(final String location) {
		this.location = location;
		this.locationSet = true;
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

	public boolean isDateSet() {
		return dateSet;
	}

	public boolean isLocationSet() {
		return locationSet;
	}

	public boolean isDescriptionSet() {
		return descriptionSet;
	}
}
