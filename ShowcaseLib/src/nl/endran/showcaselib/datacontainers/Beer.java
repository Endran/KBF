package nl.endran.showcaselib.datacontainers;


public class Beer implements ShowcaseDataObject {
	private static final long serialVersionUID = -657620736566774232L;

	private String name;
	private int methodID;
	private double percentage;
	private double ratingAvg;
	private int ratingCount;
	private int brewerID;
	private int id;
	private String color;
	private String date;

	private boolean nameSet;
	private boolean methodIDSet;
	private boolean percentageSet;
	private boolean ratingAvgSet;
	private boolean ratingCountSet;
	private boolean brewerIDSet;
	private boolean colorSet;

	public String getName() {
		return name;
	}

	public int getMethodID() {
		return methodID;
	}

	public double getPercentage() {
		return percentage;
	}

	public double getRatingAvg() {
		return ratingAvg;
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public int getBrewerID() {
		return brewerID;
	}

	public int getId() {
		return id;
	}

	public void setName(final String name) {
		this.name = name;
		this.nameSet = true;
	}

	public void setMethodID(final int methodID) {
		this.methodID = methodID;
		this.methodIDSet = true;
	}

	public void setPercentage(final double percentage) {
		this.percentage = percentage;
		this.percentageSet = true;
	}

	public void setRatingAvg(final double ratingAvg) {
		this.ratingAvg = ratingAvg;
		this.ratingAvgSet = true;
	}

	public void setRatingCount(final int ratingCount) {
		this.ratingCount = ratingCount;
		this.ratingCountSet = true;
	}

	public void setBrewerID(final int brewerID) {
		this.brewerID = brewerID;
		this.brewerIDSet = true;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public boolean isNameSet() {
		return nameSet;
	}

	public boolean isMethodIDSet() {
		return methodIDSet;
	}

	public boolean isPercentageSet() {
		return percentageSet;
	}

	public boolean isRatingAvgSet() {
		return ratingAvgSet;
	}

	public boolean isRatingCountSet() {
		return ratingCountSet;
	}

	public boolean isBrewerIDSet() {
		return brewerIDSet;
	}

	public void setDate(final String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setColor(final String color) {
		this.color = color;
		this.colorSet = true;
	}

	public String getColor() {
		return color;
	}

	public void setColorSet(final boolean colorSet) {
		this.colorSet = colorSet;
	}

	public boolean isColorSet() {
		return colorSet;
	}
}
