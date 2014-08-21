package nl.endran.showcaselib.datacontainers;

public class User implements ShowcaseDataObject {
	private static final long serialVersionUID = 7021239499243391104L;

	private String name;
	private String passwordHash;
	private int id;

	private boolean nameSet;
	private boolean passwordHashSet;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
		this.nameSet = true;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(final String passwordHash) {
		this.passwordHash = passwordHash;
		this.passwordHashSet = true;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public boolean isNameSet() {
		return nameSet;
	}

	public boolean isPasswordHashSet() {
		return passwordHashSet;
	}
}
