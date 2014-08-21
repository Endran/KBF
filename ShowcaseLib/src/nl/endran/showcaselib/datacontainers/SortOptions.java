package nl.endran.showcaselib.datacontainers;

public enum SortOptions {
	NUMBER(0), ALPHABET(1), COLOR(2), MY_RATING(3), AVERAGE_RATING(4), PRICE(5), PERCENTAGE(6);

	private final int value;

	SortOptions(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static SortOptions getByValue(final int value) {
		for (final SortOptions sortOptions : SortOptions.values()) {
			if (sortOptions.value == value) {
				return sortOptions;
			}
		}
		return NUMBER;
	}
}
