package nl.endran.service;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * TBD
 */
public class ServiceBinderHelper {
	protected ILogger log = LoggerFactory.GetLogger(this);

	private Activity activity = null;
	private Service service = null;
	private final Class<?> serviceClass;
	private final ServiceBinderHelperListener serviceBinderHelperListener;

	private boolean isBound;

	public interface ServiceBinderHelperListener {
		public void connected(Service service);

		public void disconnected();
	}

	public ServiceBinderHelper(final Activity activity, final Class<?> serviceClass,
			final ServiceBinderHelperListener serviceBinderHelperListener) {
		this.activity = activity;
		this.serviceClass = serviceClass;
		this.serviceBinderHelperListener = serviceBinderHelperListener;
	}

	private final ServiceConnection serviceConnection = new ServiceConnection() {
		public void onServiceConnected(final ComponentName className, final IBinder binder) {
			log.verbose("ServiceConnection onServiceConnected");
			service = ((IEndranServiceBinder) binder).getService();
			log.info("Setting service connected");

			if (service != null) {
				serviceBinderHelperListener.connected(service);
			}
		}

		public void onServiceDisconnected(final ComponentName className) {
			service = null;
			log.info("Setting service disconnected");
			serviceBinderHelperListener.disconnected();
		}
	};

	public boolean bind() {
		log.verbose("bind");
		if (serviceBinderHelperListener == null) {
			log.verbose("serviceBinderHelperListener == null");
			return false;
		}

		isBound = activity.bindService(new Intent(activity, serviceClass), serviceConnection, Context.BIND_AUTO_CREATE);
		return isBound;
	}

	public void unbind() {
		log.verbose("unbind");
		if (service != null && isBound) {
			activity.unbindService(serviceConnection);
			isBound = false;
		}
	}
}
