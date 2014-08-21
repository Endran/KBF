package nl.endran.threading;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;

public class EndranThreadDecoupling {
	private final ILogger log = LoggerFactory.GetLogger(this);
	private final long loopInMs;

	private final Thread thread;
	private boolean keepRunning;
	private final Queue<IEndranCommand> queue = new LinkedBlockingQueue<IEndranCommand>();

	public EndranThreadDecoupling(final long loopInMs) {
		this.loopInMs = loopInMs;

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				dataThread();
			}
		});
		thread.start();
	}

	private void dataThread() {
		keepRunning = true;
		while (keepRunning) {
			final IEndranCommand command = dequeue();
			if (command != null) {
				command.execute();
			} else {
				synchronized (thread) {
					try {
						if (loopInMs > 0) {
							thread.wait(loopInMs);
						} else {
							thread.wait();
						}
					} catch (final InterruptedException ex) {
						log.info("dataThread InterruptedException", ex);
					}
				}
			}
		}
		log.info("dataThread finished");
	}

	public void stop() {
		keepRunning = false;
		synchronized (thread) {
			thread.notify();
		}
	}

	protected synchronized void enqueue(final IEndranCommand command) {
		queue.add(command);
		synchronized (thread) {
			thread.notify();
		}
	}

	protected synchronized IEndranCommand dequeue() {
		if (queue.size() <= 0) {
			return null;
		}
		return queue.remove();
	}
}
