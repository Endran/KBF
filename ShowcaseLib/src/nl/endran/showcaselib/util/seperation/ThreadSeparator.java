package nl.endran.showcaselib.util.seperation;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import android.os.Handler;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;

public class ThreadSeparator {
	final private ILogger log = LoggerFactory.GetLogger(this);

	private final Handler handler;
	private final Thread seperatedThread;
	private final Queue<ThreadSeparatorCommand> queue = new LinkedBlockingQueue<ThreadSeparatorCommand>();

	private boolean keepRunning = false;

	public ThreadSeparator() {
		handler = new Handler();
		seperatedThread = new Thread(new Runnable() {
			public void run() {
				mainLoop();
			}
		});
		seperatedThread.start();
	}

	private void mainLoop() {
		keepRunning = true;
		while (keepRunning) {
			try {
				final ThreadSeparatorCommand command = dequeue();
				if (command != null) {
					final String errorMessage = command.execute();
					handler.post(new Runnable() {
						public void run() {
							if (errorMessage == null) {
								command.informListener();
							} else {
								command.failed(errorMessage);
							}
						}
					});
				} else {
					synchronized (seperatedThread) {
						try {
							seperatedThread.wait();
						} catch (final InterruptedException ex) {
							log.info("seperatedThread InterruptedException", ex);
						}
					}
				}
			} catch (final Exception ex) {
				log.error("seperatedThread unexcpteced exception", ex);
			}
		}
		log.info("seperatedThread mainLoop finished");
	}

	/**
	 * Stop the separate thread. All commands in the queue are cleared.
	 */
	public synchronized void stop() {
		keepRunning = false;
		queue.clear();
		seperatedThread.interrupt();
	}

	/**
	 * Add a command to be executed on a seperate thread.
	 * 
	 * @param command
	 */
	public synchronized void enqueue(final ThreadSeparatorCommand command) {
		queue.add(command);
		synchronized (seperatedThread) {
			seperatedThread.notify();
		}
	}

	private synchronized ThreadSeparatorCommand dequeue() {
		if (queue.size() <= 0) {
			return null;
		}
		return queue.remove();
	}
}
