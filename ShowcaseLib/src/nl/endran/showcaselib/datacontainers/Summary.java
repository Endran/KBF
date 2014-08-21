package nl.endran.showcaselib.datacontainers;

public class Summary implements ShowcaseDataObject {
	private static final long serialVersionUID = -7067222315856750835L;

	private int id;
	private String beerName;
	private String beerDiscription;
	private double beerRatingAvg;
	private int beerRatingCount;
	private String beerColor;
	private String methodName;
	private String brewerName;
	private String brewerLocation;
	private String brewerUrl;
	private int festivalBeerID;
	private String stockName;
	private int festivalBeerNumber;
	private double festivalBeerPrice;
	private String paymentName;
	private String festivalBeerAward;
	private boolean festivalBeerFirstTime;
	private int beerId;
	private double beerPercentage;

	public Summary() {
	}

	/**
	 * For test only!!!! Remove in production
	 */
	public Summary(final String beerName, final String beerDiscription, final double beerRatingAvg, final int beerRatingCount, final String beerColor,
			final String methodName, final String brewerName, final String brewerLocation, final String brewerUrl, final int festivalBeerID,
			final String stockName, final int festivalBeerNumber, final double festivalBeerPrice, final String paymentName, final String festivalBeerAward,
			final boolean festivalBeerFirstTime, final int beerId, final double beerPercentage) {
		this.beerName = beerName;
		this.beerDiscription = beerDiscription;
		this.beerRatingAvg = beerRatingAvg;
		this.beerRatingCount = beerRatingCount;
		this.beerColor = beerColor;
		this.methodName = methodName;
		this.brewerName = brewerName;
		this.brewerLocation = brewerLocation;
		this.brewerUrl = brewerUrl;
		this.festivalBeerID = festivalBeerID;
		this.stockName = stockName;
		this.festivalBeerNumber = festivalBeerNumber;
		this.festivalBeerPrice = festivalBeerPrice;
		this.paymentName = paymentName;
		this.festivalBeerAward = festivalBeerAward;
		this.festivalBeerFirstTime = festivalBeerFirstTime;
		this.id = festivalBeerID;
		this.beerId = beerId;
		this.beerPercentage = beerPercentage;
	}

	public Summary(final String beerName, final String beerDiscription, final int beerRatingAvg, final int beerRatingCount, final String methodName,
			final String browerName, final String browerLocation, final String browerUrl, final int festivalBeerID, final String stockName,
			final int festivalBeerNumber, final int festivalBeerPrice, final String paymentName, final String festivalBeerAward,
			final boolean festivalBeerFirstTime, final int id, final int beerId) {
		this.beerName = beerName;
		this.beerDiscription = beerDiscription;
		this.beerRatingAvg = beerRatingAvg;
		this.beerRatingCount = beerRatingCount;
		this.methodName = methodName;
		this.brewerName = browerName;
		this.brewerLocation = browerLocation;
		this.brewerUrl = browerUrl;
		this.festivalBeerID = festivalBeerID;
		this.stockName = stockName;
		this.festivalBeerNumber = festivalBeerNumber;
		this.festivalBeerPrice = festivalBeerPrice;
		this.paymentName = paymentName;
		this.festivalBeerAward = festivalBeerAward;
		this.festivalBeerFirstTime = festivalBeerFirstTime;
		this.id = id;
		this.beerId = beerId;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getBeerName() {
		return beerName;
	}

	public void setBeerName(final String beerName) {
		this.beerName = beerName;
	}

	public String getBeerDiscription() {
		return beerDiscription;
	}

	public void setBeerDiscription(final String beerDiscription) {
		this.beerDiscription = beerDiscription;
	}

	public double getBeerRatingAvg() {
		return beerRatingAvg;
	}

	public void setBeerRatingAvg(final double beerRatingAvg) {
		this.beerRatingAvg = beerRatingAvg;
	}

	public int getBeerRatingCount() {
		return beerRatingCount;
	}

	public void setBeerRatingCount(final int beerRatingCount) {
		this.beerRatingCount = beerRatingCount;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(final String methodName) {
		this.methodName = methodName;
	}

	public String getBrewerName() {
		return brewerName;
	}

	public void setBrewerName(final String brewerName) {
		this.brewerName = brewerName;
	}

	public String getBrewerLocation() {
		return brewerLocation;
	}

	public void setBrewerLocation(final String brewerLocation) {
		this.brewerLocation = brewerLocation;
	}

	public String getBrewerUrl() {
		return brewerUrl;
	}

	public void setBrewerUrl(final String brewerUrl) {
		this.brewerUrl = brewerUrl;
	}

	public int getFestivalBeerID() {
		return festivalBeerID;
	}

	public void setFestivalBeerID(final int festivalBeerID) {
		this.festivalBeerID = festivalBeerID;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(final String stockName) {
		this.stockName = stockName;
	}

	public int getFestivalBeerNumber() {
		return festivalBeerNumber;
	}

	public void setFestivalBeerNumber(final int festivalBeerNumber) {
		this.festivalBeerNumber = festivalBeerNumber;
	}

	public double getFestivalBeerPrice() {
		return festivalBeerPrice;
	}

	public void setFestivalBeerPrice(final double festivalBeerPrice) {
		this.festivalBeerPrice = festivalBeerPrice;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(final String paymentName) {
		this.paymentName = paymentName;
	}

	public String getFestivalBeerAward() {
		return festivalBeerAward;
	}

	public void setFestivalBeerAward(final String festivalBeerAward) {
		this.festivalBeerAward = festivalBeerAward;
	}

	public boolean isFestivalBeerFirstTime() {
		return festivalBeerFirstTime;
	}

	public void setFestivalBeerFirstTime(final boolean festivalBeerFirstTime) {
		this.festivalBeerFirstTime = festivalBeerFirstTime;
	}

	public int getBeerId() {
		return beerId;
	}

	public void setBeerId(final int beerId) {
		this.beerId = beerId;
	}

	public double getBeerPercentage() {
		return beerPercentage;
	}

	public void setBeerPercentage(final double percentage) {
		this.beerPercentage = percentage;
	}

	public String getBeerColor() {
		return beerColor;
	}

	public void setBeerColor(final String beerColor) {
		this.beerColor = beerColor;
	}
}