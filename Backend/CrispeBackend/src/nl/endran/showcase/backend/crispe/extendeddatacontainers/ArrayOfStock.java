package nl.endran.showcase.backend.crispe.extendeddatacontainers;

import java.util.ArrayList;

import nl.endran.showcaselib.datacontainers.Stock;

public class ArrayOfStock implements IArrayOf<Stock> {
	private final ArrayList<Stock> stockList = new ArrayList<Stock>();

	public ArrayList<Stock> getList() {
		return stockList;
	}

	public void setStock(final Stock stock) {
		stockList.add(stock);
	}

}
