package nl.endran.showcase.backend.crispe.extendeddatacontainers;

import java.util.ArrayList;

import nl.endran.showcaselib.datacontainers.Festival;

public class ArrayOfFestival implements IArrayOf<Festival> {
	private final ArrayList<Festival> festivalList = new ArrayList<Festival>();

	public ArrayList<Festival> getList() {
		return festivalList;
	}

	public void setFestival(final Festival festival) {
		festivalList.add(festival);
	}

}
