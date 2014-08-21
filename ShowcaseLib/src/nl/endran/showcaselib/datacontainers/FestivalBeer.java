package nl.endran.showcaselib.datacontainers;


public class FestivalBeer implements ShowcaseDataObject {
	private static final long serialVersionUID = 8232223446710880060L;

	private int festivalID;
	private int beerID;
	private int imageID;
	private int number;
	private int paymentID;
	private double price;
	private String award;
	private boolean firstTime;
	private int stockID;
	private String description;
	private int id;
	private String stockName;
	private String paymentName;
	private String imageName;
	private boolean updatedServer = true;

	private boolean festivalIDSet;
	private boolean beerIDSet;
	private boolean imageIDSet;
	private boolean numberSet;
	private boolean paymentIDSet;
	private boolean priceSet;
	private boolean awardSet;
	private boolean firstTimeSet;
	private boolean stockIDSet;
	private boolean descriptionSet;
	private boolean stockNameSet;
	private boolean paymentNameSet;
	private boolean imageNameSet;
	private boolean updatedServerSet;

	public int getFestivalID() {
		return festivalID;
	}

	public void setFestivalID(final int festivalID) {
		this.festivalID = festivalID;
		this.festivalIDSet = true;
	}

	public int getBeerID() {
		return beerID;
	}

	public void setBeerID(final int beerID) {
		this.beerID = beerID;
		this.beerIDSet = true;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
		this.numberSet = true;
	}

	public int getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(final int paymentID) {
		this.paymentID = paymentID;
		this.paymentIDSet = true;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
		this.priceSet = true;
	}

	public String getAward() {
		return award;
	}

	public void setAward(final String award) {
		this.award = award;
		this.awardSet = true;
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public void setFirstTime(final boolean firstTime) {
		this.firstTime = firstTime;
		this.firstTimeSet = true;
	}

	public int getStockID() {
		return stockID;
	}

	public void setStockID(final int stockID) {
		this.stockID = stockID;
		this.stockIDSet = true;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
		this.descriptionSet = true;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(final int imageID) {
		this.imageID = imageID;
		this.imageIDSet = true;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(final String stockName) {
		this.stockName = stockName;
		this.stockNameSet = true;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(final String paymentName) {
		this.paymentName = paymentName;
		this.paymentNameSet = true;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImage(final String imageName) {
		this.imageName = imageName;
		this.imageNameSet = true;
	}

	public boolean isFestivalIDSet() {
		return festivalIDSet;
	}

	public boolean isBeerIDSet() {
		return beerIDSet;
	}

	public boolean isImageIDSet() {
		return imageIDSet;
	}

	public boolean isNumberSet() {
		return numberSet;
	}

	public boolean isPaymentIDSet() {
		return paymentIDSet;
	}

	public boolean isPriceSet() {
		return priceSet;
	}

	public boolean isAwardSet() {
		return awardSet;
	}

	public boolean isFirstTimeSet() {
		return firstTimeSet;
	}

	public boolean isStockIDSet() {
		return stockIDSet;
	}

	public boolean isDescriptionSet() {
		return descriptionSet;
	}

	public boolean isStockNameSet() {
		return stockNameSet;
	}

	public boolean isPaymentNameSet() {
		return paymentNameSet;
	}

	public boolean isImageNameSet() {
		return imageNameSet;
	}

	public boolean isUpdatedServerSet() {
		return updatedServerSet;
	}

	public void setUpdatedServerSet(final boolean updatedServerSet) {
		this.updatedServerSet = updatedServerSet;
	}

	public boolean isUpdatedServer() {
		return updatedServer;
	}

	public void setUpdatedServer(final boolean updatedServer) {
		this.updatedServer = updatedServer;
		updatedServerSet = true;
	}
}
