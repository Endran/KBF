package nl.endran.showcaselib.usecases.data;

import java.util.ArrayList;
import java.util.List;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.usecases.data.GetFestivalBeerNamesAndIds.GetFestivalBeerNamesAndIdsListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;
import android.util.Pair;

public class GetFestivalBeerNamesAndIds extends BaseThreadSeparatorCommand<GetFestivalBeerNamesAndIdsListener> {

	public interface GetFestivalBeerNamesAndIdsListener extends BaseListener {

		public void onGetFestivalBeerNamesAndIds(List<Pair<String, Integer>> festivalBeerNamesAndIdsList);
	}

	List<Pair<String, Integer>> festivalBeerNamesAndIdsList = new ArrayList<Pair<String, Integer>>();

	public GetFestivalBeerNamesAndIds(final ShowcaseDatabase showcaseDatabase, final GetFestivalBeerNamesAndIdsListener listener) {
		super(showcaseDatabase, listener);
	}

	@Override
	public String execute() {

		final List<Summary> summaries = this.showcaseDatabase.getAllSummaries();
		for (final Summary summary : summaries) {
			final String name = summary.getBeerName();
			final int id = summary.getFestivalBeerID();
			this.festivalBeerNamesAndIdsList.add(new Pair<String, Integer>(name, id));
		}

		return null;
	}

	@Override
	public void informListener() {
		this.listener.onGetFestivalBeerNamesAndIds(this.festivalBeerNamesAndIdsList);
	}

}
