package nl.endran.showcase.backend.crispe.extendeddatacontainers;

import java.util.ArrayList;

import nl.endran.showcaselib.datacontainers.User;

public class ArrayOfUser implements IArrayOf<User> {
	private final ArrayList<User> userList = new ArrayList<User>();

	public ArrayList<User> getList() {
		return userList;
	}

	public void setUser(final User user) {
		userList.add(user);
	}
}
