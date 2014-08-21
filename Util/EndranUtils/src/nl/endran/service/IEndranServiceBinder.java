package nl.endran.service;

import android.app.Service;

/**
 * This interface only has one method; getService. This method returns an
 * abstract service object that can be cast to the concrete implementation of
 * the service.
 */
public interface IEndranServiceBinder {
	/**
	 * Returns an abstract service object that should be cast to the concrete
	 * implementation of the service.
	 */
	public Service getService();
}
