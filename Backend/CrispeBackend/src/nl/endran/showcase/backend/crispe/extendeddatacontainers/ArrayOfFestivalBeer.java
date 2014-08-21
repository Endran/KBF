package nl.endran.showcase.backend.crispe.extendeddatacontainers;

import java.util.ArrayList;

import nl.endran.showcaselib.datacontainers.FestivalBeer;

public class ArrayOfFestivalBeer implements IArrayOf<FestivalBeer> {
	private final ArrayList<FestivalBeer> festivalBeerList = new ArrayList<FestivalBeer>();

	public ArrayList<FestivalBeer> getList() {
		return festivalBeerList;
	}

	public void setFestivalBeer(final FestivalBeer festivalBeer) {
		festivalBeerList.add(festivalBeer);
	}
}
