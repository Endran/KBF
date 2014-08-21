package nl.endran.showcase.application.kbf;

import java.io.OutputStream;
import java.lang.Thread.UncaughtExceptionHandler;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import nl.endran.storage.Storage;
import android.content.Context;

public class KbfDefaultUncaughtExceptionHandler implements UncaughtExceptionHandler {
	ILogger log = LoggerFactory.GetLogger(this);

	public final static String crashLogFile = "CrashLog.txt";
	public final static String beginOfException = "beginOfException";
	public final static String endOfException = "endOfException";
	public final static String nextItem = "DefaultUncaughtExceptionHandlerNextItem";

	private final Context context;
	private final UncaughtExceptionHandler defaultUncaughtExceptionHandler;

	public KbfDefaultUncaughtExceptionHandler(final Context context) {
		this.context = context;
		defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	@Override
	public void uncaughtException(final Thread thread, final Throwable throwable) {

		try {
			final String threadName = (thread == null ? "thread=null" : thread.getName());
			log.error("uncaughtException in thread " + threadName, throwable);

			final String applicationName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
			final String applicationVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;

			final String appName = applicationName + "(" + applicationVersion + ")\n" + threadName;

			final String exceptionMessage = throwable.getMessage();
			String exceptionTrace = throwable.toString();
			for (final StackTraceElement stackTraceElement : throwable.getStackTrace()) {
				exceptionTrace += "\n" + stackTraceElement.toString();
			}

			final StringBuilder completeMessage = new StringBuilder();
			completeMessage.append(beginOfException).append(nextItem).append(appName).append(nextItem).append(exceptionMessage).append(nextItem)
					.append(exceptionTrace).append(nextItem).append(endOfException);
			final String message = completeMessage.toString();

			final Storage storage = new Storage(context);

			final OutputStream outputStream = storage.getFileOutputStream(crashLogFile);
			outputStream.write(message.getBytes());
			outputStream.close();
		} catch (final Exception e) {
			log.error("uncaughtException Exception generated in this handler!!", e);
		} finally {
			close(throwable);
		}
	}

	private void close(final Throwable throwable) {
		Thread.setDefaultUncaughtExceptionHandler(defaultUncaughtExceptionHandler);
		(new Thread(new Runnable() {
			@Override
			public void run() {
				throw new RuntimeException("KbfDefaultUncaughtExceptionHandler", throwable);
			}
		})).start();
	}
}
