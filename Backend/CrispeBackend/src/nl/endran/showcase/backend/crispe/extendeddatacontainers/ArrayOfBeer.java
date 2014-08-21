package nl.endran.showcase.backend.crispe.extendeddatacontainers;

import java.util.ArrayList;

import nl.endran.showcaselib.datacontainers.Beer;

public class ArrayOfBeer implements IArrayOf<Beer> {
	private final ArrayList<Beer> beerList = new ArrayList<Beer>();

	public ArrayList<Beer> getList() {
		return beerList;
	}

	public void setBeer(final Beer beer) {
		beerList.add(beer);
	}

}
