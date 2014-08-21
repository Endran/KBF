package nl.endran.showcaselib.datacontainers;


public class Method implements ShowcaseDataObject {
	private static final long serialVersionUID = 315092281390155648L;

	private int id;
	private String name;

	private boolean nameSet;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
		this.nameSet = true;
	}

	public boolean isNameSet() {
		return nameSet;
	}
}
