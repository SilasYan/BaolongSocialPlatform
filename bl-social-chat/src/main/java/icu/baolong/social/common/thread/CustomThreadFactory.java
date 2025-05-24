package icu.baolong.social.common.thread;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂，对原先的线程工厂做增强
 *
 * @author Silas Yan 2025-05-24 21:52
 */
public class CustomThreadFactory implements ThreadFactory {

	private static ThreadFactory threadFactory;

	public CustomThreadFactory(ThreadFactory threadFactory) {
		CustomThreadFactory.threadFactory = threadFactory;
	}

	/**
	 * Constructs a new unstarted {@code Thread} to run the given runnable.
	 *
	 * @param r a runnable to be executed by new thread instance
	 * @return constructed thread, or {@code null} if the request to create a thread is rejected
	 * @see <a href="../../lang/Thread.html#inheritance">Inheritance when
	 * creating threads</a>
	 */
	@Override
	public Thread newThread(Runnable r) {
		Thread thread = threadFactory.newThread(r);
		thread.setUncaughtExceptionHandler(ThreadUncaughtExceptionHandler.getInstance());
		return thread;
	}
}
