package nl.endran.showcase.backend.crispe.extendeddatacontainers;

import java.util.ArrayList;

import nl.endran.showcaselib.datacontainers.UserBeer;

public class ArrayOfUserBeer implements IArrayOf<UserBeer> {
	private final ArrayList<UserBeer> userBeerList = new ArrayList<UserBeer>();

	public ArrayList<UserBeer> getList() {
		return userBeerList;
	}

	public void setUserBeer(final UserBeer userBeer) {
		userBeerList.add(userBeer);
	}
}
