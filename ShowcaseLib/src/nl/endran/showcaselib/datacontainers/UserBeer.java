package nl.endran.showcaselib.datacontainers;

public class UserBeer implements ShowcaseDataObject {
	private static final long serialVersionUID = 788415134033803109L;

	private int id;
	private int userId;
	private int beerId;
	private int rating;
	private boolean favorite;
	private boolean wishlist;
	private String note;
	private boolean updatedServer = true;
	private String userName;
	private String date;

	private boolean ratingSet;
	private boolean favoriteSet;
	private boolean wishlistSet;
	private boolean noteSet;
	private boolean updatedServerSet;
	private boolean userNameSet;
	private boolean dateSet;

	public int getUserId() {
		return userId;
	}

	public void setUserId(final int userId) {
		this.userId = userId;
	}

	public int getBeerId() {
		return beerId;
	}

	public void setBeerId(final int beerId) {
		this.beerId = beerId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(final int rating) {
		this.rating = rating;
		this.ratingSet = true;
	}

	public boolean getFavorite() {
		return favorite;
	}

	public void setFavorite(final boolean favorite) {
		this.favorite = favorite;
		this.favoriteSet = true;
	}

	public boolean getWishlist() {
		return wishlist;
	}

	public void setWishlist(final boolean wishlist) {
		this.wishlist = wishlist;
		this.wishlistSet = true;
	}

	public String getNote() {
		return note;
	}

	public void setNote(final String note) {
		this.note = note;
		this.noteSet = true;
	}

	public boolean isUpdatedServer() {
		return updatedServer;
	}

	public void setUpdatedServer(final boolean updatedServer) {
		this.updatedServer = updatedServer;
		this.updatedServerSet = true;
	}

	public boolean isRatingSet() {
		return ratingSet;
	}

	public boolean isFavoriteSet() {
		return favoriteSet;
	}

	public boolean isWishlistSet() {
		return wishlistSet;
	}

	public boolean isNoteSet() {
		return noteSet;
	}

	public boolean isUpdatedServerSet() {
		return updatedServerSet;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
		userNameSet = true;
	}

	public boolean isUserNameSet() {
		return userNameSet;
	}

	public void setUserNameSet(final boolean userNameSet) {
		this.userNameSet = userNameSet;
	}

	public void setDate(final String date) {
		this.date = date;
		dateSet = true;
	}

	public String getDate() {
		return date;
	}

	public boolean isDateSet() {
		return dateSet;
	}

	public void setDateSet(final boolean dateSet) {
		this.dateSet = dateSet;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}
}
