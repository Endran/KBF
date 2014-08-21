package nl.endran.service;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * This is an abstract service, that can be used for all service implementation.
 * It contains some basic logging for crucial life cycle steps, and provides for
 * the local-service-binder mechanism. <br>
 * When binding to this service you can cast the binder to
 * {@link IEndranServiceBinder}, and obtain the instance of the concrete
 * implementation of this service via the <code>getService</code> method of
 * IEndranServiceBinder.
 */
public abstract class EndranService extends Service {
	protected ILogger log = LoggerFactory.GetLogger(this);

	private final IBinder localBinder = new LocalBinder();

	protected EndranService context;

	// SuppressWarnings deprecation is only needed, due to the fact we want to
	// log this life cycle step
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(final Intent intent, final int startId) {
		super.onStart(intent, startId);
		log.verbose("onStart");
	}

	// Return our custom localbinder, so the instance of the conrete class can
	// be obtained in the activity or content provider.
	@Override
	public IBinder onBind(final Intent intent) {
		log.verbose("onBind");
		return localBinder;
	}

	// Log that this method is called, and return sticky, because we want this
	// service to continue running until it is explicitly stopped
	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {
		log.verbose("onStartCommand");
		return START_STICKY;
	}

	// Save this as context for easy reference
	@Override
	public void onCreate() {
		super.onCreate();
		log.verbose("onCreate");
		context = this;
	}

	// Only log this crucial life cyvle step
	@Override
	public void onDestroy() {
		super.onDestroy();
		log.verbose("onDestroy");
	}

	/**
	 * The LocalBinder class extends binder, so that we can return is during the
	 * onBind call. The receiving end of onBind can cast it to
	 * IEndranServiceBinder, to obtain the instance of this service.
	 */
	public class LocalBinder extends Binder implements IEndranServiceBinder {
		public Service getService() {
			log.verbose("LocalBinder getService");
			return context;
		}
	}

	/**
	 * Override this in the first/main activity. Initialize the logger,
	 * otherwise the loglevel is set to VERBOSE and the logtag to
	 * UninitializedLogTag. <br>
	 * <br>
	 * Example implementation: <br>
	 * LoggerFactory.setLogLevel(LogLevel.VERBOSE); <br>
	 * LoggerFactory.setLogTag(MyLogTag); <br>
	 * LoggerFactory.setLogLevelForClass(SomeClass.class.getSimpleName(),
	 * LogLevel.ERROR); <br>
	 */
}