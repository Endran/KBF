package nl.endran.showcase.backend.crispe.extendeddatacontainers;

import java.util.ArrayList;

import nl.endran.showcaselib.datacontainers.Brewer;

public class ArrayOfBrewer implements IArrayOf<Brewer> {
	private final ArrayList<Brewer> brewerList = new ArrayList<Brewer>();

	public ArrayList<Brewer> getList() {
		return brewerList;
	}

	public void setBrewer(final Brewer brewer) {
		brewerList.add(brewer);
	}

}
