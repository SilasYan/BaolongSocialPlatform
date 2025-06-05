package icu.baolong.social.function.transaction;

/**
 * 安全执行持有对象
 *
 * @author Silas Yan 2025-06-04 21:37
 */
public class SecureInvokeHolder {

	private static final ThreadLocal<Boolean> INVOKE_THREAD_LOCAL = new ThreadLocal<>();

	public static void set() {
		INVOKE_THREAD_LOCAL.set(Boolean.TRUE);
	}

	public static void del() {
		INVOKE_THREAD_LOCAL.remove();
	}

	public static boolean get() {
		return INVOKE_THREAD_LOCAL.get() != null;
	}
}
