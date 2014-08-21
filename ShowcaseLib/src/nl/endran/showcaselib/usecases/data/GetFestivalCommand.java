package nl.endran.showcaselib.usecases.data;

import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.Festival;
import nl.endran.showcaselib.usecases.data.GetFestivalCommand.GetFestivalListener;
import nl.endran.showcaselib.util.seperation.BaseListener;
import nl.endran.showcaselib.util.seperation.BaseThreadSeparatorCommand;

public class GetFestivalCommand extends BaseThreadSeparatorCommand<GetFestivalListener> {

	public interface GetFestivalListener extends BaseListener {

		public void onFestival(Festival festival);
	}

	private final int festivalId;

	private Festival festival;

	public GetFestivalCommand(final ShowcaseDatabase showcaseDatabase, final int festivalId, final GetFestivalListener listener) {
		super(showcaseDatabase, listener);
		this.festivalId = festivalId;
	}

	@Override
	public String execute() {
		this.festival = this.showcaseDatabase.getFestival(this.festivalId);
		if (this.festival == null) {
			return "An error occurred when retrieving festival " + this.festivalId;
		}

		return null;
	}

	@Override
	public void informListener() {
		this.listener.onFestival(this.festival);
	}
}
