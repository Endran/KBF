package nl.endran.showcase.backend.crispe.extendeddatacontainers;

import java.util.ArrayList;

import nl.endran.showcaselib.datacontainers.Payment;

public class ArrayOfPayment implements IArrayOf<Payment> {
	private final ArrayList<Payment> paymentList = new ArrayList<Payment>();

	public ArrayList<Payment> getList() {
		return paymentList;
	}

	public void setPayment(final Payment payment) {
		paymentList.add(payment);
	}

}
