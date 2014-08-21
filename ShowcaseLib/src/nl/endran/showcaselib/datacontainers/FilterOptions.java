package nl.endran.showcaselib.datacontainers;

public enum FilterOptions {
	NO_FILTER(0), RATED(1), NOT_RATED(2), FESTIVAL_FIRST(3), EXCLUSIVE(4);

	private final int value;

	FilterOptions(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static FilterOptions getByValue(final int value) {
		for (final FilterOptions filterOptions : FilterOptions.values()) {
			if (filterOptions.value == value) {
				return filterOptions;
			}
		}
		return NO_FILTER;
	}
}
