package icu.baolong.social.common.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;

/**
 * 异步线程内部未捕获异常处理
 *
 * @author Silas Yan 2025-05-24 21:46
 */
@Slf4j
public class ThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

	private ThreadUncaughtExceptionHandler() {}

	private static final ThreadUncaughtExceptionHandler INSTANCE = new ThreadUncaughtExceptionHandler();

	public static ThreadUncaughtExceptionHandler getInstance() {return INSTANCE;}

	/**
	 * Method invoked when the given thread terminates due to the given uncaught exception.
	 * <p>Any exception thrown by this method will be ignored by the
	 * Java Virtual Machine.
	 *
	 * @param t the thread
	 * @param e the exception
	 */
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		log.error("Exception in thread {} ", t.getName(), e);
	}
}
